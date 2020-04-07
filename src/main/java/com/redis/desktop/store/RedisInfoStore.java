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
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;

import com.redis.desktop.component.CommonComponent;
import com.redis.desktop.listener.event.RedisCloseEvent;
import com.redis.desktop.model.DbNodeModel;
import com.redis.desktop.model.RedisNodeModel;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

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
	
	private Map<String,JedisPool> clients = new ConcurrentHashMap<String, JedisPool>();
	
	public void add(String name, RedisNodeModel node) {
		redis.put(name, node);
	}
	
//	public void add(String name, Jedis client) {
//		clients.put(name, client);
//	}
	
	public RedisNodeModel getRedis(String name) {
		return redis.get(name);
	}
	
	public Jedis getRedisClient(String name) {
		return getRedisClient(redis.get(name));
	}
	
	public Jedis getRedisClient(DbNodeModel dbNode) {
		Jedis redisClient = getRedisClient(dbNode.getRedisNodeModel());
		if(redisClient != null) {
			if(dbNode.getDb() != null && dbNode.getDb() >= 0) {
				redisClient.select(dbNode.getDb());
			}
		}
		return redisClient;
	}
	
	public Jedis getRedisClient(RedisNodeModel redisNode) {
		JedisPool redisPool = clients.get(redisNode.getAddress());
		if(redisPool == null) {
			JedisPoolConfig config = new JedisPoolConfig();
			config.setMaxTotal(5);
			if(StringUtils.isBlank(redisNode.getAuthorization())) {
				redisPool = new JedisPool(config, 
						redisNode.getAddress(),
						NumberUtils.toInt(redisNode.getAddress(), Protocol.DEFAULT_PORT));
			} else {
				redisPool = new JedisPool(config, 
						redisNode.getAddress(),
						NumberUtils.toInt(redisNode.getAddress(), Protocol.DEFAULT_PORT),
						Protocol.DEFAULT_TIMEOUT, redisNode.getAuthorization());
			}
			clients.put(redisNode.getAddress(), redisPool);
		}
		return redisPool.getResource();
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
		JedisPool rp = clients.remove(add);
		rp.close();
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
	
	private boolean tryClient(String address, JedisPool pool) {
		try {
			return !pool.isClosed();
			//return StringUtils.equalsIgnoreCase("pong", client.ping());
		} catch(Exception e) {
			logger.warn("client failed:{}, Exception:{}", address, e.getMessage());
		}
		return false;
	}
}

