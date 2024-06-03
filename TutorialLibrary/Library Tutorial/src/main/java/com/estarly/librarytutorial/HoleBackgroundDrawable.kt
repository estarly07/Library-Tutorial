package com.estarly.librarytutorial

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PixelFormat
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.drawable.Drawable

class HoleBackgroundDrawable(
                context      : Context,
    private val targetRect   : Rect,
    private val borderRadius : Float = 16f,
    private val paddingSize  : Float = 10f,
    private val background   : Int   = Color.parseColor("#CC000000")
) : Drawable() {
    private val paint = Paint().apply {
        isAntiAlias = true
        color       = background
    }
    private val borderPaint = Paint().apply {
        isAntiAlias = true
        color       = Color.WHITE
        style       = Paint.Style.STROKE
        strokeWidth = 6f
    }
    private val padding : Int  = context.dpToPx( paddingSize).toInt()
    override fun draw(canvas: Canvas) {
        val saveCount = canvas.saveLayer(
            bounds.left  .toFloat(),
            bounds.top   .toFloat(),
            bounds.right .toFloat(),
            bounds.bottom.toFloat(),
            null
        )

        canvas.drawRect(bounds, paint)
        val paddedRect = Rect(
               targetRect.left   - padding,
               targetRect.top    - padding,
              targetRect.right  + padding,
            targetRect.bottom + padding
        )
        val path = Path().apply {
            addRoundRect(RectF(paddedRect),borderRadius, borderRadius, Path.Direction.CW )
        }

        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
        canvas.drawPath(path, paint)

        paint.xfermode = null
        canvas.drawPath(path, borderPaint)

        canvas.restoreToCount(saveCount)
    }

    override fun setAlpha(alpha: Int) { paint.alpha = alpha }
    override fun setColorFilter(colorFilter: ColorFilter?) { paint.colorFilter = colorFilter}
    override fun getOpacity(): Int = PixelFormat.TRANSLUCENT
}