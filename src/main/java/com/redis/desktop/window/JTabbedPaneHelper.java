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
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.redis.desktop.component.CommonComponent;

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
public class JTabbedPaneHelper extends CommonComponent{

	@Value("${spring.tree.open.icon}")
	private String homeTabIcon = "";
	
	@Value("classpath:icons/icon_system-users_64.png")
	private Resource queryToolFileIcon;
	
	private JTabbedPane tab;
	
	@PostConstruct
	public void initialize() {
		JToolBar toolBar = new JToolBar();
		toolBar.add(new JButton(createImageIcon(queryToolFileIcon)));
		tab = new JTabbedPane(JTabbedPane.TOP);
		JPanel panel = new JPanel(new BorderLayout());
		JScrollPane scrollPane = new JScrollPane(table());
		panel.add(toolBar, BorderLayout.NORTH);
		panel.add(scrollPane, BorderLayout.CENTER);
		tab.addTab("系统环境变量", panel);
		
		JToolBar bar = new JToolBar();
		bar.add(new JButton(new ImageIcon(homeTabIcon)));
		JPanel propertyPanel = new JPanel(new BorderLayout());
		JScrollPane propertyScrollPane = new JScrollPane(property());
		propertyPanel.add(bar, BorderLayout.NORTH);
		propertyPanel.add(propertyScrollPane, BorderLayout.CENTER);
		tab.addTab("系统属性", propertyPanel);
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
	
	
	private JTable property() {
		Vector<String> columnNames = new Vector<String>();
		columnNames.addElement("键");
		columnNames.addElement("值");
		Vector<Vector<String>> data = new Vector<Vector<String>>();
		System.getProperties().keySet().forEach(k -> {
			Vector<String> row = new Vector<String>();
			row.addElement(String.valueOf(k));
			row.addElement(System.getProperty(String.valueOf(k)));
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

