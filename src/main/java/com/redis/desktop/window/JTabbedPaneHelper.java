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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.redis.desktop.component.ComboBox;
import com.redis.desktop.component.CommonComponent;
import com.redis.desktop.component.MenuItem;
import com.redis.desktop.component.TabbedPane;
import com.redis.desktop.component.Table;
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
	
	private TabbedPane tab;
	
	@Autowired
	private RedisInfoStore redisInfoStore;
	
	private List<String> nodes = Collections.synchronizedList(new ArrayList<String>());
	
	@PostConstruct
	public void initialize() {
		JToolBar bar = new JToolBar();
		bar.add(new JButton(createImageIcon(homeIconFile)));
		bar.add(new JButton(createImageIcon(queryToolIconFile)));
		
		tab = new TabbedPane(JTabbedPane.TOP);
		Font tabFont = new Font(Font.MONOSPACED, Font.BOLD, 15);
		tab.setFont(tabFont);
		JPanel propertyPanel = new JPanel(new BorderLayout());
		JScrollPane propertyScrollPane = new JScrollPane(sytemProperty());
		propertyPanel.add(propertyScrollPane, BorderLayout.CENTER);
		tab.addTab("首页", createImageIcon(homeTabIconFile), propertyPanel);
		nodes.add("首页");
		
		JPopupMenu m = new JPopupMenu();
		MenuItem pupop = new MenuItem("关闭");
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
	private Table sytemProperty() {
		Vector<String> columnNames = new Vector<String>();
		columnNames.addElement("系统属性键");
		columnNames.addElement("系统属性值");
		Vector<Vector<String>> data = new Vector<Vector<String>>();
		System.getProperties().keySet().forEach(k -> {
			if(String.valueOf(k).startsWith("java.")) {
				Vector<String> row = new Vector<String>();
				row.addElement(String.valueOf(k));
				row.addElement(System.getProperty(String.valueOf(k)));
				data.addElement(row);
			}
		});
		
		Table table = new Table(data, columnNames);
		table.setEnabled(false);
		table.setFillsViewportHeight(true);
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
		Table table = dbTable(loadData(dbNode, null), dbNode);
		JScrollPane scrollPane = new JScrollPane(table);
		//scrollPane.setRowHeaderView(new TableR);table 行号 需要自己实现
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
		JButton addItemButton = new JButton(createImageIcon(addItemIconFile));
		bar.add(addItemButton);
//		JButton button = new JButton(createImageIcon(updateItemIconFile));
//		button.setEnabled(false);
//		bar.add(button);
		JButton deleteItemButton = new JButton(createImageIcon(deleteItemIconFile));
		bar.add(deleteItemButton);
		
		ComboBox<DbScanCountModel> comboBox = new ComboBox<DbScanCountModel>();
		comboBox.addItem(new DbScanCountModel("scan-10", 10));
		comboBox.addItem(new DbScanCountModel("scan-100", 100));
		comboBox.addItem(new DbScanCountModel("scan-500", 500));
		comboBox.addItem(new DbScanCountModel("scan-1000", 1000));
		for(int i = 0; i < comboBox.getItemCount(); i++) {
			DbScanCountModel temp = comboBox.getItemAt(i);
			if(temp.getCount().intValue() == dbNode.getScanCount()) {
				comboBox.setSelectedIndex(i);
			}
		}
		
		addItemButton.addActionListener((e) -> {
			String key = JOptionPane.showInputDialog(table, "请输入Key", "输入框", JOptionPane.INFORMATION_MESSAGE);
			if(StringUtils.isBlank(key)) {
				JOptionPane.showMessageDialog(table, "Key值不可以为空", "错误提示", JOptionPane.ERROR_MESSAGE);
				return;
			}
			Vector<String> rowData = new Vector<String>();
			rowData.add(key);
			rowData.add("");
			rowData.add("-1");
			DefaultTableModel tableModel = ((DefaultTableModel)table.getModel());
			logger.info("row-count:{}", tableModel.getRowCount());
			tableModel.insertRow(0, rowData);
			logger.info("row-count:{}", tableModel.getRowCount());
			table.setRowSelectionInterval(0, 0);
		});
		
//		int row = model.getRowCount() - 1;
//		int col = 0;
//		if (table.editCellAt(row, col)) {
//		    Component editor = table.getEditorComponent();
//		    editor.requestFocusInWindow();
//		    Component c = editor.getComponentAt(0, 0);
//		    if (c != null && c instanceof JTextComponent) {
//		        ((JTextComponent) c).selectAll();
//		    }
//		}
		
		deleteItemButton.addActionListener((e) -> {
			int row = table.getSelectedRow();
			logger.info("select row:{}", row);
			if(row >= 0) {
				Object objectKey = table.getValueAt(row, 0);
				if(objectKey == null) {
					JOptionPane.showMessageDialog(table, "无法删除不存在的Key", "错误提示", JOptionPane.ERROR_MESSAGE);
					return;
				}
				String key = objectKey.toString();
				Jedis client = redisInfoStore.getRedisClient(dbNode);
				if(client == null) {
					JOptionPane.showMessageDialog(table, 
							"无法获取Redis:" + dbNode.getRedisNodeModel().getAddress() + ",请重连接.", "错误提示", JOptionPane.ERROR_MESSAGE);
					return;
				} 
				client.del(key);
				((DefaultTableModel)table.getModel()).removeRow(row);
				return;
			}
			JOptionPane.showMessageDialog(table, "请选择要删除的行", "错误提示", JOptionPane.INFORMATION_MESSAGE);
		});
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
		Jedis client = redisInfoStore.getRedisClient(dbNode);
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
		DefaultTableModel tableModel = new DefaultTableModel(data, columnNames);
		tableModel.addTableModelListener((e) -> {
			if(e.getType() == TableModelEvent.INSERT) {
				logger.info("insert : {}", e.getLastRow());
				return;
			}
			if(e.getType() == TableModelEvent.DELETE) {
				logger.info("delete : {}", e.getLastRow());
				return;
			}
			DefaultTableModel dtm = (DefaultTableModel)e.getSource();
			Object objectKey = dtm.getValueAt(e.getFirstRow(), 0);
			String key = objectKey == null ? null : objectKey.toString();
			Object currentValue = dtm.getValueAt(e.getFirstRow(), e.getColumn());
			String value = currentValue == null ? null : currentValue.toString();
			logger.info("object:{}", currentValue);
			logger.info("value:{}", value);
			logger.info("first:{}", e.getFirstRow());
			logger.info("column:{}", e.getColumn());
			logger.info("lastRow:{}", e.getLastRow());
			logger.info("type:{}", e.getType());
			if(e.getType() == TableModelEvent.UPDATE) {
				Jedis c = redisInfoStore.getRedisClient(dbNode);
				if(c == null) {
					JOptionPane.showMessageDialog(tab, 
							"无法获取Redis:" + dbNode.getRedisNodeModel().getAddress() + ",请重连接.", "错误提示", JOptionPane.ERROR_MESSAGE);
					return;
				} 
				if(key == null) {
					JOptionPane.showMessageDialog(tab, "key不存在,无法编辑.", "错误提示", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if(client.exists(key).booleanValue()
						&& !StringUtils.equalsIgnoreCase("string", client.type(key))){
					JOptionPane.showMessageDialog(tab, "暂不支持该类型编辑.", "错误提示", JOptionPane.ERROR_MESSAGE);
				}
				if(e.getColumn() == 1) {
					c.set(key, value);
				}
				if(e.getColumn() == 2) {
					c.expire(key, NumberUtils.toInt(value));
				}
			}
		});
		return tableModel;
	}
	
	private Table dbTable(TableModel tableModel, DbNodeModel dbNode) {
		Table table = new Table(tableModel);
		table.setFillsViewportHeight(false);
		return table;
	}
}

