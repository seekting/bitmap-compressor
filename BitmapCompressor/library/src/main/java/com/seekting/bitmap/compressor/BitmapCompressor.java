package com.seekting.bitmap.compressor;

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
import java.util.List;
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

    }

    public static <From> void registerSourceFactory(Class<From> clazz, SourceFactory<From> factory) {
        ObjectHelper.requireNotNull(clazz,"");
        ObjectHelper.requireNotNull(factory,"");
        mSourceFactory.put(clazz, factory);
    }

    public static <From> boolean unRegisterSourceFactory(Class<From> clazz) {
        ObjectHelper.requireNotNull(clazz,"");
        return mSourceFactory.remove(clazz) != null;

    }

    public static <From> void registerDecoder(Class<From> clazz, Decoder<? extends Source<From>> decoder) {
        ObjectHelper.requireNotNull(clazz,"");
        ObjectHelper.requireNotNull(decoder,"");
        mDecoder.put(clazz, decoder);
    }

    public static <From> boolean unRegisterDecoder(Class<From> clazz) {
        ObjectHelper.requireNotNull(clazz,"");
        return mDecoder.remove(clazz) != null;
    }

    public static <To> void registerEncoder(Class<To> clazz, Encoder<To> encoder) {
        ObjectHelper.requireNotNull(clazz,"");
        ObjectHelper.requireNotNull(encoder,"");
        mEncoder.put(clazz, encoder);
    }

    public static <To> boolean unRegisterEncoder(Class<To> clazz) {
        ObjectHelper.requireNotNull(clazz,"");
        return mEncoder.remove(clazz) != null;
    }

    public static void post(Runnable runnable) {
        sHandler.post(runnable);
    }

    public static <From> Decoder<Source<From>> getDecoder(Class<?> fromClass) {
        return (Decoder<Source<From>>) mDecoder.get(fromClass);
    }

    public static <To> Encoder<To> getEncoder(Class<?> toClass) {
        Encoder<?> j = mEncoder.get(toClass);
        return (Encoder<To>) mEncoder.get(toClass);
    }

    public static <From> SourceFactory<From> getSourceFactory(Class<?> sourceClass) {
        return (SourceFactory<From>) mSourceFactory.get(sourceClass);
    }

    public static <From> CompressRequestBuilder<From> add(From from) {
        ObjectHelper.requireNotNull(from, "from is null!!");
        CompressRequestBuilder<From> compressRequestBuilder = new CompressRequestBuilder<>();
        compressRequestBuilder.mDecoder = getDecoder(from.getClass());
        compressRequestBuilder.add(from);
        if (compressRequestBuilder.mDecoder == null) {
            throw new IllegalArgumentException("have you register any decoder for type of " + from.getClass() + "?");
        }
        return compressRequestBuilder;

    }

    public static <From> CompressRequestBuilder<From> addAll(List<From> from) {
        ObjectHelper.requireNotNull(from, "from list is null!!");
        if (from.isEmpty()) {
            throw new IllegalArgumentException("from list is null or empty!!");
        }
        CompressRequestBuilder<From> compressRequestBuilder = new CompressRequestBuilder<>();
        compressRequestBuilder.mDecoder = getDecoder(from.get(0).getClass());
        compressRequestBuilder.addAll(from);
        if (compressRequestBuilder.mDecoder == null) {
            throw new IllegalArgumentException("have you register any decoder for type of " + from.get(0).getClass() + "?");
        }
        return compressRequestBuilder;

    }

}
