package com.example.fit

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
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
    var idClient: Int = 0
    val schoose = 0
    val choose1 = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_raspisanie)
        datePicker = findViewById(R.id.datePicker)

        idClient = intent.getIntExtra("IDClient",0)

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
        datePicker.setText(StringHelper.parseDate("MM/dd/yyyy","dd/MM/yyyy",
           date
        ))

        datePicker.setOnClickListener {
            setDate()
        }

        databaseHelper = DatabaseHelper(this)
        recyclerView = findViewById(R.id.lv2)
        adapter = RecyclerAdapter()
        recyclerView.adapter = adapter

        //databaseHelper!!.addTrainer("Иванов",15)
        //databaseHelper!!.addTrainer("Сидорчук",4)

        //databaseHelper!!.addClient("Борис","9843")
        //databaseHelper!!.addClient("Сидор","44333")

        //databaseHelper!!.addTraining("Джампинг",60,"Прыжки на мини-батутах",300.00,10)
        //databaseHelper!!.addTraining("Йога",45,"Прыжки на миртптртпх",280.00,5)

        //databaseHelper!!.addTrainerTraining(1,1, "11/06/2023",10,0)
        //databaseHelper!!.addTrainerTraining(2,1, "11/06/2023",11,6)
        //databaseHelper!!.addTrainerTraining(1,2, "12/06/2023",12,10)

        val stdList = databaseHelper.allTrainingDay(datePicker.text.toString())
        Log.e("date","${datePicker.text.toString()}")
        adapter?.addItems(stdList)

        adapter?.setOnClickItem {
            val intent = Intent(this,InfoTraining::class.java)
            intent.putExtra("idTr", it.getIdTr())
            intent.putExtra("idTraining", it.getIdTraining())
            intent.putExtra("countRemains", it.getCountRemainsPeople())
            intent.putExtra("idTrainer", it.getIdTrainer())
            intent.putExtra("nameTraining", it.getNameTraining())
            intent.putExtra("dateTraining", it.getDateTraining())
            intent.putExtra("timeTraining", it.getTimeTraining())
            intent.putExtra("duration", it.getDuration())
            intent.putExtra("description", it.getDescription())
            intent.putExtra("nameTrainer", it.getNameTrainer())
            intent.putExtra("idClient", idClient)
            std = it
            startActivityForResult(intent,schoose)      }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //добавление нового занятия завершено
        if (requestCode == schoose) {
            if (resultCode == Activity.RESULT_OK) {
                var res = data?.getStringExtra(key)
                val stdList = databaseHelper.allTrainingDay(datePicker.text.toString())
                adapter?.addItems(stdList)
            }
        }
        if (requestCode == choose1) {
            if (resultCode == Activity.RESULT_OK) {
                var res = data?.getStringExtra(key1)
            }
            val stdList = databaseHelper.allTrainingDay(datePicker.text.toString())
            adapter?.addItems(stdList)
        }
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
                datePicker.setText(StringHelper.parseDate("MM/dd/yyyy","dd/MM/yyyy",date))
                datePicker.error = null
                val stdList = databaseHelper.allTrainingDay(datePicker.text.toString())
                adapter?.addItems(stdList)
            },year,month,day
        )
        dpd.datePicker.maxDate = today.timeInMillis
        dpd.datePicker.minDate = Calendar.getInstance().timeInMillis //минимальная дата для выбора

        dpd.show()
    }

    fun priceClick(view: View) {
        val intent = Intent(this,Price::class.java)
        startActivity(intent)
    }
    fun trainingClick(view: View) {

    }
    fun profileClick(view: View) {
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

        val intent = Intent(this,Profile::class.java)
        intent.putExtra("idClient", idClient)
        intent.putExtra("date", date.toString())
        startActivityForResult(intent,choose1)

    }
}