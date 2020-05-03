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
import javax.swing.JOptionPane;
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
import com.redis.desktop.model.RedisNodeModel;
import com.redis.desktop.store.RedisInfoStore;
import com.redis.desktop.util.Commons;

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
	
	@Value("classpath:icons/icon_edit_24.png")
	private Resource updateRedisServerIconFile;
	
	@Value("classpath:icons/icon_delete_24.png")
	private Resource deleteRedisServerIconFile;
	
	private JToolBar toolBar = new JToolBar();
	
	@Autowired
	private SystemExitListener systemExitListener;
	
	@Autowired
	private TreeHelper tree;
	
	@Autowired
	private MainFrame mainFrame;
	
	@Autowired
	private RedisInfoStore redisInfoStore;
	
	@PostConstruct
	public void initialize() {
		
		JButton addRedisServer = new JButton(createImageIcon(addRedisServerIconFile));
		addRedisServer.setToolTipText("添加Redis");
		addRedisServer.addActionListener((e)-> getBean(RedisEditFrame.class).showWindow());
		toolBar.add(addRedisServer);
		
		JButton editRedisServer = new JButton(createImageIcon(updateRedisServerIconFile));
		editRedisServer.setToolTipText("编辑Redis");
		editRedisServer.addActionListener((e -> {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode)tree.tree().getLastSelectedPathComponent();
			logger.info("编辑:{}", node);
			if(node != null && node.getUserObject() instanceof RedisNodeModel) {
				logger.info("编辑:{}", node.getUserObject());
				RedisNodeModel n = (RedisNodeModel) node.getUserObject();
				getBean(RedisEditFrame.class).editRedis(n);
				return;
			}
			JOptionPane.showMessageDialog(mainFrame, "请选择Redis服务节点编辑", "提示框", JOptionPane.INFORMATION_MESSAGE);
		}));
		toolBar.add(editRedisServer);
		
		JButton deleteRedisServer = new JButton(createImageIcon(deleteRedisServerIconFile));
		deleteRedisServer.setToolTipText("删除选中Redis");
		deleteRedisServer.addActionListener((e) ->{
			RedisNodeModel rnm = deleteTree(tree.tree(),(DefaultMutableTreeNode)tree.tree().getLastSelectedPathComponent());
			if(rnm != null) {
				redisInfoStore.clearRedis(rnm.getAddress());
				deleteObject(Commons.fileName(rnm));
			}
			
		});
		toolBar.add(deleteRedisServer);
		
		JButton exit = new JButton(createImageIcon(systemExitIconFile));
		exit.setToolTipText("退出");
		exit.addActionListener(systemExitListener);
		toolBar.add(exit);
	}
	
	private RedisNodeModel deleteTree(JTree tree, DefaultMutableTreeNode node) {
		if(node.isRoot()) {
			return null;
		}
		Object o = node.getUserObject();
		if(o instanceof RedisNodeModel) {
			RedisNodeModel redis = (RedisNodeModel)o;
			((DefaultTreeModel)tree.getModel()).removeNodeFromParent(node);
			return redis;
		}
		return null;
	}
	
	public JToolBar toolBar() {
		return toolBar;
	}
}

