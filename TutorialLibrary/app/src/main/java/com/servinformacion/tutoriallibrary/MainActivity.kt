package com.servinformacion.tutoriallibrary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.estarly.librarytutorial.Step
import com.estarly.librarytutorial.TutorialOverlay

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val targetView : TextView = findViewById(R.id.txt)
        val targetView1: TextView = findViewById(R.id.txt1)
        val targetView2: TextView = findViewById(R.id.txt2)
        val tutorialOverlay = TutorialOverlay(this, padding = 5f)
        targetView.setOnClickListener {
            tutorialOverlay.showTutorial(listOf(
                Step(targetView,"Hola qwer aelkw wkwkkew sdf"),
                Step(targetView1,"Como estas sdddddddd sdkdkkd skkkkk"),
                Step(targetView2,"Hoy sssssssss sdfs sdf sdddddd sds"),
            ))
        }
    }
}
