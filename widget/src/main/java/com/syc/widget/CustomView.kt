package com.syc.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Point
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.annotation.ColorRes
import kotlin.math.cos
import kotlin.math.sin

const val LINE_LENGTH = 20f
class CustomView(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    View(context, attrs, defStyleAttr) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private val rect = RectF()

    constructor(context: Context, attrs: AttributeSet) : this(context, attrs, 0)
    constructor(context: Context) : this(context, null, 0)

    init {
        setLayerType(LAYER_TYPE_SOFTWARE, null)
//        setBackgroundColor(resources.getColor(R.color.bg))
    }

    override fun onDraw(canvas: Canvas?) {
        paint.color = getColor(R.color.red)
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 8f
        canvas?.run {
            rect.set(0f, 0f, measuredWidth.toFloat(), measuredHeight.toFloat())
            drawArc(rect, 180f, 180f, false, paint)
            save()
            translate(0f, (measuredHeight / 2).toFloat())
            restore()

            paint.color = getColor(R.color.blue)
            var degrees = 180.0
            for( i in 0 until 3 step 1){
                degrees += 18*i
                Log.e("sss","degrees=$degrees")
                val startPoint = getShortPoint(degrees)
                val endPoint = getLongPoint(degrees)
                canvas.drawLine(
                    startPoint.x.toFloat(),
                    startPoint.y.toFloat(),
                    endPoint.x.toFloat(),
                    endPoint.y.toFloat(),paint)
            }

        }
    }


    private fun getColor(@ColorRes res: Int): Int {
        return resources.getColor(res);
    }

    private fun getShortPoint(degrees: Double) = Point().apply {
        val radius = measuredWidth/2 - LINE_LENGTH
        val x = cos(degrees) * radius + measuredWidth/2
        val y = sin(degrees) * radius + measuredHeight/2
        set(x.toInt(), y.toInt())
    }

    private fun getLongPoint(degrees: Double) = Point().apply {
        val radius = measuredWidth/2
        val x = cos(degrees) * radius + measuredWidth/2
        val y = sin(degrees) * radius + measuredHeight/2
        set(x.toInt(), y.toInt())
    }


}