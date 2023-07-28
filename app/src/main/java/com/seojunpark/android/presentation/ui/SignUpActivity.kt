package com.seojunpark.android.presentation.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.seojunpark.android.R
import com.seojunpark.android.data.dto.request.SignUpRequest
import com.seojunpark.android.databinding.ActivitySignUpBinding
import com.seojunpark.android.presentation.viewmodel.SignUpViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private val viewModel: SignUpViewModel by viewModels()
    var passwordStatus: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater).apply {
            setContentView(root)
            lifecycleOwner = this@SignUpActivity
            viewModel = this@SignUpActivity.viewModel
        }

        with(binding) {
            btnSignUp.setOnClickListener {
                viewModel?.signUp()
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

            repwShowHide.setOnClickListener {
                passwordStatus = !passwordStatus

                if (passwordStatus) {
                    inputRepw.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                    repwShowHide.setBackgroundResource(R.drawable.baseline_eye_on)
                } else {
                    inputRepw.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD or InputType.TYPE_CLASS_TEXT
                    repwShowHide.setBackgroundResource(R.drawable.baseline_eye_off)
                }
            }

            goLogin.setOnClickListener {
                startActivity(Intent(this@SignUpActivity, LoginActivity::class.java))
                overridePendingTransition(0, 0)
            }
        }

        lifecycleScope.launch {
            viewModel.doneEvent.observe(this@SignUpActivity) {
                Toast.makeText(this@SignUpActivity, it.second, Toast.LENGTH_SHORT).show()
                if (it.first) {
                    startActivity(Intent(this@SignUpActivity, LoginActivity::class.java))
                }
            }
        }
    }
}