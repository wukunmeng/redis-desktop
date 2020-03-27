/**
 * Project Name:common
 * File Name:Tree.java
 * Package Name:com.redis.desktop.window
 * Date:2020年3月22日上午10:16:20
 * Copyright (c) 2020, wukunmeng@tom.com All Rights Reserved.
 **/

package com.redis.desktop.window;

import javax.annotation.PostConstruct;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreePath;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.redis.desktop.component.CommonComponent;

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

	@PostConstruct
	public void initialize() {
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Redis");
		for (int i = 0; i < 10; i++) {
			root.add(new DefaultMutableTreeNode("db" + i));
		}
		tree = new JTree(root);
		treeCellRenderer((DefaultTreeCellRenderer) tree.getCellRenderer());
		//treeUI((BasicTreeUI)tree.getUI());
		tree.collapsePath(new TreePath(root.getRoot()));
		
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
}
