/*
 * Copyright (C) 2013 IEL of ISCAS
 * Project:CSDTP
 * Author: voidmain
 * Create Date: Apr 24, 20139:34:22 AM
 */
package cn.ac.iscas.iel.csdtp.exception;

/**
 * When thread is sampling data, one should not change the sensor list
 * 
 * @Project CSDTP
 * @Package cn.ac.iscas.iel.csdtp.exception
 * @Class ChangeSensorWhileCollectingDataException
 * @Date Apr 24, 2013 9:34:22 AM
 * @author voidmain
 */
public class ChangeSensorWhileCollectingDataException extends Exception {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = -6356398480108936449L;

	public ChangeSensorWhileCollectingDataException() {
		super();
	}

	public ChangeSensorWhileCollectingDataException(String message) {
		super(message);
	}

}
