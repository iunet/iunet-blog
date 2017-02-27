package iunet.util;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class StringUtil {

	private static Random rd = new Random();
    
    public static String getRandom(int count) {
        return getRandom(count, null);
    }
    
    public static String getRandom(int count, String exclude) {
        if (exclude == null) {
            exclude = "";
        }
        if (count <= 0) {
            throw new IllegalArgumentException("Character length must be > 0");
        }
        int start = ' ';
        int end = 'z'+1;
        int num = end-start;
        StringBuffer buf = new StringBuffer();
        char c;
        while (count-- != 0) {
            c = (char)rd.nextInt(num);
            if (Character.isLetterOrDigit(c) && exclude.indexOf((int)c)<0) {
                buf.append(c);
            } else {
                count++;
            }
        }
        return buf.toString();
    }
    public static String getRandomFromLong() {
        
        String token = Long.toString(Math.abs(rd.nextLong()), 36);
        return token;
    }

	public static boolean isNullOrEmpty(String str)
	{
		if(str== null || str.length()==0)
		{
			return true;
		}
		return false;
	}	
	
	public static String capitalize(String str) {
		return changeFirstCharacterCase(str, true);
	}
	public static String uncapitalize(String str) {
		return changeFirstCharacterCase(str, false);
	}
	private static String changeFirstCharacterCase(String str, boolean capitalize) {
		if (str == null || str.length() == 0) {
			return str;
		}
		StringBuffer buf = new StringBuffer(str.length());
		if (capitalize) {
			buf.append(Character.toUpperCase(str.charAt(0)));
		}
		else {
			buf.append(Character.toLowerCase(str.charAt(0)));
		}
		buf.append(str.substring(1));
		return buf.toString();
	}
	
	public static String[] removeDuplicateStrings(String[] array) {
		if (array==null) {
			return array;
		}
		Set<String> set = new HashSet<String>();
		for (int i = 0; i < array.length; i++) {
			set.add(array[i]);
		}
		return set.toArray(new String[set.size()]);
	}
	
	public static String padLeft(String str,char c,int len)
	{
		String _c = String.valueOf(c);   
		String _str = str;
	    while(_str.length()<len){
	        _str = _c+_str;
	    }   
	    return _str;    
	}
	
	public static String padRight(String str,char c,int len)
	{
		String _c = String.valueOf(c);   
		String _str = str;
	    while(_str.length()<len){
	        _str = _str+_c;
	    }   
	    return _str;    
	}
	
	public static String right(String str,int len)
	{
		if(isNullOrEmpty(str))
			return "";
	    String _str = str;
	    if(_str.length() <= len){
	        return _str;
	    }
	    return _str.substring(_str.length()-len);
	}
	
	public static String left(String str,int len)
	{
		if(isNullOrEmpty(str))
			return "";
	    String _str = str;
	    if(_str.length() <= len){
	        return _str;
	    }
	    return _str.substring(0,len);
	}
	public static String join(String[] arr,String splitChar)
	{
		if(arr == null || arr.length ==0)
			return "";
		StringBuilder sbStr = new StringBuilder();
		for(int i=0;i<arr.length;i++)
		{
			sbStr.append(arr[i]);
			if(i!=arr.length-1)
				sbStr.append(splitChar);
		}
		return sbStr.toString();
	}
	public static String join(char[] arr,String splitChar)
	{
		if(arr == null || arr.length ==0)
			return "";
		StringBuilder sbStr = new StringBuilder();
		for(int i=0;i<arr.length;i++)
		{
			sbStr.append(arr[i]);
			if(i!=arr.length-1)
				sbStr.append(splitChar);
		}
		return sbStr.toString();
	}
	public static String trimEnd(String str,char... chs)
	{
		if(str == null || str.length() ==0)
			return str;
		for(char ch : chs)
		{
			while(str.length()>0 && str.charAt(str.length()-1)==ch)
			{
				str = str.substring(0, str.length()-1);
			}
		}
		return str;
	}
	public static String trimStart(String str,char... chs)
	{
		if(str == null || str.length() ==0)
			return str;
		for(char ch : chs)
		{
			while(str.length()>0 && str.charAt(0)==ch)
			{
				str = str.substring(1);
			}
		}
		return str;
	}
	public static String trim(String str,char... chs)
	{
		if(chs==null)
			chs = new char[]{' '};
		return trimEnd(trimStart(str,chs),chs);
	}
	
	public static String format(String formatString,Object... objs)
	{
		if(objs==null && objs.length==0)
			return formatString;
	
		for(int i=0;i<objs.length;i++)
		{
			if(objs[i] == null)
			{
				objs[i] = "";
			}
		}
		
		return String.format(formatString,objs);
	}
	
	/**
	 * GB 2312-80 把收录的汉字分成两级。第一级汉字是常用汉字，计 3755 个， * 置于 16～55
	 * 区，按汉语拼音字母／笔形顺序排列；第二级汉字是次常用汉字， * 计 3008 个，置于 56～87 区，按部首／笔画顺序排列，所以本程序只能查到 *
	 * 对一级汉字的声母。同时对符合声母（zh，ch，sh）只能取首字母（z，c，s）
	 */
	// 国标码和区位码转换常量
	static final int GB_SP_DIFF = 160;
	// 存放国标一级汉字不同读音的起始区位码
	static final int[] secPosValueList = { 1601, 1637, 1833, 2078, 2274, 2302,
			2433, 2594, 2787, 3106, 3212, 3472, 3635, 3722, 3730, 3858, 4027,
			4086, 4390, 4558, 4684, 4925, 5249, 5600 };
	// 存放国标一级汉字不同读音的起始区位码对应读音
	static final char[] firstLetter = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
			'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'w', 'x','y', 'z' };

	// 获取一个字符串的拼音码
	public static String getChineseSpell(String strText, boolean replaceBlank) {
		int len = strText.length();
		StringBuilder myStr = new StringBuilder();
		for (int i = 0; i < len; i++) {
			myStr.append(getSpell(strText.charAt(i)));
		}
		if (replaceBlank) {
			return myStr.toString().replace(" ", "_");
		}
		return myStr.toString();
	}
	

	private static char getSpell(char cnChar) {
		char[] temp = new char[] { cnChar };
		byte[] uniCode = new String(temp).getBytes();
		if (uniCode[0] < 128 && uniCode[0] > 0) { // 非汉字
			return cnChar;
		}
		return convert(uniCode);
	}

	/**
	 * 获取一个汉字的拼音首字母。 * GB码两个字节分别减去160，转换成10进制码组合就可以得到区位码 *
	 * 例如汉字“你”的GB码是0xC4/0xE3，分别减去0xA0（160）就是0x24/0x43 *
	 * 0x24转成10进制就是36，0x43是67，那么它的区位码就是3667，在对照表中读音为‘n’
	 * 
	 */
	private static char convert(byte[] bytes) {
		char result = '-';
		int secPosValue = 0;
		int i;
		for (i = 0; i < bytes.length; i++) {
			bytes[i] -= GB_SP_DIFF;
		}
		secPosValue = bytes[0] * 100 + bytes[1];
		for (i = 0; i < 23; i++) {
			if (secPosValue >= secPosValueList[i]
					&& secPosValue < secPosValueList[i + 1]) {
				result = firstLetter[i];
				break;
			}
		}
		return result;
	}
}