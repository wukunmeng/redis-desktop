/**
 * Project Name:common
 * File Name:RedisInfoStore.java
 * Package Name:com.redis.desktop.store
 * Date:2020年3月29日下午8:10:23
 * Copyright (c) 2020, wukunmeng@tom.com All Rights Reserved.
 **/

package com.redis.desktop.store;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.redis.desktop.component.CommonComponent;
import com.redis.desktop.model.RedisNodeModel;

import redis.clients.jedis.Jedis;

/**
 * ClassName:RedisInfoStore <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2020年3月29日 下午8:10:23 <br/>
 * @author   wukm
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
@Component
public class RedisInfoStore extends CommonComponent{

	private Map<String,RedisNodeModel> redis = new ConcurrentHashMap<String, RedisNodeModel>();
	
	private Map<String,Jedis> clients = new ConcurrentHashMap<String, Jedis>();
	
	public void add(String name, RedisNodeModel node) {
		redis.put(name, node);
	}
	
	public void add(String name, Jedis client) {
		clients.put(name, client);
		new Thread() {
			public void run() {
				while(true) {
					skip(10);
					logger.info("name:{},ping:{}",name, client.ping());
				}
			}
		}.start();
	}
	
	public RedisNodeModel getRedis(String name) {
		return redis.get(name);
	}
	
	public Jedis getRedisClient(String name) {
		return clients.get(name);
	}
	
	public void closeClients() {
		clients.forEach((k,v) -> {
			logger.info("close:{}", k);
			v.close();
		});
	}
}

