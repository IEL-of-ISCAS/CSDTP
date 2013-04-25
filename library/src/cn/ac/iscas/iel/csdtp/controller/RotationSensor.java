/*
 * Copyright (C) 2013 IEL of ISCAS
 * Project:CSDTP
 * Author: voidmain
 * Create Date: Apr 25, 20132:41:07 PM
 */
package cn.ac.iscas.iel.csdtp.controller;

/**   
 * The device's rotation vector
 * 
 * @Project CSDTP
 * @Package cn.ac.iscas.iel.csdtp.controller
 * @Class RotationSensor
 * @Date Apr 25, 2013 2:41:07 PM
 * @author voidmain
 */
public class RotationSensor extends Sensor {

	/* @see cn.ac.iscas.iel.csdtp.controller.Sensor#getSensorName()
	 *getSensorName
	 */
	@Override
	public String getSensorName() {
		return "rotation";
	}

}
