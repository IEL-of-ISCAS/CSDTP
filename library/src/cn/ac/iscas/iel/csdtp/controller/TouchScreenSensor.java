/*
 * Copyright (C) 2013 IEL of ISCAS
 * Project:CSDTP
 * Author: voidmain
 * Create Date: Apr 25, 20132:42:57 PM
 */
package cn.ac.iscas.iel.csdtp.controller;

/**   
 * Sensor of the touch screen
 * 
 * @Project CSDTP
 * @Package cn.ac.iscas.iel.csdtp.controller
 * @Class TouchSensor
 * @Date Apr 25, 2013 2:42:57 PM
 * @author voidmain
 */
public class TouchScreenSensor extends Sensor {

	/* @see cn.ac.iscas.iel.csdtp.controller.Sensor#getSensorName()
	 *getSensorName
	 */
	@Override
	public String getSensorName() {
		return "touchscreen";
	}

}
