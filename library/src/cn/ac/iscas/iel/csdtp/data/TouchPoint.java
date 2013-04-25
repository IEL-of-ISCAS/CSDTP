/*
 * Copyright (C) 2013 IEL of ISCAS
 * Project:CSDTP
 * Author: voidmain
 * Create Date: Apr 25, 20139:51:05 PM
 */
package cn.ac.iscas.iel.csdtp.data;

/**
 * Wrap the touch point info
 * 
 * @Project CSDTP
 * @Package cn.ac.iscas.iel.csdtp.data
 * @Class TouchPoint
 * @Date Apr 25, 2013 9:51:05 PM
 * @author voidmain
 */
public class TouchPoint {

	protected int mPointId;
	protected float mValueX;
	protected float mValueY;

	public TouchPoint(int pointId, float valueX, float valueY) {
		super();
		this.mPointId = pointId;
		this.mValueX = valueX;
		this.mValueY = valueY;
	}

	public int getPointId() {
		return mPointId;
	}

	public void setPointId(int pointId) {
		this.mPointId = pointId;
	}

	public float getValueX() {
		return mValueX;
	}

	public void setValueX(float valueX) {
		this.mValueX = valueX;
	}

	public float getValueY() {
		return mValueY;
	}

	public void setValueY(float valueY) {
		this.mValueY = valueY;
	}

}
