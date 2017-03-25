package com.csu.client;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;

import com.csu.service.ImWeiboActivity;
import com.csu.service.MainService;
import com.example.client.R;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PreviewCallback;
import android.hardware.Camera.Size;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;

public class VideoChatActivity extends Activity implements SurfaceHolder.Callback,PreviewCallback,ImWeiboActivity{
    private SurfaceView surfaceView;
    private RelativeLayout layout;
    private Button recordbutton;
    private Button stopbutton;
    private String comeName;
    private Camera camera;
    private boolean isPreview = false;
	//远端ip
//	String remoteIP = "192.168.43.134";
	private byte[] byteBuffer = new byte[51200];
	public boolean keepRunning = false;
	int width, height;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_videochat);
//        surfaceViewback = (SurfaceView) this.findViewById(R.id.surfaceView);
        surfaceView = (SurfaceView) this.findViewById(R.id.surfaceView);
        //下面设置surfaceView不维护自己的缓冲区,而是等待屏幕的渲染引擎将内容推送到用户面前
        surfaceView.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        surfaceView.getHolder().setFixedSize(640, 480);
        surfaceView.getHolder().setKeepScreenOn(true);
        surfaceView.getHolder().addCallback(this);
      
        layout = (RelativeLayout) this.findViewById(R.id.layout);
        recordbutton = (Button) this.findViewById(R.id.recordbutton);
        stopbutton = (Button) this.findViewById(R.id.stopbutton);
       //开启对方视频接收线程
//        videoReceivePlay= new VideoReceivePlay(6240, surfaceview, 176, 144);
//        videoReceivePlay.start();
      		
        Log.i("VideoChatActivity", "開始接收數據");
        Bundle extras = getIntent().getExtras();  
        if (!extras.isEmpty()) {  
            comeName =  extras.getString("name");  
            Log.i("ChatTestActivity", "进入视频聊天界面"+comeName);
        }
        extras.clear();
        MainService.chatActivity.put(comeName, this);
    }

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(event.getAction() == MotionEvent.ACTION_DOWN){
			layout.setVisibility(ViewGroup.VISIBLE);
		}
		return super.onTouchEvent(event);
	}
    
    public void record(View v){
//    	Thread thread = null;
    	switch (v.getId()) {
    	
		case R.id.recordbutton:
			camera.startPreview();//处理完数据之后可以预览
			camera.autoFocus(null); // 自动对焦 
//			thread = new Thread(run);
//			thread.start();
			isPreview = true;
			recordbutton.setEnabled(false);
			stopbutton.setEnabled(true);
			break;

		case R.id.stopbutton:
			if(camera!=null){
				if(isPreview){//如果正在预览 
				camera.stopPreview();
				camera.setPreviewCallback(null);
//				thread.stop();
				camera.release();
				}
			}
			recordbutton.setEnabled(true);
			stopbutton.setEnabled(false);
			break;
		}
    }
    private void log(String msg){
    	Log.i("視頻調試", msg);
    }
    //視頻幀回調
	@Override
	public void onPreviewFrame(byte[] data, Camera camera) {
		Size size = camera.getParameters().getPreviewSize(); 
		try {
		//获取一帧图像
		YuvImage yuvImage = new YuvImage(data,ImageFormat.NV21, size.width, size.height, null);
		ByteArrayOutputStream myoutputstream = new ByteArrayOutputStream();
		//把原始图像压缩成jpeg
		yuvImage.compressToJpeg(new Rect(0, 0, size.width, size.height), 80, myoutputstream);
//		Bitmap bmp = BitmapFactory.decodeByteArray(stream.toByteArray(), 0, stream.size());
		myoutputstream.flush();
		try{
        	//将图像数据通过Socket发送出去
            Socket tempSocket = new Socket(MainService.address, 6000);
            OutputStream outsocket = tempSocket.getOutputStream();
            ByteArrayInputStream inputstream = new ByteArrayInputStream(myoutputstream.toByteArray());
            int amount;
            while ((amount = inputstream.read(byteBuffer)) != -1) {
                outsocket.write(byteBuffer, 0, amount);
            }
            myoutputstream.flush();
            myoutputstream.close();
            tempSocket.close();                   
        } catch (IOException e) {
            e.printStackTrace();
        }
		myoutputstream.close();
		//把视频帧发送到远端
//		Task task = new Task(Constant.Task_Video_Chat,comeName);
//		log(stream.toByteArray().length+"打印长度---------------------");
//		task.addData(stream.toByteArray());
//		MainService.allTask.add(task);
		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		
	@Override
	//設置橫屏豎屏改變時的操作
	public void surfaceChanged(SurfaceHolder holder, int format, int width,  int height) {
	 
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		try{  
            camera = Camera.open(0);  
            Parameters params = camera.getParameters();  
            params.setPreviewSize(640, 480);  
//            params.setPictureSize(640, 480);    // 设置照片的大小
            params.setPreviewFpsRange(20,30);
            params.setPictureFormat(ImageFormat.NV21); // 设置图片格式 
//            camera.setParameters(params);   // android2.3.3以后不需要此行代码
            camera.setPreviewDisplay(holder); 
            camera.setPreviewCallback(this);  
        }catch(Exception e){  
            e.printStackTrace();  
            camera.setPreviewCallback(null);
   	     	camera.release();//如果在设置摄像头的时候出现异常，在这里释放资源
        } 
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		if(camera!=null){
			if(isPreview){//如果正在预览 
			camera.stopPreview();
			camera.setPreviewCallback(null);
			camera.release();
			}
		}
		
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void refresh(Object... param) {
		// TODO Auto-generated method stub
		
	}
	
	/** 
	    * 判断某个界面是否在前台 
	    *  
	    * @param context 
	    * @param className 
	    *            某个界面名称 
	    */  
	@Override
	public boolean isForeground() {  
		 String packageName= this.getPackageName(); 
		 
		 String topActivityClassName = null;
         ActivityManager activityManager = (ActivityManager)(this.getSystemService(android.content.Context.ACTIVITY_SERVICE )) ;
         List<RunningTaskInfo> runningTaskInfos = activityManager.getRunningTasks(1);
         
         if(runningTaskInfos != null){
             ComponentName f = runningTaskInfos.get(0).topActivity;
             topActivityClassName = f.getClassName();
         }
         
	     System.out.println("packageName="+packageName+",topActivityClassName="+topActivityClassName);
	     if (packageName!=null&&topActivityClassName!=null&&topActivityClassName.startsWith(packageName)) {
	         System.out.println("---> isRunningForeGround");
	         return true;
	     } else {
	         System.out.println("---> isRunningBackGround");
	         return false;
	     }
	}  

	//循环接受视频数据
//	Runnable run = new Runnable(){
//	public void run() {
//		try {
//			datagramSocket = new DatagramSocket(8989);
//			datagramPacket = new DatagramPacket(buffer, buffer.length);
//			keepRunning = true;
//			while (keepRunning) {
//				datagramSocket.receive(datagramPacket);
//				Canvas canvas = surfaceView.getHolder().lockCanvas();
//				//解码jpeg
//				Bitmap bitmap = BitmapFactory.decodeByteArray(buffer, 0,datagramPacket.getLength());
//				//缩放图像至指定的分辨率
//				bitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);					
//				canvas.drawBitmap(bitmap, 0, 0, null);
//				surfaceView.getHolder().unlockCanvasAndPost(canvas);
//			}
//			datagramSocket.close();
//			datagramSocket = null;
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			datagramSocket.close();
//		}
//	  }
//	};
}
