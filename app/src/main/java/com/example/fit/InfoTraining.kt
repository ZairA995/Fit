package com.example.fit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView

class InfoTraining : AppCompatActivity() {
    lateinit var training:TextView
    lateinit var trainer:TextView
    lateinit var date:TextView
    lateinit var time:TextView
    lateinit var durations:TextView
    lateinit var descriptions:TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_training)

        training = findViewById(R.id.training)
        durations = findViewById(R.id.time_of_classes)
        descriptions = findViewById(R.id.description)
        trainer = findViewById(R.id.trainer)

        var nameTraining = getIntent().getStringExtra("nameTraining")
        var nameTrainer = getIntent().getStringExtra("nameTrainer")
        var duration = getIntent().getIntExtra("duration",1)
        var description = getIntent().getStringExtra("description")
        var dateTraining = getIntent().getStringExtra("dateTraining")
        var timeTraining = getIntent().getIntExtra("timeTraining",0)
        training.setText(nameTraining)
        trainer.setText("Тренер: " + nameTrainer)
        descriptions.setText(description)
        durations.setText("Длительность: " + duration.toString() + " минут")
    }

    fun regOnLesson(view: View) {

    }
}