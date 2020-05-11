/**
 * Project Name:common
 * File Name:MainFrame.java
 * Package Name:com.redis.desktop.window
 * Date:2020年3月21日下午7:27:02
 * Copyright (c) 2020, wukunmeng@tom.com All Rights Reserved.
 **/

package com.redis.desktop.window;

import java.awt.Dimension;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.redis.desktop.component.CustomerComponent;
import com.redis.desktop.listener.SystemExitListener;

import javax.swing.JSplitPane;

import java.awt.AWTException;
import java.awt.BorderLayout;

/**
 * ClassName:MainFrame <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2020年3月21日 下午7:27:02 <br/>
 * @author   wukm
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
@Component
public class MainFrame extends JFrame{
	
	/**
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么).
	 * @since JDK 1.8
	 */
	private static final long serialVersionUID = 1L;

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Value("classpath:icons/icon_window_128.png")
	private Resource windowIconFile;
	
	@Value("classpath:icons/icon_window_24.png")
	private Resource trayIconFile;
	
	@Value("${spring.main.frame.width}")
	private Integer width = 1024;
	
	@Value("${spring.main.frame.height}")
	private Integer height = 1024;
	
	@Autowired
	private SystemExitListener systemExitListener;
	
	@Autowired
	private CustomerComponent customerComponent;
	
	@Value("${spring.main.frame.title:桌面程序}")
	private String title;
	
	@Autowired
	private TreeHelper tree;
	
	@Autowired
	private JTabbedPaneHelper jTabbedPaneHelper;
	
	@Autowired
	private FrameToolBarHelper frameToolBarHelper;
	
	public void showWindow() {
		setTitle(title);
		//setJMenuBar(topMenuBar);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				hideFrame();
			}
		});
		ImageIcon imageIcon = customerComponent.createImageIcon(windowIconFile);
		setIconImage(imageIcon.getImage());
		setSize(new Dimension(width, height));
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		JSplitPane splitPane = new JSplitPane();
		splitPane.setLeftComponent(new JScrollPane(tree.tree()));
		splitPane.setRightComponent(jTabbedPaneHelper.tabbedPane());
		getContentPane().add(frameToolBarHelper.toolBar(), BorderLayout.NORTH);
		getContentPane().add(splitPane, BorderLayout.CENTER);
		setLocationRelativeTo(null);
		setVisible(true);
		splitPane.setDividerLocation(0.2);
		trayBoot();
//		new Thread() {
//			public void run() {
//				trayBoot();
//			}
//		}.start();
	}
	/**
	 * Create the application.
	 */
	public MainFrame() {
		logger.info("create frame:{}", System.currentTimeMillis());
	}
	
	private void showAbout() {
		JOptionPane.showMessageDialog(this, "Redis桌面客户端(JRedis Desktop Client)", "关于", JOptionPane.INFORMATION_MESSAGE);
	}
	
	private void showFrame() {
		setVisible(true);
	}
	
	private void hideFrame() {
		setVisible(false);
	}
	
	public void trayBoot() {
		logger.info("SystemTray isSupported : {}", SystemTray.isSupported());
		if(SystemTray.isSupported()) {
			try {
				PopupMenu menu = new PopupMenu();
				MenuItem itemExit = new MenuItem("退出");
				MenuItem about = new MenuItem("关于");
				MenuItem showFrame = new MenuItem("显示");
				itemExit.addActionListener(e -> systemExitListener.exitSystem());
				about.addActionListener(e -> showAbout());
				showFrame.addActionListener(e -> showFrame());
				menu.add(showFrame);
				menu.add(itemExit);
				menu.addSeparator();
				menu.add(about);
				TrayIcon tray = new TrayIcon(customerComponent.createImage(trayIconFile),"Redis客户端",menu);
				tray.setImageAutoSize(false);
				SystemTray.getSystemTray().add(tray);
			} catch (AWTException e) {
				logger.error("AWTException:{}", e.getMessage());
			}
		}
	}
}

