/**
 * Project Name:common
 * File Name:RedisNodeModel.java
 * Package Name:com.redis.desktop.model
 * Date:2020年3月29日下午4:42:39
 * Copyright (c) 2020, wukunmeng@tom.com All Rights Reserved.
 **/

package com.redis.desktop.model;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

/**
 * ClassName:RedisNodeModel <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2020年3月29日 下午4:42:39 <br/>
 * @author   wukm
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
public class RedisNodeModel implements Serializable{

	/**
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么).
	 * @since JDK 1.8
	 */
	private static final long serialVersionUID = 1L;
	
	private String name;
	
	private boolean connect = false;

	private String address;
	
	private Integer port;
	
	private String authorization;
	
	private String state;

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getAuthorization() {
		return authorization;
	}

	public void setAuthorization(String authorization) {
		this.authorization = authorization;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public boolean isConnect() {
		return connect;
	}

	public void setConnect(boolean connect) {
		this.connect = connect;
	}

	public String toString() {
		StringBuilder showName = new StringBuilder();
		if(StringUtils.isBlank(this.name)) {
			showName.append(this.getAddress());
		} else {
			showName.append(this.name);
		}
		showName.append(" ");
		if(isConnect()) {
			showName.append("📡"); 
		} else {
			showName.append("🔗"); 
		}
		return showName.toString();
	}
}

