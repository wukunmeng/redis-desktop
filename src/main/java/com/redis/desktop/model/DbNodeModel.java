/**
 * Project Name:redis-desktop
 * File Name:DbNodeModel.java
 * Package Name:com.redis.desktop.model
 * Date:2020年3月31日上午10:46:16
 * Copyright (c) 2020, wukunmeng@gmail.com All Rights Reserved.
 **/

package com.redis.desktop.model;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

/**
 * ClassName:DbNodeModel <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2020年3月31日 上午10:46:16 <br/>
 * @author   wukm
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public class DbNodeModel implements Serializable {

	/**
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么).
	 * @since JDK 1.8
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer db;
	
	private String size;
	
	private RedisNodeModel redisNodeModel;
	
	private Integer tabIndex;
	
	private int scanCount = 10;

	public String getName() {
		StringBuilder name = new StringBuilder();
		name.append(getRedisNodeModel().getAddress());
		name.append("{");
		name.append(db);
		name.append("}");
		return name.toString();
	}

	public Integer getDb() {
		return db;
	}

	public void setDb(Integer db) {
		this.db = db;
	}

	public RedisNodeModel getRedisNodeModel() {
		return redisNodeModel;
	}

	public void setRedisNodeModel(RedisNodeModel redisNodeModel) {
		this.redisNodeModel = redisNodeModel;
	}
	
	
	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public Integer getTabIndex() {
		return tabIndex;
	}

	public void setTabIndex(Integer tabIndex) {
		this.tabIndex = tabIndex;
	}

	public int getScanCount() {
		return scanCount;
	}

	public void setScanCount(int scanCount) {
		this.scanCount = scanCount;
	}

	public String toString() {
		StringBuilder showName = new StringBuilder();
		showName.append("db");
		if(StringUtils.isNotBlank(getSize())) {
			int len = StringUtils.length(getSize());
			showName.append(
					StringUtils.leftPad(String.valueOf(getDb()), len, "0"));
		}
		return showName.toString();
	}
}

