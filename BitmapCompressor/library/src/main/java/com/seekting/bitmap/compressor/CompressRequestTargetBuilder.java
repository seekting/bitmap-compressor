package com.seekting.bitmap.compressor;

import android.util.SparseArray;

import com.seekting.bitmap.compressor.encoder.Encoder;
import com.seekting.bitmap.compressor.encoder.EncoderConfigKeys;


/**
 * Created by seekting on 2017/11/26.
 */

public class CompressRequestTargetBuilder<From, To> {
    CompressRequestBuilder<From> mSourceCompressRequestBuilder;
    Encoder<To> mEncoder;
    SparseArray mEncodeConfig = new SparseArray();


    public CompressRequestTargetBuilder(CompressRequestBuilder<From> sourceCompressRequestBuilder) {
        this.mSourceCompressRequestBuilder = sourceCompressRequestBuilder;
    }

    public CompressRequestTargetBuilder<From, To> config(@EncoderConfigKeys int key, Object value) {
        mEncodeConfig.put(key, value);
        return this;

    }

    public CompressRequestTargetBuilder<From, To> debug() {
        this.mSourceCompressRequestBuilder.debug();
        return this;
    }

    public CompressTask<From, To> create() {
        CompressTask compressTask = new CompressTask(
                mSourceCompressRequestBuilder.mSources,
                mSourceCompressRequestBuilder.mDecoder,
                mSourceCompressRequestBuilder.mDecodeConfig,
                mEncoder,
                mEncodeConfig,
                mSourceCompressRequestBuilder.mDebug
        );
        return compressTask;
    }


}
