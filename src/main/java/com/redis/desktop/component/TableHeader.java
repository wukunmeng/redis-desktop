/**
 * Project Name:common
 * File Name:TableHeader.java
 * Package Name:com.redis.desktop.component
 * Date:2020年4月5日上午11:11:18
 * Copyright (c) 2020, wukunmeng@tom.com All Rights Reserved.
 **/

package com.redis.desktop.component;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

import com.redis.desktop.util.FontUtil;

/**
 * ClassName:TableHeader <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2020年4月5日 上午11:11:18 <br/>
 * @author   wukm
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
public class TableHeader extends JTableHeader {

	/**
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么).
	 * @since JDK 1.8
	 */
	private static final long serialVersionUID = 1L;
	
	public TableHeader(TableColumnModel tableColumnModel) {
		super(tableColumnModel);
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

