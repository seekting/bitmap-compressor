package com.seekting.bitmap.compressor;

import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by seekting on 2017/11/27.
 */

public class FutureTarget<S, T> implements Future<List<CompressResult<S, T>>>, CompressCallBack<S, T> {
    private CancelAble mCancelAble;
    private boolean isCanceled = false;
    private List<CompressResult<S, T>> mCompressResults;
    private Waiter mWaiter = new Waiter();

    public FutureTarget(CancelAble cancelAble) {
        this.mCancelAble = cancelAble;

    }

    @Override
    public synchronized boolean cancel(boolean mayInterruptIfRunning) {
        if (isCanceled) {
            return true;
        }
        this.isCanceled = true;
        boolean notDone = !isDone();
        if (notDone) {
            isCanceled = true;
            if (mayInterruptIfRunning) {
                mCancelAble.cancel();
            }
            mWaiter.notifyAll(this);
        }
        return notDone;
    }

    @Override
    public synchronized boolean isCancelled() {
        return this.isCanceled;
    }

    @Override
    public synchronized boolean isDone() {
        return mCompressResults != null;
    }

    @Override
    public List<CompressResult<S, T>> get() throws InterruptedException, ExecutionException {
        try {
            return doGet(null);
        } catch (TimeoutException e) {
            throw new ExecutionException(e);
        }
    }

    private synchronized List<CompressResult<S, T>> doGet(Long timeoutMillis) throws ExecutionException, InterruptedException, TimeoutException {
        if (isCanceled) {
            throw new CancellationException();
        } else if (mCompressResults != null) {
            return mCompressResults;
        }

        if (timeoutMillis == null) {
            mWaiter.waitForTimeout(this, 0);
        } else if (timeoutMillis > 0) {
            mWaiter.waitForTimeout(this, timeoutMillis);
        }

        if (Thread.interrupted()) {
            throw new InterruptedException();
        } else if (isCanceled) {
            throw new CancellationException();
        } else if (mCompressResults == null) {
            throw new TimeoutException();
        }
        return mCompressResults;
    }


    @Override
    public List<CompressResult<S, T>> get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return doGet(unit.toMillis(timeout));
    }

    @Override
    public synchronized void onCompressEnd(List<CompressResult<S, T>> compressResults) {
        this.mCompressResults = compressResults;
        mWaiter.notifyAll(this);

    }


    static class Waiter {

        public void waitForTimeout(Object toWaitOn, long timeoutMillis) throws InterruptedException {
            toWaitOn.wait(timeoutMillis);
        }

        public void notifyAll(Object toNotify) {
            toNotify.notifyAll();
        }
    }
}
