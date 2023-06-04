package com.example.fit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter: RecyclerView.Adapter<RecyclerAdapter.MyVH>(){
    class MyVH (itemView: View):RecyclerView.ViewHolder(itemView) {
        val timeTraining: TextView = itemView.findViewById(R.id.time_lesson)
        val nameTraining: TextView = itemView.findViewById(R.id.name_fitness)

        fun bindView(std: UserModel) {
            nameTraining.text = std.getNameTraining()
            timeTraining.text = std.getTimeTraining().toString()
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.MyVH {
        val root = LayoutInflater.from(parent.context).inflate(R.layout.list_of_training,parent,false)
        return RecyclerAdapter.MyVH(root)
    }

    override fun getItemCount(): Int {
        return stdList.size
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.MyVH, position: Int) {
        val info = stdList[position]
        holder.bindView(info)
        holder.itemView.setOnClickListener {
            onClickItem?.invoke(info)
        }
    }
}




