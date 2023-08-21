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
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.posestion.MyApplication.Companion.filecount
import com.example.posestion.connection.RetrofitClient
import com.example.posestion.databinding.FragmentAskBinding
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class FragmentAsk : Fragment() {

    private lateinit var binding: FragmentAskBinding
    private lateinit var textlength : TextView
    private lateinit var ask : EditText
    private lateinit var tempFile: File
    private lateinit var recyclerView: RecyclerView
    private lateinit var fileadapter: AdapterAsk
    private val filenamelist = mutableListOf<DataFile>()
    private val filelist = mutableListOf<MultipartBody.Part>()
    private val files = mutableListOf<File>()
    private val user = MyApplication.user
    private var token = ""

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
                    val fileName = filePath.substring(filePath.lastIndexOf("/") + 1)
                    val file = File(filePath)
                    files.add(file)

                    filenamelist.add(DataFile(fileName))
                    recyclerView.adapter?.notifyDataSetChanged()
                    Log.d("Filelist", filelist.toString())
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

        token = user.getString("jwt", "").toString()
        filecount = 0

        recyclerView = binding.FaskRvFile
        fileadapter = AdapterAsk(filenamelist, files)
        recyclerView.layoutManager =
            StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.adapter = fileadapter

        textlength = binding.FaskTextLength
        ask = binding.FaskEditText
        ask.addTextChangedListener(textlengthListener)

        binding.FaskBtnFile.setOnClickListener {
            if(filecount<3){
                openFilePicker()
                filecount++
            }else{
                Toast.makeText(requireContext(), "파일은 3개까지 첨부 가능합니다.", Toast.LENGTH_SHORT).show();
            }
        }

        binding.FaskBtnAsk.setOnClickListener {
            if(binding.FaskEditText.text.length == 0 && binding.FaskEditTitle.text.length == 0){
                Toast.makeText(requireContext(), "제목과 내용을 입력해주세요.", Toast.LENGTH_SHORT).show()
            }else if(binding.FaskEditTitle.text.length == 0){
                Toast.makeText(requireContext(), "제목을 입력해주세요.", Toast.LENGTH_SHORT).show()
            }else if(binding.FaskEditText.text.length == 0){
                Toast.makeText(requireContext(), "내용을 입력해주세요.", Toast.LENGTH_SHORT).show()
            }else{
                val title = binding.FaskEditTitle.text.toString().toRequestBody("text/plain".toMediaTypeOrNull())
                val content = binding.FaskEditText.text.toString().toRequestBody("text/plain".toMediaTypeOrNull())

                val fileParts: List<MultipartBody.Part> = files.map { file ->
                    val requestFile = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
                    MultipartBody.Part.createFormData("files", file.name, requestFile)
                }

                val call = RetrofitObject.getRetrofitService
                call.ask(token, title, content, fileParts)
                    .enqueue(object : Callback<RetrofitClient.ResponseAsk> {
                        override fun onResponse(call: Call<RetrofitClient.ResponseAsk>, response: Response<RetrofitClient.ResponseAsk>) {
                            if (response.isSuccessful) {
                                Log.d("Retrofit", "success")
                                val result = response.body()
                                if(result != null){
                                    filecount = 0
                                    filenamelist.clear()
                                    recyclerView.adapter?.notifyDataSetChanged()
                                    binding.FaskEditText.text.clear()
                                    binding.FaskEditTitle.text.clear()
                                    Toast.makeText(requireContext(), "문의가 접수되었습니다.", Toast.LENGTH_SHORT).show()
                                }
                            } else {
                                val errorBody = response.errorBody()?.string()
                                Log.d("Retrofit", "Response Error: $errorBody")
                            }
                        }
                        override fun onFailure(call: Call<RetrofitClient.ResponseAsk>, t: Throwable) {
                            val errorMessage = "Call Failed: ${t.message}"
                            Log.d("Retrofit", errorMessage)
                        }
                    })
            }
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