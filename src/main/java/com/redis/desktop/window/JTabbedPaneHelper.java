/**
 * Project Name:redis-desktop
 * File Name:JTabbedPaneHelper.java
 * Package Name:com.redis.desktop.window
 * Date:2020年3月25日下午4:11:34
 * Copyright (c) 2020, wukunmeng@gmail.com All Rights Reserved.
 **/

package com.redis.desktop.window;

import java.awt.BorderLayout;
import java.util.Vector;

import javax.annotation.PostConstruct;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JToolBar;

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
	
	@Value("${spring.tool.bar.query.icon}")
	private String queryToolIcon = "";
	
	private JTabbedPane tab;
	
	@PostConstruct
	public void initialize() {
		JToolBar toolBar = new JToolBar();
		toolBar.add(new JButton(new ImageIcon(queryToolIcon)));
		tab = new JTabbedPane(JTabbedPane.TOP);
		JPanel panel = new JPanel(new BorderLayout());
		JScrollPane scrollPane = new JScrollPane(table());
		panel.add(toolBar, BorderLayout.NORTH);
		panel.add(scrollPane, BorderLayout.CENTER);
		tab.addTab("首页", panel);
	}
	
	private JTable table() {
		Vector<String> columnNames = new Vector<String>();
		columnNames.addElement("键");
		columnNames.addElement("值");
		Vector<Vector<String>> data = new Vector<Vector<String>>();
		System.getenv().forEach((k,v) -> {
			Vector<String> row = new Vector<String>();
			row.addElement(k);
			row.addElement(v);
			data.addElement(row);
		});
		
		JTable table = new JTable(data, columnNames);
		table.setFillsViewportHeight(true);
		return table;
	}
	
	public JTabbedPane tabbedPane() {
		return tab;
	}
}

