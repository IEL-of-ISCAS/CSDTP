/*
 * Copyright (C) 2013 IEL of ISCAS
 * Project:CSDTP
 * Author: voidmain
 * Create Date: May 6, 20132:58:50 PM
 */
package cn.ac.iscas.iel.csdtp.data;

import cn.ac.iscas.iel.csdtp.controller.Device;

/**
 * A frame that sends control messages without real payloads
 * 
 * @Project CSDTP
 * @Package cn.ac.iscas.iel.csdtp.data
 * @Class ControlFrame
 * @Date May 6, 2013 2:58:50 PM
 * @author voidmain
 */
public class ControlFrame extends Frame {

	public ControlFrame(Device theDevice, int msgType) {
		super(theDevice, msgType);
	}

}
