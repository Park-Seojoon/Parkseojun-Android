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
import com.seojunpark.android.databinding.ActivityWriteBinding
import com.seojunpark.android.databinding.ActivityWriteListBinding
import com.seojunpark.android.presentation.adapter.MainRecyclerAdapter
import com.seojunpark.android.presentation.adapter.WriteListRecyclerAdapter
import com.seojunpark.android.presentation.viewmodel.WriteListViewModel
import com.seojunpark.android.presentation.viewmodel.WriteViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WriteListActivity : AppCompatActivity() {

    lateinit var binding: ActivityWriteListBinding
    private val viewModel: WriteListViewModel by viewModels()
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWriteListBinding.inflate(layoutInflater).apply {
            setContentView(root)
            lifecycleOwner = this@WriteListActivity
            sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE)
        }

        val accessToken = sharedPreferences.getString("accessToken", "")

        if (!accessToken.isNullOrBlank()) {
            viewModel.writeList(accessToken)
        }

        binding.goBack.setOnClickListener {
            finish()
        }

        lifecycleScope.launch {
            viewModel.writeListResponse
                .flowWithLifecycle(lifecycle, Lifecycle.State.RESUMED)
                .collectLatest { list ->
                    binding.apply {
                        val adapter = WriteListRecyclerAdapter(
                            Glide.with(this@WriteListActivity),
                            viewModel,
                            accessToken!!,
                            this@WriteListActivity
                        )
                        adapter.submitList(list?.boardList)
                        recycler.adapter = adapter
                    }
                }
        }
    }
}