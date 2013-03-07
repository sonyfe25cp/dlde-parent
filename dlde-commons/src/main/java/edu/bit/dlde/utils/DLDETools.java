package edu.bit.dlde.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class DLDETools {
	
	static DLDELogger logger=new DLDELogger();
	
	public static Object byteArraytoObject(byte[] bytes)throws Exception{
		Object o=null;
		try {
			ByteArrayInputStream in =new ByteArrayInputStream(bytes);
			ObjectInputStream oin;
			oin = new ObjectInputStream(in);
			o=oin.readObject();
			oin.close();
			in.close();
		} catch (Exception e) {
			logger.error("can't covert these bytes to Object, please check the input !");
			throw e;
		}
		return o;
	}
	
	public static byte[] getBytes(Object obj)throws Exception {
		byte[] arr=null;
		try{
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			ObjectOutputStream out = new ObjectOutputStream(bout);
			out.writeObject(obj);
			out.flush();
			arr = bout.toByteArray();
			out.close();
			bout.close();
		} catch (Exception e) {
			logger.error("can't covert this Object to bytes, please check the input !");
			throw e;
		}
		return arr;
	}

}
