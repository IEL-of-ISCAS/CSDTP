/*
 * Copyright (C) 2013 IEL of ISCAS
 * Project:CSDTP
 * Author: voidmain
 * Create Date: Apr 25, 201310:24:28 AM
 */
package cn.ac.iscas.iel.csdtp.exception;

/**
 * Throws when try to send frame without setting the proper output channel
 * 
 * @Project CSDTP
 * @Package cn.ac.iscas.iel.csdtp.exception
 * @Class ChannelNotSetException
 * @Date Apr 25, 2013 10:24:28 AM
 * @author voidmain
 */
public class ChannelNotSetException extends Exception {

	/**
	 * Generated UUID
	 */
	private static final long serialVersionUID = -2329861975932251595L;

	public ChannelNotSetException() {
		super();
	}

	public ChannelNotSetException(String arg0) {
		super(arg0);
	}

}
