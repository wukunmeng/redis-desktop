/**
 * Project Name:redis-desktop
 * File Name:EventProcesser.java
 * Package Name:com.redis.desktop.listener
 * Date:2020年3月30日上午11:46:53
 * Copyright (c) 2020, wukunmeng@gmail.com All Rights Reserved.
 **/

package com.redis.desktop.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.redis.desktop.listener.event.RedisCloseEvent;
import com.redis.desktop.store.RedisInfoStore;

/**
 * ClassName:EventProcesser <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2020年3月30日 上午11:46:53 <br/>
 * @author   wukm
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
@Component
public class EventProcesser extends AbstractListener{

	@Autowired
	private RedisInfoStore redisInfoStore; 
	
	@Async
	@EventListener
	public void redisEvent(RedisCloseEvent redisCloseEvent) {
		logger.info("start process event:{}", redisCloseEvent.getRedisNode());
		redisInfoStore.closeClient(redisCloseEvent.getRedisNode());
		logger.info("end process event:{}", redisCloseEvent.getRedisNode());
	}
}

