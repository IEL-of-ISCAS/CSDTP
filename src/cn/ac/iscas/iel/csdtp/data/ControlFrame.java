/*
 * Copyright (C) 2013 IEL of ISCAS
 * Project:CSDTP
 * Author: voidmain
 * Create Date: Apr 25, 201310:30:45 AM
 */
package cn.ac.iscas.iel.csdtp.data;

import cn.ac.iscas.iel.csdtp.controller.Device;

/**
 * The frame that contains control info for server to setup/cleanup
 * 
 * @Project CSDTP
 * @Package cn.ac.iscas.iel.csdtp.data
 * @Class ControlFrame
 * @Date Apr 25, 2013 10:30:45 AM
 * @author voidmain
 */
public class ControlFrame extends Frame {

	public static enum ControlType {
		CONNECT, DISCONNECT
	}

	protected ControlType mControlType;

	public ControlFrame(Device theDevice, ControlType type) {
		super(theDevice);
		mControlType = type;
	}

	public ControlType getControlType() {
		return mControlType;
	}

	public void setControlType(ControlType controlType) {
		mControlType = controlType;
	}

}
