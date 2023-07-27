package com.seojunpark.android.presentation.ui

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isGone
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.seojunpark.android.R
import com.seojunpark.android.databinding.ActivityDetailBinding
import com.seojunpark.android.presentation.viewmodel.DetailViewModel
import com.seojunpark.android.presentation.viewmodel.LoginViewModel
import com.seojunpark.android.presentation.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale


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

        val activity = intent.getStringExtra("activity")
        val id = intent.getLongExtra("id", 0)
        val url = intent.getStringExtra("url")
        val title = intent.getStringExtra("title")
        val point = intent.getIntExtra("point", 0)
        val content = intent.getStringExtra("content")
        val myListIngType = intent.getStringExtra("myListIngType")

        with(binding) {
            Glide.with(this@DetailActivity).load(url).centerCrop()
                .into(image)

            this.title.text = title

            this.point.text = NumberFormat.getInstance(Locale.KOREA).format(point) + " point"

            this.content.text = content

            when(activity) {
                "Main" -> {
                    if (myListIngType == "NO") {
                        btn.setBackgroundResource(R.drawable.btn_background)
                        btn.text = "신청하기"
                    } else {
                        btn.setBackgroundResource(R.drawable.btn2_background)
                        btn.text = "마감"
                    }
                    goBack.setOnClickListener {
                        startActivity(Intent(this@DetailActivity, MainActivity::class.java))
                        finish()
                    }
                    btn.setOnClickListener {
                        if (myListIngType == "NO") {
                            if (!accessToken.isNullOrBlank()) {
                                viewModel.request(accessToken, id)

                                btn.setBackgroundResource(R.drawable.btn2_background)
                                btn.text = "마감"
                            }
                        }
                    }
                }
                "WriteList" -> {
                    val myListIngType2 = intent.getStringExtra("myListIngType2")
                    when (myListIngType2) {
                        "NO" -> {
                            btn.visibility = View.GONE
                        }
                        "PROCEED" -> {
                            btn.setBackgroundResource(R.drawable.btn_background)
                            btn.text = "수락"
                        }
                        "COMPLETED" -> {
                            btn.setBackgroundResource(R.drawable.btn2_background)
                            btn.text = "완료"
                        }
                    }
                    goBack.setOnClickListener {
                        finish()
                    }
                    btn.setOnClickListener {
                        if (myListIngType2 == "PROCEED") {
                            if (!accessToken.isNullOrBlank()) {
                                viewModel.check(accessToken, id)

                                btn.setBackgroundResource(R.drawable.btn2_background)
                                btn.text = "완료"
                            }
                        }
                    }
                }
                "RequestList" -> {
                    val ingType = intent.getStringExtra("ingType")
                    if (ingType == "COMPLETED") {
                        btn.setBackgroundResource(R.drawable.btn2_background)
                        btn.text = "완료"
                    } else {
                        btn.setBackgroundResource(R.drawable.btn_background)
                        btn.text = "진행"
                    }
                    goBack.setOnClickListener {
                        finish()
                    }
                }
            }
        }

        lifecycleScope.launch {
            viewModel.doneEvent.observe(this@DetailActivity) {
                Toast.makeText(this@DetailActivity, it.second, Toast.LENGTH_SHORT).show()
                if (it.first) {
                    Log.d("success", "성공")
                }
            }
        }
    }
}