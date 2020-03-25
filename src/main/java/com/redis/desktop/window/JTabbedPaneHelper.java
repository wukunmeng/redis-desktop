/**
 * Project Name:redis-desktop
 * File Name:JTabbedPaneHelper.java
 * Package Name:com.redis.desktop.window
 * Date:2020年3月25日下午4:11:34
 * Copyright (c) 2020, wukunmeng@gmail.com All Rights Reserved.
 **/

package com.redis.desktop.window;

import java.awt.BorderLayout;

import javax.annotation.PostConstruct;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * ClassName:JTabbedPaneHelper <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2020年3月25日 下午4:11:34 <br/>
 * @author   wukm
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
@Component
public class JTabbedPaneHelper {

	@Value("${spring.tree.open.icon}")
	private String homeTabIcon = "";
	
	private JTabbedPane tab;
	
	@PostConstruct
	public void initialize() {
		tab = new JTabbedPane(JTabbedPane.TOP);
		JPanel panel = new JPanel(new BorderLayout());
		JScrollPane scrollPane = new JScrollPane(table());
		panel.add(scrollPane, BorderLayout.CENTER);
		tab.addTab("首页", panel);
	}
	
	private JTable table() {
		TableColumnModel columnModel = new DefaultTableColumnModel();
		TableColumn key = new TableColumn();
		key.setHeaderValue("键");
		TableColumn value = new TableColumn();
		value.setHeaderValue("值");
		columnModel.addColumn(key);
		columnModel.addColumn(value);
		DefaultTableModel data = new DefaultTableModel();
		System.getenv().forEach((k,v) -> data.addRow(new Object[] {k, v}));
		JTable table = new JTable(data, columnModel);
		table.setFillsViewportHeight(true);
		return table;
	}
	
	public JTabbedPane tabbedPane() {
		return tab;
	}
}

