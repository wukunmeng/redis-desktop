/**
 * Project Name:common
 * File Name:FrameToolBarHelper.java
 * Package Name:com.redis.desktop.window
 * Date:2020年3月28日下午4:09:08
 * Copyright (c) 2020, wukunmeng@tom.com All Rights Reserved.
 **/

package com.redis.desktop.window;

import javax.annotation.PostConstruct;
import javax.swing.JButton;
import javax.swing.JToolBar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.redis.desktop.component.CommonComponent;
import com.redis.desktop.listener.EditRedisServerListener;
import com.redis.desktop.listener.SystemExitListener;

/**
 * ClassName:FrameToolBarHelper <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2020年3月28日 下午4:09:08 <br/>
 * @author   wukm
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
@Component
public class FrameToolBarHelper extends CommonComponent {

	@Value("classpath:icons/icon_system-log-out_24.png")
	private Resource systemExitIconFile;
	
	
	@Value("classpath:icons/icon_sign-add_24.png")
	private Resource addRedisServerIconFile;
	
	private JToolBar toolBar = new JToolBar();
	
	@Autowired
	private SystemExitListener systemExitListener;
	
	@Autowired
	private EditRedisServerListener editRedisServerListener;
	
	@PostConstruct
	public void initialize() {
		
		JButton addRedisServer = new JButton(createImageIcon(addRedisServerIconFile));
		addRedisServer.setToolTipText("添加Redis");
		addRedisServer.addActionListener(editRedisServerListener);
		toolBar.add(addRedisServer);
		
		JButton exit = new JButton(createImageIcon(systemExitIconFile));
		exit.setToolTipText("退出");
		exit.addActionListener(systemExitListener);
		toolBar.add(exit);
	}
	
	public JToolBar toolBar() {
		return toolBar;
	}
}

