package com.xuanit.move.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.widget.TextView;

/**
 * 处理字符串工具类
 * 
 * @author
 * 
 */
public class StringUtils {

	/**
	 * 替换一个字符串中的某些指定字符
	 * 
	 * @param strData
	 *            String 原始字符串
	 * @param regex
	 *            String 要替换的字符串
	 * @param replacement
	 *            String 替代字符串
	 * @return String 替换后的字符串
	 */
	public static String replaceString(String strData, String regex, String replacement) {
		if (strData == null) {
			return null;
		}
		int index;
		index = strData.indexOf(regex);
		String strNew = "";
		if (index >= 0) {
			while (index >= 0) {
				strNew += strData.substring(0, index) + replacement;
				strData = strData.substring(index + regex.length());
				index = strData.indexOf(regex);
			}
			strNew += strData;
			return strNew;
		}
		return strData;
	}

	/**
	 * 替换字符串中特殊字符
	 */
	public static String encodeString(String strData) {
		if (strData == null) {
			return "";
		}
		strData = replaceString(strData, "&", "&amp;");
		strData = replaceString(strData, "<", "&lt;");
		strData = replaceString(strData, ">", "&gt;");
		strData = replaceString(strData, "&apos;", "&apos;");
		strData = replaceString(strData, "\"", "&quot;");
		return strData;
	}

	/**
	 * 还原字符串中特殊字符
	 */
	public static String decodeString(String strData) {
		strData = replaceString(strData, "&lt;", "<");
		strData = replaceString(strData, "&gt;", ">");
		strData = replaceString(strData, "&apos;", "&apos;");
		strData = replaceString(strData, "&quot;", "\"");
		strData = replaceString(strData, "&amp;", "&");
		return strData;
	}

	/**
	 * 是否是中文
	 */
	public static boolean isChinese(char c) {

		return Character.toString(c).matches("[\\u4E00-\\u9FA5]+");
	}

	/**
	 * 判断是否为空
	 * 
	 * @param text
	 * @return 如果为空，则返回true,反之则返回false
	 */
	public static boolean isNullOrEmpty(String text) {
		if (text == null || "".equals(text.trim()) || text.trim().length() == 0 || "null".equals(text)) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isZeroOrNull(String text) {

		if (text == null || "".equals(text.trim()) || "0".equals(text) || text.trim().length() == 0
				|| "null".equals(text.trim())) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 获得MD5加密字符串
	 * 
	 * @param str
	 *            字符串
	 * @return
	 */
	public static String getMD5Str(String str) {
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(str.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		byte[] byteArray = messageDigest.digest();
		StringBuffer md5StrBuff = new StringBuffer();
		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
			else
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
		}
		return md5StrBuff.toString();
	}

	/**
	 * 是否是 在 某个范围内的汉字(startIndex和endIndex 只接受同时为0情况其他情况视为没有范围)
	 * 
	 * @param str
	 * @param startIndex
	 *            默认设置为0 没有范围
	 * @param endIndex
	 *            默认设置为0 没有范围
	 * @return
	 */
	public static boolean isChineseChar(String str, int startIndex, int endIndex) {
		boolean temp = false;
		if (str.length() < startIndex || str.length() > endIndex) {
			return temp;
		}
		String regex = "^[\u4e00-\u9fa5]+$";
		if (startIndex > endIndex && startIndex >= 0) {
			regex = "^[\u4e00-\u9fa5]{" + startIndex + "," + endIndex + "}$";
		}
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(str);
		if (m.find()) {
			temp = true;
		}
		return temp;
	}

	/**
	 * 得到字符串长度
	 * 
	 * @param text
	 * @return
	 */
	public static int getCharCount(String text) {
		String Reg = "^[\u4e00-\u9fa5]+$";
		int result = 0;
		for (int i = 0; i < text.length(); i++) {
			String b = Character.toString(text.charAt(i));
			if (b.matches(Reg))
				result += 2;
			else
				result++;
		}
		return result;
	}

	/**
	 * 获取截取后的字符串
	 * 
	 * @param text
	 *            原字符串
	 * @param length
	 *            截取长度
	 * @return
	 */
	public static String getSubString(String text, int length) {
		return getSubString(text, length, true);
	}

	/**
	 * 对流转化成字符串
	 * 
	 * @param is
	 * @return
	 */
	public static String getStringByStream(InputStream is) {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			StringBuffer buffer = new StringBuffer();
			String line = "";
			while ((line = in.readLine()) != null) {
				buffer.append(line + "\n");
			}
			return buffer.toString().replaceAll("\n\n", "\n");
		} catch (OutOfMemoryError o) {
			System.gc();
		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * 获取截取后的字符串
	 * 
	 * @param text
	 *            原字符串
	 * @param length
	 *            截取长度
	 * @param isOmit
	 *            是否加上省略号
	 * @return
	 */
	public static String getSubString(String text, int length, boolean isOmit) {
		if (isNullOrEmpty(text)) {
			return "";
		}
		if (getCharCount(text) <= length + 1) {
			return text;
		}

		StringBuffer sb = new StringBuffer();
		String Reg = "^[\u4e00-\u9fa5]{1}$";
		int result = 0;
		for (int i = 0; i < text.length(); i++) {
			String b = Character.toString(text.charAt(i));
			if (b.matches(Reg)) {
				result += 2;
			} else {
				result++;
			}

			if (result <= length + 1) {
				sb.append(b);
			} else {
				if (isOmit) {
					sb.append("...");
				}
				break;
			}
		}
		return sb.toString();
	}

	/**
	 * 电话号码验证
	 * 
	 * @param phoneNumber
	 *            手机号码
	 * @return
	 */
	public static boolean validatePhoneNumber(String phoneNumber) {
		// Pattern pattern =
		// Pattern.compile("^((13[0-9])|(15[0-9])|(18[0-9]))\\d{8}$");
		Pattern pattern = Pattern.compile("^[-+/0-9]+$");
		Matcher m = pattern.matcher(phoneNumber);
		return m.matches();
	}

	/**
	 * 邮箱验证
	 * 
	 * @param mail
	 *            邮箱
	 * @return
	 */
	public static boolean validateEmail(String mail) {
		Pattern pattern = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
		Matcher m = pattern.matcher(mail);
		return m.matches();
	}

	/**
	 * 验证字符串内容是否合法
	 * 
	 * @param content
	 *            字符串内容
	 * @return
	 */
	public static boolean validateLegalString(String content) {
		String illegal = "`~!#%^&*=+\\|{};:'\",<>/?○●★☆☉♀♂※¤╬の〆";
		boolean legal = true;
		L1: for (int i = 0; i < content.length(); i++) {
			for (int j = 0; j < illegal.length(); j++) {
				if (content.charAt(i) == illegal.charAt(j)) {
					legal = false;
					break L1;
				}
			}
		}
		return legal;
	}

	/**
	 * 获取更新的时间
	 * 
	 * @param dateStr
	 * @return
	 * @throws Exception
	 */
	public static String getCreateString(String dateStr) {
		if (dateStr != null && !"".equals(dateStr)) {
			try {
				if (dateStr.indexOf(".") > -1) {
					dateStr = dateStr.substring(0, dateStr.indexOf("."));
				}
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date date = sdf.parse(dateStr);
				Calendar calendar = Calendar.getInstance();

				int oneMinuteUnit = 60;
				int oneHourUnit = 60 * 60;
				int oneDayUnit = 60 * 60 * 24;
				long i = (calendar.getTimeInMillis() - date.getTime()) / 1000;
				if (i < oneMinuteUnit && i > 0) {
					return i + "秒前";
				} else if (i < oneHourUnit && i > oneMinuteUnit) {
					return i / 60 + "分钟前";
				} else if (i < oneDayUnit && i > oneHourUnit) {
					return (i / (60 * 60)) + "小时前";
				} else {
					return dateStr;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 获取更新的时间
	 * 
	 * @param dateStr
	 * @return
	 * @throws Exception
	 */
	public static String getCreateString(Date date) {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (calendar.get(Calendar.YEAR) - (date.getYear() + 1900) > 0) {
			return sdf.format(date);
		} else if (calendar.get(Calendar.MONTH) - date.getMonth() > 0) {
			return sdf.format(date);
		} else if (calendar.get(Calendar.DAY_OF_MONTH) - date.getDate() > 0) {
			return sdf.format(date);
		} else if (calendar.get(Calendar.HOUR_OF_DAY) - date.getHours() > 0) {
			int i = calendar.get(Calendar.HOUR_OF_DAY) - date.getHours();
			return i + "小时前";
		} else if (calendar.get(Calendar.MINUTE) - date.getMinutes() > 0) {
			int i = calendar.get(Calendar.MINUTE) - date.getMinutes();
			return i + "分钟前";
		} else if (calendar.get(Calendar.SECOND) - date.getSeconds() > 0) {
			int i = calendar.get(Calendar.SECOND) - date.getSeconds();
			return i + "秒前";
		} else {
			return sdf.format(date);
		}
	}

	/**
	 * 如果为空显示暂无信息
	 * 
	 * @param tv
	 *            控件名
	 * @param str
	 *            信息
	 */
	public static void viewText(TextView tv, String str) {
		if (isNullOrEmpty(str)) {
			tv.setText("暂无资料");
		} else {
			tv.setText(str);
		}
	}

	/**
	 * 对流转化成字符串
	 * 
	 * @param is
	 * @return
	 */
	public static String getContentByString(InputStream is) {
		try {
			if (is == null)
				return null;
			byte[] b = new byte[1024];
			int len = -1;
			StringBuilder sb = new StringBuilder();
			while ((len = is.read(b)) != -1) {
				sb.append(new String(b, 0, len));
			}
			return sb.toString();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	/**
	 * 对流转化成字符串
	 * 
	 * @param is
	 * @return
	 */
	public static String getContentByString1(InputStream is) {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			StringBuffer buffer = new StringBuffer();
			String line = "";
			while ((line = in.readLine()) != null) {
				buffer.append(line + "\n");
			}
			return buffer.toString().replaceAll("\n\n", "\n");
		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * 截取字符串，去掉sign后边的
	 * 
	 * @param source
	 *            原始字符串
	 * @param sign
	 * @return
	 */
	public static String splitByIndex(String source, String sign) {
		String temp = "";
		if (isNullOrEmpty(source)) {
			return temp;
		}
		int length = source.indexOf(sign);
		if (length > -1) {
			temp = source.substring(0, length);
		} else {
			return source;
		}
		return temp;
	}

	/**
	 * 保留小数点后一位
	 * 
	 * @param d
	 * @return
	 * @throws Exception
	 */
	public static String formatNumber(double d) {
		try {
			DecimalFormat df = new DecimalFormat("#,##0.0");
			return df.format(d);
		} catch (Exception e) {
		}
		return "";
	}

	public static String formatNumber(String d) {
		return formatNumber(Double.parseDouble(d));
	}

	/*
	 * 获取字符串格式的当前时间
	 */
	public static String getStringDate() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/*
	 * 时间格式转换，yyyy-MM-dd xx:xx:xx为：yyyy-MM-dd
	 */
	public static String getStringDate(String date) {

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date d = new Date();
		try {
			d = formatter.parse(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String dateString = formatter.format(d);
		return dateString;

	}

	/*
	 * 截取sign后边的数字
	 */
	public static String getStringNum(String str, String sign) {
		String reg = ":split:";
		return str.replace(sign, reg).replaceAll(reg + "\\d+", "").replaceAll(" ", "").trim();

	}

	/**
	 * 时间格式转换，yyyy/MM/dd xx:xx:xx为：yyyy-MM-dd
	 * 
	 * @param date
	 * @return
	 */
	public static String getStringForDatenew(String date) {

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		Date d = new Date();
		try {
			d = formatter.parse(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String dateString = f.format(d);
		return dateString;

	}

	/**
	 * 时间格式转换，yyyy-MM-dd xx:xx:xx为：MM-dd xx:xx 不要年和秒
	 * 
	 * @param date
	 * @return
	 */
	public static String getStringForDate(String date) {

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		SimpleDateFormat f = new SimpleDateFormat("MM-dd HH:mm");
		Date d = new Date();
		try {
			d = formatter.parse(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String dateString = f.format(d);
		return dateString;

	}

	/**
	 * 时间格式转换，yyyy/MM/dd xx:xx:xx为：MM-dd xx:xx 不要年和秒
	 * 
	 * @param date
	 * @return
	 */
	public static String getStringForDate2(String date) {

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		SimpleDateFormat f = new SimpleDateFormat("MM-dd HH:mm");
		Date d = new Date();
		try {
			d = formatter.parse(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String dateString = f.format(d);
		return dateString;

	}

	/**
	 * 时间格式转换，yyyy/MM/dd xx:xx:xx为：yyyy-MM-dd HH:mm 不要年和秒
	 * 
	 * @param date
	 * @return
	 */
	public static String getStringForDate1(String date) {

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date d = new Date();
		try {
			d = formatter.parse(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String dateString = f.format(d);
		return dateString;

	}

	/**
	 * 时间格式转换，yyyy-MM-dd xx:xx:xx为：yyyy-MM-dd HH:mm 不要秒
	 * 
	 * @param date
	 * @return
	 */
	public static String getStringFormatDate(String date) {

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date d = new Date();
		try {
			d = formatter.parse(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String dateString = f.format(d);
		return dateString;
	}

	/**
	 * 获取指定时间MM-dd
	 * 
	 * @param date
	 * @return
	 */
	public static String getCurrentFormatDate(Date date) {
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("MM-dd");
			return formatter.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 获取指定时间的 星期
	 * 
	 * @param date
	 * @return
	 */
	public static String getCurrentFormatWeek(Date date) {
		try {
			String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
			if (w < 0)
				w = 0;
			return weekDays[w];
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 获取指定时间HH:mm
	 * 
	 * @param date
	 * @return
	 */
	public static String getCurrentFormatTime(Date date) {
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
			return formatter.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 获取指定时间指定格式的
	 * 
	 * @param date
	 * @return
	 */
	public static String getCurrentFormatTime(SimpleDateFormat formatter, Date date) {
		try {
			return formatter.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 时间格式转换指定格式转换成指定的格式
	 * 
	 * @param date
	 * @return
	 */
	public static String getStringFormatString(SimpleDateFormat srcFormat, SimpleDateFormat desFormat, String dateStr) {

		Date d = new Date();
		try {
			d = srcFormat.parse(dateStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String dateString = desFormat.format(d);
		return dateString;
	}

	/**
	 * 时间格式转换 将指定格式的时间转化Date
	 * 
	 * @param date
	 * @return
	 */
	public static Date getStringToDate(SimpleDateFormat formatter, String date) {
		Date d = new Date();
		try {
			return d = formatter.parse(date);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 判断价格是否为0或空
	 * 
	 * @param price
	 * @return
	 */
	public static boolean isPriceZero(String price) {
		if (isNullOrEmpty(price)) {
			return true;
		}
		price = splitByIndex(price, ".");
		if ("0".equals(price)) {
			return true;
		}
		return false;

	}

	/**
	 * 取价格的整数，去掉单位
	 * 
	 * @param price
	 * @return
	 */
	public static String getPrice(String price) {
		Pattern p = Pattern.compile("^\\d+");
		Matcher m = p.matcher(price);
		if (m.find()) {
			return m.group();
		}
		return "";
	}

	/**
	 * 判断是否全为数字
	 * 
	 * @param content
	 * @return
	 */
	public static boolean isAllNumber(String content) {
		boolean isAllNumber = true;
		if (isNullOrEmpty(content)) {
			return false;
		}
		for (int i = 0; i < content.length(); i++) {
			if (content.charAt(i) < '0' || content.charAt(i) > '9') {
				isAllNumber = false;
			}
		}
		return isAllNumber;
	}

	/**
	 * 整数转字节数组
	 * 
	 * @param i
	 * @return
	 */
	public static byte[] intToByte(int i) {
		byte[] bt = new byte[4];
		bt[0] = (byte) (0xff & i);
		bt[1] = (byte) ((0xff00 & i) >> 8);
		bt[2] = (byte) ((0xff0000 & i) >> 16);
		bt[3] = (byte) ((0xff000000 & i) >> 24);
		return bt;
	}

	/**
	 * 字节数组转整数
	 * 
	 * @param bytes
	 * @return
	 */
	public static int bytesToInt(byte[] bytes) {
		int num = bytes[0] & 0xFF;
		num |= ((bytes[1] << 8) & 0xFF00);
		num |= ((bytes[2] << 16) & 0xFF0000);
		num |= ((bytes[3] << 24) & 0xFF000000);
		return num;
	}

	/**
	 * 按字节截取字符串
	 * 
	 * @param orignal
	 *            原始字符串
	 * @param count
	 *            截取位数
	 * @return 截取后的字符串
	 * @throws UnsupportedEncodingException
	 *             使用了JAVA不支持的编码格式
	 */
	public static int substring(String orignal) throws UnsupportedEncodingException {
		// 原始字符不为null，也不是空字符串
		int num = 0;
		if (null != orignal && !"".equals(orignal)) {
			// 将原始字符串转换为GBK编码格式
			String orignal_byte = new String(orignal.getBytes("UTF-8"), "UTF-8");
			StringBuffer buff = new StringBuffer();
			char c;

			String s = "";
			for (int i = 0; i < orignal_byte.length(); i++) {
				// charAt(int index)也是按照字符来分解字符串的
				if (orignal_byte.length() > i) {
					c = orignal_byte.charAt(i);
					buff.append(c);
					if (isChineseChar(c)) {// 遇到中文汉字，字节总数+2
						num += 2; // 一般汉字在utf-8中为3个字节长度
					} else {
						num += 1;
					}

				}

			}

			// 要截取的字节数大于0，且小于原始字符串的字节数
		}
		return num;
	}

	public static boolean isChineseChar(char c) {
		// 如果字节数大于1，是汉字
		try {
			return String.valueOf(c).getBytes("UTF-8").length > 1;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 字符串补0
	 * 
	 * @param int型
	 * @return string
	 */
	public static String addZerodata(int m) {
		if (m < 10) {
			return "0" + (String.valueOf(m));
		} else {
			return String.valueOf(m);
		}

	}

	public static String addZerodata(String str) {
		int m = Integer.parseInt(str);
		if (m < 10) {
			return "0" + (String.valueOf(m));
		} else {
			return str;
		}

	}

}
