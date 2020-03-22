/**
 * Project Name:common
 * File Name:Boot.java
 * Package Name:com.redis.desktop.boot
 * Date:2020年3月21日下午6:46:35
 * Copyright (c) 2020, wukunmeng@tom.com All Rights Reserved.
 **/

package com.redis.desktop.boot;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;

/**
 * ClassName:Boot <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2020年3月21日 下午6:46:35 <br/>
 * @author   wukm
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
@SpringBootApplication
@ComponentScan("com.redis")
public class Boot {

	public static void main(String[] args) {
		new SpringApplicationBuilder(Boot.class).web(WebApplicationType.NONE).headless(false).run(args);
	}

}

