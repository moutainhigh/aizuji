/**
 * 
 */
package org.gz.common.utils;

import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;

/**
 * @Title:UUID生成类
 * @author hxj
 * @date 2017年12月17日 下午1:44:16
 */
public class UUIDUtils {
	
	
	private static final String[] CHARS = new String[] { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l",
			"m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6",
			"7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R",
			"S", "T", "U", "V", "W", "X", "Y", "Z" };

	//定义时间格式
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMddHHmmssS");
	
	//定义格式化位置
	private static final FieldPosition POS = new FieldPosition(0);

	private static final DecimalFormat NUMBER_FORTMAT = new DecimalFormat("00000");
	
	// 序列号，默认从0开始
	private static int seq = 0;

	//最大序列号，最小必须是4位数
	private static final int MAX_SEQ = 99999;
	
	
	/**
	 * @Description: 生成8位随机UUID字符串(数字+字母),百万级内安全(可参考单元测试用例)
	 * @return String
	 */
	public static String genShortUUID() {
		return genShortUUID(null);
	}

	/**
	 * @Description: 生成带前辍的8位随机UUID字符串(数字+字母)
	 * @return String
	 */
	public static String genShortUUID(String prefix) {
		StringBuffer shortBuffer = new StringBuffer();
		String uuid = UUID.randomUUID().toString().replace("-", "");
		for (int i = 0; i < 8; i++) {
			String str = uuid.substring(i * 4, i * 4 + 4);
			int x = Integer.parseInt(str, 16);
			shortBuffer.append(CHARS[x % 0x3E]);
		}
		return prefix != null && prefix.length() > 0 ? prefix + shortBuffer.toString() : shortBuffer.toString();
	}

	/**
	 * 
	* @Description: 根据时间戳+序列号生成22位UUID(17位时间戳，5位序列号).
	* @Description: 注意：此处因涉及共享变量(seq)修改，为了线程安全，方法签名须加synchronized修饰符
	* @return String
	 */
	public synchronized static String genDateUUID(String prefix) {
		Calendar ca = Calendar.getInstance();
		StringBuffer buf = new StringBuffer();
		DATE_FORMAT.format(ca.getTime(), buf, POS);
		NUMBER_FORTMAT.format(seq, buf, POS);
		if (seq == MAX_SEQ) {
			seq = 0;
		} else {
			seq++;
		}
		return prefix != null && prefix.length() > 0 ? prefix+buf.toString():buf.toString();
	}
}
