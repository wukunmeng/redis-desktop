/**
 * Project Name:common
 * File Name:RedisEditFrame.java
 * Package Name:com.redis.desktop.window
 * Date:2020年3月28日下午7:32:51
 * Copyright (c) 2020, wukunmeng@tom.com All Rights Reserved.
 **/

package com.redis.desktop.window;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.springframework.stereotype.Component;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

/**
 * ClassName:RedisEditFrame <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2020年3月28日 下午7:32:51 <br/>
 * @author   wukm
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
@Component
public class RedisEditFrame extends JFrame{

	/**
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么).
	 * @since JDK 1.8
	 */
	private static final long serialVersionUID = 1L;

	public void showWindow() {
		setSize(400, 200);
		FormLayout formLayout = new FormLayout(//  
                "right:max(110dlu;p), 5dlu, left:max(110dlu;p),pref",
                "pref, 2dlu, pref, 2dlu, pref"); 
		setLayout(formLayout);
		CellConstraints cc = new CellConstraints(); 
		add(new JLabel("服务地址"), cc.xy(1, 1));
		add(new JTextField(32),cc.xywh(3, 1, 2, 1));
		add(new JLabel("密码"), cc.xy(1, 3));
		add(new JTextField(32),cc.xywh(3, 3, 2, 1));
		add(new JButton("确定"), cc.xy(1, 5));
		add(new JButton("取消"),cc.xy(3,5));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}
}

