package com.example.spotifysearchapp.ui.view

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.spotifysearchapp.ui.viewmodel.MainViewModel
import com.example.spotifysearchapp.R
import com.example.spotifysearchapp.data.models.Items
import com.example.spotifysearchapp.databinding.FragmentDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding
    private val viewModel: MainViewModel by viewModels(ownerProducer = { requireActivity() })
    private lateinit var item: Items

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(layoutInflater)
        val item = viewModel.selectedItem
        if (item != null) {
            this.item = item
        } else {
            showAlert()
        }
        initUI()
        return binding.root
    }

    private fun initUI() {

        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            title = "Details"
            setDisplayHomeAsUpEnabled(true)
        }

        (requireActivity() as MainActivity).toolbar?.apply {
            setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }

        try {
            Glide
                .with(requireContext())
                .load(item.images.first().url)
                .centerCrop()
                .error(ContextCompat.getDrawable(requireContext(), R.drawable.image_placeholder))
                .into(binding.ivIcon)
        } catch (e: Exception) {
            binding.ivIcon.background = ContextCompat.getDrawable(requireContext(),
                R.drawable.image_placeholder
            )
        }

        if (!item.name.isNullOrEmpty()) {
            binding.tvName.text = item.name
        } else {
            binding.tvName.visibility = View.GONE
        }

        if (!item.description.isNullOrEmpty()) {
            binding.tvDescription.text = item.description
        } else {
            binding.tvDescription.visibility = View.GONE
        }

        if (!item.type.isNullOrEmpty()) {
            binding.tvType.text = item.type
        } else {
            binding.tvType.visibility = View.GONE
        }
    }

    private fun showAlert() {
        AlertDialog.Builder(requireContext()).apply {
            setTitle(getString(R.string.error_header))
            setMessage("Some error occurred. Please go back and try again")
            setPositiveButton("OK"
            ) { dialog, _ ->
                findNavController().popBackStack()
                dialog?.dismiss()
            }
        }
    }

}