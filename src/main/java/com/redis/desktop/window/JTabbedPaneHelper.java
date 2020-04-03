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
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import javax.annotation.PostConstruct;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.redis.desktop.component.CommonComponent;
import com.redis.desktop.model.DbNodeModel;
import com.redis.desktop.model.DbScanCountModel;
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
	
	@Value("classpath:icons/icon_sign-add_24.png")
	private Resource addItemIconFile;
	
	@Value("classpath:icons/icon_edit_24.png")
	private Resource updateItemIconFile;
	
	@Value("classpath:icons/icon_delete_24.png")
	private Resource deleteItemIconFile;
	
	private JTabbedPane tab;
	
	@Autowired
	private RedisInfoStore redisInfoStore;
	
	private List<String> nodes = Collections.synchronizedList(new ArrayList<String>());
	
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
		nodes.add("首页");
		
//		JPanel panel = new JPanel(new BorderLayout());
//		JScrollPane scrollPane = new JScrollPane(table());
//		panel.add(bar, BorderLayout.NORTH);
//		panel.add(scrollPane, BorderLayout.CENTER);
//		tab.addTab("系统属性", createImageIcon(systemFileManagerIconFile), panel);
//		nodes.add("系统属性");
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
				nodes.remove(tab.getSelectedIndex());
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
	
	public JTable table() {
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
		table.setRowHeight(36);
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
		table.setRowHeight(36);
		table.setEnabled(false);
		table.setFillsViewportHeight(false);
		return table;
	}
	
	public JTabbedPane tabbedPane() {
		return tab;
	}
	
	public boolean isOpenTab(DbNodeModel node) {
		return nodes.contains(node.getName());
	}
	
	public void selectIndex(DbNodeModel node) {
		if(isOpenTab(node))
		tab.setSelectedIndex(nodes.indexOf(node.getName()));
	}
	
	public void createTab(DbNodeModel dbNode) {
		JToolBar bar = new JToolBar();
		JTable table = dbTable(loadData(dbNode, null));
		JScrollPane scrollPane = new JScrollPane(table);
		JPanel panel = new JPanel(new BorderLayout());
		JButton home = new JButton(createImageIcon(homeIconFile));
		home.addActionListener((e) -> tab.setSelectedIndex(0));
		bar.add(home);
		bar.addSeparator(new Dimension(20, 0));
		JTextField queryText = new JTextField(30);
//		queryText.getDocument().addDocumentListener(new ChangedValueListener() {
//			@Override
//			public void valuechanged(DocumentEvent e, String value) {
//				table.setModel(loadData(dbNode, value));
//			}
//		});
		bar.add(queryText);
		JButton queryButton = new JButton(createImageIcon(queryToolIconFile));
		queryButton.addActionListener((e) -> {
			table.setModel(loadData(dbNode, queryText.getText()));
		});
		bar.add(queryButton);
		bar.addSeparator(new Dimension(10, 0));
		bar.add(new JButton(createImageIcon(addItemIconFile)));
		bar.add(new JButton(createImageIcon(updateItemIconFile)));
		bar.add(new JButton(createImageIcon(deleteItemIconFile)));
		
		JComboBox<DbScanCountModel> comboBox = new JComboBox<DbScanCountModel>();
		comboBox.addItem(new DbScanCountModel("scan-10", 10));
		comboBox.addItem(new DbScanCountModel("scan-100", 100));
		comboBox.addItem(new DbScanCountModel("scan-500", 500));
		comboBox.addItem(new DbScanCountModel("scan-1000", 1000));
		for(int i = 0; i < comboBox.getItemCount(); i++) {
			DbScanCountModel temp = comboBox.getItemAt(0);
			if(temp.getCount().intValue() == dbNode.getScanCount()) {
				comboBox.setSelectedIndex(i);
			}
		}
		comboBox.addItemListener((e) -> {
			logger.info("item:{}, state:{}", e.getItem(), e.getStateChange());
			switch (e.getStateChange()) {
				case ItemEvent.SELECTED:
					DbScanCountModel dbScanCount = (DbScanCountModel)e.getItem();
					dbNode.setScanCount(dbScanCount.getCount());
					break;
				default:
					break;
				}
		});
		bar.addSeparator(new Dimension(20, 0));
		bar.add(comboBox);
		
		panel.add(bar, BorderLayout.NORTH);
		panel.add(scrollPane, BorderLayout.CENTER);
		tab.addTab(dbNode.getName(), createImageIcon(systemFileManagerIconFile), panel);
		tab.setSelectedIndex(tab.getTabCount() - 1);
		nodes.add(dbNode.getName());
	}
	
	private DefaultTableModel loadData(DbNodeModel dbNode, String match) {
		Jedis client = 
				redisInfoStore.getRedisClient(dbNode.getRedisNodeModel().getAddress());
		if(dbNode.getDb() != null && dbNode.getDb() >= 0) {
			client.select(dbNode.getDb());
		}
		String cursor = ScanParams.SCAN_POINTER_START;
		ScanParams params = new ScanParams();
		if(StringUtils.isBlank(match)) {
			params.match("*");
		} else {
			if(match.contains("*")) {
				params.match(match);
			} else {
				params.match(match + "*");
			}
		}
		params.count(dbNode.getScanCount());
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
		Vector<String> columnNames = new Vector<String>();
		columnNames.addElement("键");
		columnNames.addElement("值");
		columnNames.addElement("TTL");
		return new DefaultTableModel(data, columnNames);
	}
	
	private JTable dbTable(TableModel tableModel) {
		JTable table = new JTable(tableModel);
		Font headerFont = new Font(Font.MONOSPACED, Font.BOLD, 16);
		table.getTableHeader().setFont(headerFont);
		Font tableFont = new Font(Font.MONOSPACED, Font.PLAIN, 15);
		table.setFont(tableFont);
		table.setRowHeight(36);
		table.setFillsViewportHeight(false);
		return table;
	}
}

