package com.seekting.bitmap.compressor;

import android.util.SparseArray;

import com.seekting.bitmap.compressor.decoder.Decoder;
import com.seekting.bitmap.compressor.encoder.Encoder;
import com.seekting.bitmap.compressor.encoder.EncoderConfigKeys;
import com.seekting.bitmap.compressor.source.Source;
import com.seekting.bitmap.compressor.source.SourceFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by seekting on 2017/11/26.
 */

public class CompressRequestBuilder<From> {

    List<Source<From>> mSources = new ArrayList<>();
    Decoder<Source<From>> mDecoder;
    SparseArray mDecodeConfig = new SparseArray();
    boolean mDebug;

    public CompressRequestBuilder<From> add(From from) {
        SourceFactory<From> sourceFactory = BitmapCompressor.getSourceFactory(from.getClass());
        if (sourceFactory == null) {
            throw new IllegalArgumentException("have you register any SourceFactory for type of " + from.getClass() + "?");
        }
        Source<From> s = sourceFactory.create(from);
        mSources.add(s);
        return this;
    }

    public CompressRequestBuilder<From> addAll(List<From> list) {
        if (list == null || list.size() == 0) {
            return this;
        }
        SourceFactory<From> factory = BitmapCompressor.getSourceFactory(list.get(0).getClass());

        for (From from : list) {
            Source<From> source = factory.create(from);
            mSources.add(source);
        }
        return this;
    }

    public CompressRequestBuilder<From> config(int key, Object value) {
        mDecodeConfig.put(key, value);
        return this;
    }

    public CompressRequestBuilder<From> debug() {
        this.mDebug = true;
        return this;
    }

    public CompressRequestTargetBuilder<From, File> to(File difFile) {
        CompressRequestTargetBuilder<From, File> compressRequestTargetBuilder = new CompressRequestTargetBuilder<From, File>(this);
        compressRequestTargetBuilder.mEncoder = BitmapCompressor.getEncoder(File.class);

        compressRequestTargetBuilder.config(EncoderConfigKeys.TARGET_FILE_DIR_STRING, difFile.getAbsolutePath());
        return compressRequestTargetBuilder;
    }

    public CompressRequestTargetBuilder<From, File> to(String dir) {
        CompressRequestTargetBuilder<From, File> compressRequestTargetBuilder = new CompressRequestTargetBuilder<From, File>(this);
        compressRequestTargetBuilder.mEncoder = BitmapCompressor.getEncoder(File.class);
        compressRequestTargetBuilder.config(EncoderConfigKeys.TARGET_FILE_DIR_STRING, dir);
        return compressRequestTargetBuilder;
    }

    public <To> CompressRequestTargetBuilder<From, To> to(Class<To> toClass) {
        CompressRequestTargetBuilder<From, To> compressRequestTargetBuilder = new CompressRequestTargetBuilder(this);
        Encoder<Object> j = BitmapCompressor.getEncoder(toClass);
        compressRequestTargetBuilder.mEncoder = (Encoder<To>) j;
        if (compressRequestTargetBuilder.mEncoder == null) {
            throw new IllegalArgumentException("have you register any encoder for type of " + toClass + " ?");
        }
        return compressRequestTargetBuilder;
    }


}
