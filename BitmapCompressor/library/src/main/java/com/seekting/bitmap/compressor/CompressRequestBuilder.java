package com.seekting.bitmap.compressor;

import android.util.SparseArray;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/11/26.
 */

public class CompressRequestBuilder<Source> {

    List<Source> mSources = new ArrayList<>();
    Decoder<Source> mDecoder;
    SparseArray mDecodeConfig = new SparseArray();

    public CompressRequestBuilder<Source> add(Source source) {
        mSources.add(source);
        return this;
    }

    public CompressRequestBuilder<Source> addAll(List<Source> sources) {
        mSources.addAll(sources);
        return this;
    }

    public CompressRequestBuilder<Source> configDecode(int key, Object value) {
        mDecodeConfig.put(key, value);
        return this;
    }


    public CompressRequestTargetBuilder<Source, File> to(String dir) {
        CompressRequestTargetBuilder<Source, File> compressRequestTargetBuilder = new CompressRequestTargetBuilder<Source, File>(this);
        compressRequestTargetBuilder.mEncoder = BitmapCompressor.getEnCoder(File.class);
        compressRequestTargetBuilder.encodeConfig(EncoderConfigKeys.TARGET_FILE_DIR_STRING, dir);
        return compressRequestTargetBuilder;
    }

    public void to() {

    }

}
