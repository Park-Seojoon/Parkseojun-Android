package com.seojunpark.android.presentation.ui

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.seojunpark.android.R
import com.seojunpark.android.databinding.ActivityProfileBinding
import com.seojunpark.android.presentation.viewmodel.LoginViewModel
import com.seojunpark.android.presentation.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale

@AndroidEntryPoint
class ProfileActivity : AppCompatActivity() {

    lateinit var binding: ActivityProfileBinding
    private val viewModel: ProfileViewModel by viewModels()
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater).apply {
            setContentView(root)
            lifecycleOwner = this@ProfileActivity
            sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE)
        }

        val accessToken = sharedPreferences.getString("accessToken", "")

        if (!accessToken.isNullOrBlank()) {
            viewModel.userInfo(accessToken)
        }

        lifecycleScope.launch {
            viewModel.userInfo
                .flowWithLifecycle(lifecycle, Lifecycle.State.RESUMED)
                .collectLatest {
                    if (it != null) {
                        binding.apply {
                            name.text = it.name
                            point.text = NumberFormat.getInstance(Locale.KOREA).format(it.point)
                        }
                    }
                }
        }

        with(binding) {
            goHome.setOnClickListener {
                startActivity(Intent(this@ProfileActivity, MainActivity::class.java))
                overridePendingTransition(0, 0)
            }

            goWrite.setOnClickListener {
                startActivity(Intent(this@ProfileActivity, WriteActivity::class.java))
                overridePendingTransition(0, 0)
            }

            btnCharging.setOnClickListener {
                Toast.makeText(this@ProfileActivity, "서비스 준비중입니다.", Toast.LENGTH_SHORT).show()
            }

            btnTakeout.setOnClickListener {
                Toast.makeText(this@ProfileActivity, "서비스 준비중입니다.", Toast.LENGTH_SHORT).show()
            }

            goWriteList.setOnClickListener {
                startActivity(Intent(this@ProfileActivity, WriteListActivity::class.java))
                overridePendingTransition(0, 0)
            }

            goRequest.setOnClickListener {
                startActivity(Intent(this@ProfileActivity, RequestActivity::class.java))
                overridePendingTransition(0, 0)
            }
        }
    }
}