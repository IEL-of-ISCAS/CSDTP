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

	private static final String SERVER_IP = "192.168.1.117";
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
		} catch (ChangeSensorWhileCollectingDataException e) {
			e.printStackTrace();
		}
		
		mMainView.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				List<TouchPoint> rawData = new ArrayList<TouchPoint>();
				for(int i = 0;i < event.getPointerCount();i ++) {
					TouchPoint tp = new TouchPoint(event.getPointerId(i), 
							event.getAxisValue(MotionEvent.AXIS_X, i), 
							event.getAxisValue(MotionEvent.AXIS_Y, i));
					rawData.add(tp);
				}
				SensorData<List<TouchPoint>> touchData = new SensorData<List<TouchPoint>>(rawData);
				mTouchSensor.updateSnapshot(touchData);
				return false;
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
