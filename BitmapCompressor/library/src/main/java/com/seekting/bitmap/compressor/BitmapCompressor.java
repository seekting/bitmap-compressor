package com.seekting.bitmap.compressor;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;

import com.seekting.bitmap.compressor.decoder.Decoder;
import com.seekting.bitmap.compressor.decoder.FileDecoder;
import com.seekting.bitmap.compressor.decoder.StringDecoder;
import com.seekting.bitmap.compressor.encoder.Encoder;
import com.seekting.bitmap.compressor.encoder.FileEncoder;
import com.seekting.bitmap.compressor.source.FileSourceFactory;
import com.seekting.bitmap.compressor.source.Source;
import com.seekting.bitmap.compressor.source.SourceFactory;
import com.seekting.bitmap.compressor.source.StringSourceFactory;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by seekting on 2017/11/26.
 */

public class BitmapCompressor {

    public static Map<Class, Decoder<?>> mDecoder = new HashMap<>();
    public static Map<Class, SourceFactory<?>> mSourceFactory = new HashMap<>();
    public static Map<Class, Encoder<?>> mEncoder = new HashMap<>();
    public static final Handler sHandler = new Handler(Looper.getMainLooper());

    static {

        mSourceFactory.put(File.class, new FileSourceFactory());
        mSourceFactory.put(String.class, new StringSourceFactory());

        mDecoder.put(File.class, new FileDecoder());
        mDecoder.put(String.class, new StringDecoder());

        mEncoder.put(File.class, new FileEncoder());
        mEncoder.put(Bitmap.class, new FileEncoder());

    }

    public static <From> void registerSourceFactory(Class<From> clazz, SourceFactory<From> factory) {
        mSourceFactory.put(clazz, factory);
    }

    public static <From> boolean unRegisterSourceFactory(Class<From> clazz) {
        return mSourceFactory.remove(clazz) != null;

    }

    public static <From> void registerDecoder(Class<From> clazz, Decoder<? extends Source<From>> decoder) {
        mDecoder.put(clazz, decoder);
    }

    public static <From> boolean unRegisterDecoder(Class<From> clazz) {
        return mDecoder.get(clazz) != null;
    }

    public static void post(Runnable runnable) {
        sHandler.post(runnable);
    }

    public static <From> Decoder<Source<From>> getDecoder(Class<?> fromClass) {
        return (Decoder<Source<From>>) mDecoder.get(fromClass);
    }

    public static <To> Encoder<To> getEnCoder(Class<?> toClass) {
        return (Encoder<To>) mEncoder.get(toClass);
    }

    public static <From> SourceFactory<From> getSourceFactory(Class<?> sourceClass) {
        return (SourceFactory<From>) mSourceFactory.get(sourceClass);
    }

    public static <From> CompressRequestBuilder<From> add(From from) {
        CompressRequestBuilder<From> compressRequestBuilder = new CompressRequestBuilder<>();
        compressRequestBuilder.mDecoder = getDecoder(from.getClass());
        compressRequestBuilder.add(from);
        if (compressRequestBuilder.mDecoder == null) {
            throw new IllegalArgumentException("have you register any decoder for type of " + from.getClass() + "?");
        }
        return compressRequestBuilder;

    }

}
