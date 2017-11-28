package com.seekting.bitmap.compressor;

import java.util.List;

/**
 * Created by seekting on 2017/11/27.
 */

public interface CompressCallBack<S, T> {
     void onCompressEnd(List<CompressResult<S, T>> compressResults);
}
