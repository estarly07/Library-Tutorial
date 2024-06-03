package com.estarly.librarytutorial

import android.app.Activity
import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewTreeObserver
import android.widget.FrameLayout
import com.estarly.librarytutorial.databinding.TutorialBinding

class TutorialOverlay(private val activity: Activity) {

    fun showTutorial(view: View, title: String, padding : Float = 10f) {
        val binding = TutorialBinding.inflate(LayoutInflater.from(activity),null,false)
        with(binding){
            tutorialText.text = title

            val rootLayout = activity.findViewById<FrameLayout>(android.R.id.content)
            rootLayout.addView(root)

            view.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    view.viewTreeObserver.removeOnGlobalLayoutListener(this)

                    val location = IntArray(2)
                    view.getLocationInWindow(location)
                    val rect = Rect(
                               location[0],
                           location[1] - view.height,
                          location[0] + view.width,
                        location[1] + view.height
                    )

                    val holeDrawable = HoleBackgroundDrawable(context = activity, targetRect = rect, paddingSize = padding)
                    backgroundOverlay.background = holeDrawable

                    root.visibility = View.VISIBLE
                }
            })
        }
    }
}
