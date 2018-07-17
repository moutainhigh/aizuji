package org.gz.thirdParty.utils;

import java.io.FileOutputStream;
import java.io.OutputStream;

import org.apache.commons.codec.binary.Base64;

public class Base64Trans {
	
	public static byte[] GenerateImageByte(String imgStr){
		if (imgStr == null) //图像数据为空      
            return null;      
        Base64 base64 = new Base64();  
        
        //Base64解码      
        byte[] b = base64.decode(imgStr.getBytes());  
        for(int i=0;i<b.length;++i)      
        {      
            if(b[i]<0)      
            {//调整异常数据      
                b[i]+=256;      
            }      
        }    
        return b;
	}
	
	public static boolean GenerateImage(byte[] b,String imgPath){
		if (b == null) //图像数据为空      
        return false;    
		//生成jpeg图片     
		try       
        {
	        String imgFilePath = imgPath;//新生成的图片      
	        OutputStream out = new FileOutputStream(imgFilePath);          
	        out.write(b);      
	        out.flush();      
	        out.close();      
	        return true;
        }catch (Exception e){      
            return false;      
        } 
	}
}
