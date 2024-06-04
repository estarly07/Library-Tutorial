package com.estarly.librarytutorial

import android.app.Activity
import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.estarly.librarytutorial.databinding.TutorialBinding

class TutorialOverlay(private val activity: Activity,private val padding : Float = 10f) {
    private var currentStep = 0
    private val viewPositions = mutableListOf<Rect>()
    fun showTutorial(steps: List<Step>) {
        if (steps.isEmpty()) { throw IllegalArgumentException("Steps list is empty") }

        val binding = TutorialBinding.inflate(LayoutInflater.from(activity),null,false)
        with(binding){
            backgroundListener.setOnClickListener {  }
            val rootLayout = activity.findViewById<FrameLayout>(android.R.id.content)
            rootLayout.addView(root)
            nextButton.setOnClickListener {
                currentStep++
                if (currentStep < steps.size) {
                    updateTutorial(title = steps.map { it.title }[currentStep], binding = binding)
                } else {
                    root.visibility = View.GONE
                }
            }
            calculateViewPositions(views = steps.map { it.view }, binding = binding)
            currentStep = 0
            updateTutorial(title = steps.map { it.title }[currentStep], binding = binding)
        }
    }
    private fun calculateViewPositions(views: List<View>, binding: TutorialBinding) {
        with(binding){
            val rootLocation = IntArray(2)
            root.getLocationInWindow(rootLocation)

            for (view in views) {
                val location = IntArray(2)
                view.getLocationInWindow(location)
                val rect = Rect(
                    location[0] - rootLocation[0],
                    location[1] - rootLocation[1],
                    location[0] - rootLocation[0] + view.width,
                    location[1] - rootLocation[1] + view.height
                )
                viewPositions.add(rect)
            }
        }
    }
    private fun updateTutorial(title: String, binding: TutorialBinding) {
        with(binding){
            val rect = viewPositions[currentStep]

            tutorialText.text = title

            val holeDrawable = HoleBackgroundDrawable(context = activity, targetRect = rect, paddingSize = padding)
            backgroundOverlay.background = holeDrawable

//            tutorialText.x = rect.left.toFloat()
//            tutorialText.y = rect.top.toFloat() - tutorialText.height - 16

            root.visibility = View.VISIBLE
        }
    }
}
