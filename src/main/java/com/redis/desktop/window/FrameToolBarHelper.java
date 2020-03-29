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
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.redis.desktop.component.CommonComponent;
import com.redis.desktop.listener.SystemExitListener;
import com.redis.desktop.store.RedisInfoStore;

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
	
	@Value("classpath:icons/icon_delete_24.png")
	private Resource deleteRedisServerIconFile;
	
	private JToolBar toolBar = new JToolBar();
	
	@Autowired
	private SystemExitListener systemExitListener;
	
	@Autowired
	private Tree tree;
	
	@Autowired
	private RedisInfoStore redisInfoStore;
	
	@PostConstruct
	public void initialize() {
		
		JButton addRedisServer = new JButton(createImageIcon(addRedisServerIconFile));
		addRedisServer.setToolTipText("添加Redis");
		addRedisServer.addActionListener((e)-> getBean(RedisEditFrame.class).showWindow());
		toolBar.add(addRedisServer);
		
		JButton deleteRedisServer = new JButton(createImageIcon(deleteRedisServerIconFile));
		deleteRedisServer.setToolTipText("删除选中Redis");
		deleteRedisServer.addActionListener((e) ->{
			deleteTree(tree.tree(),(DefaultMutableTreeNode)tree.tree().getLastSelectedPathComponent());
		});
		toolBar.add(deleteRedisServer);
		
		JButton exit = new JButton(createImageIcon(systemExitIconFile));
		exit.setToolTipText("退出");
		exit.addActionListener(systemExitListener);
		toolBar.add(exit);
	}
	
	private String deleteTree(JTree tree, DefaultMutableTreeNode node) {
		if(node.isRoot()) {
			return null;
		}
		if(redisInfoStore.getRedis(node.toString()) == null) {
			return deleteTree(tree, (DefaultMutableTreeNode)node.getParent());
		} else {
			String path = node.toString();
			((DefaultTreeModel)tree.getModel()).removeNodeFromParent(node);
			return path;
		}
	}
	
	public JToolBar toolBar() {
		return toolBar;
	}
}

