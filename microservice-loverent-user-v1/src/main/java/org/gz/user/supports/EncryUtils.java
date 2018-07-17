package org.gz.user.supports;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 
 * @author hangdx
 *
 */
public abstract class EncryUtils
{
	public static final String KEY_SHA = "SHA";
	public static final String KEY_SHA_256 = "SHA-256";
	public static final String KEY_MD5 = "MD5";

	/**
	 * MAC算法可选以下多种算法
	 * 
	 * <pre>
	 * HmacMD5 
	 * HmacSHA1 
	 * HmacSHA256 
	 * HmacSHA384 
	 * HmacSHA512
	 * </pre>
	 */
	public static final String KEY_MAC = "HmacMD5";

	/**
	 * BASE64解码
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("restriction")
	public static byte[] decryptBASE64(String key) throws Exception
	{
		return (new BASE64Decoder()).decodeBuffer(key);
	}

	/**
	 * BASE64编码
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("restriction")
	public static String encryptBASE64(byte[] key)
	{
		String result = null;
		result =  (new BASE64Encoder()).encodeBuffer(key).trim();
		return result;
	}

	/**
	 * MD5加密
	 * @param source
	 * @return
	 * @throws NoSuchAlgorithmException 
	 * @throws Exception
	 */
	public static String encryptMD5(String source) 
	{
        String result = null;
		MessageDigest md = null;  
	    try
		{
			md = MessageDigest.getInstance(KEY_MD5);
			byte[] digest = md.digest(source.getBytes());       
			result = bytes2Hex(digest);  
		} 
	    catch (NoSuchAlgorithmException e)
		{
		}    
	    return result;
	     

	}
	/**
	 * MD5加密
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptMD5(byte[] data) throws Exception
	{

		MessageDigest md5 = MessageDigest.getInstance(KEY_MD5);
		md5.update(data);

		return md5.digest();

	}
	/**
	 * 先MD5加密，再Base64编码
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static String encryptMD5BASE64(String data) 
	{
		String result = "";
		result =  encryptBASE64(encryptMD5(data).getBytes());
		return result;
	}
	
	/**
	 * SHA加密
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptSHA(byte[] data) throws Exception
	{

		MessageDigest sha = MessageDigest.getInstance(KEY_SHA);
		sha.update(data);

		return sha.digest();

	}
	/**
	 * SHA-256加密
	 * @param strSrc  要加密的字符串
	 * @return        得到64位字符串
	 */
	public static String encryptSHA256(String strSrc) 
	{
        MessageDigest md = null;
        String strDes = null;

        byte[] bt = strSrc.getBytes();
        try 
        {
            md = MessageDigest.getInstance(KEY_SHA_256);
            md.update(bt);
            //to HexString
            strDes = bytes2Hex(md.digest()); 
        } 
        catch (NoSuchAlgorithmException e) 
        {
            return null;
        }
        return strDes;
    }

    

	/**
	 * 初始化HMAC密钥
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String initMacKey() throws Exception
	{
		KeyGenerator keyGenerator = KeyGenerator.getInstance(KEY_MAC);

		SecretKey secretKey = keyGenerator.generateKey();
		return encryptBASE64(secretKey.getEncoded());
	}

	/**
	 * HMAC加密
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptHMAC(byte[] data, String key) throws Exception
	{

		SecretKey secretKey = new SecretKeySpec(decryptBASE64(key), KEY_MAC);
		Mac mac = Mac.getInstance(secretKey.getAlgorithm());
		mac.init(secretKey);

		return mac.doFinal(data);
	}
	/**
	 * 轮换字节数组为十六进制字符串 
	 * @param bts
	 * @return
	 */
	private static String bytes2Hex(byte[] bts) 
    {
        String des = "";
        String tmp = null;
        for (int i = 0; i < bts.length; i++) 
        {
            tmp = (Integer.toHexString(bts[i] & 0xFF));
            if (tmp.length() == 1) 
            {
                des += "0";
            }
            des += tmp;
        }
        return des;
    }
	
	
}
