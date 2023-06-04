package com.example.fit

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.lang.Exception
import java.util.ArrayList

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

    fun addTrainer(nameTrainer: String, duration: Int) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put("trainerName", nameTrainer)
        values.put("durationJob", duration)
        db.insert(TABLE_TRAINER, null, values)
    }

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

    /*fun addTrainerTraining(nameTrainer: String, nameTraining: String, dateOfTraining: String, timeOfTraining: Int, countRemainsPeople: Int) {
        var db = this.readableDatabase

        val selectTrainer = "SELECT idTrainer FROM $TABLE_TRAINER WHERE trainerName = " + nameTrainer + ";"
        val cCity = db.rawQuery(selectTrainer, null)
        val id_trainer = cCity.getInt(cCity.getColumnIndexOrThrow("idTrainer"))

        val selectTraining = "SELECT idTraining FROM $TABLE_TRAINING WHERE nameTraining = " + nameTraining + ";"
        val tCity = db.rawQuery(selectTraining, null)
        val id_training = tCity.getInt(tCity.getColumnIndexOrThrow("idTraining"))

        Log.e("ic", "${id_trainer},${id_training}")
        /*if (cCity.moveToFirst()) {
            do {
                userModel.setDateTraining(cCity.getString(cCity.getColumnIndexOrThrow("dateOfTraining")))
                userModel.setTimeTraining(cCity.getInt(cCity.getColumnIndexOrThrow("timeOfTraining")))
            } while (cCity.moveToNext())
        }*/
        db = this.writableDatabase
        val values = ContentValues()

        values.put("idTraining", id_training)
        values.put("idTrainer", id_trainer)
        values.put("dateOfTraining", dateOfTraining)
        values.put("timeOfTraining", timeOfTraining)
        values.put("countRemainsPeople", countRemainsPeople)
        Log.e("insert", "${values}")
        db.insert(TABLE_TRAINER_TRAINING, null, values)
    }*/

/*
    fun updateUser(id: Int, name: String, hobby: String, city: String) {
        val db = this.writableDatabase

        // updating name in users table
        val values = ContentValues()
        values.put(KEY_FIRSTNAME, name)
        db.update(TABLE_USER, values, "$KEY_ID = ?", arrayOf(id.toString()))

        // updating hobby in users_hobby table
        val valuesHobby = ContentValues()
        valuesHobby.put(KEY_HOBBY, hobby)
        db.update(TABLE_USER_HOBBY, valuesHobby, "$KEY_ID = ?", arrayOf(id.toString()))

        // updating city in users_city table
        val valuesCity = ContentValues()
        valuesCity.put(KEY_CITY, city)
        db.update(TABLE_USER_CITY, valuesCity, "$KEY_ID = ?", arrayOf(id.toString()))
    }

    fun deleteUSer(id: Int) {

        // delete row in students table based on id
        val db = this.writableDatabase

        //deleting from users table
        db.delete(TABLE_USER, "$KEY_ID = ?", arrayOf(id.toString()))

        //deleting from users_hobby table
        db.delete(TABLE_USER_HOBBY, "$KEY_ID = ?", arrayOf(id.toString()))

        //deleting from users_city table
        db.delete(TABLE_USER_CITY, "$KEY_ID = ?", arrayOf(id.toString()))
    }*/

    companion object {

        var DATABASE_NAME = "Jumping_Room3"
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
                "clientPhone TEXT NOT NULL, " +
                "clientAge INTEGER);")

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