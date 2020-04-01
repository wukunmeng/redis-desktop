/**
 * Project Name:redis-desktop
 * File Name:ChangedValueListener.java
 * Package Name:com.redis.desktop.listener
 * Date:2020年4月1日下午6:55:35
 * Copyright (c) 2020, wukunmeng@gmail.com All Rights Reserved.
 **/

package com.redis.desktop.listener;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

/**
 * ClassName:ChangedValueListener <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2020年4月1日 下午6:55:35 <br/>
 * @author   wukm
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public abstract class ChangedValueListener extends AbstractListener implements DocumentListener {

	@Override
	public void insertUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		try {
			String text = e.getDocument().getText(0, e.getDocument().getLength());
			logger.info("insert:{}", text);
			valuechanged(e, text);
		} catch(BadLocationException ex) {
			logger.info("BadLocationException:{}", ex.getMessage());
		}
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		try {
			String text = e.getDocument().getText(0, e.getDocument().getLength());
			logger.info("remove:{}", text);
			valuechanged(e, text);
		} catch(BadLocationException ex) {
			logger.info("BadLocationException:{}", ex.getMessage());
		}
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		try {
			String text = e.getDocument().getText(0, e.getDocument().getLength());
			logger.info("changed:{}", text);
			valuechanged(e, text);
		} catch(BadLocationException ex) {
			logger.info("BadLocationException:{}", ex.getMessage());
		}
	}
	
	public abstract void valuechanged(DocumentEvent e, String value);
}

