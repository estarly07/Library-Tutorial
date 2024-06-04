package com.estarly.librarytutorial

import android.os.Handler
import android.os.Looper
import android.view.animation.AlphaAnimation
import android.widget.TextView

enum class Animations { FADE_IN, WRITING, BOTH }

internal fun TextView.fadeInAnimation(duration: Long = 300) {
    val fadeIn = AlphaAnimation(0f, 1f)
    fadeIn.duration = duration
    this.startAnimation(fadeIn)
}

internal fun TextView.writingAnimation(text: String, delay: Long = 25) {
    this.text = ""
    val handler = Handler(Looper.getMainLooper())
    var index = 0

    val runnable = object : Runnable {
        override fun run() {
            if (index < text.length) {
                this@writingAnimation.text = text.substring(0, index + 1)
                index++
                handler.postDelayed(this, delay)
            }
        }
    }
    handler.post(runnable)
}

internal fun TextView.combinedAnimation(text: String, fadeDuration: Long = 600, writingDelay: Long = 25) {
    this.fadeInAnimation(fadeDuration)
    this.writingAnimation(text, writingDelay)
}