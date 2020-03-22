/**
 * Project Name:common
 * File Name:Tree.java
 * Package Name:com.redis.desktop.window
 * Date:2020年3月22日上午10:16:20
 * Copyright (c) 2020, wukunmeng@tom.com All Rights Reserved.
 **/

package com.redis.desktop.window;

import javax.annotation.PostConstruct;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.plaf.basic.BasicTreeUI;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreePath;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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
public class Tree {

	@Value("${spring.tree.open.icon}")
	private String openIconImagePath = "";
	
	@Value("${spring.tree.closed.icon}")
	private String closedIconImagePath = "";
	
	@Value("${spring.tree.leaf.icon}")
	private String leafIconImagePath = "";
	
	@Value("${spring.tree.collapsed.icon}")
	private String collapsedImagePath = "";
	
	@Value("${spring.tree.expanded.icon}")
	private String expandedImagePath = "";
	
	private JTree tree;

	@PostConstruct
	public void initialize() {
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Redis");
		for (int i = 0; i < 10; i++) {
			root.add(new DefaultMutableTreeNode("db" + i));
		}
		tree = new JTree(root);
		treeCellRenderer((DefaultTreeCellRenderer) tree.getCellRenderer());
		treeUI((BasicTreeUI)tree.getUI());
		tree.collapsePath(new TreePath(root.getRoot()));
		
	}
	
	private void treeCellRenderer(DefaultTreeCellRenderer treeCellRenderer) {
		treeCellRenderer.setOpenIcon(new ImageIcon(openIconImagePath));
		treeCellRenderer.setClosedIcon(new ImageIcon(closedIconImagePath));
		treeCellRenderer.setLeafIcon(new ImageIcon(leafIconImagePath));
		//treeCellRenderer.setIcon(new ImageIcon(leafIconImagePath));
	}
	
	private void treeUI(BasicTreeUI treeUI) {
		treeUI.setCollapsedIcon(new ImageIcon(collapsedImagePath));
		treeUI.setExpandedIcon(new ImageIcon(expandedImagePath));
	}

	public JTree tree() {
		return tree;
	}
}
