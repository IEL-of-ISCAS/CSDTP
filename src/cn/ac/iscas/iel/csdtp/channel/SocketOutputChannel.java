/*
 * Copyright (C) 2013 IEL of ISCAS
 * Project:CSDTP
 * Author: voidmain
 * Create Date: Apr 24, 20135:20:17 PM
 */
package cn.ac.iscas.iel.csdtp.channel;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import cn.ac.iscas.iel.csdtp.data.Frame;

/**
 * Use socket as the output channel
 * 
 * @Project CSDTP
 * @Package cn.ac.iscas.iel.csdtp.channel
 * @Class SocketOutputChannel
 * @Date Apr 24, 2013 5:20:17 PM
 * @author voidmain
 */
public class SocketOutputChannel extends OutputChannel {

	protected Socket mSenderSocket;
	protected DataOutputStream mOutStream;

	public SocketOutputChannel(Socket socket) throws IOException {
		super();
		mSenderSocket = socket;
		mOutStream = new DataOutputStream(mSenderSocket.getOutputStream());
	}

	@Override
	public void sendFrame(Frame frameData) {
		try {
			mMapper.writeValue(mOutStream, frameData);
			mOutStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void cleanUp() {
		if (mSenderSocket != null) {
			try {
				mSenderSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
				mSenderSocket = null;
			}
		}
	}

}
