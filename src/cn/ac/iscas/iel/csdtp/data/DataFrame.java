/*
 * Copyright (C) 2013 IEL of ISCAS
 * Project:CSDTP
 * Author: voidmain
 * Create Date: Apr 25, 201310:31:59 AM
 */
package cn.ac.iscas.iel.csdtp.data;

import java.util.ArrayList;
import java.util.List;

import cn.ac.iscas.iel.csdtp.controller.Device;

/**
 * The frame that contains actual data
 * 
 * @Project CSDTP
 * @Package cn.ac.iscas.iel.csdtp.data
 * @Class DataFrame
 * @Date Apr 25, 2013 10:31:59 AM
 * @author voidmain
 */
public class DataFrame extends Frame {

	public DataFrame(Device theDevice) {
		super(theDevice);

		mSensorDataset = new ArrayList<SensorData>();
	}

	/**
	 * A list of data collected from all registered sensors
	 */
	protected List<SensorData> mSensorDataset;

	public void addNewData(SensorData data) {
		mSensorDataset.add(data);
	}
	
	public List<SensorData> getSensorDataset() {
		return mSensorDataset;
	}
	
	public void setSensorDataset(List<SensorData> dataset) {
		mSensorDataset = dataset;
	}

}
