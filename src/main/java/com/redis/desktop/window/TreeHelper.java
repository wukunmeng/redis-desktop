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
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.redis.desktop.component.CommonComponent;
import com.redis.desktop.component.MenuItem;
import com.redis.desktop.component.Tree;
import com.redis.desktop.model.DbNodeModel;
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
public class TreeHelper extends CommonComponent {

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
	
	private Tree tree;
	
	@Autowired
	private MainFrame frame;
	
	@Autowired
	private RedisInfoStore redisInfoStore;
	
	@Autowired
	private JTabbedPaneHelper jTabbedPaneHelper;

	private DefaultMutableTreeNode root = new DefaultMutableTreeNode("Redis");
	
	@PostConstruct
	public void initialize() {
		for(String name:listDataFile()) {
			logger.info("name:{}", name);
			RedisNodeModel node = readObject(name);
			root.add(new DefaultMutableTreeNode(node));
			redisInfoStore.add(node.getAddress(), node);
		}
		tree = new Tree(root);
		treeCellRenderer((DefaultTreeCellRenderer) tree.getCellRenderer());
		//treeUI((BasicTreeUI)tree.getUI());
		tree.collapsePath(new TreePath(root.getRoot()));
		
		//popmenu
		JPopupMenu closeRedis = new JPopupMenu();
		MenuItem pupop = new MenuItem("关闭");
		closeRedis.add(pupop);
		pupop.addActionListener((e)->{
			//int index = tree.getSelectionModel();
			//m.setVisible(false);
		});
		
		tree.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				logger.info("client:{}", e.getClickCount());
				logger.info("button:{}", e.getButton());
				if(e.getClickCount() == 2) {
					JTree tree = (JTree)e.getSource();
					logger.info("client:{}", e.getSource().getClass().getName());
					Object o = tree.getLastSelectedPathComponent();
					if(o == null) {
						logger.info("clicked...");
						return;
					}
					DefaultMutableTreeNode currentTreeNode = (DefaultMutableTreeNode)o;
					if((currentTreeNode.getUserObject() instanceof RedisNodeModel)) {
						logger.info("click:{}", currentTreeNode.getUserObject().toString());
						processRedisNode(tree,currentTreeNode);
						return;
					}
					if((currentTreeNode.getUserObject() instanceof DbNodeModel)) {
						logger.info("click:{}", currentTreeNode.getUserObject().toString());
						processDbNode(tree,currentTreeNode);
						return;
					}
					logger.info("path:{}",currentTreeNode.toString());
				}

				if(e.getClickCount() == 1) {
					if(e.getButton() == MouseEvent.BUTTON2) {
						closeRedis.setVisible(true);
					}
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

	public Tree tree() {
		return tree;
	}
	
	public void addChildren(RedisNodeModel redis) {
		DefaultMutableTreeNode current = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
		if(current != null && current.getUserObject() instanceof RedisNodeModel) {
			current.setUserObject(redis);
			return;
		}
		DefaultMutableTreeNode node = new DefaultMutableTreeNode(redis);
		DefaultTreeModel model = ((DefaultTreeModel)tree.getModel());
		model.insertNodeInto(node, root, root.getChildCount());;
	}
	
	public void processRedisNode(JTree tree, DefaultMutableTreeNode clickNode) {
		RedisNodeModel redisNode = (RedisNodeModel)clickNode.getUserObject();
		RedisNodeModel node = redisInfoStore.getRedis(redisNode.getAddress());
		Jedis client = null;
		try {
			if(node != null && clickNode.getChildCount() < 1) {
				client = redisInfoStore.getRedisClient(node);
				List<String> list = client.configGet("databases");
				if(list == null || list.size() != 2) {
					logger.info("database-index:{}", list);
					DbNodeModel dbNode = new DbNodeModel();
					dbNode.setDb(-1);
					((DefaultTreeModel)tree.getModel()).insertNodeInto(new DefaultMutableTreeNode(dbNode), clickNode, clickNode.getChildCount());
				}
				String databases = list.get(1);
				int index = Integer.parseInt(databases);
				for(int i = 0; i < index; i++) {
					DbNodeModel dbNode = new DbNodeModel();
					dbNode.setDb(i);
					dbNode.setSize(databases);
					dbNode.setRedisNodeModel(redisNode);
					((DefaultTreeModel)tree.getModel()).insertNodeInto(new DefaultMutableTreeNode(dbNode), clickNode, clickNode.getChildCount());
				}
				redisNode.setConnect(true);
			}
		} catch (Exception ex) {
			logger.error("connect:{}, exception:{}", redisNode.getAddress(), ex.getMessage());
			JOptionPane.showMessageDialog(frame, "不能连接:" + redisNode.getAddress(), "错误提示框", JOptionPane.ERROR_MESSAGE);
		} finally {
			if(client != null) {
				client.close();
			}
		}
	}
	
	public void processDbNode(JTree tree, DefaultMutableTreeNode clickNode) {
		DbNodeModel dbNode = (DbNodeModel)clickNode.getUserObject();
		if(jTabbedPaneHelper.isOpenTab(dbNode)) {
			jTabbedPaneHelper.selectIndex(dbNode);
		} else {
			jTabbedPaneHelper.createTab(dbNode);
		}
	}
}
