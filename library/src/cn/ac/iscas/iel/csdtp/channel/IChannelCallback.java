/*
 * Copyright (C) 2013 IEL of ISCAS
 * Project:CSDTP
 * Author: voidmain
 * Create Date: May 6, 20132:45:34 PM
 */
package cn.ac.iscas.iel.csdtp.channel;

import cn.ac.iscas.iel.csdtp.data.ResponseData;

/**   
 * A callback interface that passes the returned data to client
 * 
 * @Project CSDTP
 * @Package cn.ac.iscas.iel.csdtp.channel
 * @Class IChannelCallback
 * @Date May 6, 2013 2:45:34 PM
 * @author voidmain
 */
public interface IChannelCallback {
	
	/**
	 * Passes the data back to client
	 * @param data, the parsed data
	 */
	public void onResponse(ResponseData data); 

}
