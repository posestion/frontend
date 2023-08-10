package com.example.posestion.camera

import android.content.ContentValues
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.provider.MediaStore
import android.util.Log
import android.view.ScaleGestureDetector
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.AspectRatio
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.video.MediaStoreOutputOptions
import androidx.camera.video.Quality
import androidx.camera.video.QualitySelector
import androidx.camera.video.Recorder
import androidx.camera.video.Recording
import androidx.camera.video.VideoCapture
import androidx.camera.video.VideoRecordEvent
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import com.example.posestion.R
import com.example.posestion.databinding.ActivityCameraMainBinding
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.ExecutorService
import kotlin.concurrent.thread


class CameraMainActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityCameraMainBinding

    private var imageCapture: ImageCapture? = null
    private var videoCapture: VideoCapture<Recorder>? = null
    private var recording: Recording? = null

    private lateinit var cameraExecutor: ExecutorService

    private var isImageCapture: Boolean = true
    private var isFlashOn: Boolean = false
    private var ratioStatus: RatioType = RatioType.Ratio9_16
    private var jpegQuality: Int = 75 //1%~100% Int
    private var timerFlag: TimerType = TimerType.OFF
    private var timerCount = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityCameraMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        // Request camera permissions
        if (allPermissionsGranted()) {
            startCamera()

        } else {
            ActivityCompat.requestPermissions(
                this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
        }

        setFlashIcon(isFlashOn)
        setRatioIcon()
        setTimerIcon()

        // Set up the listeners for take photo and video capture buttons
        viewBinding.captureButton.setOnClickListener { pressShutter() }
        viewBinding.flashButton.setOnClickListener { setFlashStatus(!isFlashOn) }
        viewBinding.poseZoom.addOnChangeListener { slider, value, fromUser ->
            Log.d(TAG, "onCreate: Slider: $value")
        }
        viewBinding.leftMode.setOnClickListener {
            isImageCapture = !isImageCapture
            setCaptureMode()
        }
        viewBinding.ratioButton.setOnClickListener { setNextRatio() }
        viewBinding.timerButton.setOnClickListener { setNextTimer() }

    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults:
        IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                startCamera()
            } else {
                Toast.makeText(this,
                    "Permissions not granted by the user.",
                    Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            // Used to bind the lifecycle of cameras to the lifecycle owner
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            // Preview
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(viewBinding.viewFinder.surfaceProvider)
                }

            if(isImageCapture){
                viewBinding.leftMode.text = "동영상"
                viewBinding.rightMode.text = "사진"
                imageCapture = ImageCapture.Builder()
                .setJpegQuality(jpegQuality)
                .setTargetAspectRatio(if (ratioStatus == RatioType.Ratio9_16) AspectRatio.RATIO_16_9 else AspectRatio.RATIO_4_3)
                    .setFlashMode(if (isFlashOn) ImageCapture.FLASH_MODE_ON else ImageCapture.FLASH_MODE_OFF)
                    .build()
            }else{
                viewBinding.leftMode.text = "사진"
                viewBinding.rightMode.text = "동영상"
                val recorder = Recorder.Builder()
                    .setQualitySelector(QualitySelector.from(Quality.HIGHEST))
                    .build()
                videoCapture = VideoCapture.withOutput(recorder)
            }

//            viewBinding.flashButton.text = if (isFlashOn) "플래시 켜짐" else "플래시 꺼짐"

            // Select back camera as a default
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                // Unbind use cases before rebinding
                cameraProvider.unbindAll()

                // Bind use cases to camera
                var camera = if(isImageCapture){
                    cameraProvider.bindToLifecycle(
                        this, cameraSelector, preview, imageCapture)
                }else{
                    // Bind use cases to camera
                    cameraProvider.bindToLifecycle(this, cameraSelector, preview, videoCapture)
                }

                val scaleGestureDetector = ScaleGestureDetector(this,
                    object : ScaleGestureDetector.SimpleOnScaleGestureListener() {
                        override fun onScale(detector: ScaleGestureDetector): Boolean {
                            val scale = camera.cameraInfo.zoomState.value!!.zoomRatio * detector.scaleFactor
                            camera.cameraControl.setZoomRatio(scale)
                            return true
                        }
                    })

                viewBinding.viewFinder.setOnTouchListener { view, event ->
                    view.performClick()
                    scaleGestureDetector.onTouchEvent(event)
                    return@setOnTouchListener true
                }

            } catch(exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)
            }

        }, ContextCompat.getMainExecutor(this))
    }

    private fun takePhoto() {
        // Get a stable reference of the modifiable image capture use case
        val imageCapture = imageCapture ?: return

        // Create time stamped name and MediaStore entry.
        val name = SimpleDateFormat(FILENAME_FORMAT, Locale.US)
            .format(System.currentTimeMillis())
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, name)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            if(Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/POSESTION")
            }
        }

        val metadata = ImageCapture.Metadata()
//        metadata.isReversedHorizontal = isInvert
        Log.d(TAG, "takePhoto: " + metadata.isReversedHorizontal)

        // Create output options object which contains file + metadata
        val outputOptions = ImageCapture.OutputFileOptions
            .Builder(contentResolver,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues)
            .setMetadata(metadata)
            .build()

        // Set up image capture listener, which is triggered after photo has
        // been taken
        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    Log.e(TAG, "Photo capture failed: ${exc.message}", exc)
                }

                override fun
                        onImageSaved(output: ImageCapture.OutputFileResults){
                    val msg = "Photo capture succeeded: ${output.savedUri}"
                    Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
                    Log.d(TAG, msg)
                }
            }
        )
    }

    private fun captureVideo(){
        Log.d(TAG, "captureVideo: caputer srart")
        val videoCapture = videoCapture ?: return
        viewBinding.captureButton.isEnabled = false
        val curRecording = recording
        if (curRecording != null) {
            // Stop the current recording session.
            curRecording.stop()
            recording = null
            return
        }

        // create and start a new recording session
        val name = SimpleDateFormat(FILENAME_FORMAT, Locale.US)
            .format(System.currentTimeMillis())
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, name)
            put(MediaStore.MediaColumns.MIME_TYPE, "video/mp4")
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                put(MediaStore.Video.Media.RELATIVE_PATH, "Movies/POSESTION")
            }
        }

        val mediaStoreOutputOptions = MediaStoreOutputOptions
            .Builder(contentResolver, MediaStore.Video.Media.EXTERNAL_CONTENT_URI)
            .setContentValues(contentValues)
            .build()
        recording = videoCapture.output
            .prepareRecording(this, mediaStoreOutputOptions)
            .apply {
                if (PermissionChecker.checkSelfPermission(this@CameraMainActivity,
                        android.Manifest.permission.RECORD_AUDIO) ==
                    PermissionChecker.PERMISSION_GRANTED)
                {
                    withAudioEnabled()
                }
            }
            .start(ContextCompat.getMainExecutor(this)) { recordEvent ->
                when(recordEvent) {
                    is VideoRecordEvent.Start -> {
                        setButtonOffOnRecoding(true)
                        viewBinding.captureButton.apply {
                            isEnabled = true
                        }
                    }
                    is VideoRecordEvent.Finalize -> {
                        setButtonOffOnRecoding(false)
                        if (!recordEvent.hasError()) {
                            val msg = "비디오 촬영 완료: " +
                                    "${recordEvent.outputResults.outputUri}"
                            Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT)
                                .show()
                            Log.d(TAG, msg)
                        } else {
                            recording?.close()
                            recording = null
                            Log.e(TAG, "Video capture ends with error: " +
                                    "${recordEvent.error}")
                        }
                        viewBinding.captureButton.apply {
                            isEnabled = true
                        }
                    }
                }
            }
    }

    private fun setButtonOffOnRecoding(isRecording: Boolean){
        viewBinding.ratioButton.isEnabled = !isRecording
        viewBinding.flashButton.isEnabled = !isRecording
    }

    private fun setFlashStatus(newStatus: Boolean){
        isFlashOn = newStatus
        setFlashIcon(newStatus)
        startCamera()
    }

    private fun setFlashIcon(nowStatus: Boolean){
        if(nowStatus){
            viewBinding.flashButton.setImageResource(R.drawable.camera_flash_on)
        }else{
            viewBinding.flashButton.setImageResource(R.drawable.camera_flash_off)

        }
    }

    private fun setCaptureMode(){
        if(isImageCapture){
            viewBinding.captureButton.setOnClickListener {
                takePhoto()
            }
            startCamera()
        }else{
            viewBinding.captureButton.setOnClickListener {
                captureVideo()
            }
            startCamera()
        }
    }

    private fun setNextRatio(){
        ratioStatus = ratioStatus.next()
        setRatioIcon()
        startCamera()
    }

    private fun setRatioIcon(){
        when (ratioStatus) {
            RatioType.Ratio9_16 -> {
                viewBinding.ratioButton.setImageResource(R.drawable.ratio_916)
            }
            RatioType.Ratio4_3 -> {
                viewBinding.ratioButton.setImageResource(R.drawable.ratio_34)
            }
            RatioType.Ratio1_1 -> {
                viewBinding.ratioButton.setImageResource(R.drawable.ratio_11)
            }
            RatioType.Ratio_full -> {
                viewBinding.ratioButton.setImageResource(R.drawable.ratio_full)
            }
        }
    }

    private fun setNextTimer(){
        timerFlag = timerFlag.next()
        setTimerIcon()
        startCamera()
    }

    private fun setTimerIcon(){
        when (timerFlag) {
            TimerType.OFF -> {
                viewBinding.timerButton.setImageResource(R.drawable.camera_timer_off)
            }
            TimerType.S3 -> {
                viewBinding.timerButton.setImageResource(R.drawable.camera_timer_3)
            }
            TimerType.S5 -> {
                viewBinding.timerButton.setImageResource(R.drawable.camera_timer_5)
            }
            TimerType.S10 -> {
                viewBinding.timerButton.setImageResource(R.drawable.camera_timer_10)
            }
        }
    }

    private fun pressShutter(){
        if(timerFlag == TimerType.OFF){
            Log.d(TAG, "pressShutter: no timer, timecount=$timerCount, timefiag=$timerFlag")
            if(isImageCapture) takePhoto() else captureVideo()
        }else{
            timerCount = timerFlag.second()
            thread (start = true){
                while (timerCount >= 0){
                    handler.sendEmptyMessage(0)
                    Thread.sleep(1000)
                }
            }
        }
    }

    // Timer Handler
    private val handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
//            viewBinding.txtTimerCount.visibility = View.VISIBLE
//            viewBinding.txtTimerCount.text = timerCount.toString()
            Log.d(TAG, "handleMessage: leftTime: $timerCount")
            timerCount--
            Log.d(TAG, " now timecount=$timerCount, timefiag=$timerFlag")
            if(timerCount == -1) {
                Log.d(TAG, " now timecount=$timerCount, timefiag=$timerFlag")
                if(isImageCapture) takePhoto() else captureVideo()
//                viewBinding.txtTimerCount.visibility = View.INVISIBLE
//                timerCount = timerFlag.second()

            }
        }
    }

    companion object {
        private const val TAG = "CameraXApp"
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS =
            mutableListOf (
                android.Manifest.permission.CAMERA,
                android.Manifest.permission.RECORD_AUDIO
            ).apply {
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                    add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                }
            }.toTypedArray()
    }
}