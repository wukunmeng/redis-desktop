/**
 * Project Name:common
 * File Name:RedisEditFrame.java
 * Package Name:com.redis.desktop.window
 * Date:2020年3月28日下午7:32:51
 * Copyright (c) 2020, wukunmeng@tom.com All Rights Reserved.
 **/

package com.redis.desktop.window;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;
import javax.swing.JTextField;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.redis.desktop.component.CustomerComponent;
import com.redis.desktop.model.RedisNodeModel;
import com.redis.desktop.store.RedisInfoStore;
import com.redis.desktop.util.Commons;

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

	@Autowired
	private Tree tree;
	
	@Autowired
	private CustomerComponent customerComponent;
	
	@Autowired
	private RedisInfoStore redisInfoStore;
	
	public void showWindow() {
		setSize(400, 200);
		setUndecorated(true);
		getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);
		FormLayout formLayout = new FormLayout(//  
                "right:max(110dlu;p), 5dlu, left:max(110dlu;p),pref",
                "8dlu,pref, 4dlu, pref, 4dlu, pref,4dlu, pref"); 
		setLayout(formLayout);
		CellConstraints cc = new CellConstraints(); 
		add(new JLabel("地址"), cc.xy(1, 2));
		JTextField address = new JTextField(32);
		add(address,cc.xywh(3, 2, 2, 1));
		add(new JLabel("端口"), cc.xy(1, 4));
		JTextField port = new JTextField(32);
		add(port,cc.xywh(3, 4, 2, 1));
		add(new JLabel("密钥"), cc.xy(1, 6));
		JTextField auth = new JTextField(32);
		add(auth,cc.xywh(3, 6, 2, 1));
		JButton ok = new JButton("确定");
		ok.addActionListener((e)-> {
			JOptionPane.showMessageDialog(this, "确定保存?", "信息提示框", JOptionPane.QUESTION_MESSAGE);
			RedisNodeModel note = new RedisNodeModel();
			note.setAddress(address.getText());
			note.setPort(Integer.parseInt(port.getText()));
			note.setAuthorization(auth.getText());
			customerComponent.writeObject(Commons.fileName(note), note);
			tree.addChildren(note);
			redisInfoStore.add(note.getAddress(), note);
			setVisible(false);
		});
		add(ok, cc.xy(1, 8));
		JButton cancel = new JButton("取消");
		cancel.addActionListener((e)-> setVisible(false));
		add(cancel,cc.xy(3,8));
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}
}

