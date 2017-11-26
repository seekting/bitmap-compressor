package com.seekting.bitmap.compressor;

import android.graphics.Bitmap;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/11/26.
 */

public class BitmapCompressor {

    public static Map<Class, Decoder<?>> mDecoder = new HashMap<>();
    public static Map<Class, Encoder<?>> mEncoder = new HashMap<>();

    static {
        mDecoder.put(File.class, new FileDecoder());
        mDecoder.put(String.class, new StringDecoder());

        mEncoder.put(File.class, new FileEncoder());
        mEncoder.put(Bitmap.class, new FileEncoder());

    }

    public static <Source> Decoder<Source> getDecoder(Class<?> sourceClass) {
        return (Decoder<Source>) mDecoder.get(sourceClass);
    }

    public static <Target> Encoder<Target> getEnCoder(Class<?> targetClass) {
        return (Encoder<Target>) mEncoder.get(targetClass);
    }

    public static <Source> CompressRequestBuilder<Source> add(Source source) {
        CompressRequestBuilder<Source> compressRequestBuilder = new CompressRequestBuilder<>();
        compressRequestBuilder.mDecoder = getDecoder(source.getClass());
        compressRequestBuilder.add(source);
        return compressRequestBuilder;

    }

    public static void main(String args[]) {
        BitmapCompressor.add(new File("xx")).add(new File("222")).to();
//        BitmapCompressor.add("xxx").add("fdafs").configDecode();

    }

}
