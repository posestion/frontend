package com.example.posestion

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.documentfile.provider.DocumentFile
import com.example.posestion.databinding.FragmentAskBinding
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class FragmentAsk : Fragment() {

    private lateinit var binding: FragmentAskBinding
    private lateinit var textlength : TextView
    private lateinit var ask : EditText
    private lateinit var tempFile: File

    private val textlengthListener = object : TextWatcher {

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if (s != null) {
                textlength.text = "${s.length}/800"
            }
        }

        override fun afterTextChanged(s: Editable?) {}
    }

    private val filePickerLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            data?.data?.let { uri ->
                val file = getFileFromUri(uri)
                if (file != null) {
                    val filePath = file.absolutePath
                    Log.d("File Path", filePath)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAskBinding.inflate(layoutInflater)

        textlength = binding.FaskTextLength
        ask = binding.FaskEditText
        ask.addTextChangedListener(textlengthListener)

        binding.FaskBtnFile.setOnClickListener {
            openFilePicker()
        }

        return binding.root
    }

    private fun openFilePicker() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.type = "*/*"  // 모든 파일 유형
        filePickerLauncher.launch(intent)
    }

    private fun getFileFromUri(uri: Uri): File? {
        val documentFile = DocumentFile.fromSingleUri(requireContext(), uri)
        val contentResolver = requireContext().contentResolver

        if (documentFile != null) {
            val fileName = documentFile.name
            val mimeType = contentResolver.getType(uri)

            // 임시 파일 생성
            tempFile = File(requireContext().cacheDir, fileName)
            tempFile.createNewFile()

            contentResolver.openInputStream(uri)?.use { inputStream ->
                tempFile.outputStream().use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
            }

            return tempFile
        }
        return null
    }
}