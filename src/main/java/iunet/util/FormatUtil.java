package iunet.util;

import java.util.*;
import org.dom4j.Node;

public class FormatUtil {
	
	public static long frameStringToLong(String frames) {
		return frameStringToLong(frames, 25);
	}	
	/**转换时间帧
	 * @param frames
	 * @param frameRate
	 * @return
	 */
	public static long frameStringToLong(String frames, int frameRate) {
		return DateUtil.frameStringToLong(frames, frameRate);
	}

	public static String frameLongToString(long frames) {
		return frameLongToString(frames, 25);
	}
	
	/**转换时间帧
	 * @param frames
	 * @param frameRate
	 * @return
	 */
	public static String frameLongToString(long frames, int frameRate) {
		return DateUtil.frameLongToString(frames, frameRate);
	}

	public static String formatDateString(Date timeDate, String format) {
		
		return DateUtil.formatDateString(timeDate, format);
	}

	public static Date parseDate(Object objDate) {
		return DateUtil.parseDate(objDate);
	}

	/**取得一周某天的名称
	 * @param week
	 * @return
	 */
	public static String getNameOfWeek(int week) {
		
		return DateUtil.getNameOfWeek(week);
	}

	
	
	public static String escapeXml(String str) {
		return XmlUtil.escapeXml(str);
	}

	public static String unescapeXml(String str) {
		return XmlUtil.unescapeXml(str);
	}

	public static String tryGetItemText(Node node, String itemName,
			String defaultValue) {
		return XmlUtil.tryGetItemText(node, itemName, defaultValue);
	}	
	
	public static String formatFileName(String str) {
		return FileUtil.formatFileName(str);
	}	

	public static String formatFilePath(String arg, boolean isApple) {
		return FileUtil.formatFilePath(arg, isApple);
	}

	public static String getFilePath(String filePath, boolean removeEndSplitChar) {
		return FileUtil.getFilePath(filePath, removeEndSplitChar);
	}

	public static String getFileName(String filePath) {
		return FileUtil.getFileName(filePath);
	}
	
	public static String getChineseSpell(String strText, boolean replaceBlank) {
		
		return StringUtil.getChineseSpell(strText,replaceBlank);
	}

	
	
}