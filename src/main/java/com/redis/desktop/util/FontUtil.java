/**
 * Project Name:common
 * File Name:FontUtil.java
 * Package Name:com.redis.desktop.util
 * Date:2020年4月5日上午11:22:21
 * Copyright (c) 2020, wukunmeng@tom.com All Rights Reserved.
 **/

package com.redis.desktop.util;
/**
 * ClassName:FontUtil <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2020年4月5日 上午11:22:21 <br/>
 * @author   wukm
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */

import java.awt.Font;

public abstract class FontUtil {

	private static Font defaultFont_14 = new Font(Font.MONOSPACED, Font.BOLD, 14);
	
	private static Font defaultFont_15 = new Font(Font.MONOSPACED, Font.BOLD, 15);
	
	private static Font defaultFont_16 = new Font(Font.MONOSPACED, Font.BOLD, 16);
	
	public static Font createTreeFont() {
		return defaultFont_15;
	}
	
	public static Font createTableHeaderFont() {
		return defaultFont_14;
	}
	
	public static Font createTableDataFont() {
		return defaultFont_16;
	}
	
	public static Font createTabbedFont() {
		return defaultFont_15;
	}
	
	public static Font createMenuItemFont() {
		return defaultFont_15;
	}
	
	public static Font createComboBoxFont() {
		return defaultFont_14;
	}
}

