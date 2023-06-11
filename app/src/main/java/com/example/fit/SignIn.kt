package com.example.fit

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.content.SharedPreferences
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog


class SignIn : AppCompatActivity() {
    private var adapter: ProfileAdapter?=null
    private var std:UserModel?=null
    lateinit var databaseHelper: DatabaseHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        databaseHelper = DatabaseHelper(this)
        val inputId:EditText = findViewById(R.id.login)
        val inputName:EditText = findViewById(R.id.password)


        val butt: Button = findViewById(R.id.button_in)
        butt.setOnClickListener(View.OnClickListener {
            val id:String = inputId.text.toString()
            val name:String = inputName.text.toString()
            if (id.isNotEmpty() && name.isNotEmpty())
            {
                if(id == "12345" && name == "12345")
                {
                    Toast.makeText(this, "Администратор", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this,Raspisanie::class.java)
                    intent.putExtra("phone", name)
                    finish()
                    startActivity(intent)
                }
                else
                {
                    var status = databaseHelper.getClientCount(id,name)
                    if (status == 1) {
                        var idClients = databaseHelper.getClientId(id,name)
                        val intent = Intent(this,Raspisanie::class.java)
                        intent.putExtra("IDClient", idClients)
                        finish()
                        startActivity(intent)
                    }
                    else {
                        val builder = AlertDialog.Builder(this)
                        //set title for alert dialog
                        builder.setTitle("")
                        //set message for alert dialog
                        builder.setMessage("Зарегистрировать аккаунт с этими данными?")

                        //performing positive action
                        builder.setPositiveButton("Yes"){dialogInterface, which ->
                            var uniqPhone = databaseHelper.getClientPhone(name)
                            if (uniqPhone > 0)
                            {
                                Toast.makeText(this, "Аккаунт с таким номером уже существует", Toast.LENGTH_SHORT).show()
                            }
                            else
                            {
                                var status = databaseHelper.addClient(id,name)
                                if (status > -1) {

                                    Toast.makeText(this, "Успешно", Toast.LENGTH_SHORT).show()
                                    var idClients = databaseHelper.getClientId(id,name)
                                    val intent = Intent(this,Raspisanie::class.java)
                                    intent.putExtra("IDClient", idClients)
                                    finish()
                                    startActivity(intent)
                                }
                            }
                        }

                        builder.setNegativeButton("No"){dialogInterface, which ->
                        }

                        val alertDialog: AlertDialog = builder.create()
                        alertDialog.setCancelable(true)
                        alertDialog.show()
                    }
                }
            }
            else
            {
                Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show()
            }
        })
    }
}