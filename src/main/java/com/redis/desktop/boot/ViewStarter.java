/**
 * Project Name:redis-desktop
 * File Name:ViewStarter.java
 * Package Name:com.redis.desktop.boot
 * Date:2020年3月26日上午10:34:41
 * Copyright (c) 2020, wukunmeng@gmail.com All Rights Reserved.
 **/

package com.redis.desktop.boot;

import java.awt.EventQueue;

import com.redis.desktop.util.SpringContextUtil;
import com.redis.desktop.window.MainFrame;

/**
 * ClassName:ViewStarter <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2020年3月26日 上午10:34:41 <br/>
 * @author   wukm
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
public class ViewStarter {

	public static void run() {
		EventQueue.invokeLater(()-> {
//			WebLookAndFeel.install();
//			try {
//				UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
//			} catch (Exception e) {
//				// TODO: handle exception
//			}
			SpringContextUtil.getBean(MainFrame.class).showWindow();
		});
	}
}

