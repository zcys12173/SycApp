package com.syc.mvvm.sensor

import android.content.Context
import android.hardware.Sensor
import android.hardware.Sensor.TYPE_GYROSCOPE
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Half.EPSILON
import android.util.Log
import com.syc.mvvm.framework.base.BaseActivity
import org.apache.commons.math3.geometry.euclidean.threed.Rotation
import org.apache.commons.math3.geometry.euclidean.threed.RotationConvention
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

class MainActivity:BaseActivity(),SensorEventListener {
    private val sensorManager by lazy {
        getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    private val sensor by lazy{
        sensorManager.getDefaultSensor(TYPE_GYROSCOPE)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(this,sensor,SensorManager.SENSOR_DELAY_NORMAL)
    }


    private val NS2S = 1.0f / 1000000000.0f
    private val deltaRotationVector = FloatArray(4) { 0f }
    private var timestamp: Float = 0f
    private var angle: Float = 0f
    private var axis: Vector3D = Vector3D(1.0, 0.0, 0.0)
    override fun onSensorChanged(event: SensorEvent?) {
        event?.run {
            handleData1(this)
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    private fun handleData(event: SensorEvent?){
        if (timestamp != 0f && event != null) {
            val dT = (event.timestamp - timestamp) * NS2S
            // Axis of the rotation sample, not normalized yet.
            var axisX: Float = event.values[0]
            var axisY: Float = event.values[1]
            var axisZ: Float = event.values[2]

            // Calculate the angular speed of the sample
            val omegaMagnitude: Float = sqrt(axisX * axisX + axisY * axisY + axisZ * axisZ)

            // Normalize the rotation vector if it's big enough to get the axis
            // (that is, EPSILON should represent your maximum allowable margin of error)
            if (omegaMagnitude > EPSILON) {
                axisX /= omegaMagnitude
                axisY /= omegaMagnitude
                axisZ /= omegaMagnitude
            }

            // Integrate around this axis with the angular speed by the timestep
            // in order to get a delta rotation from this sample over the timestep
            // We will convert this axis-angle representation of the delta rotation
            // into a quaternion before turning it into the rotation matrix.
            val thetaOverTwo: Float = omegaMagnitude * dT / 2.0f
            val sinThetaOverTwo: Float = sin(thetaOverTwo)
            val cosThetaOverTwo: Float = cos(thetaOverTwo)
            deltaRotationVector[0] = sinThetaOverTwo * axisX
            deltaRotationVector[1] = sinThetaOverTwo * axisY
            deltaRotationVector[2] = sinThetaOverTwo * axisZ
            deltaRotationVector[3] = cosThetaOverTwo
        }
        Log.e("sss","w:${deltaRotationVector[3]/(1 shl 30)}," +
                "x:${deltaRotationVector[0]/(1 shl 30)}," +
                "y:${deltaRotationVector[1]/(1 shl 30)}," +
                "z:${deltaRotationVector[2]/(1 shl 30)}")
        timestamp = event?.timestamp?.toFloat() ?: 0f
        val deltaRotationMatrix = FloatArray(9) { 0f }
        SensorManager.getRotationMatrixFromVector(deltaRotationMatrix, deltaRotationVector)
    }


    /**
     * 陀螺仪原始数据转换四元数
     */
    private fun handleData1(event: SensorEvent) {
        if (event.sensor == sensor) {
            if (timestamp != 0f) {
                val dT = (event.timestamp - timestamp) * NS2S

                // Calculate the angular speed of the rotation
                val omegaMagnitude = sqrt(
                    event.values[0].pow(2) +
                            event.values[1].pow(2) +
                            event.values[2].pow(2)
                )

                // Normalize the rotation vector
                if (omegaMagnitude > 1e-5) {
                    event.values[0] /= omegaMagnitude
                    event.values[1] /= omegaMagnitude
                    event.values[2] /= omegaMagnitude
                }

                // Integrate the rotation vector over time to get the total rotation
                angle += omegaMagnitude * dT

                // Calculate the rotation axis
                try {
                    axis = Vector3D(
                        event.values[0].toDouble(),
                        event.values[1].toDouble(),
                        event.values[2].toDouble()
                    )

                    // Convert the rotation to a quaternion
                    val rotationQuaternion =
                        Rotation(axis, angle.toDouble(), RotationConvention.VECTOR_OPERATOR)
                    Log.e("sss", "w:${rotationQuaternion.q0}," +
                            "x:${rotationQuaternion.q1}," +
                            "y:${rotationQuaternion.q2}," +
                            "z:${rotationQuaternion.q3}")
                }catch (e:Exception){

                }

            }

            timestamp = event.timestamp.toFloat()
        }
    }
}