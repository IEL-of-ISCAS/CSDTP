/*
 * Copyright (C) 2013 IEL of ISCAS
 * Project:CSDTP
 * Author: voidmain
 * Create Date: Apr 25, 20132:37:36 PM
 */
package cn.ac.iscas.iel.csdtp.controller;

/**   
 * Sensor of acceleration
 * 
 * @Project CSDTP
 * @Package cn.ac.iscas.iel.csdtp.controller
 * @Class AccelerometersSensor
 * @Date Apr 25, 2013 2:37:36 PM
 * @author voidmain
 */
public class AccelerometersSensor extends Sensor {

	/* @see cn.ac.iscas.iel.csdtp.controller.Sensor#getSensorName()
	 *getSensorName
	 */
	@Override
	public String getSensorName() {
		return "accelerometers";
	}

}
