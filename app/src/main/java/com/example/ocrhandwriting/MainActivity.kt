package com.example.ocrhandwriting

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.airbnb.lottie.LottieAnimationView
import com.example.ocrhandwriting
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var uploadAnimationView: LottieAnimationView
    private val PICKFILE_REQUEST_CODE = 1
    private val REQUEST_IMAGE_CAPTURE = 2
    private lateinit var currentPhotoPath: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        uploadAnimationView = findViewById(R.id.uploadAnimationView)
        val uploadButton: Button = findViewById(R.id.buttonUpload)
        val takePictureButton: Button = findViewById(R.id.take_picture)

        uploadButton.setOnClickListener {
            openFilePicker()
        }

        takePictureButton.setOnClickListener {
            dispatchTakePictureIntent()
        }
    }

    private fun openFilePicker() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "*/*"
        startActivityForResult(intent, PICKFILE_REQUEST_CODE)
    }

    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            // Create the File where the photo should go
            val photoFile: File? = try {
                createImageFile()
            } catch (ex: IOException) {
                // Error occurred while creating the File
                null
            }
            // Continue only if the File was successfully created
            photoFile?.also {
                val photoURI: Uri = FileProvider.getUriForFile(
                    this,
                    "com.example.ocrhandwriting.fileprovider",
                    it
                )
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
        val storageDir: File = getExternalFilesDir(null)!!
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICKFILE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val fileUri = data?.data
            fileUri?.let {
                // Start the upload animation
                showUploadAnimation()
                // Simulate file upload
                uploadFile(it)
            }
        } else if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            // Handle the captured photo
            val fileUri = Uri.fromFile(File(currentPhotoPath))
            // Start the upload animation
            showUploadAnimation()
            // Simulate file upload (or other processing)
            uploadFile(fileUri)
        }
    }

    private fun showUploadAnimation() {
        uploadAnimationView.visibility = View.VISIBLE
        uploadAnimationView.playAnimation()
    }

    private fun hideUploadAnimation() {
        uploadAnimationView.visibility = View.GONE
        uploadAnimationView.cancelAnimation()
    }

    private fun uploadFile(fileUri: Uri) {
        // Simulate file upload delay
        Thread {
            // Simulate time taken to upload
            Thread.sleep(3000)
            // Hide the upload animation on completion
            runOnUiThread {
                hideUploadAnimation()
            }
        }.start()
    }
}
