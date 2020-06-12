package com.ctgu.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.houbb.opencc4j.util.ZhConverterUtil;

public class StringUtils
{
	/**
	 * 空字符串
	 */
	public static final String EMPTY = "";

	/**
	 * 空格
	 */
	public static final String BLANK = " ";

	private static Pattern PATTERN = Pattern.compile("([A-Za-z\\d]+)(_)?");

	private static Pattern PATTERN2 = Pattern.compile("[A-Z]([a-z\\d]+)?");

	// private static Pattern PATTERN_LETTER_OR_NUMBER = Pattern.compile("^[a-z0-9A-Z]+$");

	/**
	 * 判断给定字符串是否仅包含数字或字母
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isLetterDigit(String str)
	{
		String regex = "^[a-z0-9A-Z]+$";
		return str.matches(regex);
	}

	/**
	 * 主键
	 * 
	 * @return
	 */
	public static String primaryKey()
	{
		return UUID.randomUUID()
				.toString()
				.toLowerCase();
	}

	/**
	 * 下划线转驼峰法
	 * 
	 * @param line
	 *            源字符串
	 * @param smallCamel
	 *            大小驼峰,是否为小驼峰
	 * @return 转换后的字符串
	 */
	public static String underlineToCamel(String line, boolean smallCamel)
	{
		if (line == null || "".equals(line))
		{
			return "";
		}
		StringBuffer sb = new StringBuffer();
		// Pattern pattern = Pattern.compile("([A-Za-z\\d]+)(_)?");
		Matcher matcher = PATTERN.matcher(line);
		while (matcher.find())
		{
			String word = matcher.group();
			sb.append(smallCamel && matcher.start() == 0 ? Character.toLowerCase(word.charAt(0))
					: Character.toUpperCase(word.charAt(0)));
			int index = word.lastIndexOf('_');
			if (index > 0)
			{
				sb.append(word.substring(1, index)
						.toLowerCase());
			}
			else
			{
				sb.append(word.substring(1)
						.toLowerCase());
			}
		}
		return sb.toString();
	}

	/**
	 * 驼峰法转下划线
	 * 
	 * @param line
	 *            源字符串
	 * @return 转换后的字符串
	 */
	public static String camelToUnderline(String line)
	{
		if (line == null || "".equals(line))
		{
			return "";
		}
		line = String.valueOf(line.charAt(0))
				.toUpperCase()
				.concat(line.substring(1));
		StringBuffer sb = new StringBuffer();
		// Pattern pattern2 = Pattern.compile("[A-Z]([a-z\\d]+)?");
		Matcher matcher = PATTERN2.matcher(line);
		while (matcher.find())
		{
			String word = matcher.group();
			sb.append(word.toLowerCase());
			sb.append(matcher.end() == line.length() ? "" : "_");
		}
		return sb.toString();
	}

	/**
	 * 转换为繁体字
	 * 
	 * @param original
	 * @return
	 */
	public static String convertToTraditional(String original)
	{
		String result = ZhConverterUtil.convertToTraditional(original);
		return result;
	}

	/**
	 * 转换为简体字
	 * 
	 * @param original
	 * @return
	 */
	public static String convertToSimple(String original)
	{
		String result = ZhConverterUtil.convertToSimple(original);
		return result;
	}

	/**
	 * 目前只是简单的校验手机号是否为11位数字，暂不校验手机号是否有效
	 * 
	 * @param phoneNumber
	 *            要校验的手机号
	 * @return 匹配时返回true
	 */
	public static boolean checkPhoneNumberIsValid(String phoneNumber)
	{
		String regex = "1[3456789]\\d{9}";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(phoneNumber);

		boolean isMatched = matcher.matches();
		// System.out.println(isMatched);
		return isMatched;
	}

	// Empty checks
	// -----------------------------------------------------------------------
	/**
	 * <p>
	 * Checks if a String is empty ("") or null.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.isEmpty(null)      = true
	 * StringUtils.isEmpty("")        = true
	 * StringUtils.isEmpty(" ")       = false
	 * StringUtils.isEmpty("bob")     = false
	 * StringUtils.isEmpty("  bob  ") = false
	 * </pre>
	 * <p>
	 * NOTE: This method changed in Lang version 2.0. It no longer trims the String. That functionality is available in
	 * isBlank().
	 * </p>
	 *
	 * @param str
	 *            the String to check, may be null
	 * @return <code>true</code> if the String is empty or null
	 */
	public static boolean isEmpty(String str)
	{
		return str == null || str.length() == 0;
	}

	/**
	 * <p>
	 * Checks if a String is whitespace, empty ("") or null.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.isBlank(null)      = true
	 * StringUtils.isBlank("")        = true
	 * StringUtils.isBlank(" ")       = true
	 * StringUtils.isBlank("bob")     = false
	 * StringUtils.isBlank("  bob  ") = false
	 * </pre>
	 *
	 * @param str
	 *            the String to check, may be null
	 * @return <code>true</code> if the String is null, empty or whitespace
	 * @since 2.0
	 */
	public static boolean isBlank(String str)
	{
		int strLen;
		if (str == null || (strLen = str.length()) == 0)
		{
			return true;
		}
		for (int i = 0; i < strLen; i++)
		{
			if ((Character.isWhitespace(str.charAt(i)) == false))
			{
				return false;
			}
		}
		return true;
	}

	public static String randomCode()
	{
		String[] beforeStr = new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
		List<String> list = Arrays.asList(beforeStr); // 将数组转成List
		Collections.shuffle(list);
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < list.size(); i++)
		{
			sb.append(list.get(i));
		}
		String afterStr = sb.toString();
		String result = afterStr.substring(0, 4);

		return result;
	}

}
