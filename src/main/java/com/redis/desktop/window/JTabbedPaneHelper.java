/**
 * Project Name:redis-desktop
 * File Name:JTabbedPaneHelper.java
 * Package Name:com.redis.desktop.window
 * Date:2020年3月25日下午4:11:34
 * Copyright (c) 2020, wukunmeng@gmail.com All Rights Reserved.
 **/

package com.redis.desktop.window;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.annotation.PostConstruct;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.redis.desktop.component.CommonComponent;
import com.redis.desktop.model.DbNodeModel;
import com.redis.desktop.store.RedisInfoStore;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;

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

	@Value("classpath:icons/icon_home_16.png")
	private Resource homeTabIconFile;
	
	@Value("classpath:icons/icon_home_24.png")
	private Resource homeIconFile;
	
	@Value("classpath:icons/icon_system-search_24.png")
	private Resource queryToolIconFile;
	
	@Value("classpath:icons/icon_system-file-manager_16.png")
	private Resource systemFileManagerIconFile;
	
	@Value("classpath:icons/icon_letter-x-blue_16.png")
	private Resource closeTabIconFile;
	
	private JTabbedPane tab;
	
	@Autowired
	private RedisInfoStore redisInfoStore;
	
	@PostConstruct
	public void initialize() {
		JToolBar bar = new JToolBar();
		bar.add(new JButton(createImageIcon(homeIconFile)));
		bar.add(new JButton(createImageIcon(queryToolIconFile)));
		
		tab = new JTabbedPane(JTabbedPane.TOP);
		Font tabFont = new Font(Font.MONOSPACED, Font.BOLD, 15);
		tab.setFont(tabFont);
		JPanel propertyPanel = new JPanel(new BorderLayout());
		JScrollPane propertyScrollPane = new JScrollPane(property());
		propertyPanel.add(propertyScrollPane, BorderLayout.CENTER);
		tab.addTab("首页", createImageIcon(homeTabIconFile), propertyPanel);
		
		JPanel panel = new JPanel(new BorderLayout());
		JScrollPane scrollPane = new JScrollPane(table());
		panel.add(bar, BorderLayout.NORTH);
		panel.add(scrollPane, BorderLayout.CENTER);
		tab.addTab("系统属性", createImageIcon(systemFileManagerIconFile), panel);
		//TabPane tabOne = new TabPane("系统属性", createImageIcon(closeTabIconFile));
		//tab.setTabComponentAt(1, tabOne);
//		tab.addChangeListener((e) -> {
//			if(e.getSource() instanceof TabPane) {
//				((TabPane)e.getSource()).selected();
//			}
//			for(int i = 0; i < tab.getTabCount(); i++) {
//				if(tab.getTabComponentAt(i) != e.getSource()) {
//					tab.getTabComponentAt(i).setBackground(Color.BLACK);
//				}
//			}
//		});
		JPopupMenu m = new JPopupMenu();
		JMenuItem pupop = new JMenuItem("关闭");
		pupop.setFont(new Font(Font.MONOSPACED, Font.BOLD, 13));
		m.add(pupop);
		pupop.addActionListener((e)->{
			int index = tab.getSelectedIndex();
			if(index > 0) {
				tab.remove(tab.getSelectedIndex());
			}
			m.setVisible(false);
		});
		tab.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				logger.info("button:{}", e.getButton());
				logger.info("x:{},y:{}", e.getX(),e.getY());
				if(e.getButton() == MouseEvent.BUTTON3) {
					m.show(tab, e.getX(), e.getY());
					logger.info("source:{}", e.getSource().getClass().getName());
					logger.info("source:{}", e.getComponent().getClass().getName());
				}
			}
		});
	}
	
	private JTable table() {
		Vector<String> columnNames = new Vector<String>();
		columnNames.addElement("环境变量");
		columnNames.addElement("环境变量值");
		Vector<Vector<String>> data = new Vector<Vector<String>>();
		System.getenv().forEach((k,v) -> {
			Vector<String> row = new Vector<String>();
			row.addElement(k);
			row.addElement(v);
			data.addElement(row);
		});
		
		JTable table = new JTable(data, columnNames);
		Font headerFont = new Font(Font.MONOSPACED, Font.BOLD, 16);
		table.getTableHeader().setFont(headerFont);
		Font tableFont = new Font(Font.MONOSPACED, Font.PLAIN, 15);
		table.setFont(tableFont);
		table.setRowHeight(40);
		table.setFillsViewportHeight(true);
		return table;
	}
	
	
	private JTable property() {
		Vector<String> columnNames = new Vector<String>();
		columnNames.addElement("属性键");
		columnNames.addElement("属性值");
		Vector<Vector<String>> data = new Vector<Vector<String>>();
		System.getProperties().keySet().forEach(k -> {
			if(String.valueOf(k).startsWith("java.")) {
				Vector<String> row = new Vector<String>();
				row.addElement(String.valueOf(k));
				row.addElement(System.getProperty(String.valueOf(k)));
				data.addElement(row);
			}
		});
		
		JTable table = new JTable(data, columnNames);
		Font headerFont = new Font(Font.MONOSPACED, Font.BOLD, 16);
		table.getTableHeader().setFont(headerFont);
		Font tableFont = new Font(Font.MONOSPACED, Font.PLAIN, 15);
		table.setFont(tableFont);
		table.setRowHeight(40);
		table.setEnabled(false);
		table.setFillsViewportHeight(false);
		return table;
	}
	
	public JTabbedPane tabbedPane() {
		return tab;
	}
	
	public void createTab(DbNodeModel dbNode) {
		Jedis client = 
				redisInfoStore.getRedisClient(dbNode.getRedisNodeModel().getAddress());
		client.select(dbNode.getDb());
		String cursor = ScanParams.SCAN_POINTER_START;
		ScanParams params = new ScanParams();
		params.match("*");
		params.count(100);
		ScanResult<String> keys = client.scan(cursor,params);
		Vector<Vector<String>> data = new Vector<Vector<String>>();
		keys.getResult().forEach(k -> {
			Vector<String> row = new Vector<String>();
			row.add(k);
			if(StringUtils.equalsIgnoreCase("string", client.type(k))){
				row.add(client.get(k));
			} else {
				row.add("==unknow--不可读==");
			}
			row.add(String.valueOf(client.ttl(k)));
			data.add(row);
		});
		JToolBar bar = new JToolBar();
		bar.add(new JButton(createImageIcon(homeIconFile)));
		bar.addSeparator(new Dimension(20, 0));
		bar.add(new JTextField(30));
		bar.add(new JButton(createImageIcon(queryToolIconFile)));
		JPanel panel = new JPanel(new BorderLayout());
		JScrollPane scrollPane = new JScrollPane(dbTable(data));
		panel.add(bar, BorderLayout.NORTH);
		panel.add(scrollPane, BorderLayout.CENTER);
		tab.addTab(dbNode.getName(), createImageIcon(systemFileManagerIconFile), panel);
	}
	
	private JTable dbTable(Vector<Vector<String>> data) {
		Vector<String> columnNames = new Vector<String>();
		columnNames.addElement("键");
		columnNames.addElement("值");
		columnNames.addElement("TTL");
		JTable table = new JTable(data, columnNames);
		Font headerFont = new Font(Font.MONOSPACED, Font.BOLD, 16);
		table.getTableHeader().setFont(headerFont);
		Font tableFont = new Font(Font.MONOSPACED, Font.PLAIN, 15);
		table.setFont(tableFont);
		table.setRowHeight(40);
		table.setFillsViewportHeight(false);
		return table;
	}
}

