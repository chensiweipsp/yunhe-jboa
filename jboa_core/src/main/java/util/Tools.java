package util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Tools {

	/**
	 * md5加密算法
	 * 
	 * @param str
	 *            待加密字符串
	 * @return 加密后的字符�?
	 */
	public static String md5Encrypt(String data) {
		StringBuffer md5Value = new StringBuffer();
		if (data == null) {
			return "";
		} else {
			MessageDigest md5 = null;
			try {
				md5 = MessageDigest.getInstance("MD5");
			} catch (NoSuchAlgorithmException ex) {
				ex.printStackTrace();
			}
			// sun.misc.BASE64Encoder baseEncoder = new
			// sun.misc.BASE64Encoder();
			try {
				byte[] md5Bytes = md5.digest(data.getBytes("utf-8"));

				for (int i = 0; i < md5Bytes.length; i++) {
					int val = ((int) md5Bytes[i]) & 0xff;
					if (val < 16) {
						md5Value.append("0");
					}
					md5Value.append(Integer.toHexString(val));
				}
				// md5Value =
				// baseEncoder.encode(hexValue.toString().getBytes());

			} catch (Exception ex) {
				ex.printStackTrace();
			}
			return md5Value.toString();
		}
	}

	public static boolean getBoolean(Integer count) {

		boolean flag = false;
		if (count == 0) {
			flag = false;
		} else {
			flag = true;
		}
		return flag;
	}

	/**
	 * 获取六位数密码
	 * 
	 * @return
	 */
	public static String getRandomPwd() {
		int max = 999999;
		int min = 000000;
		int num = (int) (Math.random() * (max - min)) + min;
		String passWord = String.valueOf(num);
		int shao = 6 - passWord.length();
		switch (shao) {
		case 1:
			passWord = passWord + "7";
			break;
		case 2:
			passWord = passWord + "92";
			break;
		case 3:
			passWord = passWord + "033";
			break;
		case 4:
			passWord = passWord + "0404";
			break;
		case 5:
			passWord = passWord + "00205";
			break;
		}
		return passWord;
	}

	/**
	 * 加密
	 * 
	 * @param pwd
	 * @return
	 */
	public static String encryptionPWD(String pwd) {
		String encryptionStr = "";
		if (null != pwd && pwd.length() > 0) {
			for (int i = 0; i < pwd.length(); i++) {
				String ascIIintValue = Integer.toString(pwd.charAt(i));// 把一个字符转换为整数的ascii码
				encryptionStr = encryptionStr + ascIIintValue;
			}
		}
		return encryptionStr;
	}

	public static String getNowDateStr() {
		String format = "yyyy年MM月dd日 HH:mm:ss";
		DateFormat df = new SimpleDateFormat(format);
		return df.format(new Date());
	}

	public static String getWeekOfDate() {
		Date dt = new Date();
		String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0)
			w = 0;
		return weekDays[w];
	}

	/**
	 * 解密
	 * 
	 * @param encryptionpwd
	 * @return
	 */
	public static String decrypt(String encryptionpwd) {

		String decryptPwd = "";
		String s1 = encryptionpwd.substring(0, 2);
		String s2 = encryptionpwd.substring(2, 4);
		String s3 = encryptionpwd.substring(4, 6);
		String s4 = encryptionpwd.substring(6, 8);
		String s5 = encryptionpwd.substring(8, 10);
		String s6 = encryptionpwd.substring(10, 12);

		int iValue1 = Integer.parseInt(s1, 10);
		int iValue2 = Integer.parseInt(s2, 10);
		int iValue3 = Integer.parseInt(s3, 10);
		int iValue4 = Integer.parseInt(s4, 10);
		int iValue5 = Integer.parseInt(s5, 10);
		int iValue6 = Integer.parseInt(s6, 10);

		char tempC1 = (char) iValue1;// 转换为一个字符
		char tempC2 = (char) iValue2;// 转换为一个字符
		char tempC3 = (char) iValue3;// 转换为一个字符
		char tempC4 = (char) iValue4;// 转换为一个字符
		char tempC5 = (char) iValue5;// 转换为一个字符
		char tempC6 = (char) iValue6;// 转换为一个字符

		decryptPwd = String.valueOf(tempC1);
		decryptPwd = decryptPwd + String.valueOf(tempC2);
		decryptPwd = decryptPwd + String.valueOf(tempC3);
		decryptPwd = decryptPwd + String.valueOf(tempC4);
		decryptPwd = decryptPwd + String.valueOf(tempC5);
		decryptPwd = decryptPwd + String.valueOf(tempC6);
		return decryptPwd;

	}

	public static List<Object> getDiffentNums(List<?> list1, List<?> list2) {
		List<Object> list3 = new ArrayList<Object>();
		for (int i = 0; i < list1.size(); i++) {
			int a = (Integer) list1.get(i);
			int sf = 0;
			for (int j = 0; j < list2.size(); j++) {
				int b = (Integer) list2.get(j);
				if (a == b) {
					sf = sf + 1;
				}

			}
			if (sf == 0) {
				list3.add(a);
			}

		}
		return list3;
	}

	/**
	 * 根据报名所选的体育科目，进行比对
	 * 
	 * @param list
	 * @param ids
	 * @return
	 */
	public static boolean getTyCompare(List<Object> list, String[] ids) {
		boolean falg = false;
		for (int j = 0; j < ids.length; j++) {

			for (int i = 0; i < list.size(); i++) {

				if (!ids[j].equals(list.get(i))) {
					falg = false;
					continue;

				} else
					falg = true;
				return falg;
			}
		}
		return falg;
	}
}
