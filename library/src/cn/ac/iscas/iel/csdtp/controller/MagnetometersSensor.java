/*
 * Copyright (C) 2013 IEL of ISCAS
 * Project:CSDTP
 * Author: voidmain
 * Create Date: Apr 25, 20132:33:55 PM
 */
package cn.ac.iscas.iel.csdtp.controller;

/**   
 * Sensor of magnetic 
 * 
 * @Project CSDTP
 * @Package cn.ac.iscas.iel.csdtp.controller
 * @Class MagnetometersSensor
 * @Date Apr 25, 2013 2:33:55 PM
 * @author voidmain
 */
public class MagnetometersSensor extends Sensor {

	/* @see cn.ac.iscas.iel.csdtp.controller.Sensor#getSensorName()
	 *getSensorName
	 */
	@Override
	public String getSensorName() {
		return "magnetometers";
	}

}
