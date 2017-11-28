package com.seekting.bitmap.compressor;

import android.graphics.Bitmap;
import android.util.Log;
import android.util.SparseArray;

import com.seekting.bitmap.compressor.decoder.DecodeException;
import com.seekting.bitmap.compressor.decoder.Decoder;
import com.seekting.bitmap.compressor.encoder.EncodeException;
import com.seekting.bitmap.compressor.encoder.Encoder;
import com.seekting.bitmap.compressor.source.Source;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by seekting on 2017/11/26.
 */

public class CompressTask<From, To> implements CancelAble {
    public static final String TAG = "CompressTask";
    private boolean mDebug = false;
    List<Source<From>> mSources;
    Decoder<Source<From>> mDecoder;
    SparseArray mDecodeConfig;
    Encoder<To> mEncoder;
    SparseArray mEncodeConfig;
    private AtomicBoolean mIsCanceled = new AtomicBoolean();

    public CompressTask(List<Source<From>> sources, Decoder<Source<From>> decoder, SparseArray decodeConfig, Encoder<To> encoder, SparseArray encodeConfig, boolean debug) {
        mSources = sources;
        mDecoder = decoder;
        mDecodeConfig = decodeConfig;
        mEncoder = encoder;
        mEncodeConfig = encodeConfig;
        this.mDebug = debug;
    }

    public List<CompressResult<From, To>> launch() {
        List<CompressResult<From, To>> result = new ArrayList<>();
        for (Source<From> source : mSources) {
            CompressResult compressResult = new CompressResult();
            compressResult.mFrom = source.getFrom();
            Bitmap b = null;
            try {
                log("begin decode: " + source.getFrom());
                b = mDecoder.decode(source, mDecodeConfig);
                log("end decode: " + source.getFrom());

            } catch (DecodeException e) {
                compressResult.mResultError = new ResultError(e);
                log("end decode: " + source.getFrom() + e);

            }

            if (mIsCanceled.get()) {
                log("break because canceled!");
                return result;
            }
            if (b != null) {
                log("end decode: " + source.getFrom() + " width=" + b.getWidth() + ",height=" + b.getHeight());
                try {
                    log("begin encode " + source.getFrom());
                    To t = mEncoder.encode(b, source.getId(), mEncodeConfig);
                    log("end encode " + source.getFrom());
                    compressResult.mTo = t;
                } catch (EncodeException e) {
                    if (compressResult.mResultError == null) {
                        compressResult.mResultError = new ResultError(e);
                    } else {
                        compressResult.mResultError.setEncodeException(e);
                    }
                    log("end encode: " + source.getFrom() + e);
                }
            }
            if (mIsCanceled.get()) {
                log("break because canceled!");
                return result;
            }
            result.add(compressResult);

        }
        return result;
    }

    public boolean cancel() {
        return mIsCanceled.compareAndSet(false, true);


    }

    public CancelAble launchAsync(CompressCallBack<From, To> callBack, Executor executor) {
        final WeakReference<CompressCallBack<From, To>> weakReference = new WeakReference<>(callBack);
        Runnable run = new Runnable() {
            @Override
            public void run() {
                List<CompressResult<From, To>> results = launch();
                CompressCallBack<From, To> theCallBack = weakReference.get();
                if (theCallBack != null) {
                    theCallBack.onCompressEnd(results);
                } else {
                    log("warning:callBack is WeakReference in compressor System, pls Strong Reference out of compressor System");
                }
            }
        };
        executor.execute(run);
        return this;
    }

    public CancelAble launchAsync(CompressCallBack<From, To> callBack) {
        final WeakReference<CompressCallBack<From, To>> weakReference = new WeakReference<>(callBack);
        Runnable run = new Runnable() {
            @Override
            public void run() {
                List<CompressResult<From, To>> results = launch();
                CompressCallBack<From, To> theCallBack = weakReference.get();
                if (theCallBack != null) {
                    theCallBack.onCompressEnd(results);
                } else {
                    log("warning:callBack is WeakReference in compressor System, pls Strong Reference out of compressor System");
                }
            }
        };
        new Thread(run).start();
        return this;
    }

    public FutureTarget<From, To> launchAsync() {
        final FutureTarget<From, To> futureTarget = new FutureTarget<>(this);
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<CompressResult<From, To>> results = launch();
                if (!mIsCanceled.get()) {
                    futureTarget.onCompressEnd(results);
                } else {
                    log("canceled!");
                }
            }
        }).start();
        return futureTarget;
    }

    private void log(String msg) {
        if (mDebug) {
            Log.d(TAG, msg);
        }
    }


}
