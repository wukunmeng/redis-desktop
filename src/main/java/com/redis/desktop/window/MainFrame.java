/**
 * Project Name:common
 * File Name:MainFrame.java
 * Package Name:com.redis.desktop.window
 * Date:2020年3月21日下午7:27:02
 * Copyright (c) 2020, wukunmeng@tom.com All Rights Reserved.
 **/

package com.redis.desktop.window;

import java.awt.Dimension;

import javax.annotation.PostConstruct;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenuBar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.swing.JSplitPane;
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
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Value("${spring.main.frame.icon}")
	private String iconImagePath = "";
	
	@Value("${spring.main.frame.width}")
	private Integer width = 1024;
	
	@Value("${spring.main.frame.height}")
	private Integer height = 1024;
	
	@Autowired
	private JMenuBar topMenuBar;
	
	@Value("${spring.main.frame.title:桌面程序}")
	private String title;
	
	@Autowired
	private Tree tree;
	
	@Autowired
	private JTabbedPaneHelper jTabbedPaneHelper;

	/**
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么).
	 * @since JDK 1.8
	 */
	private static final long serialVersionUID = 1L;
	
	@PostConstruct
	public void initialize() {
		setTitle(title);
		setJMenuBar(topMenuBar);
		ImageIcon imageIcon = new ImageIcon(iconImagePath);
		setIconImage(imageIcon.getImage());
		setSize(new Dimension(width, height));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JSplitPane splitPane = new JSplitPane();
		splitPane.setLeftComponent(tree.tree());
		splitPane.setRightComponent(jTabbedPaneHelper.tabbedPane());
		getContentPane().add(splitPane, BorderLayout.CENTER);
		setLocationRelativeTo(null);
		setVisible(true);
		splitPane.setDividerLocation(0.2);
	}
	/**
	 * Create the application.
	 */
	public MainFrame() {
		logger.info("create frame:{}", System.currentTimeMillis());
	}
}

