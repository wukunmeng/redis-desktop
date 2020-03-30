/**
 * Project Name:redis-desktop
 * File Name:RedisCloseEvent.java
 * Package Name:com.redis.desktop.listener.event
 * Date:2020年3月30日上午11:42:30
 * Copyright (c) 2020, wukunmeng@gmail.com All Rights Reserved.
 **/

package com.redis.desktop.listener.event;

import org.springframework.context.ApplicationEvent;

import com.redis.desktop.model.RedisNodeModel;

/**
 * ClassName:RedisCloseEvent <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2020年3月30日 上午11:42:30 <br/>
 * @author   wukm
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public class RedisCloseEvent extends ApplicationEvent {

	/**
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么).
	 * @since JDK 1.8
	 */
	private static final long serialVersionUID = 1L;
	
	private RedisNodeModel source;
	
	public RedisCloseEvent(RedisNodeModel source) {
		super(source);
		// TODO Auto-generated constructor stub
		this.source = source;
	}

	public RedisNodeModel getRedisNode() {
		return this.source;
	}

}

