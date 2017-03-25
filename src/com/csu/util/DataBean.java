package com.csu.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

import org.apache.commons.lang.ArrayUtils;

public class DataBean {
	private byte[] data;//传输对象的数组
	private short task;//2
	private byte lenName;//来源名字长度
	private byte[] name;//来源名字
	private byte lenUserName;
	private byte[] userName;
	private short length;//2
	private byte tag;//1
	private short len;//2
	private Object org;//?
	
	
	public DataBean(){}
	/**
	 * @method 
	 * @param task1 任务编号 2
	 * @param id1 来源 1
	 * @param tag1 tlv格式的标签 1
	 * @param str value 2
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public byte[] ecode(int task1 ,String name1, String name2, Object str ) throws UnsupportedEncodingException {
		byte[] task = TransCoding.shortTobytes((short)task1);//2
		byte[] name = name1.getBytes();
		byte nameLen = (byte)name.length;//1
		byte[] userName = name2.getBytes();
		byte userNameLen = (byte)userName.length;//1
		byte[] length = null;
		byte tag = -1;
		byte[] org = null;
		byte[] len = null;
		if(str instanceof Integer){
			tag = (byte) Constant.Tag_Int;//1
			org = TransCoding.intToBytes((Integer)str);
			length =TransCoding.shortTobytes((short)(8 + nameLen + userNameLen + org.length));//2
			len = TransCoding.shortTobytes((short)(org.length));//2
		}
		
		if(str instanceof String){
			tag = (byte) Constant.Tag_String;//1
			org = ((String)str).getBytes();
			length =TransCoding.shortTobytes((short)(8 + nameLen + userNameLen + org.length));//2
			len = TransCoding.shortTobytes((short)(org.length));//2
		}
		
		if(str instanceof byte[]){
			tag = (byte) Constant.Tag_Bytes;//1
			org = (byte[])str;
			length =TransCoding.shortTobytes((short)(8 + nameLen + userNameLen + org.length));//2
			len = TransCoding.shortTobytes((short)(org.length));//2
		}
		
		data = ArrayUtils.add(task,nameLen);//传输任务编号及来源
		data = ArrayUtils.addAll(data,name);//来源名字
		data = ArrayUtils.add(data,userNameLen);//来源名字
		data = ArrayUtils.addAll(data,userName);//来源名字
		data = ArrayUtils.addAll(data,length);//传输总长度
		data = ArrayUtils.add(data,tag);//传输value内容的格式
		data = ArrayUtils.addAll(data,len);//传输value内容的长度
		data = ArrayUtils.addAll(data,org);//传输value的具体内容
		return data;
	}
	
	public void decode(byte[] data) throws IOException{
		ByteBuffer buff = ByteBuffer.wrap(data);
		task = buff.getShort();
		lenName = buff.get();
		name = new byte[lenName];
		buff.get(name);
		length = buff.getShort();
		tag = buff.get();
		len = buff.getShort();
		switch(tag){
		case Constant.Tag_Int:
			org = (int) buff.getInt();
			break;
		case Constant.Tag_String:
			byte[] str = new byte[len];
			buff.get(str);
			org = new String(str);
			break;
		case Constant.Tag_Bytes:
			byte[] listByte = new byte[len];
			buff.get(listByte);
			ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(listByte));
			try {
				org = in.readObject();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	
	public byte[] getName() {
		return name;
	}
	public void setName(byte[] name) {
		this.name = name;
	}
	public short getTask() {
		return task;
	}

	public void setTask(short task) {
		this.task = task;
	}

	public byte getlenName() {
		return lenName;
	}

	public void setId(byte lenName) {
		this.lenName = lenName;
	}

	public short getLength() {
		return length;
	}

	public void setLength(short length) {
		this.length = length;
	}

	public byte getTag() {
		return tag;
	}

	public void setTag(byte tag) {
		this.tag = tag;
	}

	public short getLen() {
		return len;
	}

	public void setLen(short len) {
		this.len = len;
	}

	public Object getOrg() {
		return org;
	}

	public void setOrg(Object org) {
		this.org = org;
	}
	public byte[] getData() {
		return data;
	}
	public void setData(byte[] data) {
		this.data = data;
	}
	public byte getLenName() {
		return lenName;
	}
	public void setLenName(byte lenName) {
		this.lenName = lenName;
	}
	public byte getLenUserName() {
		return lenUserName;
	}
	public void setLenUserName(byte lenUserName) {
		this.lenUserName = lenUserName;
	}
	public byte[] getUserName() {
		return userName;
	}
	public void setUserName(byte[] userName) {
		this.userName = userName;
	}

}
