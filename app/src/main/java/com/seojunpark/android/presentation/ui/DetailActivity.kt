package com.seojunpark.android.presentation.ui

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.seojunpark.android.R
import com.seojunpark.android.databinding.ActivityDetailBinding
import com.seojunpark.android.presentation.viewmodel.DetailViewModel
import com.seojunpark.android.presentation.viewmodel.LoginViewModel
import com.seojunpark.android.presentation.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    lateinit var binding: ActivityDetailBinding
    private val viewModel: DetailViewModel by viewModels()
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater).apply {
            setContentView(root)
            lifecycleOwner = this@DetailActivity
            sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE)
        }

        val accessToken = sharedPreferences.getString("accessToken", "")

        val id = intent.getLongExtra("id", 0)
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
                btn.setBackgroundResource(R.drawable.btn2_background)
                btn.text = "마감"
            } else {
                btn.setBackgroundResource(R.drawable.btn_background)
                btn.text = "신청하기"
            }

            btn.setOnClickListener {
                if (!completed) {
                    if (!accessToken.isNullOrBlank()) {
                        viewModel.request(accessToken, id)

                        btn.setBackgroundResource(R.drawable.btn2_background)
                        btn.text = "마감"
                    }
                }
            }
        }
    }
}