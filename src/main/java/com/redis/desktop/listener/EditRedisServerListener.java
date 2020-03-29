/**
 * Project Name:common
 * File Name:EditRedisServerListener.java
 * Package Name:com.redis.desktop.listener
 * Date:2020年3月28日下午4:51:31
 * Copyright (c) 2020, wukunmeng@tom.com All Rights Reserved.
 **/

package com.redis.desktop.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.redis.desktop.window.MainFrame;
import com.redis.desktop.window.RedisEditFrame;

/**
 * ClassName:EditRedisServerListener <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2020年3月28日 下午4:51:31 <br/>
 * @author   wukm
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
@Component
@Scope("prototype")
public class EditRedisServerListener extends AbstractListener implements ActionListener{

	@Autowired
	private MainFrame frame;
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JOptionPane.showMessageDialog(frame, "准备实现", "显示信息提示框", JOptionPane.QUESTION_MESSAGE);
		getBean(RedisEditFrame.class).showWindow();
	}

}

