package com.hong_dev.todocheck

import android.database.DatabaseUtils
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hong_dev.todocheck.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }

    private fun loadDatabaseAndUiUpdate(){

        val db:SQLiteDatabase = openOrCreateDatabase("Todo", MODE_PRIVATE, null)
        db.execSQL("CREATE TABLE IF NOT EXISTS todo(num INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, date TEXT, category INTEGER, note TEXT, isDone INTEGER)")

        var countAll:Long = DatabaseUtils.longForQuery(db, "SELECT COUNT(*) FROM todo WHERE isDone=0", null)



    }
}