/*
 * Copyright (C) 2013 IEL of ISCAS
 * Project:CSDTP
 * Author: voidmain
 * Create Date: Apr 23, 201310:38:38 PM
 */
package cn.ac.iscas.iel.csdtp.exception;

/**   
 * Throws the exception when there are more than one sample thread working
 * 
 * @Project CSDTP
 * @Package cn.ac.iscas.iel.csdtp.exception
 * @Class MultipleSampleThreadException
 * @Date Apr 23, 2013 10:38:38 PM
 * @author voidmain
 */
public class MultipleSampleThreadException extends Exception {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = 6050042574204598148L;

	public MultipleSampleThreadException() {
		super();
	}

	public MultipleSampleThreadException(String message) {
		super(message);
	}

}
