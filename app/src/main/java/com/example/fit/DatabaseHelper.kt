package com.example.fit

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.lang.Exception
import kotlin.collections.ArrayList

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    val allTraining: ArrayList<UserModel>
        get() {
            val userModelArrayList = ArrayList<UserModel>()

            val selectTraining =
                "SELECT * FROM $TABLE_TRAINER_TRAINING LEFT JOIN $TABLE_TRAINING ON Trainer_Training.idTraining = Training.idTraining LEFT JOIN $TABLE_TRAINER ON Trainer_Training.idTrainer = Trainer.idTrainer;"
            val db = this.readableDatabase

            val c = db.rawQuery(selectTraining, null)
            if (c.moveToFirst()) {
                do {
                    val userModel = UserModel()
                    userModel.setIdTraining(c.getInt(c.getColumnIndexOrThrow("idTraining")))
                    userModel.setIdTrainer(c.getInt(c.getColumnIndexOrThrow("idTrainer")))
                    userModel.setNameTraining(c.getString(c.getColumnIndexOrThrow("nameTraining")))
                    userModel.setCountPeople(c.getInt(c.getColumnIndexOrThrow("countPeople")))
                    userModel.setCountRemainsPeople(c.getInt(c.getColumnIndexOrThrow("countRemainsPeople")))
                    userModel.setNameTrainer(c.getString(c.getColumnIndexOrThrow("trainerName")))
                    userModel.setDuration(c.getInt(c.getColumnIndexOrThrow("durationTraining")))
                    userModel.setDescription(c.getString(c.getColumnIndexOrThrow("description")))
                    userModel.setDateTraining(c.getString(c.getColumnIndexOrThrow("dateOfTraining")))
                    userModel.setTimeTraining(c.getInt(c.getColumnIndexOrThrow("timeOfTraining")))
                    userModelArrayList.add(userModel)
                } while (c.moveToNext())
            }

            return userModelArrayList
        }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE_CLIENT)
        db.execSQL(CREATE_TABLE_TRAINER)
        db.execSQL(CREATE_TABLE_TRAINING)
        db.execSQL(CREATE_TABLE_TRAINER_TRAINING)
        db.execSQL(CREATE_TABLE_CLIENT_TRAINING)
        db.execSQL(CREATE_TABLE_ABONEMENT)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS '$TABLE_CLIENT'")
        db.execSQL("DROP TABLE IF EXISTS '$TABLE_TRAINER'")
        db.execSQL("DROP TABLE IF EXISTS '$TABLE_TRAINING'")
        db.execSQL("DROP TABLE IF EXISTS '$TABLE_TRAINER_TRAINING'")
        db.execSQL("DROP TABLE IF EXISTS '$TABLE_CLIENT_TRAINING'")
        db.execSQL("DROP TABLE IF EXISTS '$TABLE_ABONEMENT'")

        onCreate(db)

    }

    //регистрация тренера
    fun addTrainer(nameTrainer: String, duration: Int) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put("trainerName", nameTrainer)
        values.put("durationJob", duration)
        db.insert(TABLE_TRAINER, null, values)
    }

    //создание тренировки
    fun addTraining(nameTraining: String, duration: Int, description: String, cost: Double, countPeople: Int) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put("nameTraining", nameTraining)
        values.put("durationTraining", duration)
        values.put("description", description)
        values.put("cost", cost)
        values.put("countPeople", countPeople)
        db.insert(TABLE_TRAINING, null, values)
    }

    //назначение тренера на тренировку
    fun addTrainerTraining(idTrainer: Int, idTraining: Int, dateOfTraining: String, timeOfTraining: Int, countRemainsPeople: Int) {
        val db = this.writableDatabase
        val values = ContentValues()

        values.put("idTraining", idTraining)
        values.put("idTrainer", idTrainer)
        values.put("dateOfTraining", dateOfTraining)
        values.put("timeOfTraining", timeOfTraining)
        values.put("countRemainsPeople", countRemainsPeople)
        Log.e("insert", "${values}")
        db.insert(TABLE_TRAINER_TRAINING, null, values)
    }

    //обновить количество свободных мест после записи на тренировку
    fun changeCountPeople(idTraining: Int, dateOfTraining: String,timeOfTraining: Int,countRemainsPeople: Int):Long {
        val db = this.writableDatabase
        val values = ContentValues()
        //values.put("idTraining", idTraining)
        //values.put("idTrainer", idTrainer)
        //values.put("dateOfTraining", dateOfTraining)
        //values.put("timeOfTraining", timeOfTraining)
        values.put("countRemainsPeople",countRemainsPeople)
        val succes = db.update(TABLE_TRAINER_TRAINING, values," idTraining = " + idTraining + " and dateOfTraining = '"
                + dateOfTraining + "' and timeOfTraining = " + timeOfTraining + "",null)
        db.close()
        return succes.toLong()
    }

    //регистрация клиента
    fun addClient(clientName: String,clientPhone: String): Long
    {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put("clientName",clientName)
        values.put("clientPhone",clientPhone)
        val status = db.insert(TABLE_CLIENT, null, values)
        return status
    }

    //запись на тренировку
    fun addClientTraining(idTraining: Int,idClient:Int)
    {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put("idTraining",idTraining)
        values.put("idClient",idClient)
        db.insert(TABLE_CLIENT_TRAINING, null, values)
    }

    fun cancelTraining(idClient: Int,idTraining: Int):Int
    {
        val db = this.writableDatabase
        var status = db.delete(TABLE_CLIENT_TRAINING, "idClient = ${idClient} and idTraining = ${idTraining}", null)
        return status
    }

    //получение списка тренировок на выбранный день
    fun allTrainingDay(date: String): ArrayList<UserModel>
    {
        val userModelArrayList = ArrayList<UserModel>()
        //val selectTraining =
            //"SELECT * FROM $TABLE_TRAINER_TRAINING LEFT JOIN $TABLE_TRAINING ON Trainer_Training.idTraining = Training.idTraining LEFT JOIN $TABLE_TRAINER ON Trainer_Training.idTrainer = Trainer.idTrainer " +
                    //"where dateOfTraining = '" + date + "';"

        val selectTraining =
            "SELECT * FROM Trainer_Training,Training,Trainer where Trainer_Training.idTraining = Training.idTraining and Trainer_Training.idTrainer = Trainer.idTrainer and " +
                    "dateOfTraining = '" + date + "';"


        Log.e("query","${selectTraining}")
        val db = this.readableDatabase
        var count = 0

        val c = db.rawQuery(selectTraining, null)
        if (c.moveToFirst()) {
            do {
                val userModel = UserModel()
                userModel.setIdTr(c.getInt(c.getColumnIndexOrThrow("id")))
                userModel.setIdTraining(c.getInt(c.getColumnIndexOrThrow("idTraining")))
                userModel.setIdTrainer(c.getInt(c.getColumnIndexOrThrow("idTrainer")))
                userModel.setNameTraining(c.getString(c.getColumnIndexOrThrow("nameTraining")))
                userModel.setCountPeople(c.getInt(c.getColumnIndexOrThrow("countPeople")))
                userModel.setCountRemainsPeople(c.getInt(c.getColumnIndexOrThrow("countRemainsPeople")))
                userModel.setNameTrainer(c.getString(c.getColumnIndexOrThrow("trainerName")))
                userModel.setDuration(c.getInt(c.getColumnIndexOrThrow("durationTraining")))
                userModel.setDescription(c.getString(c.getColumnIndexOrThrow("description")))
                userModel.setDateTraining(c.getString(c.getColumnIndexOrThrow("dateOfTraining")))
                userModel.setTimeTraining(c.getInt(c.getColumnIndexOrThrow("timeOfTraining")))
                userModelArrayList.add(userModel)
            } while (c.moveToNext())
        }
        count = userModelArrayList.size
        Log.e("count","${count}")
        return userModelArrayList
    }


    //вывести ближайшие записи клиента
    fun getNearTraining(idClients: Int,date:String): ArrayList<UserModel>
    {
        val stdList: ArrayList<UserModel> = ArrayList()

        val query = "SELECT * FROM Client_Training, Trainer_Training, Training where Client_Training.idTraining = Trainer_Training.id and " +
                " Trainer_Training.idTraining = Training.idTraining and Client_Training.idClient = ${idClients} and " +
                " Trainer_Training.dateOfTraining >= '${date}';"

        Log.e("query","${query}")
        val db = this.readableDatabase
        val c = db.rawQuery(query, null)
        if (c.moveToFirst()) {
            do {
                val userModel = UserModel()

                userModel.setNameTraining(c.getString(c.getColumnIndexOrThrow("nameTraining")))
                userModel.setDateTraining(c.getString(c.getColumnIndexOrThrow("dateOfTraining")))
                userModel.setTimeTraining(c.getInt(c.getColumnIndexOrThrow("timeOfTraining")))
                userModel.setIdTraining(c.getInt(c.getColumnIndexOrThrow("idTraining")))
                userModel.setIdTrainer(c.getInt(c.getColumnIndexOrThrow("idTrainer")))
                userModel.setCountPeople(c.getInt(c.getColumnIndexOrThrow("countPeople")))
                userModel.setCountRemainsPeople(c.getInt(c.getColumnIndexOrThrow("countRemainsPeople")))
                userModel.setDuration(c.getInt(c.getColumnIndexOrThrow("durationTraining")))
                userModel.setDescription(c.getString(c.getColumnIndexOrThrow("description")))
                userModel.setIdTr(c.getInt(c.getColumnIndexOrThrow("id")))
                stdList.add(userModel)
            } while (c.moveToNext())
        }
        return stdList
    }

    fun getPriceList(): ArrayList<UserModel>
    {
        val stdList: ArrayList<UserModel> = ArrayList()
        val query = "SELECT * FROM Training;"

        Log.e("query","${query}")
        val db = this.readableDatabase
        val c = db.rawQuery(query, null)
        if (c.moveToFirst()) {
            do {
                val userModel = UserModel()

                userModel.setNameTraining(c.getString(c.getColumnIndexOrThrow("nameTraining")))
                userModel.setIdTraining(c.getInt(c.getColumnIndexOrThrow("idTraining")))
                userModel.setCost(c.getDouble(c.getColumnIndexOrThrow("cost")))
                userModel.setCountPeople(c.getInt(c.getColumnIndexOrThrow("countPeople")))
                userModel.setDuration(c.getInt(c.getColumnIndexOrThrow("durationTraining")))
                userModel.setDescription(c.getString(c.getColumnIndexOrThrow("description")))
                stdList.add(userModel)
            } while (c.moveToNext())
        }
        return stdList
    }


    //авторизация (поиск количества пользователей с указанным логином и паролем)
    fun getClientCount(search_name: String,search_phone:String):Int {
        val query = "SELECT count(*) FROM $TABLE_CLIENT WHERE clientName = '${search_name}' and clientPhone = '${search_phone}'"
        val db = this.readableDatabase
        val cursor: Cursor?
        cursor = db.rawQuery(query,null)
        var res:Int = 0
        if(cursor.moveToFirst())
        {
            do {
                res = cursor.getInt(0)
            }while (cursor.moveToNext())
        }
        return res
    }

    //поиск id клиента по имени и номеру
    fun getClientId(search_name: String,search_phone:String):Int {
        val query = "SELECT idClient FROM $TABLE_CLIENT WHERE clientName = '${search_name}' and clientPhone = '${search_phone}'"
        val db = this.readableDatabase
        val cursor: Cursor?
        cursor = db.rawQuery(query,null)
        var res:Int = 0
        if(cursor.moveToFirst())
        {
            do {
                res = cursor.getInt(0)
            }while (cursor.moveToNext())
        }
        return res
    }

    //получение фио по коду клиента
    fun getClientName(idClient: Int):String {
        val query = "SELECT clientName FROM $TABLE_CLIENT WHERE idClient = ${idClient}"
        val db = this.readableDatabase
        val cursor: Cursor?
        cursor = db.rawQuery(query,null)
        var name:String = ""
        if(cursor.moveToFirst())
        {
            do {
                name= cursor.getString(0)
            }while (cursor.moveToNext())
        }
        return name
    }

    //получение номера телефона по коду клиента
    fun getClientPhone(idClient: Int):String {
        val query = "SELECT clientPhone FROM $TABLE_CLIENT WHERE idClient = ${idClient}"
        val db = this.readableDatabase
        val cursor: Cursor?
        cursor = db.rawQuery(query,null)
        var phone:String = ""
        if(cursor.moveToFirst())
        {
            do {
                phone= cursor.getString(0)
            }while (cursor.moveToNext())
        }
        return phone
    }

    //проверка, что клиент уже не записан на данную тренировку
    fun getClientTr(idTr: Int,idCl:Int):Int {
        val query = "SELECT count(*) FROM $TABLE_CLIENT_TRAINING WHERE idTraining= ${idTr} and idClient = ${idCl}"
        val db = this.readableDatabase
        val cursor: Cursor?
        cursor = db.rawQuery(query,null)
        var res:Int = 0
        if(cursor.moveToFirst())
        {
            do {
                res = cursor.getInt(0)
            }while (cursor.moveToNext())
        }
        return res
    }

    //уникальность номера телефона
    fun getClientPhone(phone: String):Int {
        val query = "SELECT count(*) FROM $TABLE_CLIENT WHERE clientPhone= '${phone}'"
        val db = this.readableDatabase
        val cursor: Cursor?
        cursor = db.rawQuery(query,null)
        var res:Int = 0
        if(cursor.moveToFirst())
        {
            do {
                res = cursor.getInt(0)
            }while (cursor.moveToNext())
        }
        return res
    }

    fun getPastTraining(idClients: Int): ArrayList<UserModel>
    {
        val stdList: ArrayList<UserModel> = ArrayList()

        val query = "SELECT * FROM $TABLE_CLIENT_TRAINING LEFT JOIN $TABLE_TRAINING ON Client_Training.idTraining = Training.idTraining where " +
                " idClient = $idClients and dateOfTraining < getdate();"
        val db = this.readableDatabase
        val c = db.rawQuery(query, null)
        if (c.moveToFirst()) {
            do {
                val userModel = UserModel()
                userModel.setNameTraining(c.getString(c.getColumnIndexOrThrow("nameTraining")))
                userModel.setDateTraining(c.getString(c.getColumnIndexOrThrow("dateOfTraining")))
                userModel.setTimeTraining(c.getInt(c.getColumnIndexOrThrow("timeOfTraining")))
                stdList.add(userModel)
            } while (c.moveToNext())
        }
        return stdList
    }

    companion object {

        var DATABASE_NAME = "Jumping_Room6"
        private val DATABASE_VERSION = 1
        private val TABLE_CLIENT = "Client"
        private val TABLE_TRAINER = "Trainer"
        private val TABLE_TRAINING = "Training"
        private val TABLE_ABONEMENT = "Abonement"
        private val TABLE_TRAINER_TRAINING = "Trainer_Training"
        private val TABLE_CLIENT_TRAINING = "Client_Training"

        private val CREATE_TABLE_CLIENT = ("CREATE TABLE "
                + TABLE_CLIENT + "(idClient INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "clientName TEXT NOT NULL, " +
                "clientPhone TEXT NOT NULL); ")

        private val CREATE_TABLE_TRAINER = ("CREATE TABLE "
                + TABLE_TRAINER + "(idTrainer INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "trainerName TEXT NOT NULL, " +
                "durationJob INTEGER);")

        private val CREATE_TABLE_TRAINING = ("CREATE TABLE "
                + TABLE_TRAINING + "(idTraining INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nameTraining TEXT NOT NULL unique, " +
                "description TEXT, " +
                "durationTraining INTEGER NOT NULL, " +
                "cost REAL NOT NULL," +
                "countPeople INTEGER NOT NULL);")

        private val CREATE_TABLE_ABONEMENT = ("CREATE TABLE "
                + TABLE_ABONEMENT + "(idAbonement INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "idClient INTEGER NOT NULL, " +
                "idTraining INTEGER NOT NULL, " +
                "dateAbonement TEXT NOT NULL, " +
                "countTraining INTEGER NOT NULL," +
                "countRemainsTraining INTEGER);")

        private val CREATE_TABLE_TRAINER_TRAINING = ("CREATE TABLE "
                + TABLE_TRAINER_TRAINING + "(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "idTraining INTEGER NOT NULL, " +
                "idTrainer INTEGER NOT NULL, " +
                "dateOfTraining TEXT NOT NULL, " +
                "timeOfTraining INTEGER NOT NULL, " +
                "countRemainsPeople INTEGER);")

        private val CREATE_TABLE_CLIENT_TRAINING = ("CREATE TABLE "
                + TABLE_CLIENT_TRAINING + "(idTraining INTEGER NOT NULL, " +
                "idClient INTEGER NOT NULL);")
    }

}