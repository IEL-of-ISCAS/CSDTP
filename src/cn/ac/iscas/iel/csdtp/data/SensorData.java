/*
 * Copyright (C) 2013 IEL of ISCAS
 * Project:CSDTP
 * Author: voidmain
 * Create Date: Apr 24, 20138:56:11 AM
 */
package cn.ac.iscas.iel.csdtp.data;

/**   
 * 
 * 
 * @Project CSDTP
 * @Package cn.ac.iscas.iel.csdtp.data
 * @Class SensorData
 * @Date Apr 24, 2013 8:56:11 AM
 * @author voidmain
 */
public abstract class SensorData {
	
	public static SensorData INVALID_DATA = null; 
	
	public boolean isValidData() {
		return !this.equals(INVALID_DATA);
	}
	
}
