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

import javax.annotation.PreDestroy;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.redis.desktop.component.CommonComponent;
import com.redis.desktop.listener.event.RedisCloseEvent;
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
	}
	
	public RedisNodeModel getRedis(String name) {
		return redis.get(name);
	}
	
	public Jedis getRedisClient(String name) {
		return clients.get(name);
	}
	
	@PreDestroy
	public void closeClients() {
		logger.info("close all clients...");
		clients.forEach((k,v) -> {
			logger.info("close:{}", k);
			v.close();
		});
		logger.info("close all clients");
	}
	
	public void clearRedis(String address) {
		RedisNodeModel redisNode = redis.remove(address);
		if(redisNode == null) {
			return;
		}
		if(clients.containsKey(redisNode.getAddress())) {
			closeClient(redisNode);
		}
	}
	
	public void closeClient(RedisNodeModel node) {
		String add = node.getAddress();
		Jedis c = clients.remove(add);
		c.close();
		redis.remove(add);
	}
	
	public void checkClient() {
		clients.forEach((k,v) -> {
			boolean ok = tryClient(k, v);
			logger.info("check client :{},state:{}",k, ok ? "ok":"failed");
			if(!ok) {
				publishEvent(new RedisCloseEvent(redis.get(k)));
			}
		});
	}
	
	private boolean tryClient(String address, Jedis client) {
		try {
			return StringUtils.equalsIgnoreCase("pong", client.ping());
		} catch(Exception e) {
			logger.warn("client failed:{}, Exception:{}", address, e.getMessage());
		}
		return false;
	}
}

