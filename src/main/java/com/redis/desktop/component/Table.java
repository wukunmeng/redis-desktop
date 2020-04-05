/**
 * Project Name:common
 * File Name:Table.java
 * Package Name:com.redis.desktop.component
 * Date:2020年4月5日上午11:06:54
 * Copyright (c) 2020, wukunmeng@tom.com All Rights Reserved.
 **/

package com.redis.desktop.component;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.TableModel;

import com.redis.desktop.util.FontUtil;

/**
 * ClassName:Table <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2020年4月5日 上午11:06:54 <br/>
 * @author   wukm
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
public class Table extends JTable {

	/**
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么).
	 * @since JDK 1.8
	 */
	private static final long serialVersionUID = 1L;

	public Table() {
		super();
		init();
	}
	
	public Table(TableModel tableModel) {
		super(tableModel);
		init();
	}
	
	@SuppressWarnings("rawtypes")
	public Table(Vector rowData, Vector columnNames) {
		super(rowData, columnNames);
		init();
	}
	
	private void init() {
		setFont(FontUtil.createTableDataFont());
		setRowHeight(35);
		setTableHeader(new TableHeader(getColumnModel()));
	}
	
	public void paintComponent(Graphics g){
        Graphics2D g2d=(Graphics2D)g;   
        g2d.setRenderingHint(
             RenderingHints.KEY_ANTIALIASING,
             RenderingHints.VALUE_ANTIALIAS_ON);
        super.paintComponent(g2d);
     }
	
	public boolean isCellEditable(int row, int column) {
		if(column == 0) {
			return false;
		}
		return true;
	}
}

