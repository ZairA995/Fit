package com.example.fit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Price : AppCompatActivity() {
    lateinit var recyclerPrice: RecyclerView
    private var adapter: PriceAdapter?=null
    private var std:UserModel?=null
    lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_price)

        databaseHelper = DatabaseHelper(this)
        recyclerPrice = findViewById(R.id.training_list)
        adapter = PriceAdapter()
        recyclerPrice.adapter = adapter

        var stdList = databaseHelper.getPriceList()
        adapter?.addItems(stdList)
    }
}