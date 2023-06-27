package com.hong_dev.todocheck

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hong_dev.todocheck.databinding.RecycleritemTodoBinding

class TodoAdapter constructor(val context: Context, var todoItem:MutableList<TodoItem>) : RecyclerView.Adapter<TodoAdapter.VH>(){

    inner class VH constructor(itemView: View) : RecyclerView.ViewHolder(itemView){
        val binding:RecycleritemTodoBinding = RecycleritemTodoBinding.bind(itemView)
        init {
            binding.root.setOnClickListener {
                (context as TodoActivity).showBottomSheet(layoutPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val layoutInflater:LayoutInflater = LayoutInflater.from(context)
        val itemView:View = layoutInflater.inflate(R.layout.recycleritem_todo, parent, false)
        return VH(itemView)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.binding.tvTodoitemTitle.text= todoItem.get(position).title
        holder.binding.tvTodoitemDate.text= todoItem[position].date
    }

    override fun getItemCount(): Int = todoItem.size

}