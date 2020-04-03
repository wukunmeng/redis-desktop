/**
 * Project Name:redis-desktop
 * File Name:DbScanCountModel.java
 * Package Name:com.redis.desktop.model
 * Date:2020年4月3日下午4:57:04
 * Copyright (c) 2020, wukunmeng@gmail.com All Rights Reserved.
 **/

package com.redis.desktop.model;

import java.io.Serializable;

/**
 * ClassName:DbScanCountModel <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2020年4月3日 下午4:57:04 <br/>
 * @author   wukm
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public class DbScanCountModel implements Serializable{

	/**
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么).
	 * @since JDK 1.8
	 */
	private static final long serialVersionUID = 1L;
	
	private String name;
	
	private Integer count;
	
	public DbScanCountModel(String name,Integer count) {
		this.name = name;
		this.count = count;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
	
	public String toString() {
		return name;
	}
}

