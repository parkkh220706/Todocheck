package com.hong_dev.todocheck

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hong_dev.todocheck.databinding.ActivityTodoBinding

class TodoActivity : AppCompatActivity() {

    lateinit var binding: ActivityTodoBinding

    var category:Int=0
    var categoryTitle:Array<String> = arrayOf("ALL", "WORK", "PERSONAL", "STUDY", "ETC", "DONE")

    var todoItems:MutableList<TodoItem> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTodoBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }



}