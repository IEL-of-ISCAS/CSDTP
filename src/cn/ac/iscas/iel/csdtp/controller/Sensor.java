/*
 * Copyright (C) 2013 IEL of ISCAS
 * Project:CSDTP
 * Author: voidmain
 * Create Date: Apr 24, 20138:48:41 AM
 */
package cn.ac.iscas.iel.csdtp.controller;

import cn.ac.iscas.iel.csdtp.data.SensorData;

/**
 * Abstract class that represents a sensor
 * 
 * Sensor data is changing all the time, there's no need to send immediately.
 * So, each sensor has a snapshot state.
 * 
 * Whenever the device is to generate a new data frame, it calls the
 * getSnapshot() method of the sensor.
 * 
 * Besides, in order to understand which sensor does the data come from, there's
 * a getSensorName() method that adds the name of the sensor. This is useful
 * when used to compose data in the frame object.
 * 
 * Also, the sensor's data may vary from a scalar value to a 3D vector, or even,
 * a list of 2D vectors. Use corresponding SensorData's child class.
 * 
 * @Project CSDTP
 * @Package cn.ac.iscas.iel.csdtp.controller
 * @Class Sensor
 * @Date Apr 24, 2013 8:48:41 AM
 * @author voidmain
 */
public abstract class Sensor {

	/**
	 * Set the snapshot to invalid state
	 */
	public Sensor() {
		super();
		this.mSnapshot = SensorData.INVALID_DATA;
	}

	/**
	 * Retrieves the sensor that's sending this data
	 * 
	 * @return the sensor's name
	 */
	public abstract String getSensorName();

	/**
	 * The actual data for the frame
	 */
	protected SensorData mSnapshot;

	/**
	 * Update the snapshot with a new data, this method is thread-safe
	 */
	public synchronized void updateSnapshot(SensorData newData) {
		mSnapshot = newData;
	}

	/**
	 * Retrieves the current snapshot for the frame
	 * 
	 * @return current snap shot
	 */
	public SensorData getSnapshot() {
		return mSnapshot;
	}

	/**
	 * Judge if the sensor equals to the other one according to their sensor
	 * name
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Sensor) {
			Sensor otherSensor = (Sensor) obj;
			if (otherSensor.getSensorName() != null) {
				return otherSensor.getSensorName().equals(getSensorName());
			}
		}

		return false;
	}

}
