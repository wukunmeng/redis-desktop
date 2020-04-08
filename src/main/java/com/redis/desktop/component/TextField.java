/**
 * Project Name:redis-desktop
 * File Name:TextField.java
 * Package Name:com.redis.desktop.component
 * Date:2020年4月8日上午10:16:53
 * Copyright (c) 2020, wukunmeng@gmail.com All Rights Reserved.
 **/

package com.redis.desktop.component;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JTextField;

import com.redis.desktop.util.FontUtil;

/**
 * ClassName:TextField <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2020年4月8日 上午10:16:53 <br/>
 * @author   wukm
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public class TextField extends JTextField {

	/**
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么).
	 * @since JDK 1.8
	 */
	private static final long serialVersionUID = 1L;

	public TextField() {
		super();
		init();
	}
	
	public TextField(String text) {
		super(text);
		init();
	}
	
	public TextField(int columns) {
		super(columns);
		init();
	}
	
	private void init() {
		setFont(FontUtil.createTableHeaderFont());
	}
	
	public void paintComponent(Graphics g){
        Graphics2D g2d=(Graphics2D)g;   
        g2d.setRenderingHint(
             RenderingHints.KEY_ANTIALIASING,
             RenderingHints.VALUE_ANTIALIAS_ON);
        super.paintComponent(g2d);
     }
}

