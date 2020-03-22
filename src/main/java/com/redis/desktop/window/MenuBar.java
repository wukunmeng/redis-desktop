/**
 * Project Name:common
 * File Name:MenuBar.java
 * Package Name:com.redis.desktop.window
 * Date:2020年3月21日下午7:49:28
 * Copyright (c) 2020, wukunmeng@tom.com All Rights Reserved.
 **/

package com.redis.desktop.window;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.annotation.PostConstruct;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import org.springframework.stereotype.Component;

/**
 * ClassName:MenuBar <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2020年3月21日 下午7:49:28 <br/>
 * @author   wukm
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
@Component
public class MenuBar extends JMenuBar{

	/**
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么).
	 * @since JDK 1.8
	 */
	private static final long serialVersionUID = 1L;

	@PostConstruct
	public void initialize() {
		JMenuItem exit = new JMenuItem("退出");
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
		});
		JMenu menu = new JMenu("文件");
		menu.add(exit);
		add(menu);
	}
	
}

