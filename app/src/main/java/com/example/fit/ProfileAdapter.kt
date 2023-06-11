package com.example.fit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ProfileAdapter: RecyclerView.Adapter<ProfileAdapter.MyVH>(){
    class MyVH (itemView: View):RecyclerView.ViewHolder(itemView) {
        val timeTraining: TextView = itemView.findViewById(R.id.time_lesson)
        val nameTraining: TextView = itemView.findViewById(R.id.name_fitness)
        val dateTraining: TextView = itemView.findViewById(R.id.date_lesson)


        fun bindView(std: UserModel) {
            nameTraining.text = std.getNameTraining()
            timeTraining.text = std.getTimeTraining().toString() + ":00"
            dateTraining.text = std.getDateTraining()
        }
    }

    private var stdList: ArrayList<UserModel> = ArrayList()
    fun addItems(items:ArrayList<UserModel>)
    {
        this.stdList = items
        notifyDataSetChanged()
    }

    private var onClickItem:((UserModel)->Unit)? = null

    fun setOnClickItem(callback: (UserModel) -> Unit)
    {
        this.onClickItem = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileAdapter.MyVH {
        val root = LayoutInflater.from(parent.context).inflate(R.layout.list_of_near_training,parent,false)
        return ProfileAdapter.MyVH(root)
    }

    override fun onBindViewHolder(holder: MyVH, position: Int) {
        val info = stdList[position]
        holder.bindView(info)
        holder.itemView.setOnClickListener {
            onClickItem?.invoke(info)
        }
    }

    override fun getItemCount(): Int {
        return stdList.size
    }


}