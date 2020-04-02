/**
 * Project Name:common
 * File Name:Boot.java
 * Package Name:com.redis.desktop.boot
 * Date:2020年3月21日下午6:46:35
 * Copyright (c) 2020, wukunmeng@tom.com All Rights Reserved.
 **/

package com.redis.desktop.boot;

import java.util.Properties;

import javax.swing.UIManager;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;

/**
 * ClassName:Boot <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2020年3月21日 下午6:46:35 <br/>
 * @author   wukm
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
@SpringBootApplication
@ComponentScan("com.redis")
public class Boot {

	public static void main(String[] args) {
		try {
			Properties props = System.getProperties();
			props.setProperty("javax.accessibility.assistive_technologies", "");
			UIManager.put("swing.boldMetal", Boolean.FALSE);
//			System.setProperty("awt.useSystemAAFontSettings", "on"); 
//			System.setProperty("swing.aatext", "true");
//			Font font = new Font(Font.MONOSPACED, Font.BOLD, 16);
//	        Enumeration<Object> keys = UIManager.getDefaults().keys();
//	        while (keys.hasMoreElements()) {
//	            Object key = keys.nextElement();
//	            Object value = UIManager.get(key);
//	            if (value instanceof javax.swing.plaf.FontUIResource) {
//	                UIManager.put(key, font);
//	            }
//	        }
	        //WebLookAndFeel.install();
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		new SpringApplicationBuilder(Boot.class).web(WebApplicationType.NONE).headless(false).run(args);
		ViewStarter.run();
	}

}

