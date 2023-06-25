package com.hong_dev.todocheck

import android.content.Intent
import android.database.DatabaseUtils
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.hong_dev.todocheck.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.includeCategoryAll.root.setOnClickListener { v: View? -> clickedCategory(0)  }
        binding.includeCategoryPersonal.root.setOnClickListener { v: View? -> clickedCategory(1) }
        binding.includeCategoryWork.root.setOnClickListener { v: View? -> clickedCategory(2) }
        binding.includeCategoryStudy.root.setOnClickListener { v: View? -> clickedCategory(3) }
        binding.includeCategoryEtc.root.setOnClickListener { v: View? -> clickedCategory(4)}
        binding.includeCategoryDone.root.setOnClickListener { v: View? -> clickedCategory(5) }


    }

    fun clickedCategory(category: Int){
        val intent = Intent(this, TodoActivity::class.java)
        intent.putExtra("category", category)
        startActivity(intent)

    }

    override fun onResume() {
        super.onResume()
        loadDatabaseAndUiUpdate()
    }

    private fun loadDatabaseAndUiUpdate(){

        val db:SQLiteDatabase = openOrCreateDatabase("Todo", MODE_PRIVATE, null)
        db.execSQL("CREATE TABLE IF NOT EXISTS todo(num INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, date TEXT, category INTEGER, note TEXT, isDone INTEGER)")

        var countAll:Long = DatabaseUtils.longForQuery(db, "SELECT COUNT(*) FROM todo WHERE isDone=0", null)
        var countPersonal:Long = DatabaseUtils.longForQuery(db, "SELECT COUNT(*) FROM todo WHERE isDone=0 and category=?", arrayOf("1"))
        var countWork:Long = DatabaseUtils.longForQuery(db, "SELECT COUNT(*) FROM todo Where isDone=0 and category=?", arrayOf("2"))
        var countStudy:Long = DatabaseUtils.longForQuery(db, "SELECT COUNT(*) FROM todo Where isDone=0 and category=?", arrayOf("3"))
        var countEtc:Long = DatabaseUtils.longForQuery(db, "SELECT COUNT(*) FROM todo Where isDone=0 and category=?", arrayOf("4"))
        var countDone:Long = DatabaseUtils.longForQuery(db, "SELECT COUNT(*) FROM todo Where isDone=1", null)

        binding.includeCategoryAll.tvNum.text= countAll.toString()
        binding.includeCategoryPersonal.tvNum.text= countAll.toString()
        binding.includeCategoryWork.tvNum.text= countAll.toString()
        binding.includeCategoryStudy.tvNum.text= countAll.toString()
        binding.includeCategoryEtc.tvNum.text= countAll.toString()
        binding.includeCategoryDone.tvNum.text= countAll.toString()


    }
}