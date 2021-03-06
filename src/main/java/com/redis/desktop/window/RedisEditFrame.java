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
import javax.swing.JOptionPane;
import javax.swing.JRootPane;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.redis.desktop.component.CustomerComponent;
import com.redis.desktop.component.Label;
import com.redis.desktop.component.TextField;
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
	private TreeHelper tree;
	
	@Autowired
	private CustomerComponent customerComponent;
	
	@Autowired
	private RedisInfoStore redisInfoStore;
	
	//地址
	private TextField address = new TextField(32);
	//端口
	private TextField port = new TextField(32);
	//密钥
	private TextField auth = new TextField(32);
	
	private RedisNodeModel note = null;
	
	public void editRedis(RedisNodeModel redisNode) {
		this.note = redisNode;
		address.setText(this.note.getAddress());
		port.setText(String.valueOf(this.note.getPort()));
		auth.setText(this.note.getAuthorization());
		showWindow();
	}
	
	public void showWindow() {
		setSize(400, 200);
		if(!isUndecorated()) {
			setUndecorated(true);
		}
		getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);
		FormLayout formLayout = new FormLayout(//  
                "right:max(110dlu;p), 5dlu, left:max(110dlu;p),pref",
                "8dlu,pref, 4dlu, pref, 4dlu, pref,4dlu, pref"); 
		setLayout(formLayout);
		CellConstraints cc = new CellConstraints(); 
		add(new Label("地址"), cc.xy(1, 2));
		add(address,cc.xywh(3, 2, 2, 1));
		add(new Label("端口"), cc.xy(1, 4));
		add(port,cc.xywh(3, 4, 2, 1));
		add(new Label("密钥"), cc.xy(1, 6));
		add(auth,cc.xywh(3, 6, 2, 1));
		JButton ok = new JButton("确定");
		ok.addActionListener((e)-> {
			if(note == null) {
				note = new RedisNodeModel();
			}
			if(StringUtils.isBlank(address.getText())) {
				showMessage("地址信息不可为空,请填写正确地址");
			}
			note.setAddress(address.getText());
			if(StringUtils.isBlank(port.getText())) {
				note.setPort(6379);
			} else if(!NumberUtils.isDigits(port.getText())) {
				showMessage("端口号必须是有效数字");
			} else {
				note.setPort(NumberUtils.toInt(port.getText()));
			}
			note.setAuthorization(auth.getText());
			redisInfoStore.getRedis(note.getAddress());
			customerComponent.writeObject(Commons.fileName(note), note);
			tree.addChildren(note);
			redisInfoStore.add(note.getAddress(), note);
			clear();
			setVisible(false);
		});
		add(ok, cc.xy(1, 8));
		JButton cancel = new JButton("取消");
		cancel.addActionListener((e)-> {
			clear();
			setVisible(false);
		});
		add(cancel,cc.xy(3,8));
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	private void clear() {
		note = null;
		address.setText("");
		port.setText("");
		auth.setText("");
	}
	
	private void showMessage(String tip) {
		JOptionPane.showMessageDialog(this, tip, "提示框", JOptionPane.INFORMATION_MESSAGE);
	}
}

