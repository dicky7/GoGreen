package com.manut.gogreen.ui.dashboard

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.manut.gogreen.R
import com.manut.gogreen.data.utils.UploadRequestBody
import com.manut.gogreen.databinding.FragmentDashboardBinding
import com.github.dhaval2404.imagepicker.ImagePicker
import com.manut.gogreen.data.utils.snackbar
import okhttp3.MultipartBody
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class DashboardFragment : Fragment(), View.OnClickListener {
    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private  val dashboardViewModel: DashboardViewModel by viewModels()
    private var selectedImageUri: Uri? = null

    companion object {
        const val REQUEST_CODE_PICK_IMAGE = 102


    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
       _binding = FragmentDashboardBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dashboardViewModel.isLoading.observe(viewLifecycleOwner, {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        })

        dashboardViewModel.textView.observe(viewLifecycleOwner,{
            binding.tvResult.text = "Classification : $it"
        })


        binding.imgClassification.setOnClickListener(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(v: View?) {
       if (v?.id == R.id.img_classification){
           selectImage()
       }
    }

    private fun selectImage() {
        ImagePicker.with(this)
            .galleryMimeTypes(  //Exclude gif images
                mimeTypes = arrayOf(
                    "image/png",
                    "image/jpg",
                    "image/jpeg"
                )
            )
            .compress(1024)
            .saveDir(File(requireActivity().cacheDir, "green"))
            .start(REQUEST_CODE_PICK_IMAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            Activity.RESULT_OK -> {
                //Image Uri will not be null for RESULT_OK
                when(requestCode){
                    REQUEST_CODE_PICK_IMAGE ->{
                        selectedImageUri = data?.data
                        binding.imgClassification.setImageURI(selectedImageUri)
                        uploadImage()
                    }
                }

            }
            ImagePicker.RESULT_ERROR -> {
                Toast.makeText(requireContext(),"elor" + ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
            }
            else -> {
                Toast.makeText(requireContext(), "Task Cancelled", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun uploadImage() {
        if (selectedImageUri == null) {
            binding.layoutRoot.snackbar("Select an Image First")
            return
        }
        val parcelFileDescriptor =
           requireContext().contentResolver.openFileDescriptor(selectedImageUri!!, "r", null) ?: return

        val inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor)
        val file = File( activity?.cacheDir, "cahcey")
        val outputStream = FileOutputStream(file)
        inputStream.copyTo(outputStream)

        val body = UploadRequestBody(file,"img")
        dashboardViewModel.classificationImage(MultipartBody.Part.createFormData("img",file.name,body),)
    }
}