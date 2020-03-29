/**
 * Project Name:common
 * File Name:Commons.java
 * Package Name:com.redis.desktop.util
 * Date:2020年3月29日下午7:09:39
 * Copyright (c) 2020, wukunmeng@tom.com All Rights Reserved.
 **/

package com.redis.desktop.util;

import com.redis.desktop.model.RedisNodeModel;

/**
 * ClassName:Commons <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2020年3月29日 下午7:09:39 <br/>
 * @author   wukm
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
public abstract class Commons {

	public final static String DATA_FILE = ".data";
	
	public final static String fileName(RedisNodeModel redis) {
		return redis.getAddress().concat(DATA_FILE);
	}
}

