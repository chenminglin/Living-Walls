package com.bethena.walls_test

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SensorManagerTestActivity : AppCompatActivity() {
    lateinit var sensorManager: SensorManager
    lateinit var sensor: Sensor

    lateinit var tv_x_value: TextView
    lateinit var tv_y_value: TextView
    lateinit var tv_z_value: TextView
    lateinit var tv_x_delta: TextView
    lateinit var tv_y_delta: TextView
    lateinit var tv_z_delta: TextView

    var lastX = 0f
    var lastY = 0f
    var lastZ = 0f
    var listener: SensorEventListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent?) {
            event?.let {

                tv_x_value.text = "${it.values[0]}"
                tv_y_value.text = "${it.values[1]}"
                tv_z_value.text = "${it.values[2]}"

                tv_x_delta.text = "${it.values[0] - lastX}"
                tv_y_delta.text = "${it.values[1] - lastY}"
                tv_z_delta.text = "${it.values[2] - lastZ}"

                lastX = it.values[0]
                lastY = it.values[1]
                lastZ = it.values[2]
            }
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sensor_manager_test)
        tv_x_value = findViewById(R.id.tv_x_value)
        tv_y_value = findViewById(R.id.tv_y_value)
        tv_z_value = findViewById(R.id.tv_z_value)
        tv_x_delta = findViewById(R.id.tv_x_delta)
        tv_y_delta = findViewById(R.id.tv_y_delta)
        tv_z_delta = findViewById(R.id.tv_z_delta)

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
        sensorManager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_NORMAL)

        findViewById<RadioGroup>(R.id.rg).setOnCheckedChangeListener { group, checkedId ->
            sensorManager.unregisterListener(listener)
            when (checkedId) {
                R.id.rb_gyro -> {
                    sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
                }
                R.id.rb_gravity -> {
                    sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY)
                }
                R.id.rb_accele -> {
                    sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
                }
            }
            sensorManager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_NORMAL)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        sensorManager.unregisterListener(listener)
    }
}