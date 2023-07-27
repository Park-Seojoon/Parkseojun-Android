package com.seojunpark.android.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.seojunpark.android.data.dto.MainDTO
import com.seojunpark.android.data.dto.MainResponse
import com.seojunpark.android.databinding.RecyclerItemBinding

class MainRecyclerAdapter(
    private val glide: RequestManager
) : ListAdapter<MainDTO, MainRecyclerAdapter.ViewHolder>(DiffCallback<MainDTO>()) {

    inner class ViewHolder(val binding: RecyclerItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val image: ImageView
        val title: TextView
        val point: TextView

        init {
            image = binding.image
            title = binding.title
            point = binding.point
        }

        fun bind(list: MainDTO) {
            glide.load(list.url).centerCrop().into(image)
            title.text = list.title
            point.text = list.point.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RecyclerItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val list = getItem(position)
        holder.bind(list)
    }
}