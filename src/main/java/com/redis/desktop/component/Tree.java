/**
 * Project Name:common
 * File Name:Tree.java
 * Package Name:com.redis.desktop.component
 * Date:2020年4月5日上午11:43:29
 * Copyright (c) 2020, wukunmeng@tom.com All Rights Reserved.
 **/

package com.redis.desktop.component;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JTree;
import javax.swing.tree.TreeNode;

import com.redis.desktop.util.FontUtil;

/**
 * ClassName:Tree <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2020年4月5日 上午11:43:29 <br/>
 * @author   wukm
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
public class Tree extends JTree {

	/**
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么).
	 * @since JDK 1.8
	 */
	private static final long serialVersionUID = 1L;

	public Tree(TreeNode root) {
		super(root);
		init();
	}
	
	private void init() {
		setFont(FontUtil.createTreeFont());
	}
	
	public void paintComponent(Graphics g){
        Graphics2D g2d=(Graphics2D)g;   
        g2d.setRenderingHint(
             RenderingHints.KEY_ANTIALIASING,
             RenderingHints.VALUE_ANTIALIAS_ON);
        super.paintComponent(g2d);
     }
}

