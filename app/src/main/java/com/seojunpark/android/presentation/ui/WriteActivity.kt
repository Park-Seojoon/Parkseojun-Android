package com.seojunpark.android.presentation.ui

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.seojunpark.android.databinding.ActivityWriteBinding
import com.seojunpark.android.presentation.viewmodel.WriteViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

@AndroidEntryPoint
class WriteActivity : AppCompatActivity() {

    lateinit var binding: ActivityWriteBinding
    private val viewModel: WriteViewModel by viewModels()
    private lateinit var sharedPreferences: SharedPreferences

    // 갤러리 앱으로부터 결과를 받기 위한 ActivityResultLauncher를 선언합니다.
    private lateinit var galleryLauncher: ActivityResultLauncher<Intent>

    // 선택된 이미지 파일의 URI를 저장할 변수를 선언합니다.
    private var selectedImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWriteBinding.inflate(layoutInflater).apply {
            setContentView(root)
            lifecycleOwner = this@WriteActivity
            viewModel = this@WriteActivity.viewModel
            sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE)
        }

        val accessToken = sharedPreferences.getString("accessToken", "")

        galleryLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                if (data != null) {
                    val imageUri = data.data
                    if (imageUri != null) {
                        // 선택된 이미지를 화면에 보여줍니다.
                        binding.image.setImageURI(imageUri)

                        // 선택된 이미지의 URI를 저장합니다.
                        selectedImageUri = imageUri
                    }
                }
            }
        }

        with(binding) {
            image.setOnClickListener {
                val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                galleryLauncher.launch(galleryIntent)
            }

            goHome.setOnClickListener {
                startActivity(Intent(this@WriteActivity, MainActivity::class.java))
                overridePendingTransition(0, 0)
            }

            goProfile.setOnClickListener {
                startActivity(Intent(this@WriteActivity, ProfileActivity::class.java))
                overridePendingTransition(0, 0)
            }

            btn.setOnClickListener {
                // 선택된 이미지를 MultipartBody.Part로 변환합니다.
                val imageParts = selectedImageUri?.let { uriToMultipartBodyParts(it) } ?: emptyList()

                viewModel?.write(accessToken!!, imageParts)
            }

            lifecycleScope.launch {
                viewModel?.doneEvent?.observe(this@WriteActivity) {
                    Toast.makeText(this@WriteActivity, it.second, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    // 선택된 이미지의 URI를 파일로 변환한 뒤 MultipartBody.Part로 변환합니다.
    private fun uriToMultipartBodyParts(uri: Uri): List<MultipartBody.Part> {
        try {
            val inputStream = contentResolver.openInputStream(uri)
            val file = File(cacheDir, "temp_image")
            val outputStream = FileOutputStream(file)
            inputStream?.copyTo(outputStream)
            inputStream?.close()
            outputStream.close()

            val requestFile = RequestBody.create(contentResolver.getType(uri)!!.toMediaTypeOrNull(), file)
            val part = MultipartBody.Part.createFormData("files", file.name, requestFile)

            return listOf(part)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return emptyList()
    }
}
