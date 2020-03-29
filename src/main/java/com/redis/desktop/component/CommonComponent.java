/**
 * Project Name:redis-desktop
 * File Name:CommonComponent.java
 * Package Name:com.redis.desktop.component
 * Date:2020年3月26日下午4:15:48
 * Copyright (c) 2020, wukunmeng@gmail.com All Rights Reserved.
 **/

package com.redis.desktop.component;
import java.io.IOException;

/**
 * ClassName:CommonComponent <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2020年3月26日 下午4:15:48 <br/>
 * @author   wukm
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
import javax.swing.ImageIcon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;

public abstract class CommonComponent implements ApplicationContextAware {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private ApplicationContext applicationContext;
	
	protected ImageIcon createImageIcon(Resource resource) {
		try {
			return new ImageIcon(resource.getURL());
		} catch (IOException e) {
			logger.error("IOException:{}", e.getMessage());
		}
		return null;
	}
	
	@Override
	public final void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		// TODO Auto-generated method stub
		this.applicationContext = applicationContext;
	}
	
	public <T> T getBean(Class<T> requiredType){
		return this.applicationContext.getBean(requiredType);
	}
}

