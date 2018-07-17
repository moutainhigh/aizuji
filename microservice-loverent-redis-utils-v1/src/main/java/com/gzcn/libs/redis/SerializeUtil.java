package com.gzcn.libs.redis;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * 
 * 对象序列化和反序列化
 *
 */
public class SerializeUtil {
	public static byte[] serialize(Object object) throws Exception {
		ObjectOutputStream oos = null;
		ByteArrayOutputStream baos = null;
		try {
			// 序列化
			baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			oos.writeObject(object);
			byte[] bytes = baos.toByteArray();
			return bytes;
		} catch (Exception e) {
			throw e;
		}finally {
			if(oos != null){
				oos.close();
			}
			if(baos != null){
				baos.close();
			}
		}

	}

	public static Object unserialize(byte[] bytes) throws Exception {
		ByteArrayInputStream bais = null;
		ObjectInputStream ois = null;
		try {
			// 反序列化
			bais = new ByteArrayInputStream(bytes);
			ois = new ObjectInputStream(bais);
			return ois.readObject();
		} catch (Exception e) {
			throw e;
		}finally {
			if(ois != null){
				ois.close();
			}
			if(bais != null){
				bais.close();
			}
			
		}
	}
}
