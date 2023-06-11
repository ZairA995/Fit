package com.example.fit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlin.properties.Delegates

const val key = "key"
class InfoTraining : AppCompatActivity() {
    lateinit var training:TextView
    var information by Delegates.notNull<Int>()
    var id_trainers by Delegates.notNull<Int>()
    var countR by Delegates.notNull<Int>()
    lateinit var trainer:TextView
    lateinit var date:TextView
    lateinit var time:TextView
    lateinit var durations:TextView
    lateinit var descriptions:TextView
    private var adapter: RecyclerAdapter?=null
    private var std:UserModel?=null
    lateinit var onReg:Button
    lateinit var databaseHelper: DatabaseHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_training)

        databaseHelper = DatabaseHelper(this)

        onReg = findViewById(R.id.onRegis)
        training = findViewById(R.id.training)
        durations = findViewById(R.id.time_of_classes)
        descriptions = findViewById(R.id.description)
        trainer = findViewById(R.id.trainer)
        information = intent.getIntExtra("idTraining",0)
        id_trainers = getIntent().getIntExtra("idTrainer",0)
        countR = getIntent().getIntExtra("countRemains",1)

        Log.e("information","${information},${countR},${id_trainers}")
        var nameTraining = getIntent().getStringExtra("nameTraining")
        var nameTrainer = getIntent().getStringExtra("nameTrainer")
        var duration = getIntent().getIntExtra("duration",1)
        var description = getIntent().getStringExtra("description")
        var dateTraining = getIntent().getStringExtra("dateTraining")
        var timeTraining = getIntent().getIntExtra("timeTraining",0)
        var idClient = intent.getIntExtra("idClient",0)
        var idTr = intent.getIntExtra("idTr",0)
        training.setText(nameTraining)
        trainer.setText("Тренер: " + nameTrainer)
        descriptions.setText(description)
        durations.setText("Длительность: " + duration.toString() + " минут")

        onReg.setOnClickListener(object: View.OnClickListener {
            override fun onClick(view: View): Unit {
                if (countR == 0)
                {
                    Toast.makeText(this@InfoTraining,"Все места на тренировку заняты",Toast.LENGTH_SHORT).show()
                }
                else
                {
                    val res = databaseHelper.getClientTr(idTr,idClient)
                    if (res > 0)
                    {
                        Toast.makeText(this@InfoTraining,"Вы уже записаны на эту тренировку",Toast.LENGTH_SHORT).show()
                    }
                    else
                    {
                        databaseHelper!!.addClientTraining(idTr,idClient)
                        var status = dateTraining?.let {
                            databaseHelper.changeCountPeople(information,
                                dateTraining,timeTraining,countR - 1)
                        }
                        Log.e("status","${status}")
                        if (status!! > -1)
                            Toast.makeText(this@InfoTraining,"Вы записаны",Toast.LENGTH_SHORT).show()
                        else
                            Toast.makeText(this@InfoTraining, "ytn", Toast.LENGTH_SHORT).show()
                        val intent = Intent()
                        intent.putExtra(key, "OK")
                        setResult(RESULT_OK)
                        finish()
                    }
                }
            }
        })
    }
}
