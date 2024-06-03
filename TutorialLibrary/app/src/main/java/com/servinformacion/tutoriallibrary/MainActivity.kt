package com.servinformacion.tutoriallibrary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.estarly.librarytutorial.TutorialOverlay

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val targetView: TextView = findViewById(R.id.txt)

        val tutorialOverlay = TutorialOverlay(this)
        tutorialOverlay.showTutorial(targetView, "Este es tu tutorial", padding = 5f)
    }
}