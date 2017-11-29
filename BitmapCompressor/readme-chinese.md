# BitmapCompressor

BitmapCompressor是一个图片压缩工具，运行在android app上，它的作用是把手机里的图片文件压缩成需要的尺寸及大小。方便用户上传到服务端，为用户节省流量。

## 简单的使用，在当前线程里返回

以下例子是批量压缩三张图片，并放入到/sdcard/compressor目录里，CompressResult里有压缩结果。<br>
它包含图片源地址，图片压缩后的地址，图片的压缩过程中出现的异常。

```java
String path1 = "/sdcard/DCIM/Camera/moments_1510131377943.jpg";
String path2 = "/sdcard/DCIM/Camera/moments_1510131393760.jpg";
String path3 = "/sdcard/DCIM/Camera/moments_15101313937601.jpg";
String intoDir = "/sdcard/compressor";
List<CompressResult<String, File>> list = BitmapCompressor
        .add(path1)
        .add(path2)
        .add(path3)
        .to(intoDir)
        .debug()//enable log
        .create()
        .launch();


for (CompressResult<String, File> result : list) {
    if (result.getResultError() != null) {
        Log.e("seekting", result.toString());
    } else {
        Log.d("seekting", result.toString());

    }
}

```
```Console
 D/CompressTask: begin decode: /sdcard/DCIM/Camera/moments_1510131377943.jpg
 D/CompressTask: end decode: /sdcard/DCIM/Camera/moments_1510131377943.jpg
 D/CompressTask: end decode: /sdcard/DCIM/Camera/moments_1510131377943.jpg width=3036,height=4048
 D/CompressTask: begin encode /sdcard/DCIM/Camera/moments_1510131377943.jpg
 D/CompressTask: end encode /sdcard/DCIM/Camera/moments_1510131377943.jpg
 D/CompressTask: begin decode: /sdcard/DCIM/Camera/moments_1510131393760.jpg
 D/CompressTask: end decode: /sdcard/DCIM/Camera/moments_1510131393760.jpg
 D/CompressTask: end decode: /sdcard/DCIM/Camera/moments_1510131393760.jpg width=3036,height=4048
 D/CompressTask: begin encode /sdcard/DCIM/Camera/moments_1510131393760.jpg
 D/CompressTask: end encode /sdcard/DCIM/Camera/moments_1510131393760.jpg
 D/CompressTask: begin decode: /sdcard/DCIM/Camera/moments_15101313937601.jpg
 D/CompressTask: end decode: /sdcard/DCIM/Camera/moments_15101313937601.jpgBaseException{mThrowable=BaseException{mThrowable=null, msg='File Not Exit:/sdcard/DCIM/Camera/moments_15101313937601.jpg'}, msg='BitmapFactory decode fail!! '}
 D/seekting: CompressResult{mFrom=/sdcard/DCIM/Camera/moments_1510131377943.jpg, mTo=/sdcard/compressor/compress_moments_1510131377943.jpg, mResultError=null}
 D/seekting: CompressResult{mFrom=/sdcard/DCIM/Camera/moments_1510131393760.jpg, mTo=/sdcard/compressor/compress_moments_1510131393760.jpg, mResultError=null}
 E/seekting: CompressResult{mFrom=/sdcard/DCIM/Camera/moments_15101313937601.jpg, mTo=null, mResultError=ResultError{mDecodeException=BaseException{mThrowable=BaseException{mThrowable=null, msg='File Not Exit:/sdcard/DCIM/Camera/moments_15101313937601.jpg'}, msg='BitmapFactory decode fail!! '}, mEncodeException=null}}


```

## 异步运行，获得Future句柄

```java
 private void async() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String path1 = "/sdcard/DCIM/Camera/moments_1510131377943.jpg";
                String path2 = "/sdcard/DCIM/Camera/moments_1510131393760.jpg";
                String path3 = "/sdcard/DCIM/Camera/moments_15101313937601.jpg";
                String intoDir = "/sdcard/compressor";
                FutureTarget<String, File> futureTarget = BitmapCompressor
                        .add(path1)
                        .add(path2)
                        .add(path3)
                        .to(intoDir)
                        .create()
                        .launchAsync();
                try {
                    List<CompressResult<String, File>> list = futureTarget.get();//block
                    for (CompressResult<String, File> result : list) {
                        if (result.getResultError() != null) {
                            Log.e("seekting", result.toString());
                        } else {
                            Log.d("seekting", result.toString());

                        }
                    }
                } catch (Throwable e) {
                    Log.e("seekting", "error", e);
                }


            }
        }).start();
    }

```

## 通过Future实现TimeOut

```java
private void async(final long time, final TimeUnit timeUnit) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String path1 = "/sdcard/DCIM/Camera/moments_1510131377943.jpg";
                String path2 = "/sdcard/DCIM/Camera/moments_1510131393760.jpg";
                String path3 = "/sdcard/DCIM/Camera/moments_15101313937601.jpg";
                String intoDir = "/sdcard/compressor";
                FutureTarget<String, File> futureTarget = BitmapCompressor
                        .add(path1)
                        .add(path2)
                        .add(path3)
                        .to(intoDir)
                        .create()
                        .launchAsync();
                try {
                    List<CompressResult<String, File>> list = futureTarget.get(time, timeUnit);//block
                    for (CompressResult<String, File> result : list) {
                        if (result.getResultError() != null) {
                            Log.e("seekting", result.toString());
                        } else {
                            Log.d("seekting", result.toString());

                        }
                    }
                } catch (Throwable e) {
                    Log.e("seekting", "error", e);
                }


            }
        }).start();
    }

```


## 通过CallBack的形式

```java
public class DemoLaunchWithCallBack extends Activity {
    private CompressCallBack<String, File> mCallBack;
    private CancelAble mCancelAble;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        asyncCallBack();

    }

    private void asyncCallBack() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String path1 = "/sdcard/DCIM/Camera/moments_1510131377943.jpg";
                String path2 = "/sdcard/DCIM/Camera/moments_1510131393760.jpg";
                String path3 = "/sdcard/DCIM/Camera/moments_15101313937601.jpg";
                String intoDir = "/sdcard/compressor";
                mCallBack = new CompressCallBack<String, File>() {
                    @Override
                    public void onCompressEnd(List<CompressResult<String, File>> compressResults) {
                        for (CompressResult<String, File> result : compressResults) {
                            if (result.getResultError() != null) {
                                Log.e("seekting", result.toString());
                            } else {
                                Log.d("seekting", result.toString());

                            }
                        }

                    }
                };
                mCancelAble = BitmapCompressor
                        .add(path1)
                        .add(path2)
                        .add(path3)
                        .to(intoDir)
                        .create()
                        .launchAsync(mCallBack);


            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCancelAble != null) {
            mCancelAble.cancel();
        }
        mCallBack = null;

    }

```

```Console
 D/seekting: CompressResult{mFrom=/sdcard/DCIM/Camera/moments_1510131377943.jpg, mTo=/sdcard/compressor/compress_moments_1510131377943.jpg, mResultError=null}
 D/seekting: CompressResult{mFrom=/sdcard/DCIM/Camera/moments_1510131393760.jpg, mTo=/sdcard/compressor/compress_moments_1510131393760.jpg, mResultError=null}
 E/seekting: CompressResult{mFrom=/sdcard/DCIM/Camera/moments_15101313937601.jpg, mTo=null, mResultError=ResultError{mDecodeException=BaseException{mThrowable=BaseException{mThrowable=null, msg='File Not Exit:/sdcard/DCIM/Camera/moments_15101313937601.jpg'}, msg='BitmapFactory decode fail!! '}, mEncodeException=null}}


```

## compress的错误信息

```java
 private void syncError() {
        String path2 = "notexit1.jpg";
        String path3 = "/notexit.jpg";
        String intoDir = "/sdcard/compressor";
        List<CompressResult<String, File>> result = BitmapCompressor
                .add(path2)
                .add(path3)
                .to(intoDir)
                .create()
                .launch();

        for (CompressResult<String, File> objectFileCompressResult : result) {
            if (objectFileCompressResult.getResultError() != null) {
                Log.e("seekting", "error", objectFileCompressResult.getResultError());
            }

        }
    }

```

```Console
 E/seekting: error
ResultError{mDecodeException=com.seekting.bitmap.compressor.decoder.DecodeException: BitmapFactory decode fail!! , mEncodeException=null}
at com.seekting.bitmap.compressor.CompressTask.launch(CompressTask.java:54)
at com.seekting.compressor.DemoErrorLaunchActivity.syncError(DemoErrorLaunchActivity.java:36)
at com.seekting.compressor.DemoErrorLaunchActivity.onCreate(DemoErrorLaunchActivity.java:23)
at android.app.Activity.performCreate(Activity.java:6682)
at android.app.Instrumentation.callActivityOnCreate(Instrumentation.java:1118)
at android.app.ActivityThread.performLaunchActivity(ActivityThread.java:2619)
at android.app.ActivityThread.handleLaunchActivity(ActivityThread.java:2727)
at android.app.ActivityThread.-wrap12(ActivityThread.java)
at android.app.ActivityThread$H.handleMessage(ActivityThread.java:1478)
at android.os.Handler.dispatchMessage(Handler.java:102)
at android.os.Looper.loop(Looper.java:154)
at android.app.ActivityThread.main(ActivityThread.java:6121)
at java.lang.reflect.Method.invoke(Native Method)
at com.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:889)
at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:779)
Caused by: com.seekting.bitmap.compressor.decoder.DecodeException: BitmapFactory decode fail!!
at com.seekting.bitmap.compressor.decoder.StringDecoder.decode(StringDecoder.java:23)
at com.seekting.bitmap.compressor.decoder.StringDecoder.decode(StringDecoder.java:15)
at com.seekting.bitmap.compressor.CompressTask.launch(CompressTask.java:50)
at com.seekting.compressor.DemoErrorLaunchActivity.syncError(DemoErrorLaunchActivity.java:36) 
at com.seekting.compressor.DemoErrorLaunchActivity.onCreate(DemoErrorLaunchActivity.java:23) 
at android.app.Activity.performCreate(Activity.java:6682) 
at android.app.Instrumentation.callActivityOnCreate(Instrumentation.java:1118) 
at android.app.ActivityThread.performLaunchActivity(ActivityThread.java:2619) 
at android.app.ActivityThread.handleLaunchActivity(ActivityThread.java:2727) 
at android.app.ActivityThread.-wrap12(ActivityThread.java) 
at android.app.ActivityThread$H.handleMessage(ActivityThread.java:1478) 
at android.os.Handler.dispatchMessage(Handler.java:102) 
at android.os.Looper.loop(Looper.java:154) 
at android.app.ActivityThread.main(ActivityThread.java:6121) 
at java.lang.reflect.Method.invoke(Native Method) 
at com.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:889) 
at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:779) 
Caused by: com.seekting.bitmap.compressor.decoder.DecodeException: File Not Exit:/notexit1.jpg
at com.seekting.bitmap.compressor.decoder.DecodeUtil.decode(DecodeUtil.java:20)
at com.seekting.bitmap.compressor.decoder.StringDecoder.decode(StringDecoder.java:20)
at com.seekting.bitmap.compressor.decoder.StringDecoder.decode(StringDecoder.java:15) 
at com.seekting.bitmap.compressor.CompressTask.launch(CompressTask.java:50) 
at com.seekting.compressor.DemoErrorLaunchActivity.syncError(DemoErrorLaunchActivity.java:36) 
at com.seekting.compressor.DemoErrorLaunchActivity.onCreate(DemoErrorLaunchActivity.java:23) 
at android.app.Activity.performCreate(Activity.java:6682) 
at android.app.Instrumentation.callActivityOnCreate(Instrumentation.java:1118) 
at android.app.ActivityThread.performLaunchActivity(ActivityThread.java:2619) 
at android.app.ActivityThread.handleLaunchActivity(ActivityThread.java:2727) 
at android.app.ActivityThread.-wrap12(ActivityThread.java) 
at android.app.ActivityThread$H.handleMessage(ActivityThread.java:1478) 
at android.os.Handler.dispatchMessage(Handler.java:102) 
at android.os.Looper.loop(Looper.java:154) 
at android.app.ActivityThread.main(ActivityThread.java:6121) 
at java.lang.reflect.Method.invoke(Native Method) 
at com.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:889) 
at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:779) 
 E/seekting: error
ResultError{mDecodeException=com.seekting.bitmap.compressor.decoder.DecodeException: BitmapFactory decode fail!! , mEncodeException=null}
at com.seekting.bitmap.compressor.CompressTask.launch(CompressTask.java:54)
at com.seekting.compressor.DemoErrorLaunchActivity.syncError(DemoErrorLaunchActivity.java:36)
at com.seekting.compressor.DemoErrorLaunchActivity.onCreate(DemoErrorLaunchActivity.java:23)
at android.app.Activity.performCreate(Activity.java:6682)
at android.app.Instrumentation.callActivityOnCreate(Instrumentation.java:1118)
at android.app.ActivityThread.performLaunchActivity(ActivityThread.java:2619)
at android.app.ActivityThread.handleLaunchActivity(ActivityThread.java:2727)
at android.app.ActivityThread.-wrap12(ActivityThread.java)
at android.app.ActivityThread$H.handleMessage(ActivityThread.java:1478)
at android.os.Handler.dispatchMessage(Handler.java:102)
at android.os.Looper.loop(Looper.java:154)
at android.app.ActivityThread.main(ActivityThread.java:6121)
at java.lang.reflect.Method.invoke(Native Method)
at com.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:889)
at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:779)
Caused by: com.seekting.bitmap.compressor.decoder.DecodeException: BitmapFactory decode fail!!
at com.seekting.bitmap.compressor.decoder.StringDecoder.decode(StringDecoder.java:23)
at com.seekting.bitmap.compressor.decoder.StringDecoder.decode(StringDecoder.java:15)
at com.seekting.bitmap.compressor.CompressTask.launch(CompressTask.java:50)
at com.seekting.compressor.DemoErrorLaunchActivity.syncError(DemoErrorLaunchActivity.java:36) 
at com.seekting.compressor.DemoErrorLaunchActivity.onCreate(DemoErrorLaunchActivity.java:23) 
at android.app.Activity.performCreate(Activity.java:6682) 
at android.app.Instrumentation.callActivityOnCreate(Instrumentation.java:1118) 
at android.app.ActivityThread.performLaunchActivity(ActivityThread.java:2619) 
at android.app.ActivityThread.handleLaunchActivity(ActivityThread.java:2727) 
at android.app.ActivityThread.-wrap12(ActivityThread.java) 
at android.app.ActivityThread$H.handleMessage(ActivityThread.java:1478) 
at android.os.Handler.dispatchMessage(Handler.java:102) 
at android.os.Looper.loop(Looper.java:154) 
at android.app.ActivityThread.main(ActivityThread.java:6121) 
at java.lang.reflect.Method.invoke(Native Method) 
at com.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:889) 
at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:779) 
Caused by: com.seekting.bitmap.compressor.decoder.DecodeException: File Not Exit:/notexit.jpg
at com.seekting.bitmap.compressor.decoder.DecodeUtil.decode(DecodeUtil.java:20)
at com.seekting.bitmap.compressor.decoder.StringDecoder.decode(StringDecoder.java:20)
at com.seekting.bitmap.compressor.decoder.StringDecoder.decode(StringDecoder.java:15) 
at com.seekting.bitmap.compressor.CompressTask.launch(CompressTask.java:50) 
at com.seekting.compressor.DemoErrorLaunchActivity.syncError(DemoErrorLaunchActivity.java:36) 
at com.seekting.compressor.DemoErrorLaunchActivity.onCreate(DemoErrorLaunchActivity.java:23) 
at android.app.Activity.performCreate(Activity.java:6682) 
at android.app.Instrumentation.callActivityOnCreate(Instrumentation.java:1118) 
at android.app.ActivityThread.performLaunchActivity(ActivityThread.java:2619) 
at android.app.ActivityThread.handleLaunchActivity(ActivityThread.java:2727) 
at android.app.ActivityThread.-wrap12(ActivityThread.java) 
at android.app.ActivityThread$H.handleMessage(ActivityThread.java:1478) 
at android.os.Handler.dispatchMessage(Handler.java:102) 
at android.os.Looper.loop(Looper.java:154) 
at android.app.ActivityThread.main(ActivityThread.java:6121) 
at java.lang.reflect.Method.invoke(Native Method) 
at com.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:889) 
at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:779) 


```

## 扩展：自定义From的Decode方式

如果有一个ImageUrl是网络资源，而你又期望它能压缩这张网络图片，我们可以这样
```java
public class CustomCompressActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ImageUrl imageUrl = new ImageUrl("http://avatar.csdn.net/4/8/A/1_u012947056.jpg");
        BitmapCompressor.add(imageUrl).debug().to(new File(getCacheDir(), "compresstest"));


    }


}

public class ImageUrl {
    public ImageUrl(String url) {
        this.url = url;
    }

    public String url;
}

```

但是不能如你所愿，你没有注册SourceFactory,因为系统里目前不支持该源的解析，所以你需要自己去实现它.
```Console
 E/AndroidRuntime: FATAL EXCEPTION: main
Process: com.seekting.bitmapcompressor, PID: 9426
java.lang.RuntimeException: Unable to start activity ComponentInfo{com.seekting.bitmapcompressor/com.seekting.compressor.CustomCompressActivity}: java.lang.IllegalArgumentException: have you register any SourceFactory for type of class com.seekting.compressor.CustomCompressActivity$ImageUrl?
at android.app.ActivityThread.performLaunchActivity(ActivityThread.java:2666)
at android.app.ActivityThread.handleLaunchActivity(ActivityThread.java:2727)
at android.app.ActivityThread.-wrap12(ActivityThread.java)
at android.app.ActivityThread$H.handleMessage(ActivityThread.java:1478)
at android.os.Handler.dispatchMessage(Handler.java:102)
at android.os.Looper.loop(Looper.java:154)
at android.app.ActivityThread.main(ActivityThread.java:6121)
at java.lang.reflect.Method.invoke(Native Method)
at com.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:889)
at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:779)
Caused by: java.lang.IllegalArgumentException: have you register any SourceFactory for type of class com.seekting.compressor.CustomCompressActivity$ImageUrl?
at com.seekting.bitmap.compressor.CompressRequestBuilder.add(CompressRequestBuilder.java:28)
at com.seekting.bitmap.compressor.BitmapCompressor.add(BitmapCompressor.java:81)
at com.seekting.compressor.CustomCompressActivity.onCreate(CustomCompressActivity.java:20)
at android.app.Activity.performCreate(Activity.java:6682)
at android.app.Instrumentation.callActivityOnCreate(Instrumentation.java:1118)
at android.app.ActivityThread.performLaunchActivity(ActivityThread.java:2619)
at android.app.ActivityThread.handleLaunchActivity(ActivityThread.java:2727) 
at android.app.ActivityThread.-wrap12(ActivityThread.java) 
at android.app.ActivityThread$H.handleMessage(ActivityThread.java:1478) 
at android.os.Handler.dispatchMessage(Handler.java:102) 
at android.os.Looper.loop(Looper.java:154) 
at android.app.ActivityThread.main(ActivityThread.java:6121) 
at java.lang.reflect.Method.invoke(Native Method) 
at com.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:889) 
at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:779) 

```

我们来实现它：
### 创建Source<ImageUrl>
```java

public class ImageUrlSource extends Source<ImageUrl> {


    public ImageUrlSource(ImageUrl imageUrl) {
        super(imageUrl);
    }

    @Override
    public void generateId(ImageUrl imageUrl) {

    }
}

```


### 创建Decoder<ImageUrl>
```java
public class ImageUrlDecoder implements Decoder<ImageUrlSource> {


    @Override
    public Bitmap decode(ImageUrlSource source, SparseArray sparseArray) throws DecodeException {
        try {
            URL uri = new URL(source.getFrom().url);
            URLConnection co = uri.openConnection();
            InputStream input = co.getInputStream();
            return DecodeUtil.decodeExpectBitmap(input, co.getContentLength(), sparseArray);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

```
### SourceFactory<ImageUrl>
```java
package com.seekting.compressor;


import com.seekting.bitmap.compressor.source.SourceFactory;

/**
 * Created by seekting on 2017/11/28.
 */

public class ImgeUrlSourceFactory implements SourceFactory<ImageUrl> {
    @Override
    public ImageUrlSource create(ImageUrl imageUrl) {
        return new ImageUrlSource(imageUrl);
    }
}


```

### 注册decoder,SourceFactory
```java
 BitmapCompressor.registerSourceFactory(ImageUrl.class, new ImgeUrlSourceFactory());
 BitmapCompressor.registerDecoder(ImageUrl.class, new ImageUrlDecoder());

```
### 像File,String的Source一样使用它
```java
package com.seekting.compressor;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.seekting.bitmap.compressor.BitmapCompressor;
import com.seekting.bitmap.compressor.CompressResult;

import java.io.File;
import java.util.List;

/**
 * Created by seekting on 2017/11/28.
 */

public class CustomCompressActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new Thread(new Runnable() {
            @Override
            public void run() {
                ImageUrl imageUrl = new ImageUrl("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3189807401,4180388965&fm=27&gp=0.jpg");
                String intoDir = "/sdcard/compressor";
                BitmapCompressor.registerSourceFactory(ImageUrl.class, new ImgeUrlSourceFactory());
                BitmapCompressor.registerDecoder(ImageUrl.class, new ImageUrlDecoder());
                List<CompressResult<ImageUrl, File>> list = BitmapCompressor.add(imageUrl).debug().to(intoDir).create().launch();
                for (CompressResult<ImageUrl, File> imageUrlFileCompressResult : list) {
                    Log.d("seekting", "imageUrlFileCompressResult=" + imageUrlFileCompressResult);
                    if (imageUrlFileCompressResult.getResultError() != null) {
                        imageUrlFileCompressResult.getResultError().printStackTrace();
                    }
                }

            }
        }).start();


    }


}


```

输出结果:
```Console
 D/CompressTask: begin decode: com.seekting.compressor.ImageUrl@d65e82f
 D/NetworkSecurityConfig: No Network Security Config specified, using platform default
 D/CompressTask: end decode: com.seekting.compressor.ImageUrl@d65e82f
 D/CompressTask: end decode: com.seekting.compressor.ImageUrl@d65e82f width=500,height=648
 D/CompressTask: begin encode com.seekting.compressor.ImageUrl@d65e82f
 D/CompressTask: end encode com.seekting.compressor.ImageUrl@d65e82f
 D/seekting: imageUrlFileCompressResult=CompressResult{mFrom=com.seekting.compressor.ImageUrl@d65e82f, mTo=/sdcard/compressor/compress_c9595d8f-f8e9-4015-a46e-b26dadf32e2c.jpg, mResultError=null}


```

## 自定义Encoder

比如你期望转成Bitmap
```java
String path1 = "/sdcard/DCIM/Camera/moments_1510131377943.jpg";
List<CompressResult<String, Bitmap>> list = BitmapCompressor.add(path1)
                                            .debug()
                                            .to(Bitmap
                                            .class)
                                            .create()
                                            .launch();
for (CompressResult<String, Bitmap> imageUrlFileCompressResult : list) {
    Log.d("seekting", "imageUrlFileCompressResult=" + imageUrlFileCompressResult);
    if (imageUrlFileCompressResult.getResultError() != null) {
        imageUrlFileCompressResult.getResultError().printStackTrace();
    }
}

```
然而这并不能如你所愿:
```Console
FATAL EXCEPTION: Thread-2
Process: com.seekting.bitmapcompressor, PID: 18017
java.lang.IllegalArgumentException: have you register any encoder for type of class android.graphics.Bitmap?
 at com.seekting.bitmap.compressor.CompressRequestBuilder.to(CompressRequestBuilder.java:78)
 at com.seekting.compressor.DemoCustomCompress1Activity$1.run(DemoCustomCompress1Activity.java:26)
 at java.lang.Thread.run(Thread.java:761)
```

你需求注册Encoder<Bitmap>


```java
public class BitmapEncoder implements Encoder<Bitmap> {
    @Override
    public Bitmap encode(Bitmap bitmap, String id, SparseArray sparseArray) throws EncodeException {
        return bitmap;
    }
}



```
通过registerEncoder注册
```java
String path1 = "/sdcard/DCIM/Camera/moments_1510131377943.jpg";
BitmapCompressor.registerEncoder(Bitmap.class, new BitmapEncoder());
List<CompressResult<String, Bitmap>> list = BitmapCompressor.add(path1).debug().to(Bitmap.class).create().launch();
for (CompressResult<String, Bitmap> imageUrlFileCompressResult : list) {
    Log.d("seekting", "imageUrlFileCompressResult=" + imageUrlFileCompressResult);
    if (imageUrlFileCompressResult.getResultError() != null) {
        imageUrlFileCompressResult.getResultError().printStackTrace();
    }
}

```

输出成功：
```Console
 D/seekting: compressResult=CompressResult{mFrom=/sdcard/DCIM/Camera/moments_1510131377943.jpg, mTo=android.graphics.Bitmap@c1297c5, mResultError=null}

```

