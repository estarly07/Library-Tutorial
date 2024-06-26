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
    fun showTutorial(steps: List<Step>, animation : Animations = Animations.FADE_IN) {
        if (steps.isEmpty()) { throw IllegalArgumentException("Steps list is empty") }

        val binding = TutorialBinding.inflate(LayoutInflater.from(activity),null,false)
        with(binding){
            backgroundListener.setOnClickListener {  }
            val rootLayout = activity.findViewById<FrameLayout>(android.R.id.content)
            rootLayout.addView(root)
            nextButton.setOnClickListener {
                currentStep++
                if (currentStep < steps.size) {
                    updateTutorial(title = steps.map { it.title }[currentStep], binding = binding, animation = animation)
                } else {
                    root.visibility = View.GONE
                }
            }
            btnClose.setOnClickListener { root.visibility = View.GONE }
            calculateViewPositions(views = steps.map { it.view }, binding = binding)
            currentStep = 0
            updateTutorial(title = steps.map { it.title }[currentStep], binding = binding, animation = animation)
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
    private fun updateTutorial(title: String, binding: TutorialBinding, animation : Animations) {
        with(binding){
            val rect = viewPositions[currentStep]
            tutorialText.visibility = View.INVISIBLE
            tutorialText.text = title
            val holeDrawable = HoleBackgroundDrawable(context = activity, targetRect = rect, paddingSize = padding)
            backgroundOverlay.background = holeDrawable
            root.post {
                val screenHeight = root.height
                if (rect.centerY() > screenHeight / 2) {
                    tutorialText.layoutParams = FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.WRAP_CONTENT
                    ).apply {
                        bottomMargin = screenHeight - rect.top + 16
                        gravity = android.view.Gravity.BOTTOM
                    }
                } else {
                    tutorialText.layoutParams = FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.WRAP_CONTENT
                    ).apply {
                        topMargin = rect.bottom + 16
                        gravity = android.view.Gravity.TOP
                    }
                }
                when (animation) {
                    Animations.FADE_IN -> tutorialText.fadeInAnimation()
                    Animations.WRITING -> tutorialText.writingAnimation(title)
                    Animations.BOTH    -> tutorialText.combinedAnimation(title)
                }

                tutorialText.visibility = View.VISIBLE
            }
        }
    }
}
