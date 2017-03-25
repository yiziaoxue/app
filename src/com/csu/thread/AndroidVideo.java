//package com.csu.thread;
//import java.io.File;
//import java.io.RandomAccessFile;
//import java.nio.ByteBuffer;
//import java.util.concurrent.ArrayBlockingQueue;
//import java.util.concurrent.BlockingQueue;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.graphics.Canvas;
//import android.graphics.Color;
//import android.os.Bundle;
//import android.os.Handler;
//import android.view.View;
//
//import android.content.res.Configuration;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.SurfaceHolder;
//import android.view.SurfaceView;
//import android.view.Window;
//import android.view.WindowManager;
//import android.view.SurfaceHolder.Callback;
//import android.graphics.PixelFormat;
//import android.hardware.Camera;
//import android.widget.ImageView;
//
///**
// * @author archko
// */
//public class AndroidVideo extends Activity implements Callback, Camera.PictureCallback {
//
//    public static final String TAG="AndroidVideo";
//    private SurfaceView mSurfaceView=null;
//    private SurfaceHolder mSurfaceHolder=null;
//    private Camera mCamera=null;
//    private boolean mPreviewRunning=false;
//    int mWidth=352, mHeight=288;
//    ImageView displayer;
//    static Handler mHandler=new Handler();
//    H264Encoder mH264Encoder;
//
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        getWindow().setFormat(PixelFormat.TRANSLUCENT);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//            WindowManager.LayoutParams.FLAG_FULLSCREEN);
//
//        setContentView(R.layout.camera);
//
//        mSurfaceView=(SurfaceView) this.findViewById(R.id.surface_camera);
//        mSurfaceHolder=mSurfaceView.getHolder();
//        mSurfaceHolder.addCallback(this);
//        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
//        displayer=(ImageView) findViewById(R.id.displayer);
//    }
//
//    public void updateImage(Bitmap bitmap) {
//        displayer.setImageBitmap(bitmap);
//    }
//
//    @Override
//    public void onPictureTaken(byte[] data, Camera camera) {
//    }
//
//    @Override
//    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
//        if (mPreviewRunning) {
//            mCamera.stopPreview();
//        }
//
//        Camera.Parameters p=mCamera.getParameters();
//        p.setPreviewSize(mWidth, mHeight);
//        p.setPreviewFormat(PixelFormat.YCbCr_420_SP);
//        mH264Encoder=new H264Encoder(mWidth, mHeight, this);
//        mCamera.setPreviewCallback(mH264Encoder);
//        mCamera.setParameters(p);
//        try {
//            mCamera.setPreviewDisplay(holder);
//        } catch (Exception ex) {
//        }
//        mCamera.startPreview();
//        mPreviewRunning=true;
//    }
//
//    @Override
//    public void surfaceCreated(SurfaceHolder holder) {
//        mCamera=Camera.open();
//    }
//
//    @Override
//    public void surfaceDestroyed(SurfaceHolder holder) {
//        if (mCamera!=null) {
//            mH264Encoder.destory();
//            mCamera.setPreviewCallback(null);
//            mCamera.stopPreview();
//            mPreviewRunning=false;
//            mCamera.release();
//            mCamera=null;
//        }
//    }
//
//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        try {
//            super.onConfigurationChanged(newConfig);
//            if (this.getResources().getConfiguration().orientation==Configuration.ORIENTATION_LANDSCAPE) {
//            } else if (this.getResources().getConfiguration().orientation==Configuration.ORIENTATION_PORTRAIT) {
//            }
//        } catch (Exception ex) {
//        }
//    }
//
//}
//
//class H264Encoder implements Camera.PreviewCallback {
//
//    public static final String TAG="H264Encoder";
//    long encoder=0;
//    RandomAccessFile raf=null;
//    byte[] h264Buff=null;
//    int mWidth=352, mHeight=288;
//    AndroidVideo mVideo;
//
//    static {
//        System.loadLibrary("H264Android");
//    }
//
//    private H264Encoder() {
//    }
//
//    public H264Encoder(int width, int height) {
//        mWidth=width;
//        mHeight=height;
//        encoder=CompressBegin(width, height);
//        h264Buff=new byte[width*height*8];
//        try {
//            File file=new File("/sdcard/camera.h264");
//            raf=new RandomAccessFile(file, "rw");
//        } catch (Exception ex) {
//            Log.v(TAG, ex.toString());
//        }
//    }
//
//    public H264Encoder(int width, int height, AndroidVideo video) {
//        mVideo=video;
//        mWidth=width;
//        mHeight=height;
//        encoder=CompressBegin(width, height);
//        h264Buff=new byte[width*height*8];
//        try {
//            File file=new File("/sdcard/camera.h264");
//            raf=new RandomAccessFile(file, "rw");
//        } catch (Exception ex) {
//            Log.v(TAG, ex.toString());
//        }
//
//        showVideo=true;
//        mStarted=true;
//        mFrameThread=new Thread(frameRunnable);
//        //mFrameThread.setPriority(4);
//        mFrameThread.start();
//    }
//
//    @Override
//    protected void finalize() {
//        CompressEnd(encoder);
//        if (null!=raf) {
//            try {
//                raf.close();
//            } catch (Exception ex) {
//                Log.v(TAG, ex.toString());
//            }
//        }
//        try {
//            super.finalize();
//        } catch (Throwable e) {
//            e.printStackTrace();
//        }
//    }
//
//    private native int CompressBuffer(long encoder, int type, byte[] in, int insize, byte[] out);
//
//    private native long CompressBegin(int width, int height);
//
//    private native int CompressEnd(long encoder);
//
//    @Override
//    public void onPreviewFrame(byte[] data, Camera camera) {
//        /*int result=CompressBuffer(encoder, -1, data, data.length, h264Buff);
//        Log.d(TAG, "result:"+result);
//        try {
//            if (result>0) {
//                raf.write(h264Buff, 0, result);
//                //drawFrame(h264Buff, mWidth, mHeight);
//            }
//        } catch (Exception ex) {
//            Log.v(TAG, ex.toString());
//        }*/
//
//        Camera.Parameters parameters=camera.getParameters();
//        final Camera.Size size=parameters.getPreviewSize();
//        putVideoFrame(data, size.width, size.height);
//    }
//
//    ByteBuffer mVideoFrame;
//    Bitmap mRGB8888Bitmap;
//
//    public void drawFrame(byte[] data, int width, int height) {
//        Log.d(TAG, "drawFrame width:"+width+" height:"+height+" data:"+data.length);
//        long start=System.currentTimeMillis();
//
//        try {
//            mVideoFrame=ByteBuffer.wrap(data);
//            if (mRGB8888Bitmap==null) {
//                mRGB8888Bitmap=Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
//            }
//            mRGB8888Bitmap.copyPixelsFromBuffer(mVideoFrame);
//            Canvas canvas=new Canvas(mRGB8888Bitmap);
//            canvas.drawBitmap(mRGB8888Bitmap, 0, 0, null);
//            mVideo.updateImage(mRGB8888Bitmap);
//
//            long end=System.currentTimeMillis();
//            Log.d(TAG, "��һ��ͼ����Ҫʱ�䣺"+(end-start));
//            mVideoFrame.rewind();
//
//        } catch (Exception e) {
//        }
//    }
//
//    //-----------------------------------
//    boolean showVideo=false;
//    boolean mStarted=false;
//    int mFps=15;
//    long delta=100l;
//    Thread mFrameThread=null;
//    private BlockingQueue<Object> mFrameList=new ArrayBlockingQueue<Object>(18);
//
//    private void putVideoFrame(byte[] data, int width, int height) {
//        if (!showVideo) {
//            Log.d(TAG, "putVideoFrame.�Ѿ�û����ʾ��Ƶ�ˡ�");
//            return;
//        }
//        Log.d(TAG, "putVideoFrame.");
//        ///synchronized(mFrameList){
//        Object[] obj=new Object[]{data, width, height};
//        mFrameList.offer(obj);
//        //}
//        //������̱������ͬ���ģ���Ԥ����֡���뻺���еȴ����͡�
//    }
//
//    Runnable frameRunnable=new Runnable() {
//
//        @Override
//        public void run() {
//            while (showVideo&&mStarted) {
//                sendVideoFrame();//�������֡����ȥ
//
//                if (mFrameList.size()>=mFps) {
//                    Log.d(TAG, "����֡�����ˡ�");
//
//                    Object[] obj=(Object[]) mFrameList.poll();
//                    mFrameList.clear();
//                    mFrameList.offer(obj);
//                }
//            }
//
//            Log.d(TAG, "���������߳�.");
//            //mFrameList.clear();
//        }
//    };
//
//    private void sendVideoFrame() {
//        //Log.d(TAG, "sendVideoFrame.");
//        Object[] obj=null;
//        obj=(Object[]) mFrameList.poll();
//        Log.d(TAG, "obj:"+obj);
//        if (null!=obj) {
//            long start=System.currentTimeMillis();
//            byte[] data=(byte[]) obj[0];
//            Integer width=(Integer) obj[1];
//            Integer height=(Integer) obj[2];
//
//            if (showVideo) {
//                int result=CompressBuffer(encoder, -1, data, data.length, h264Buff);
//                if (result>0) {
//                    try {
//                        Log.d(TAG, "result:"+result);
//                        raf.write(h264Buff, 0, result);
//                        //drawFrame(h264Buff, mWidth, mHeight);
//                    } catch (Exception ex) {
//                        Log.v(TAG, ex.toString());
//                    }
//
//                    long end=System.currentTimeMillis();
//                    start=end-start;
//                    //���Ʒ��͵�֡����
//                    end=delta-start;
//                    Log.d(TAG, "����һ��ͼ����Ҫʱ�䣺"+(start)+" width:"+width+" height:"+height);
//                    /*if (end>0) {//Ϊʲô������˯���أ��������ڵ�����ã�����ǿ������ʵҲû�б�Ҫһֱ������Ƶ֡�ģ�����Ϳ� �����Ʒ��͡�Ԥ�Ʒ���һ��Ϊ200���룬һ��Ϊ5֡���Ƿ��͵�ʱ��С��200�ľ�Ҫ�ȴ��ˡ������������ˣ��������200����ģ��Ͳ�Ҫ˯���ˡ����˸о������֡������12���ң������о��ǽ������ˣ������ϣ�������̫�������ǰ�᣺Ŀǰ�ֻ�Ҳ�ﵽ��20+���������硣������
//                        try {
//                            Thread.sleep(end);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }*/
//                }
//            } else {
//                Log.d(TAG, "�޷�������Ƶ��");
//            }
//            obj=null;
//        } else {
//            try {
//                Thread.sleep(20l);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    public void destory() {
//        showVideo=false;
//        mStarted=false;
//    }
//}