package com.seekting.bitmap.compressor;

import android.graphics.Bitmap;
import android.util.SparseArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/11/26.
 */

public class CompressTask<Source, Target> {
    List<Source> mSources;


    Decoder<Source> mDecoder;

    SparseArray mDecodeConfig;

    Encoder<Target> mEncoder;
    SparseArray mEncodeConfig;


    public CompressTask(List<Source> sources, Decoder<Source> decoder, SparseArray decodeConfig, Encoder<Target> encoder, SparseArray encodeConfig) {
        mSources = sources;
        mDecoder = decoder;
        mDecodeConfig = decodeConfig;
        mEncoder = encoder;
        mEncodeConfig = encodeConfig;
    }

    public List<Target> launch() {
        List<Target> result = new ArrayList<>();
        for (Source source : mSources) {
            Bitmap b = mDecoder.decode(source, mDecodeConfig);
            Target t = mEncoder.encode(b, mEncodeConfig);
            result.add(t);

        }
        return result;
    }
}
