package com.seekting.simplebitmapcompressor

import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.seekting.bitmap.compressor.BitmapCompressor
import com.seekting.bitmap.compressor.CompressResult
import com.seekting.bitmap.compressor.decoder.DecodeConfigKeys
import com.seekting.bitmap.compressor.encoder.EncoderConfigKeys
import java.io.File

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val path1 = "/sdcard/DCIM/Camera/moments_1510131377943.jpg"
        val path2 = "/sdcard/DCIM/Camera/moments_1510131393760.jpg"
        val result: List<CompressResult<String, File>> = BitmapCompressor.add(path1).add(path2)
                .config(DecodeConfigKeys.MIN_BITMAP_HEIGHT, 720)
                .config(DecodeConfigKeys.MIN_BITMAP_WIDTH, 720)
                .config(DecodeConfigKeys.MIN_FILE_SIZE, 1024 * 20)
                .debug()
                .to("/sdcard/simple-compress")
                .config(EncoderConfigKeys.COMPRESS_FACTOR, 0.2f)
                .create().launch()
        for (compressResult in result) {
            val file = compressResult.to
            Log.d("seekting", "file.absolutePath${file.absolutePath}")
            val op = BitmapFactory.Options()
            op.inJustDecodeBounds = true
            BitmapFactory.decodeFile(file.absolutePath, op)
            Log.d("seekting", "MainActivity.onCreate()${op.outWidth}-${op.outHeight}")


        }
    }
}
