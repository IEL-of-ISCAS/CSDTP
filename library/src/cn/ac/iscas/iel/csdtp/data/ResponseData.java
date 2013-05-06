/*
 * Copyright (C) 2013 IEL of ISCAS
 * Project:CSDTP
 * Author: voidmain
 * Create Date: May 6, 20132:39:38 PM
 */
package cn.ac.iscas.iel.csdtp.data;

/**
 * Data returned from
 * 
 * @Project CSDTP
 * @Package cn.ac.iscas.iel.csdtp.data
 * @Class ResponseData
 * @Date May 6, 2013 2:39:38 PM
 * @author voidmain
 */
public class ResponseData {

	protected String mPhoneID;
	protected String mStatus;

	public String getPhoneID() {
		return mPhoneID;
	}

	public void setPhoneID(String phoneID) {
		this.mPhoneID = phoneID;
	}

	public String getStatus() {
		return mStatus;
	}

	public void setStatus(String status) {
		this.mStatus = status;
	}

}
