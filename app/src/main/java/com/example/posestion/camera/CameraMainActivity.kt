package com.example.posestion.camera

import android.R.attr.x
import android.R.attr.y
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.Paint
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.provider.MediaStore
import android.util.Log
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View.OnTouchListener
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.AspectRatio
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.video.MediaStoreOutputOptions
import androidx.camera.video.Quality
import androidx.camera.video.QualitySelector
import androidx.camera.video.Recorder
import androidx.camera.video.Recording
import androidx.camera.video.VideoCapture
import androidx.camera.video.VideoRecordEvent
import androidx.camera.view.PreviewView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import com.example.posestion.R
import com.example.posestion.databinding.ActivityCameraMainBinding
import java.io.File
import java.io.File.separator
import java.io.FileOutputStream
import java.io.OutputStream
import java.nio.ByteBuffer
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.ExecutorService
import kotlin.concurrent.thread


class CameraMainActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityCameraMainBinding

    private var imageCapture: ImageCapture? = null
    private var videoCapture: VideoCapture<Recorder>? = null
    private var recording: Recording? = null

    private var isImageCapture: Boolean = true
    private var isFlashOn: Boolean = false
    private var ratioStatus: RatioType = RatioType.Ratio4_3
    private var jpegQuality: Int = 75 //1%~100% Int
    private var timerFlag: TimerType = TimerType.OFF
    private var timerCount = 0
    private var isMarked: Boolean = true
    private var isRecording: Boolean = false

    // for pose move
    private var xCoOrdinate = 0f
    private  var yCoOrdinate = 0f

    //for pose zoom
    private val minPoseZoom = 0.3f
    private val maxPoseZoom = 5f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityCameraMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        setFlashIcon(isFlashOn)
        setRatioIcon()
        setTimerIcon()

        // Set up the listeners for take photo and video capture buttons
        viewBinding.captureButton.setOnClickListener { pressShutter() }

        viewBinding.flashButton.setOnClickListener { setFlashStatus(!isFlashOn) }

        viewBinding.poseZoom.value = 22f
        viewBinding.poseZoom.addOnChangeListener { slider, value, fromUser ->
            Log.d(TAG, "onCreate: Slider: $value")
            setPoseSize(calcRealScale(value))
        }

        viewBinding.leftMode.setOnClickListener {
            isImageCapture = !isImageCapture
            startCamera()
        }
        viewBinding.ratioButton.setOnClickListener { setNextRatio() }
        viewBinding.timerButton.setOnClickListener { setNextTimer() }
        viewBinding.settingButton.setOnClickListener {
            val intent = Intent(this, CameraSettingActivity::class.java)
            startActivity(intent)
        }

        viewBinding.poseLayer.setOnTouchListener(OnTouchListener { view, event ->
            when (event.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    xCoOrdinate = view.x - event.rawX
                    yCoOrdinate = view.y - event.rawY
                }

                MotionEvent.ACTION_MOVE -> view.animate().x(event.rawX + xCoOrdinate)
                    .y(event.rawY + yCoOrdinate).setDuration(0).start()

                else -> return@OnTouchListener false
            }
            true
        })

        // Request camera permissions
        if (allPermissionsGranted()) {
            startCamera()

        } else {
            ActivityCompat.requestPermissions(
                this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
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
                .setTargetAspectRatio(if (ratioStatus == RatioType.Ratio9_16) AspectRatio.RATIO_16_9 else AspectRatio.RATIO_4_3)
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
        setButtonStatus(false, isPhoto = true)
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
            ContextCompat.getMainExecutor(this),
            object: ImageCapture.OnImageCapturedCallback(){
                override fun onCaptureSuccess(image: ImageProxy) {
                    var bitmap = imageProxyToBitmap(image).rotate(image.imageInfo.rotationDegrees.toFloat())
                    if(isMarked){
                        bitmap = mark(bitmap, getMarkText(), 255, 50)
                    }
                    saveImage(bitmap, this@CameraMainActivity)
                    setButtonStatus(true, isPhoto = true)
                    super.onCaptureSuccess(image)
                }
            }
        )
    }

    /**
     *  convert image proxy to bitmap
     *  @param image
     */
    private fun imageProxyToBitmap(image: ImageProxy): Bitmap {
        val planeProxy = image.planes[0]
        val buffer: ByteBuffer = planeProxy.buffer
        val bytes = ByteArray(buffer.remaining())
        buffer.get(bytes)
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    }

    private fun Bitmap.rotate(degrees: Float): Bitmap =
        Bitmap.createBitmap(this, 0, 0, width, height, Matrix().apply { postRotate(degrees) }, true)

    private fun mark(
        src: Bitmap,
        watermark: String,
        alpha: Int,
        size: Int,
        color: Int = Color.BLACK,
    ): Bitmap {
        val w = src.width
        val h = src.height
        var textX = 20
        var textY = h-70
        val result = Bitmap.createBitmap(w, h, src.config)
        val canvas = Canvas(result)
        canvas.drawBitmap(src, 0f, 0f, null)
        val paint = Paint()
        paint.color = color
        paint.alpha = alpha
        paint.textSize = size.toFloat()
        paint.isAntiAlias = true

        for (line in watermark.split("\n")) {
            canvas.drawText(line, textX.toFloat(), textY.toFloat(), paint)
            textY += (paint.descent() - paint.ascent()).toInt()
        }
        return result
    }

    private fun getMarkText(): String{
        var markString:String = Build.MODEL
        val sdf = SimpleDateFormat("yyyy년 MM월 dd일 hh:mm:ss")
        markString += "\n" + sdf.format(Date())
        return markString
    }

    /// @param folderName can be your app's name
    private fun saveImage(bitmap: Bitmap, context: Context, folderName: String = "POSESTION") {
        if (android.os.Build.VERSION.SDK_INT >= 29) {
            val values = contentValues()
            values.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/" + folderName)
            values.put(MediaStore.Images.Media.IS_PENDING, true)
            // RELATIVE_PATH and IS_PENDING are introduced in API 29.

            val uri: Uri? = context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
            if (uri != null) {
                saveImageToStream(bitmap, context.contentResolver.openOutputStream(uri))
                values.put(MediaStore.Images.Media.IS_PENDING, false)
                context.contentResolver.update(uri, values, null, null)
                Toast.makeText(this, "OK", Toast.LENGTH_SHORT).show()
            }
        } else {
            val directory = File(Environment.getExternalStorageDirectory().toString() + separator + folderName)
            // getExternalStorageDirectory is deprecated in API 29

            if (!directory.exists()) {
                directory.mkdirs()
            }
            val fileName = System.currentTimeMillis().toString() + ".png"
            val file = File(directory, fileName)
            saveImageToStream(bitmap, FileOutputStream(file))
            if (file.absolutePath != null) {
                val values = contentValues()
                values.put(MediaStore.Images.Media.DATA, file.absolutePath)
                // .DATA is deprecated in API 29
                context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
                Toast.makeText(this, "OK", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun contentValues() : ContentValues {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/png")
        values.put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis() / 1000);
        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
        return values
    }

    private fun saveImageToStream(bitmap: Bitmap, outputStream: OutputStream?) {
        if (outputStream != null) {
            try {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                outputStream.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun captureVideo(){
        Log.d(TAG, "captureVideo: caputer srart")
        setButtonStatus(false, isPhoto = false)
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
                        viewBinding.captureButton.apply {
                            isEnabled = true
                        }
                        isRecording = true
                    }
                    is VideoRecordEvent.Finalize -> {
                        if (!recordEvent.hasError()) {
                            val msg = "비디오 촬영 완료: " +
                                    "${recordEvent.outputResults.outputUri}"
                            Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT)
                                .show()
                            setButtonStatus(true, isPhoto = false)
                            Log.d(TAG, msg)
                        } else {
                            recording?.close()
                            recording = null
                            Log.e(TAG, "Video capture ends with error: " +
                                    "${recordEvent.error}")
                            setButtonStatus(true, isPhoto = false)
                        }
                        viewBinding.captureButton.apply {
                            isEnabled = true
                        }
                        isRecording = false
                        setButtonStatus(true, isPhoto = false)
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

    private fun setNextRatio(){
        ratioStatus = ratioStatus.next()
        setPreviewRatio()
        setRatioIcon()
        startCamera()
    }

    private fun setPreviewRatio(){
        when (ratioStatus) {
            RatioType.Ratio9_16 -> {
                viewBinding.viewFinder.scaleType = PreviewView.ScaleType.FIT_CENTER
            }
            RatioType.Ratio4_3 -> {
                viewBinding.ratioButton.setImageResource(R.drawable.ratio_34)
            }
//            RatioType.Ratio1_1 -> {
//                viewBinding.ratioButton.setImageResource(R.drawable.ratio_11)
//            }
//            RatioType.Ratio_full -> {
//                viewBinding.ratioButton.setImageResource(R.drawable.ratio_full)
//            }
        }
    }

    private fun setRatioIcon(){
        when (ratioStatus) {
            RatioType.Ratio9_16 -> {
                viewBinding.ratioButton.setImageResource(R.drawable.ratio_916)
            }
            RatioType.Ratio4_3 -> {
                viewBinding.ratioButton.setImageResource(R.drawable.ratio_34)
            }
//            RatioType.Ratio1_1 -> {
//                viewBinding.ratioButton.setImageResource(R.drawable.ratio_11)
//            }
//            RatioType.Ratio_full -> {
//                viewBinding.ratioButton.setImageResource(R.drawable.ratio_full)
//            }
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

    private fun setPoseSize(size: Float){
        viewBinding.poseLayer.scaleX = size
        viewBinding.poseLayer.scaleY = size
    }

    private fun calcRealScale(sliderFloat: Float): Float{
        return (minPoseZoom + (sliderFloat * (maxPoseZoom - minPoseZoom) / 100)).toFloat()
    }

    private fun pressShutter(){
        if(isRecording){
            captureVideo()
            return
        }
        if(timerFlag == TimerType.OFF){
            Log.d(TAG, "pressShutter: no timer, timecount=$timerCount, timefiag=$timerFlag")
            if(isImageCapture) takePhoto() else captureVideo()
        }else{
            setButtonStatus(false, isPhoto = true)
            timerCount = timerFlag.second()
            thread (start = true){
                while (timerCount >= 0){
                    handler.sendEmptyMessage(0)
                    Thread.sleep(1000)
                }
            }
        }
    }

    private fun setButtonStatus(setMode: Boolean, isPhoto: Boolean){
        viewBinding.timerButton.isClickable = setMode
        viewBinding.timerButton.isFocusable = setMode
        viewBinding.timerButton.isEnabled = setMode

        viewBinding.ratioButton.isClickable = setMode
        viewBinding.ratioButton.isFocusable = setMode
        viewBinding.ratioButton.isEnabled = setMode

        viewBinding.flashButton.isClickable = setMode
        viewBinding.flashButton.isFocusable = setMode
        viewBinding.flashButton.isEnabled = setMode

        viewBinding.settingButton.isClickable = setMode
        viewBinding.settingButton.isFocusable = setMode
        viewBinding.settingButton.isEnabled = setMode

        viewBinding.leftMode.isClickable = setMode
        viewBinding.leftMode.isFocusable = setMode
        viewBinding.leftMode.isEnabled = setMode

        viewBinding.cartButton.isClickable = setMode
        viewBinding.cartButton.isFocusable = setMode
        viewBinding.cartButton.isEnabled = setMode

        if(isPhoto){
            viewBinding.captureButton.isClickable = setMode
            viewBinding.captureButton.isFocusable = setMode
            viewBinding.captureButton.isEnabled = setMode
        }else{
            viewBinding.captureButton.isClickable = true
            viewBinding.captureButton.isFocusable = true
            viewBinding.captureButton.isEnabled = true
            if(setMode){
                viewBinding.captureButton.setImageResource(R.drawable.camera_shutter)
            }else{
                viewBinding.captureButton.setImageResource(R.drawable.camera_shutter_recording)
            }
        }
    }

    // Timer Handler
    private val handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
//            viewBinding.txtTimerCount.visibility = View.VISIBLE
//            viewBinding.txtTimerCount.text = timerCount.toString()
            viewBinding.timerSecond.text = timerCount.toString()
            timerCount--
            if(timerCount == -1) {
                viewBinding.timerSecond.text = null
                if(isImageCapture) takePhoto() else captureVideo()
//                viewBinding.txtTimerCount.visibility = View.INVISIBLE
//                timerCount = timerFlag.second()

            }
        }
    }

    companion object {
        private const val TAG = "POSESTION"
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