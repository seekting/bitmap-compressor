package com.seekting.compressor;

import android.graphics.Bitmap;
import android.util.SparseArray;

import com.seekting.bitmap.compressor.decoder.DecodeException;
import com.seekting.bitmap.compressor.decoder.DecodeUtil;
import com.seekting.bitmap.compressor.decoder.Decoder;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by seekting on 2017/11/28.
 */

public class ImageUrlDecoder implements Decoder<ImageUrlSource> {


    @Override
    public Bitmap decode(ImageUrlSource source, SparseArray sparseArray) throws DecodeException {
        try {
            URL uri = new URL(source.getFrom().url);
            URLConnection co = uri.openConnection();
            InputStream input = co.getInputStream();
            return DecodeUtil.decodeExpectBitmap(input, co.getContentLength(), sparseArray);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
