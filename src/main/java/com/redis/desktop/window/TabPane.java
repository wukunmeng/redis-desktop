/**
 * Project Name:redis-desktop
 * File Name:TabPane.java
 * Package Name:com.redis.desktop.window
 * Date:2020年3月27日下午1:51:22
 * Copyright (c) 2020, wukunmeng@gmail.com All Rights Reserved.
 **/

package com.redis.desktop.window;
/**
 * ClassName:TabPane <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2020年3月27日 下午1:51:22 <br/>
 * @author   wukm
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TabPane extends JPanel {

	/**
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么).
	 * @since JDK 1.8
	 */
	private static final long serialVersionUID = 1L;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private JLabel title = new JLabel();
	
	private JButton icon = new JButton();
	
	public TabPane() {
		logger.info("init tabPane:{}", this.getClass().getName());
		setLayout(new FlowLayout());
	}
	
	public TabPane(String tile, Icon icon) {
		this();
		this.title.setText(tile);
		//this.title.addMouseListener(this);
		this.icon.setIcon(icon);
		this.icon.setPreferredSize(new Dimension(12,20));
		this.add(this.title);
		this.add(this.icon);
	}
	
	public void selectedBackground() {
		this.title.setBackground(Color.blue);
		this.icon.setBackground(Color.blue);
	}
}

