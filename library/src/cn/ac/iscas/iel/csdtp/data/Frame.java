/*
 * Copyright (C) 2013 IEL of ISCAS
 * Project:CSDTP
 * Author: voidmain
 * Create Date: Apr 23, 201311:43:59 PM
 */
package cn.ac.iscas.iel.csdtp.data;

import java.util.Date;

import cn.ac.iscas.iel.csdtp.controller.Device;

/**
 * The frame that will be sent to server side
 * 
 * @Project CSDTP
 * @Package cn.ac.iscas.iel.csdtp.data
 * @Class Frame
 * @Date Apr 23, 2013 11:43:59 PM
 * @author voidmain
 */
public abstract class Frame {
	/**
	 * Display name of the device the frame comes
	 */
	protected String mDeviceName;

	/**
	 * The inner UUID used to identify the device
	 */
	protected String mInnerUUID;

	public Frame(Device theDevice, int msgType) {
		mDeviceName = theDevice.getDeviceName();
		mInnerUUID = theDevice.getInnerUUID();
		mTimestamp = new Date();
		setMsgType(msgType);
	}

	public String getDeviceName() {
		return mDeviceName;
	}

	public void setDeviceName(String deviceName) {
		mDeviceName = deviceName;
	}

	public String getPhoneID() {
		return mInnerUUID;
	}

	public void setPhoneID(String innerUUID) {
		mInnerUUID = innerUUID;
	}

	protected Date mTimestamp;

	public Date getTimestamp() {
		return mTimestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.mTimestamp = timestamp;
	}

	protected int mMsgType;

	public void setMsgType(int msgType) {
		mMsgType = msgType;
	}

	public int getMsgType() {
		return mMsgType;
	}

	/**
	 * Message types
	 */
	public static final int MSG_TYPE_NONE                    = 0;
	public static final int MSG_TYPE_NEWCONNECT              = 1;
	public static final int MSG_TYPE_DISCONNECT              = 2;
	public static final int MSG_TYPE_GIVEUPCONTROL           = 3;
	public static final int MSG_TYPE_REQUESTCONTROL          = 4;
	public static final int MSG_TYPE_LOCKNAV                 = 5;
	public static final int MSG_TYPE_UNLOCKNAV               = 6;
	public static final int MSG_TYPE_PUSH                    = 7;
	public static final int MSG_TYPE_RELEASE                 = 8;
	public static final int MSG_TYPE_DRAG                    = 9;
	public static final int MSG_TYPE_MOVE                    = 10;
	public static final int MSG_TYPE_VOLUMEUP                = 11;
	public static final int MSG_TYPE_VOLUMEDOWN              = 12;
	public static final int MSG_TYPE_RAYCASTMANIPULATOR      = 13;
	public static final int MSG_TYPE_TRACKBALLMANIPULATOR    = 14;
	public static final int MSG_TYPE_FLIGHTMANIPULATOR       = 15;
	public static final int MSG_TYPE_DRIVERMANIPULATOR       = 16;
	public static final int MSG_TYPE_NAVMANIPULATOR          = 17;
	
	/**
	 * Status 
	 */
	public static final int STATUS_SUCCESS = 1;
	public static final int STATUS_ERROR = 0;

}
