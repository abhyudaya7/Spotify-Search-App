package com.example.spotifysearchapp.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.spotifysearchapp.R
import com.example.spotifysearchapp.data.models.Items
import com.example.spotifysearchapp.databinding.RvItemBinding

class RvAdapter(
    private val context: Context,
    private val list: List<Items>,
    private val onClick: (Items) -> Unit
) : RecyclerView.Adapter<RvAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RvItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        list[position].let { item ->
            try {
                Glide
                    .with(context)
                    .load(item.images.last().url)
                    .centerCrop()
                    .error(ContextCompat.getDrawable(context, R.drawable.image_placeholder))
                    .into(holder.itemImage)
            } catch (e: Exception) {
                Log.d("LoadingException", e.stackTrace.toString())
            }

            holder.itemText.text = item.name ?: "Name"

            holder.itemRoot.setOnClickListener {
                onClick(item)
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(binding: RvItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val itemImage = binding.ivItemImage
        val itemText = binding.tvItemName
        val itemRoot = binding.itemRoot
    }
}