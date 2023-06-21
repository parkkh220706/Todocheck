package com.hong_dev.todocheck

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hong_dev.todocheck.databinding.ActivityTodoBinding

class TodoActivity : AppCompatActivity() {

    lateinit var binding: ActivityTodoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTodoBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }



}