package com.seojunpark.android.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.seojunpark.android.R
import com.seojunpark.android.databinding.ActivityDetailBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater).apply {
            setContentView(root)
            lifecycleOwner = this@DetailActivity
        }

        val url = intent.getStringExtra("url")
        val title = intent.getStringExtra("title")
        val point = intent.getIntExtra("point", 0)
        val content = intent.getStringExtra("content")
        val completed = intent.getBooleanExtra("completed", true)

        with(binding) {
            Glide.with(this@DetailActivity).load(url).centerCrop()
                .into(image)

            goBack.setOnClickListener {
                finish()
            }

            this.title.text = title

            this.point.text = point.toString()

            this.content.text = content

            if (completed) {
                btn.setBackgroundResource(R.drawable.btn_background)
                btn.text = "신청하기"
            } else {
                btn.setBackgroundResource(R.drawable.btn2_background)
                btn.text = "마감"
            }

            btn.setOnClickListener {

            }
        }
    }
}