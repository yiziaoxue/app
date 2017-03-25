//package com.csu.util;
//
//import java.io.IOException;
//import java.net.DatagramPacket;
//import java.net.DatagramSocket;
//import java.net.InetAddress;
//import java.util.HashMap;
//import java.util.Map;
//
//import com.csu.service.Task;
//
//import android.util.Log;
//
//public class DataSend extends Thread{
//	private DatagramSocket socket;
//	private InetAddress address;
//	private int port;
//	public byte[] back;//接收返回信息
//	public DataSend(DatagramSocket socket,InetAddress address,int port){
//		this.socket = socket;
//		this.address = address;
//		this.port = port;
//	}
//	
//	public void send(byte[] data){
//		DatagramPacket dataGramPacket = new DatagramPacket(data, data.length, address, port);
//		try {
//			socket.send(dataGramPacket);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		Lg.m("数据发送成功");
//	}
//	
//	public byte[] get(){
//		Log.i("Main", "成功进入获得返回值的方法");
//		return back;
//	}
//
//	@Override
//	public void run() {
//		super.run();
//		byte[] receive = new byte[1024];
//		DataBean bean = new DataBean();
//		DatagramPacket backPacket = new DatagramPacket(back, back.length); 
//		Task task = null;
//		Map<String,byte[]> map = null;
//		while(true){
//			try {
//				socket.receive(backPacket);
//				Log.i("DataSend", "成功接收到数据包");
//				back = receive;
//				bean.decode(back);
//				task = new Task();
//				map = new HashMap<String,byte[]>();
//				map.put("data", back);
//				task.setTaskId(bean.getTask());
//				task.setTaskParam(map);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//	}
//}	
