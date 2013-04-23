/*
 * Copyright (C) 2013 IEL of ISCAS 
 * Project:CSDTP
 * Author: voidmain
 * Create Date: Apr 23, 20139:26:24 PM
 */
package cn.ac.iscas.iel.csdtp.controller;

import javax.media.j3d.MultipleParentException;

/**
 * Device class
 * 
 * A device is an abstract of physical controllers. 
 * 
 * The device is responsible for generating frames, which in turn 
 * is filled by a bunch of sensor's data.
 * 
 * The device may be sampling at a very high rate that network bandwidth cannot 
 * meet the rate, so device also contains a FrameOutQueue at the sender side.
 * 
 * The FrameOutQueue stores the sampled data, and pushes to network/communication channel, 
 * for example the TCP socket, who sends the data out.
 * 
 * On the receiver side, there is a corresponding FrameInQueue that collects data from 
 * communication channel, parses the data, and bump the result into the queue which can 
 * be utilized by the receiver side.
 * 
 * @Project CSDTP
 * @Package cn.ac.iscas.iel.csdtp.controller
 * @Class Device
 * @Date Apr 23, 2013 9:26:24 PM
 * @author voidmain
 */
public class Device {

	protected SampleThread mSampleThread;

	/**
	 * Collects 30 frames per sec by default
	 */
	protected static final int DEFAULT_SAMPLE_RATE = 30;

	/**
	 * How many frames to collecte per second
	 */
	protected int mSampleRate;

	public Device() {
		mSampleRate = DEFAULT_SAMPLE_RATE;
	}

	/**
	 * Start a new sample thread for sampling.
	 * 
	 * Needs to guarantee there is only one working sample thread.
	 */
	public void startSampling() {
		if (mSampleThread.isAlive()) {
			throw new MultipleParentException(
					"There is a working sample thread, if you want to start a new one, call stopSampling() first.");
		}

		mSampleThread = new SampleThread();
		mSampleThread.start();
	}

	/**
	 * Makes the sample thread stop working.
	 * 
	 * There is no pauseSampling method, if you want to pause the current sampling work, 
	 * just stopSampling() and when you want to resume sampling, call startSampling again.
	 */
	public void stopSampling() {
		if(mSampleThread != null && mSampleThread.isAlive()) {
			mSampleThread.makeStop();
		}
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
					// do stuff here
					sleep(mWaitingPeriod);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		public void makeStop() {
			mRunning = false;
		}

	}
}
