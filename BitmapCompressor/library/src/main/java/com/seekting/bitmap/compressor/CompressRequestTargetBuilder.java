package com.seekting.bitmap.compressor;

import android.util.SparseArray;


/**
 * Created by Administrator on 2017/11/26.
 */

public class CompressRequestTargetBuilder<Source, Target> {
    CompressRequestBuilder<Source> mSourceCompressRequestBuilder;
    Encoder<Target> mEncoder;
    SparseArray mEncodeConfig = new SparseArray();


    public CompressRequestTargetBuilder(CompressRequestBuilder<Source> sourceCompressRequestBuilder) {
        this.mSourceCompressRequestBuilder = sourceCompressRequestBuilder;
    }

    public CompressRequestTargetBuilder<Source, Target> encodeConfig(@EncoderConfigKeys int key, Object value) {
        mEncodeConfig.put(key, value);
        return this;

    }

    public CompressTask<Source, Target> create() {
        CompressTask compressTask = new CompressTask(
                mSourceCompressRequestBuilder.mSources,
                mSourceCompressRequestBuilder.mDecoder,
                mSourceCompressRequestBuilder.mDecodeConfig,
                mEncoder,
                mEncodeConfig
        );
        return compressTask;
    }


}
