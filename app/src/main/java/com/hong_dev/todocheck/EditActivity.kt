package com.hong_dev.todocheck

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.hong_dev.todocheck.databinding.ActivityEditBinding
import java.text.SimpleDateFormat
import java.util.*

class EditActivity : AppCompatActivity() {

    lateinit var binding: ActivityEditBinding

    var category:Int = 0
    var categoryTitle = arrayOf(
        "ALL", "WORK", "PERSONAL", "STUDY", "ETC", "DONE"
    )
    var date: String = "2022년 07월 06일"

    var bottomSheetDialog: BottomSheetDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "할일추가"

        category = intent.getIntExtra("category", 0)
        binding.tvCategory.text = categoryTitle[category]

        date = SimpleDateFormat("yyyy년 mm월dd일").format(Date())
        binding.tvDate.text = date

        binding.tvDate.setOnClickListener { showBottomDialogCalendar() }
        binding.tvCategory.setOnClickListener { showBottomDialogCategory() }
        binding.btnComplete.setOnClickListener { clickComplete() }

    }

    fun showBottomDialogCalendar(){

    }

    fun showBottomDialogCategory(){

    }

    fun clickCategory(){
        this.category = category
        binding.tvCategory.text = categoryTitle[category]
        bottomSheetDialog?.dismiss()
    }

    fun clickComplete(){

    }
}