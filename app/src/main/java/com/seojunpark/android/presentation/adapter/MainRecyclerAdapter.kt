package com.seojunpark.android.presentation.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.seojunpark.android.data.dto.response.DetailResponse
import com.seojunpark.android.data.dto.response.MainDTO
import com.seojunpark.android.databinding.RecyclerItemBinding
import com.seojunpark.android.presentation.ui.DetailActivity
import com.seojunpark.android.presentation.ui.MainActivity
import com.seojunpark.android.presentation.viewmodel.MainViewModel
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale

class MainRecyclerAdapter(
    private val glide: RequestManager,
    private val viewModel: MainViewModel,
    private val accessToken: String,
    private val activity: MainActivity
) : ListAdapter<MainDTO, MainRecyclerAdapter.ViewHolder>(DiffCallback<MainDTO>()) {

    var detailList: DetailResponse? = null

    inner class ViewHolder(val binding: RecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val image: ImageView
        val title: TextView
        val point: TextView

        init {
            image = binding.image
            title = binding.title
            point = binding.point

            binding.root.setOnClickListener {

                val position = adapterPosition

                val list = getItem(position)

                viewModel.loadDetailList(accessToken, list.id)

                activity.lifecycleScope.launch {
                    viewModel.detailList.collect {
                        if (it != null) {
                            detailList = it

                            Log.d("detailList", detailList.toString())

                            if (detailList != null) {
                                if (list.id == detailList!!.id) {
                                    val intent = Intent(activity, DetailActivity::class.java)
                                    intent.putExtra("activity", "Main")
                                    intent.putExtra("id", detailList!!.id)
                                    intent.putExtra("url", detailList!!.url)
                                    intent.putExtra("title", detailList!!.title)
                                    intent.putExtra("point", detailList!!.point)
                                    intent.putExtra("content", detailList!!.content)
                                    intent.putExtra("myListIngType", detailList!!.myListIngType)
                                    activity.startActivity(intent)
                                    activity.overridePendingTransition(0, 0)
                                }
                            } else {
                                activity.startActivity(Intent(activity, MainActivity::class.java))
                            }
                        }
                    }
                }
            }
        }

        fun bind(list: MainDTO) {
            glide.load(list.url)
                .transform(CenterCrop(), RoundedCorners(30))
                .into(image)
            title.text = list.title
            point.text = NumberFormat.getInstance(Locale.KOREA).format(list.point) + " point"
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