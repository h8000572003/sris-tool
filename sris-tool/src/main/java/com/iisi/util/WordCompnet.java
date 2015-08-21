package com.iisi.util;

import java.util.List;
import java.util.Map;

/**
 * word使用元件
 * 
 * @author Andy
 *
 */
public interface WordCompnet {
	/**
	 * 套用樣板
	 * 
	 * @param templatePath
	 *            樣板路徑
	 * @param map
	 *            key and value 帶入
	 * @param outPath
	 *            輸出路徑
	 */
	void copyFromTemplate(String templatePath, Map<String, String> map,
			String outPath);

	/**
	 * 套用樣板
	 * 
	 * @param templatePath
	 * @param mapList
	 *            key and value 帶入
	 * @param outPath
	 *            輸出路徑
	 */
	void copyFromTemplate(String templatePath,
			List<Map<String, String>> mapList, List<String> outPath);
}
