package com.example.fit

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.recyclerview.widget.RecyclerView
import java.util.*


class Raspisanie : AppCompatActivity() {
    lateinit var datePicker: EditText
    lateinit var timePicker: TextView
    lateinit var recyclerView: RecyclerView
    private var adapter: RecyclerAdapter?=null
    private var std:UserModel?=null
    lateinit var databaseHelper: DatabaseHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_raspisanie)
        datePicker = findViewById(R.id.datePicker)

        var today = Calendar.getInstance()
        var year = today.get(Calendar.YEAR)
        var month = today.get(Calendar.MONTH)
        var day = today.get(Calendar.DAY_OF_MONTH)
        var returnDate = "${month + 1} $day $year"
        var date = StringHelper.parseDate(
            "MM dd yyyy",
            "MM/dd/yyyy",
            returnDate
        )
        datePicker.setText(StringHelper.parseDate("MM/dd/yyyy","dd MMM yyyy",
           date
        ))

        datePicker.setOnClickListener {
            setDate()
        }

        databaseHelper = DatabaseHelper(this)
        recyclerView = findViewById(R.id.lv2)
        adapter = RecyclerAdapter()
        recyclerView.adapter = adapter

        val stdList = databaseHelper.allTraining
        adapter?.addItems(stdList)

        adapter?.setOnClickItem {
            val intent = Intent(this,InfoTraining::class.java)
            intent.putExtra("nameTraining", it.getNameTraining())
            intent.putExtra("dateTraining", it.getDateTraining())
            intent.putExtra("timeTraining", it.getTimeTraining())
            intent.putExtra("duration", it.getDuration())
            intent.putExtra("description", it.getDescription())
            intent.putExtra("nameTrainer", it.getNameTrainer())
            std = it
            startActivity(intent)        }
        //databaseHelper!!.addTrainer("Иванов",15)
        //databaseHelper!!.addTrainer("Сидорчук",4)

        //databaseHelper!!.addTraining("Джампинг",60,"Прыжки на мини-батутах",300.00,10)
        //databaseHelper!!.addTraining("Йога",45,"Прыжки на миртптртпх",280.00,5)
        //databaseHelper!!.addTraining("Джампинг2",60,"Прыжки арптатетнтх",600.00,1)

        //databaseHelper!!.addTrainerTraining(1,1, "23/05/2023",10,4)
        //databaseHelper!!.addTrainerTraining(2,1, "23/05/2023",11,6)
        //databaseHelper!!.addTrainerTraining(2,1, "23/05/2023",13,10)
        //databaseHelper!!.addTrainerTraining(1,2, "24/05/2023",15,4)

    }

    private fun setDate()
    {
        //ограничение выбора даты - максимальная дата - последний день месяца
        val today = Calendar.getInstance()
        today.set(today.get(Calendar.YEAR),today.get(Calendar.MONTH),today.get(Calendar.DAY_OF_MONTH) + 7)

        //получение текущей даты
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(
            this,DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

                val returnDate = "${monthOfYear + 1} $dayOfMonth $year"
                val date = StringHelper.parseDate(
                    "MM dd yyyy",
                    "MM/dd/yyyy",
                    returnDate
                )
                val dateString = date
                datePicker.setText(StringHelper.parseDate("MM/dd/yyyy","dd MMM yyyy",date))
                datePicker.error = null
                Toast.makeText(this, "pick date input format and display $dateString", Toast.LENGTH_LONG).show()
            },year,month,day
        )
        dpd.datePicker.maxDate = today.timeInMillis
        dpd.datePicker.minDate = Calendar.getInstance().timeInMillis //минимальная дата для выбора

        dpd.show()
    }

    fun priceClick(view: View) {

    }
    fun trainingClick(view: View) {

    }
    fun profileClick(view: View) {

    }
}