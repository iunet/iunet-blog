package iunet.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUtil {
	
	private static final Logger log = LoggerFactory.getLogger(FileUtil.class);
	
	public static String readTxtFile(String filePath) {
		log.info("readTxtFile filePath:{}", filePath);
		String Str = "";
		try {
			InputStreamReader read = new InputStreamReader(FileUtil.class.getResourceAsStream(filePath), "UTF-8");
			BufferedReader bufferedReader = new BufferedReader(read);
			String lineTxt = null;
			while ((lineTxt = bufferedReader.readLine()) != null) {
				Str += lineTxt;
			}
			read.close();
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		} finally {
			return Str;
		}
	}
	
	/**去除文件名非法字符
	 * @param arg
	 * @return
	 */
	public static String formatFileName(String arg) {
		return arg.replaceAll("[\\|/|?|:|*|<|>|" + "\"" + "]", "");
	}

	
	/**格式化路径
	 * @param arg
	 * @param isApple
	 * @return
	 */
	public static String formatFilePath(String arg, boolean isApple) {
		return isApple ? arg.replace("\\", "/") : arg.replace("/", "\\");
	}

	/**取得文件路径
	 * @param filePath
	 * @param removeEndSplitChar 移除结尾的分隔符
	 * @return
	 */
	public static String getFilePath(String filePath, boolean removeEndSplitChar) {
		if (filePath == null)
			return "";
		int charIndex = filePath.lastIndexOf("/");
		if (charIndex < 0)
			charIndex = filePath.lastIndexOf("\\");
		if (charIndex > 0) {
			if (!removeEndSplitChar)
				charIndex += 1;
			return filePath.substring(0, charIndex);
		}
		return filePath;
	}

	
	/**取得文件名
	 * @param filePath
	 * @return
	 */
	public static String getFileName(String filePath) {
		if (filePath == null)
			return "";
		int charIndex = filePath.lastIndexOf("/");
		if (charIndex < 0)
			charIndex = filePath.lastIndexOf("\\");
		if (charIndex > 0) {
			return filePath.substring(charIndex + 1);
		}
		return filePath;
	}

	/**
	 * 获取文件扩展名
	 */
	public static String getExtensionName(String fileName) {
		String extName = "";
		if (fileName.lastIndexOf(".") != -1) {
			extName = fileName.substring(fileName.lastIndexOf("."));
		}
		return extName;
	}

	/**
	 * 删除文件、文件夹
	 * 
	 * @param path
	 *            需要删除的文件|文件夹
	 */
	public static void removeFile(String path) {
		removeFile(new File(path));
	}

	public static void removeFile(File path) {	
		if(!path.isFile()&&!path.isDirectory()) 
		{
			return;
		}
		if (path.isDirectory()) {
			File[] child = path.listFiles();
			if (child != null && child.length != 0) {
				for (int i = 0; i < child.length; i++) {
					removeFile(child[i]);
					child[i].delete();
				}
			}
		}
		path.delete();
	}
	
	/**替换路径
	 * @param filePath
	 * @param path1
	 * @param path2
	 * @return
	 */
	public static String replaceFilePath(String filePath,String path1,String path2)
    {
        if (StringUtil.isNullOrEmpty(filePath) || StringUtil.isNullOrEmpty(path1))
            return "";

        String lowerPath = FileUtil.formatFilePath(filePath.toLowerCase(),true);            
        String lowerItem = FileUtil.formatFilePath(path1.toLowerCase(),true);

        int index = lowerPath.indexOf(lowerItem);
        if (index >= 0)
        {
            return FileUtil.formatFilePath(path2 + filePath.substring(lowerItem.length()), path2.indexOf("/") >= 0);                
        }
        return filePath;

    }
}
