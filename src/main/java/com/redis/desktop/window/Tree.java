/**
 * Project Name:common
 * File Name:Tree.java
 * Package Name:com.redis.desktop.window
 * Date:2020年3月22日上午10:16:20
 * Copyright (c) 2020, wukunmeng@tom.com All Rights Reserved.
 **/

package com.redis.desktop.window;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.redis.desktop.component.CommonComponent;
import com.redis.desktop.model.RedisNodeModel;
import com.redis.desktop.store.RedisInfoStore;

import redis.clients.jedis.Jedis;

/**
 * ClassName:Tree <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2020年3月22日 上午10:16:20 <br/>
 * 
 * @author wukm
 * @version
 * @since JDK 1.8
 * @see
 */
@Component
public class Tree extends CommonComponent {

	@Value("classpath:icons/icon_expand_16.png")
	private Resource expandIconFile;
	
	@Value("classpath:icons/icon_collapse_16.png")
	private Resource collapseIconFile;
	
	@Value("classpath:icons/icon_open_16.png")
	private Resource openIconFile;
	
	@Value("classpath:icons/icon_close_16.png")
	private Resource closeIconFile;
	
	@Value("classpath:icons/icon_tree-leaf_16.png")
	private Resource leafIconFile;
	
	private JTree tree;
	
	@Autowired
	private RedisInfoStore redisInfoStore;

	private DefaultMutableTreeNode root = new DefaultMutableTreeNode("Redis");
	
	@PostConstruct
	public void initialize() {
		for(String name:listDataFile()) {
			logger.info("name:{}", name);
			RedisNodeModel node = readObject(name);
			root.add(new DefaultMutableTreeNode(node.getAddress()));
			redisInfoStore.add(node.getAddress(), node);
		}
		tree = new JTree(root);
		treeCellRenderer((DefaultTreeCellRenderer) tree.getCellRenderer());
		//treeUI((BasicTreeUI)tree.getUI());
		tree.collapsePath(new TreePath(root.getRoot()));
		tree.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				logger.info("client:{}", e.getClickCount());
				if(e.getClickCount() == 2) {
					JTree tree = (JTree)e.getSource();
					logger.info("client:{}", e.getSource().getClass().getName());
					DefaultMutableTreeNode currentTreeNode = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
					RedisNodeModel node = redisInfoStore.getRedis(currentTreeNode.toString());
					if(node != null && currentTreeNode.getChildCount() < 1) {
						Jedis client = new Jedis(node.getAddress(), node.getPort());
						if(!StringUtils.isEmpty(node.getAuthorization())) {
							client.auth(node.getAuthorization());
						}
						logger.info("ping:{}",client.ping());
						redisInfoStore.add(node.getAddress(), client);
						List<String> list = client.configGet("databases");
						if(list == null || list.size() != 2) {
							logger.info("database-index:{}", list);
							((DefaultTreeModel)tree.getModel()).insertNodeInto(new DefaultMutableTreeNode("db"), currentTreeNode, currentTreeNode.getChildCount());
						}
						int index = Integer.parseInt(list.get(1));
						for(int i = 0; i < index; i++) {
							logger.info("database-index:{}", i);
							((DefaultTreeModel)tree.getModel()).insertNodeInto(new DefaultMutableTreeNode("db" + i), currentTreeNode, currentTreeNode.getChildCount());
						}
					}
					logger.info("path:{}",currentTreeNode.toString());
				}
			}
		});
//		tree.addTreeSelectionListener((e) -> {
//			Object[] paths = e.getNewLeadSelectionPath().getPath();
//			RedisNodeModel node = redisInfoStore.getRedis(paths[paths.length - 1].toString());
//			if(node != null) {
//				Jedis client = new Jedis(node.getAddress(), node.getPort());
//				if(!StringUtils.isEmpty(node.getAuthorization())) {
//					client.auth(node.getAuthorization());
//				}
//				logger.info("ping:{}",client.ping());
//				redisInfoStore.add(node.getAddress(), client);
//				DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
//				for(int i = 0;i < 10; i ++) {
//					logger.info("test:{}", i);
//					((DefaultTreeModel)tree.getModel()).insertNodeInto(currentNode, new DefaultMutableTreeNode("db" + i), currentNode.getChildCount());
//				}
//			}
//			logger.info("path:{}",paths);
//		});
	}
	
	private void treeCellRenderer(DefaultTreeCellRenderer treeCellRenderer) {
		treeCellRenderer.setOpenIcon(createImageIcon(openIconFile));
		treeCellRenderer.setClosedIcon(createImageIcon(closeIconFile));
		treeCellRenderer.setLeafIcon(createImageIcon(leafIconFile));
		//treeCellRenderer.setIcon(new ImageIcon(leafIconImagePath));
	}
	
//	private void treeUI(BasicTreeUI treeUI) {
//		treeUI.setCollapsedIcon(createImageIcon(collapseIconFile));
//		treeUI.setExpandedIcon(createImageIcon(expandIconFile));
//	}

	public JTree tree() {
		return tree;
	}
	
	public void addChildren(RedisNodeModel redis) {
		DefaultMutableTreeNode node = new DefaultMutableTreeNode(redis.getAddress());
		DefaultTreeModel model = ((DefaultTreeModel)tree.getModel());
		model.insertNodeInto(node, root, root.getChildCount());;
	}	
}
