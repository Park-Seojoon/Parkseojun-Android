package com.seojunpark.android.presentation.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.seojunpark.android.R
import com.seojunpark.android.databinding.ActivityLoginBinding
import com.seojunpark.android.presentation.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater).apply {
            setContentView(root)
            lifecycleOwner = this@LoginActivity
            viewModel = this@LoginActivity.viewModel
        }

        with(binding) {
            btnLogin.setOnClickListener {
                viewModel?.login()
            }

            goSingUp.setOnClickListener {
                startActivity(Intent(this@LoginActivity, SignUpActivity::class.java))
                overridePendingTransition(0, 0)
            }
        }

        lifecycleScope.launch {
            viewModel.doneEvent.observe(this@LoginActivity) {
                Toast.makeText(this@LoginActivity, it.second, Toast.LENGTH_SHORT).show()
                if (it.first) {
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                }
            }
        }
    }
}