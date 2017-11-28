package com.seekting.compressor;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.seekting.bitmap.compressor.BitmapCompressor;
import com.seekting.bitmap.compressor.CompressResult;

import java.io.File;
import java.util.List;

/**
 * Created by seekting on 2017/11/28.
 */

public class CustomCompressActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new Thread(new Runnable() {
            @Override
            public void run() {
                ImageUrl imageUrl = new ImageUrl("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3189807401,4180388965&fm=27&gp=0.jpg");
                String intoDir = "/sdcard/compressor";
                BitmapCompressor.registerSourceFactory(ImageUrl.class, new ImgeUrlSourceFactory());
                BitmapCompressor.registerDecoder(ImageUrl.class, new ImageUrlDecoder());
                List<CompressResult<ImageUrl, File>> list = BitmapCompressor.add(imageUrl).debug().to(intoDir).create().launch();
                for (CompressResult<ImageUrl, File> imageUrlFileCompressResult : list) {
                    Log.d("seekting", "imageUrlFileCompressResult=" + imageUrlFileCompressResult);
                    if (imageUrlFileCompressResult.getResultError() != null) {
                        imageUrlFileCompressResult.getResultError().printStackTrace();
                    }
                }

            }
        }).start();


    }


}
