package com.seojunpark.android.presentation.ui

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.seojunpark.android.R
import com.seojunpark.android.databinding.ActivityLoginBinding
import com.seojunpark.android.presentation.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()
    private lateinit var sharedPreferences: SharedPreferences
    var passwordStatus: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater).apply {
            setContentView(root)
            lifecycleOwner = this@LoginActivity
            viewModel = this@LoginActivity.viewModel
            sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE)
        }

        with(binding) {
            btnLogin.setOnClickListener {
                viewModel?.login()
            }

            pwShowHide.setOnClickListener {
                passwordStatus = !passwordStatus

                if (passwordStatus) {
                    inputPw.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                    pwShowHide.setBackgroundResource(R.drawable.baseline_eye_on)
                } else {
                    inputPw.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD or InputType.TYPE_CLASS_TEXT
                    pwShowHide.setBackgroundResource(R.drawable.baseline_eye_off)
                }
            }

            goSingUp.setOnClickListener {
                startActivity(Intent(this@LoginActivity, SignUpActivity::class.java))
                overridePendingTransition(0, 0)
            }
        }

        lifecycleScope.launch {
            viewModel.loginInfo
                .flowWithLifecycle(lifecycle, Lifecycle.State.RESUMED)
                .collectLatest {
                    val accessToken = viewModel.loginInfo.value?.accessToken
                    if (accessToken != null) {
                        val editor = sharedPreferences.edit()
                        editor.putString("accessToken", accessToken)
                        editor.apply()
                    }
                }
        }

        observeEvent()
    }

    private fun observeEvent() {
        viewModel.doneEvent.observe(this) {
            Toast.makeText(this, it.second, Toast.LENGTH_SHORT).show()
            if (it.first) {
                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                finish()
            }
        }
    }
}