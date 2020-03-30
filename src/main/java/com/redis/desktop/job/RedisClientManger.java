/**
 * Project Name:redis-desktop
 * File Name:RedisClientManger.java
 * Package Name:com.redis.desktop.job
 * Date:2020年3月30日上午10:59:06
 * Copyright (c) 2020, wukunmeng@gmail.com All Rights Reserved.
 **/

package com.redis.desktop.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.redis.desktop.store.RedisInfoStore;

/**
 * ClassName:RedisClientManger <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2020年3月30日 上午10:59:06 <br/>
 * @author   wukm
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
@Component
@EnableScheduling
@EnableAsync
public class RedisClientManger extends AbstractJobComponent{

	@Autowired
	private RedisInfoStore redisInfoStore;
	
	@Async
	@Scheduled(fixedDelay = 10000L)
	public void checkClientConnect() {
		logger.info("start check:{}", System.currentTimeMillis());
		redisInfoStore.checkClient();
		logger.info("end check:{}", System.currentTimeMillis());
	}
}

