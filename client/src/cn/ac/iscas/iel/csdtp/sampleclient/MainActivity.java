package cn.ac.iscas.iel.csdtp.sampleclient;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Service;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;
import android.view.View;
import cn.ac.iscas.iel.csdtp.channel.SocketOutputChannel;
import cn.ac.iscas.iel.csdtp.controller.AccelerometersSensor;
import cn.ac.iscas.iel.csdtp.controller.Device;
import cn.ac.iscas.iel.csdtp.controller.MagnetometersSensor;
import cn.ac.iscas.iel.csdtp.controller.RotationSensor;
import cn.ac.iscas.iel.csdtp.controller.TouchScreenSensor;
import cn.ac.iscas.iel.csdtp.data.SensorData;
import cn.ac.iscas.iel.csdtp.data.TouchPoint;
import cn.ac.iscas.iel.csdtp.exception.ChangeSensorWhileCollectingDataException;
import cn.ac.iscas.iel.csdtp.exception.MultipleSampleThreadException;

public class MainActivity extends Activity implements SensorEventListener {

	private static final String SERVER_IP = "192.168.1.109";
	private static final int PORT = 1234;

	private View mMainView;

	private Device mDevice;
	private AccelerometersSensor mAccSensor;
	private MagnetometersSensor mMagSensor;
	private RotationSensor mRotSensor;
	private TouchScreenSensor mTouchSensor;

	private SensorManager mSensorManager;
	private Sensor mPhyAccSensor;
	private Sensor mPhyMagSensor;
	private Sensor mPhyRotSensor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mMainView = findViewById(R.id.main_layout);

		mDevice = new Device("android");
		mDevice.setOutputChannel(new SocketOutputChannel(SERVER_IP, PORT));

		mSensorManager = (SensorManager) getSystemService(Service.SENSOR_SERVICE);
		mPhyAccSensor = mSensorManager
				.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		mPhyMagSensor = mSensorManager
				.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
		mPhyRotSensor = mSensorManager
				.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);

		mAccSensor = new AccelerometersSensor();
		mMagSensor = new MagnetometersSensor();
		mRotSensor = new RotationSensor();
		mTouchSensor = new TouchScreenSensor();

		try {
			mDevice.registerSensor(mAccSensor);
			mDevice.registerSensor(mMagSensor);
			mDevice.registerSensor(mRotSensor);
			mDevice.registerSensor(mTouchSensor);
		} catch (ChangeSensorWhileCollectingDataException e) {
			e.printStackTrace();
		}

		mMainView.setOnTouchListener(new View.OnTouchListener() {

			@SuppressWarnings("unchecked")
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// Initial data
				if (mTouchSensor.getSnapshot() == null
						|| !mTouchSensor.getSnapshot().isValidData()) {
					List<TouchPoint> emptyData = new ArrayList<TouchPoint>();
					mTouchSensor
							.updateSnapshot(new SensorData<List<TouchPoint>>(
									emptyData));
				}

				List<TouchPoint> data = (List<TouchPoint>) mTouchSensor
						.getSnapshot().getData();

				final int action = MotionEventCompat.getActionMasked(event);

				switch (action) {
				case MotionEvent.ACTION_DOWN: {
					final int pointerIndex = MotionEventCompat
							.getActionIndex(event);
					final float x = MotionEventCompat.getX(event, pointerIndex);
					final float y = MotionEventCompat.getY(event, pointerIndex);
					final int pointerId = MotionEventCompat.getPointerId(event,
							0);
					data.add(new TouchPoint(pointerId, x, y));
					break;
				}

				case MotionEvent.ACTION_POINTER_DOWN: {
					final int pointerIndex = MotionEventCompat
							.getActionIndex(event);
					final int pointerId = MotionEventCompat.getPointerId(event,
							pointerIndex);
					final float x = MotionEventCompat.getX(event, pointerIndex);
					final float y = MotionEventCompat.getY(event, pointerIndex);

					data.add(new TouchPoint(pointerId, x, y));
					break;
				}

				case MotionEvent.ACTION_MOVE: {
					List<TouchPoint> newData = new ArrayList<TouchPoint>();
					for (TouchPoint tp : data) {
						final int pointerId = tp.getPointId();
						final int pointerIdx = MotionEventCompat
								.findPointerIndex(event, pointerId);
						final float x = MotionEventCompat.getX(event,
								pointerIdx);
						final float y = MotionEventCompat.getY(event,
								pointerIdx);
						TouchPoint newTp = new TouchPoint(pointerId, x, y);
						newData.add(newTp);
					}
					data = newData;
					break;
				}

				case MotionEvent.ACTION_UP: {
					data.clear();
					break;
				}

				case MotionEvent.ACTION_CANCEL: {
					data.clear();
					break;
				}

				case MotionEvent.ACTION_POINTER_UP: {
					final int pointerIndex = MotionEventCompat
							.getActionIndex(event);
					final int pointerId = MotionEventCompat.getPointerId(event,
							pointerIndex);

					for (TouchPoint tp : data) {
						if (tp.getPointId() == pointerId) {
							data.remove(tp);
							break;
						}
					}
					break;
				}
				}

				SensorData<List<TouchPoint>> newSnapshot = new SensorData<List<TouchPoint>>(
						data);
				mTouchSensor.updateSnapshot(newSnapshot);
				return true;
			}
		});
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		SensorData<float[]> data = new SensorData<float[]>(event.values);
		if (event.sensor == mPhyAccSensor) {
			mAccSensor.updateSnapshot(data);
		} else if (event.sensor == mPhyMagSensor) {
			mMagSensor.updateSnapshot(data);
		} else if (event.sensor == mPhyRotSensor) {
			mRotSensor.updateSnapshot(data);
		}
	}

	private void pauseSensor() {
		mSensorManager.unregisterListener(this);
	}

	private void resumeSensor() {
		mSensorManager.registerListener(this, mPhyAccSensor,
				SensorManager.SENSOR_DELAY_NORMAL);
		mSensorManager.registerListener(this, mPhyMagSensor,
				SensorManager.SENSOR_DELAY_NORMAL);
		mSensorManager.registerListener(this, mPhyRotSensor,
				SensorManager.SENSOR_DELAY_NORMAL);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		mDevice.stopSending();
	}

	@Override
	protected void onPause() {
		super.onPause();

		pauseSensor();
		mDevice.stopSampling();
	}

	@Override
	protected void onResume() {
		super.onResume();

		try {
			resumeSensor();
			mDevice.startSampling();
		} catch (MultipleSampleThreadException e) {
			e.printStackTrace();
		}
	}

}
