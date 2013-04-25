/*
 * Copyright (C) 2013 IEL of ISCAS 
 * Project:CSDTP
 * Author: voidmain
 * Create Date: Apr 23, 20139:26:24 PM
 */
package cn.ac.iscas.iel.csdtp.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

import cn.ac.iscas.iel.csdtp.channel.OutputChannel;
import cn.ac.iscas.iel.csdtp.data.DataFrame;
import cn.ac.iscas.iel.csdtp.data.Frame;
import cn.ac.iscas.iel.csdtp.data.SensorData;
import cn.ac.iscas.iel.csdtp.exception.ChangeSensorWhileCollectingDataException;
import cn.ac.iscas.iel.csdtp.exception.ChannelNotSetException;
import cn.ac.iscas.iel.csdtp.exception.MultipleSampleThreadException;
import cn.ac.iscas.iel.csdtp.utils.StringUtils;

/**
 * Device class
 * 
 * A device is an abstract of physical controllers.
 * 
 * The device is responsible for generating frames, which in turn is filled by a
 * bunch of sensor's data.
 * 
 * The device may be sampling at a very high rate that network bandwidth cannot
 * meet the rate, so device also contains a FrameOutQueue at the sender side.
 * 
 * The FrameOutQueue stores the sampled data, and pushes to
 * network/communication channel, for example the TCP socket, who sends the data
 * out.
 * 
 * @Project CSDTP
 * @Package cn.ac.iscas.iel.csdtp.controller
 * @Class Device
 * @Date Apr 23, 2013 9:26:24 PM
 * @author voidmain
 */
public class Device {

	private static final String TAG = Device.class.getName();

	/**
	 * Name of this device, used to display on server side, if not set by user,
	 * the name would be the inner UUID
	 */
	protected String mDeviceName;

	/**
	 * Inner id used to identify the controller device
	 */
	protected String mInnerUUID;

	/**
	 * The working sample thread
	 */
	protected SampleThread mSampleThread;

	/**
	 * Collects 30 frames per sec by default
	 */
	protected static final int DEFAULT_SAMPLE_RATE = 30;

	/**
	 * How many frames to collecte per second
	 */
	protected int mSampleRate;

	/**
	 * The frames that will be sent
	 */
	protected ConcurrentLinkedQueue<Frame> mFrameOutQueue;

	/**
	 * The list that stores the list of sensors
	 */
	protected List<Sensor> mSensorList;

	/**
	 * Debug loggers
	 */
	protected Logger mLogger;

	/**
	 * The thread that sends output data
	 */
	protected SenderThread mSenderThread;

	/**
	 * The channel that sends the collected data
	 */
	protected OutputChannel mOutChannel;

	public Device() {
		mInnerUUID = UUID.randomUUID().toString();
		mDeviceName = mInnerUUID;
		mSampleRate = DEFAULT_SAMPLE_RATE;
		mFrameOutQueue = new ConcurrentLinkedQueue<Frame>();
		mSensorList = new ArrayList<Sensor>();
		mLogger = Logger.getLogger(TAG);
	}

	public Device(String deviceName) {
		this();
		mDeviceName = deviceName;
	}

	public String getDeviceName() {
		return mDeviceName;
	}

	public void setDeviceName(String deviceName) {
		this.mDeviceName = deviceName;
	}

	public String getInnerUUID() {
		return mInnerUUID;
	}

	public void setOutputChannel(OutputChannel channel) {
		this.mOutChannel = channel;
	}

	/**
	 * Start a new sample thread for sampling.
	 * 
	 * Needs to guarantee there is only one working sample thread.
	 * 
	 * @throws MultipleSampleThreadException
	 */
	public void startSampling() throws MultipleSampleThreadException {
		if (mSenderThread == null || !mSenderThread.isAlive()) {
			try {
				mSenderThread = new SenderThread();
				mSenderThread.start();
			} catch (ChannelNotSetException e) {
				e.printStackTrace();
			}
		}

		if (mSampleThread.isAlive()) {
			throw new MultipleSampleThreadException(
					"There is a working sample thread, if you want to start a new one, call stopSampling() first.");
		}

		mSampleThread = new SampleThread();
		mSampleThread.start();
	}

	/**
	 * Makes the sample thread stop working.
	 * 
	 * There is no pauseSampling method, if you want to pause the current
	 * sampling work, just stopSampling() and when you want to resume sampling,
	 * call startSampling again.
	 */
	public void stopSampling() {
		if (mSampleThread != null && mSampleThread.isAlive()) {
			mSampleThread.makeStop();
		}
	}

	/**
	 * Stops the sending thread
	 */
	public void stopSending() {
		if (mSenderThread != null && mSenderThread.isAlive()) {
			mSenderThread.makeStop();
		}
	}

	/**
	 * Add a new sensor to the global sensor list
	 * 
	 * @param newSensor
	 * @throws ChangeSensorWhileCollectingDataException
	 */
	public void registerSensor(Sensor newSensor)
			throws ChangeSensorWhileCollectingDataException {
		if (mSampleThread != null && mSampleThread.isAlive()) {
			throw new ChangeSensorWhileCollectingDataException(
					"Cannot change sensor list while sampling. If you do want to change the sensor list, please stop sampling first.");
		}
		if (!mSensorList.contains(newSensor)) {
			mSensorList.add(newSensor);
		} else {
			mLogger.log(Level.WARNING, "Sensor " + newSensor.getSensorName()
					+ " exists in the list, did not add this one");
		}
	}

	/**
	 * Removes the sensor that matches the sensorName parameter
	 * 
	 * @param sensorName
	 * @throws ChangeSensorWhileCollectingDataException
	 */
	public void unregisterSensor(String sensorName)
			throws ChangeSensorWhileCollectingDataException {
		if (mSampleThread != null && mSampleThread.isAlive()) {
			throw new ChangeSensorWhileCollectingDataException(
					"Cannot change sensor list while sampling. If you do want to change the sensor list, please stop sampling first.");
		}
		if (StringUtils.isEmpty(sensorName)) {
			return;
		}
		for (Sensor sensor : mSensorList) {
			if (sensorName.equals(sensor.getSensorName())) {
				mSensorList.remove(sensor);
				break;
			}
		}
	}

	/**
	 * Collects all sensor data, and compose them into a new frame
	 * 
	 * @return the new composed frame
	 */
	public Frame genFrame() {
		DataFrame theFrame = new DataFrame(Device.this);
		for (Sensor sensor : mSensorList) {
			SensorData<?> theData = sensor.getSnapshot();
			if (theData.isValidData()) { // Only send valid data
				theFrame.addNewData(theData);
			}
		}
		return theFrame;
	}

	/**
	 * Adds a new frame to send
	 * 
	 * @param newFrame
	 */
	public void pushToSendQueue(Frame newFrame) {
		mFrameOutQueue.add(newFrame);
	}

	/**
	 * Returns the current queue to be send
	 * 
	 * @return
	 */
	public ConcurrentLinkedQueue<Frame> getOutQueue() {
		return mFrameOutQueue;
	}

	/**
	 * The sample thread class
	 * 
	 * Keeps gathering sensors' data until the use stops it explicitly.
	 * 
	 * The thread samples at the rate of the user sets.
	 * 
	 * @Project CSDTP
	 * @Package cn.ac.iscas.iel.csdtp.controller
	 * @Class SampleThread
	 * @Date Apr 23, 2013 10:42:50 PM
	 * @author voidmain
	 */
	protected class SampleThread extends Thread {

		protected boolean mRunning;
		protected long mWaitingPeriod;

		public SampleThread() {
			mRunning = true;
			mWaitingPeriod = 1000 / mSampleRate;
		}

		@Override
		public void run() {
			while (mRunning) {
				try {
					Frame newFrame = genFrame();
					pushToSendQueue(newFrame);
					sleep(mWaitingPeriod);
				} catch (InterruptedException e) {
					mRunning = false;
					e.printStackTrace();
				}
			}
		}

		public void makeStop() {
			mRunning = false;
		}

	}

	/**
	 * The sender thread that reads the data from out queue
	 * 
	 * @Project CSDTP
	 * @Package cn.ac.iscas.iel.csdtp.controller
	 * @Class SenderThread
	 * @Date Apr 24, 2013 5:10:24 PM
	 * @author voidmain
	 */
	protected class SenderThread extends Thread {

		protected boolean mSending;

		public SenderThread() throws ChannelNotSetException {
			mSending = true;
			if (mOutChannel == null) {
				throw new ChannelNotSetException(
						"Please call setOutputChannel() before creating sender thread");
			}
		}

		@Override
		public void run() {
			mOutChannel.prepare();

			while (mSending) {
				Frame frame = mFrameOutQueue.poll();
				if (frame != null) {
					mOutChannel.sendFrame(frame);
				}
			}

			mOutChannel.cleanUp();
		}

		public void makeStop() {
			mSending = false;
		}

	}
}
