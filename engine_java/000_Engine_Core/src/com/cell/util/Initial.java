package com.cell.util;

import com.cell.CUtil;

/**
 * <b>INI檔案</b><br>
 * <b>格式</b><br>
 * 節<br>
 *  name = value<br>
 * 參數<br>
 *  [section]<br>
 * 註解<br>
 *  註解使用分號表示（;）。在分號後面的文字，直到該行結尾都全部為註解。<br>
 *  ; comment text<br>
 *  <br>
 * <b>範例</b><br>
 * 下面是一個虛擬的程式，其INI檔有兩個小節，前面的小節是用來設定擁有者的資訊，
 * 而後面的小節是用來設定資料庫的位置。前面的註解記載誰最後編輯此檔案，而後面
 * 的註解記錄為何不使用域名而是使用IP位址。<br>
 * 
 * ; last modified 1 April 2001 by John Doe<br>
 * [owner]<br>
 * name=John Doe<br>
 * organization=Acme Products<br>
 * <br>
 * [database]<br>
 * server=192.0.2.42 ; use IP address in case network name resolution is not working<br>
 * port=143<br>
 * file = "acme payroll.dat"<br>
 * <br>
 * @author WAZA
 */
public class Initial
{
	Properties[] sections;
	
	public Initial(String text) 
	{
		text = text.replaceAll(";.*", "");
		
		String[] section_texts = text.split("\\[.+\\]");
		
		for (String se : section_texts) {
			text = CUtil.replaceString(text, se, ";", 1);
		}
		text = CUtil.replaceString(text, "\n", "");
		
		String[] section_heads = CUtil.splitString(text, ";");
		
		this.sections = new Properties[section_heads.length];
		for (int i = 0; i < section_heads.length; i++) {
			if (!section_heads[i].isEmpty()) {
//				System.out.println(section_heads[i]);
//				sections[i].debug = true;
				sections[i] = new Properties();
				sections[i].comment_text = new String[]{";"};
				sections[i].loadText(section_texts[i], "=");
			}
		}

	}
	
	public static void main(String[] args)
	{
		new Initial(
				"; last modified 1 April 2001 by John Doe\n\n" +
				"[owner]\n\n" +
				"name=John Doe\n\n" +
				"organization=Acme Products\n\n" +
				"\n\n" +
				"[database]\n\n" +
				"server=192.0.2.42 ; use IP address in case network name resolution is not working\n\n" +
				"port=143\n\n" +
				"file = acme payroll.dat\n\n"
				) ;
	}
	
	
}
