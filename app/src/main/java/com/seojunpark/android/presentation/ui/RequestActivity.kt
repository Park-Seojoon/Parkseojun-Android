package com.seojunpark.android.presentation.ui

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.seojunpark.android.R
import com.seojunpark.android.databinding.ActivityRequestBinding
import com.seojunpark.android.databinding.ActivityWriteListBinding
import com.seojunpark.android.presentation.adapter.RequestListRecyclerAdapter
import com.seojunpark.android.presentation.adapter.WriteListRecyclerAdapter
import com.seojunpark.android.presentation.viewmodel.RequestViewModel
import com.seojunpark.android.presentation.viewmodel.WriteListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RequestActivity : AppCompatActivity() {

    lateinit var binding: ActivityRequestBinding
    private val viewModel: RequestViewModel by viewModels()
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRequestBinding.inflate(layoutInflater).apply {
            setContentView(root)
            lifecycleOwner = this@RequestActivity
            sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE)
        }

        val accessToken = sharedPreferences.getString("accessToken", "")

        if (!accessToken.isNullOrBlank()) {
            viewModel.requestList(accessToken)
        }

        binding.goBack.setOnClickListener {
            finish()
        }

        lifecycleScope.launch {
            viewModel.requestListResponse
                .flowWithLifecycle(lifecycle, Lifecycle.State.RESUMED)
                .collectLatest { list ->
                    binding.apply {
                        val adapter = RequestListRecyclerAdapter(
                            Glide.with(this@RequestActivity),
                            viewModel,
                            accessToken!!,
                            this@RequestActivity
                        )
                        adapter.submitList(list?.boardList)
                        recycler.adapter = adapter
                    }
                }
        }
    }
}