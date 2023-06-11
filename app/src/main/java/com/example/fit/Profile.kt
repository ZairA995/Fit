package com.example.fit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class Profile : AppCompatActivity() {
    lateinit var datePicker: EditText
    lateinit var timePicker: TextView
    lateinit var fio: EditText
    lateinit var phone: EditText
    lateinit var recyclerViewTraining: RecyclerView
    lateinit var recyclerViewAbonement: RecyclerView
    private var adapter: ProfileAdapter?=null
    private var std:UserModel?=null
    lateinit var databaseHelper: DatabaseHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        fio = findViewById(R.id.fio)
        phone = findViewById(R.id.phone)
        databaseHelper = DatabaseHelper(this)
        var idClient = intent.getIntExtra("idClient",0)
        var date = intent.getStringExtra("date")
        fio.setText(databaseHelper.getClientName(idClient))
        phone.setText(databaseHelper.getClientPhone(idClient))

        recyclerViewTraining = findViewById(R.id.training_list)
        adapter = ProfileAdapter()
        recyclerViewTraining.adapter = adapter

        var stdList = databaseHelper.getNearTraining(idClient, date.toString())
        adapter?.addItems(stdList)

        adapter?.setOnClickItem {
            databaseHelper.cancelTraining(idClient, it.getIdTr())
            var status = databaseHelper.changeCountPeople(it.getIdTraining(),
                    it.getDateTraining(),it.getTimeTraining(),it.getCountRemainsPeople() + 1)
            if (status > -1)
            {
                Toast.makeText(this@Profile, "Место освобождено", Toast.LENGTH_SHORT).show()
            }
            adapter?.addItems(stdList)
            std = it
        }
    }

}