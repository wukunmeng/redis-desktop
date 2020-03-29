/**
 * Project Name:redis-desktop
 * File Name:CommonComponent.java
 * Package Name:com.redis.desktop.component
 * Date:2020年3月26日下午4:15:48
 * Copyright (c) 2020, wukunmeng@gmail.com All Rights Reserved.
 **/

package com.redis.desktop.component;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.Serializable;

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

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;
import org.springframework.util.SerializationUtils;

import com.redis.desktop.util.Commons;

public abstract class CommonComponent implements ApplicationContextAware {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Value("${temp.path:/home/wukm/dwhelper/data}")
	private String dataPath;
	
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
	
	public void writeObject(String fileName, Serializable s) {
		File file = new File(new File(dataPath),fileName);
		try {
			FileUtils.writeByteArrayToFile(file, 
					SerializationUtils.serialize(s));
		} catch(IOException e) {
			logger.error("read file:{},IOException:{}", 
					file.getAbsolutePath(), e.getMessage());;
		}
	}
	
	public String[] listDataFile() {
		File dir = new File(dataPath);
		return dir.list(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				// TODO Auto-generated method stub
				File f = new File(dir, name);
				if(f.isFile()) {
					return name.endsWith(Commons.DATA_FILE);
				}
				return false;
			}
		});
	}
	
	@SuppressWarnings("unchecked")
	public <T> T readObject(String fileName) {
		File file = new File(new File(dataPath),fileName);
		try {
			return (T)SerializationUtils.deserialize(
					FileUtils.readFileToByteArray(file));
		} catch(IOException e) {
			logger.error("read file:{},IOException:{}", 
					file.getAbsolutePath(), e.getMessage());;
		}
		return null;
	}
	
	public void skip(long s) {
		try {
			Thread.sleep(s * 1000);
		} catch (Exception e) {
			logger.error("Exception:{}", e.getMessage());
		}
	}
}

