/**
 * Project Name:common
 * File Name:SystemExitListener.java
 * Package Name:com.redis.desktop.listener
 * Date:2020年3月28日下午4:21:42
 * Copyright (c) 2020, wukunmeng@tom.com All Rights Reserved.
 **/

package com.redis.desktop.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

/**
 * ClassName:SystemExitListener <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2020年3月28日 下午4:21:42 <br/>
 * @author   wukm
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
@Component
public class SystemExitListener extends AbstractListener implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO 修正为spring boot的优雅退出方式
		logger.info("system exit...");
		System.exit(SpringApplication.exit(getContext()));
	}

}

