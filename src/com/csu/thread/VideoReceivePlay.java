//package com.csu.thread;
//
//import java.io.IOException;
//import java.net.DatagramPacket;
//import java.net.DatagramSocket;
//import java.net.SocketException;
//
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.Canvas;
//import android.view.SurfaceView;
//
////接收视频帧并播放出来
//public class VideoReceivePlay extends Thread{
//	private DatagramSocket datagramSocket;
//	private DatagramPacket datagramPacket;
//	private int listenPort;
//	private byte[] buffer = new byte[51200];
//	public boolean keepRunning;
//	SurfaceView surfaceView;
//	int width, height;
//
//	//监听端口listenPort，把收到的图像显示在surfaceView上，显示出来的分辨率为width*height
//	public VideoReceivePlay(int listenPort, SurfaceView surfaceView, int width, int height){
//		this.listenPort= listenPort;
//		this.surfaceView= surfaceView;
//		this.width= width;
//		this.height= height;
//	}
//	@Override
//	public void run() {
//		// TODO Auto-generated method stub
//		super.run();
//
//		try {
//			datagramSocket = new DatagramSocket(listenPort);
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
//		} catch (SocketException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			datagramSocket.close();
//
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			datagramSocket.close();
//
//		}
//
//	}
//
//
//}
