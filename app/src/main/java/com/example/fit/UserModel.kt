package com.example.fit

import java.io.Serializable

class UserModel : Serializable {
    private var nameTrainer: String? = null
    private var nameTraining: String? = null
    private var description: String? = null
    private var duration: Int = 1
    private var dateOfTraining: String? = null
    private var timeOfTraining: Int = 0
    private var countPeople: Int = 0
    private var countRemainsPeople: Int = 0
    private var cost: Double = 0.0
    private var idTrainer: Int = 0
    private var idTraining: Int = 0
    private var idTr: Int = 0

    fun getCountPeople(): Int {
        return countPeople
    }
    fun setCountPeople(count: Int) {
        this.countPeople = count
    }

    fun getCountRemainsPeople(): Int {
        return countRemainsPeople
    }
    fun setCountRemainsPeople(count: Int) {
        this.countRemainsPeople = count
    }

    fun getNameTrainer(): String {
        return nameTrainer.toString()
    }
    fun setNameTrainer(nameTrainer: String) {
        this.nameTrainer = nameTrainer
    }

    fun getNameTraining(): String {
        return nameTraining.toString()
    }
    fun setNameTraining(nameTraining: String) {
        this.nameTraining = nameTraining
    }

    fun getDescription(): String {
        return description.toString()
    }
    fun setDescription(description: String) {
        this.description = description
    }

    fun getIdTr(): Int {
        return idTr
    }
    fun setIdTr(idTr: Int) {
        this.idTr = idTr
    }

    fun getIdTrainer(): Int {
        return idTrainer!!
    }
    fun setIdTrainer(idTrainer: Int) {
        this.idTrainer = idTrainer
    }

    fun getDuration(): Int {
        return duration
    }
    fun setDuration(duration: Int) {
        this.duration = duration
    }

    fun getDateTraining(): String {
        return dateOfTraining.toString()
    }
    fun setDateTraining(dateOfTraining: String) {
        this.dateOfTraining = dateOfTraining
    }

    fun getTimeTraining(): Int {
        return timeOfTraining
    }
    fun setTimeTraining(timeOfTraining: Int) {
        this.timeOfTraining = timeOfTraining
    }

    fun getIdTraining(): Int {
        return idTraining!!
    }
    fun setIdTraining(idTraining: Int) {
        this.idTraining = idTraining
    }

    fun getCost(): Double {
        return cost
    }
    fun setCost(cost: Double) {
        this.cost = cost
    }

}