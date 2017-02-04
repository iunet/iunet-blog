import java.util.Date;

import org.jsoup.nodes.Document;

import iunet.util.*;

public class test {
	public static void main(String[] args) {
		/*String a = "/vm/error.vm";
		System.out.println("--------------");
		System.out.println("readTxtFile:"+FileUtil.readTxtFile(a));
		System.out.println("--------------");
		System.out.println("readHtmlFile:"+HtmlUtil.readHtmlFile(a));
		System.out.println("--------------");*/
		System.out.println(DateUtil.formatDateString(new Date(), "yyyy年MM月dd日 HH:mm:ss"));
	}
}
