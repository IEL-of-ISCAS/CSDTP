/*
 * Copyright (C) 2013 IEL of ISCAS
 * Project:CSDTP
 * Author: voidmain
 * Create Date: Apr 24, 20135:04:12 PM
 */
package cn.ac.iscas.iel.csdtp.channel;

import org.codehaus.jackson.map.ObjectMapper;

import cn.ac.iscas.iel.csdtp.data.Frame;

/**
 * Abstract the communication channel
 * 
 * The output channel is responsible of sending data
 * 
 * The frame data is pushed from frame out queue by sending thread
 * 
 * @Project CSDTP
 * @Package cn.ac.iscas.iel.csdtp.channel
 * @Class Channel
 * @Date Apr 24, 2013 5:04:12 PM
 * @author voidmain
 */
public abstract class OutputChannel {

	/**
	 * Uses jackson to convert between json and java object
	 */
	protected ObjectMapper mMapper;

	protected IChannelCallback mCallback;

	public OutputChannel() {
		this.mMapper = new ObjectMapper();
	}

	public void setCallback(IChannelCallback callback) {
		mCallback = callback;
	}

	public IChannelCallback getCallback() {
		return mCallback;
	}

	public abstract void prepare();

	/**
	 * Send frame data with the communication channel
	 * 
	 * @param frameData
	 */
	public abstract void sendFrame(Frame frameData);

	/**
	 * Close the channel and similar stuff
	 */
	public abstract void cleanUp();

}
