package com.seojunpark.android.presentation.ui

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.seojunpark.android.R
import com.seojunpark.android.databinding.ActivityMainBinding
import com.seojunpark.android.presentation.adapter.MainRecyclerAdapter
import com.seojunpark.android.presentation.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private lateinit var sharedPreferences: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater).apply {
            setContentView(root)
            lifecycleOwner = this@MainActivity
            sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE)
        }

        val accessToken = sharedPreferences.getString("accessToken", "")

        if (!accessToken.isNullOrBlank()) {
            viewModel.loadList(accessToken)
        }

        with(binding) {
            goWrite.setOnClickListener {
                startActivity(Intent(this@MainActivity, WriteActivity::class.java))
                overridePendingTransition(0, 0)
            }

            goProfile.setOnClickListener {
                startActivity(Intent(this@MainActivity, ProfileActivity::class.java))
                overridePendingTransition(0, 0)
            }
        }

        lifecycleScope.launch {
            viewModel.listResponse
                .flowWithLifecycle(lifecycle, Lifecycle.State.RESUMED)
                .collectLatest { list ->
                    binding.apply {
                        val adapter = MainRecyclerAdapter(
                            Glide.with(this@MainActivity)
                        )
                        adapter.submitList(list?.boardList)
                        recycler.adapter = adapter
                    }
                }
        }
    }
}