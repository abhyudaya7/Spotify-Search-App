package com.example.spotifysearchapp.ui.view

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.spotifysearchapp.ui.viewmodel.MainViewModel
import com.example.spotifysearchapp.R
import com.example.spotifysearchapp.ui.UiLoadingState
import com.example.spotifysearchapp.data.models.SpotifySearchData
import com.example.spotifysearchapp.databinding.FragmentHomeBinding
import com.example.spotifysearchapp.ui.adapter.RvAdapter
import com.example.spotifysearchapp.utils.AppUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: MainViewModel by viewModels(ownerProducer = { requireActivity() })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)
        initUI()
        return binding.root
    }

    private fun initUI() {

        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            title = "Spotify Search App"
            setDisplayHomeAsUpEnabled(false)
        }

        binding.etSearchText.addTextChangedListener {
            if (binding.tvSearchError.visibility == View.VISIBLE)
                binding.tvSearchError.visibility = View.GONE
        }

        binding.btnSearch.setOnClickListener {
            if (binding.etSearchText.text.isNotEmpty()) {
                binding.searchResults.visibility = View.GONE
                binding.tvSearchSomething.text = getString(R.string.fetching_data)
                binding.tvSearchSomething.visibility = View.VISIBLE
                val inputMethodManager =
                    requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                inputMethodManager?.hideSoftInputFromWindow(it.windowToken, 0)
                collectSearchResults(viewModel.getSearchResults(binding.etSearchText.text.toString()))
            } else {
                binding.tvSearchError.visibility = View.VISIBLE
            }
        }

        binding.ivDownArrowAlbums.setOnClickListener {
            if (binding.rvAlbums.visibility == View.GONE) {
                binding.rvAlbums.visibility = View.VISIBLE
            } else {
                binding.rvAlbums.visibility = View.GONE
            }
        }

        binding.ivDownArrowArtists.setOnClickListener {
            if (binding.rvArtists.visibility == View.GONE) {
                binding.rvArtists.visibility = View.VISIBLE
            } else {
                binding.rvArtists.visibility = View.GONE
            }
        }

        binding.ivDownArrowPlaylists.setOnClickListener {
            if (binding.rvPlaylists.visibility == View.GONE) {
                binding.rvPlaylists.visibility = View.VISIBLE
            } else {
                binding.rvPlaylists.visibility = View.GONE
            }
        }


    }

    private fun showAlertDialogOnError() {
        AlertDialog.Builder(requireContext()).apply {
            setTitle(getString(R.string.error_header))
            setMessage(getString(R.string.fetching_error))
            setPositiveButton(
                "OK"
            ) { dialog, _ -> dialog?.dismiss() }
            show()
        }
    }

    private fun handleSearchResults(result: SpotifySearchData) {

        binding.tvSearchSomething.visibility = View.GONE

        if (AppUtils.checkIfSearchItemValid(result.albums)) {
            binding.cvAlbums.visibility = View.VISIBLE
            val albumsList = AppUtils.filterNonNullList(result.albums!!.items)
            Log.d("SpotifyApp", "List size: ${albumsList.size}")
            binding.rvAlbums.layoutManager = LinearLayoutManager(requireContext())
            binding.rvAlbums.adapter = RvAdapter(requireContext(), albumsList) {
                viewModel.selectedItem = it
                findNavController().navigate(R.id.action_homeFragment_to_detailsFragment)
            }
        }

        if (AppUtils.checkIfSearchItemValid(result.artists)) {
            binding.cvArtists.visibility = View.VISIBLE
            val artistsList = AppUtils.filterNonNullList(result.artists!!.items)
            binding.rvArtists.layoutManager = LinearLayoutManager(requireContext())
            binding.rvArtists.adapter = RvAdapter(requireContext(), artistsList) {
                viewModel.selectedItem = it
                findNavController().navigate(R.id.action_homeFragment_to_detailsFragment)
            }
        }

        if (AppUtils.checkIfSearchItemValid(result.playlists)) {
            binding.cvPlaylists.visibility = View.VISIBLE
            val playlistsList = AppUtils.filterNonNullList(result.playlists!!.items)
            binding.rvPlaylists.layoutManager = LinearLayoutManager(requireContext())
            binding.rvPlaylists.adapter = RvAdapter(requireContext(), playlistsList) {
                viewModel.selectedItem = it
                findNavController().navigate(R.id.action_homeFragment_to_detailsFragment)
            }
        }

    }

    private fun collectSearchResults(flow: StateFlow<UiLoadingState<SpotifySearchData>>) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                flow.collect {
                    when (it) {
                        is UiLoadingState.LoadingStarted -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }

                        is UiLoadingState.LoadingSuccess -> {
                            binding.progressBar.visibility = View.GONE
                            handleSearchResults(it.result)
                        }

                        is UiLoadingState.LoadingError -> {
                            binding.progressBar.visibility = View.GONE
                            showAlertDialogOnError()
                        }
                    }
                }
            }
        }
    }

}