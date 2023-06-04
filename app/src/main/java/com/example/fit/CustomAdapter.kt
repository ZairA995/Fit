package com.example.fit

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter(private val context: Context, private val userModelArrayList: ArrayList<UserModel>) :
    BaseAdapter() {
    override fun getCount(): Int {
        return userModelArrayList.size
    }

    override fun getItem(p0: Int): Any {
        return userModelArrayList[p0]
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var convertView = p1
        val holder: ViewHolder

        if (convertView == null) {
            holder = ViewHolder()
            val inflater = context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inflater.inflate(R.layout.list_of_training, null, true)

            holder.nameTraining = convertView!!.findViewById(R.id.name_fitness) as TextView
            holder.timeOfTraining = convertView!!.findViewById(R.id.time_lesson) as TextView

            convertView.tag = holder
        }
        else {
            holder = convertView.tag as ViewHolder
        }

        holder.nameTraining!!.text = userModelArrayList[p0].getNameTraining()
        holder.timeOfTraining!!.text = userModelArrayList[p0].getTimeTraining().toString()
        return convertView
    }

    private inner class ViewHolder {
        var nameTraining: TextView? = null
        var timeOfTraining: TextView? = null
    }

}