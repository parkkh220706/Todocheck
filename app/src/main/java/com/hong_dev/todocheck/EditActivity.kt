package com.hong_dev.todocheck

import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CalendarView
import androidx.activity.OnBackPressedCallback
import androidx.core.view.GravityCompat
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
    var date: String = "2022. 07. 06."

    var bottomSheetDialog: BottomSheetDialog? = null // 널허용, null로 초기화.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Add Schedule"

        category = intent.getIntExtra("category", 0)
        binding.tvCategory.text = categoryTitle[category]

        date = SimpleDateFormat("yyyy. mm. dd.").format(Date())
        binding.tvDate.text = date

        binding.tvDate.setOnClickListener { showBottomDialogCalendar() }
        binding.tvCategory.setOnClickListener { showBottomDialogCategory() }
        binding.btnComplete.setOnClickListener { clickComplete() }

    }

    fun showBottomDialogCalendar(){
        bottomSheetDialog = BottomSheetDialog(this)
        bottomSheetDialog?.setContentView(R.layout.dialog_calendar)
        bottomSheetDialog?.show()

        val calendarView = bottomSheetDialog?.findViewById<CalendarView>(R.id.dialog_calendar)
        calendarView?.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val calendar = GregorianCalendar(year, month, dayOfMonth)
            date = SimpleDateFormat("yyyy. MM. dd.").format(calendar.time)
            binding.tvDate.text = date
            bottomSheetDialog?.dismiss()
        }

    }

    fun showBottomDialogCategory(){
        bottomSheetDialog = BottomSheetDialog(this)
        bottomSheetDialog?.setContentView(R.layout.dialog_category)
        bottomSheetDialog?.show()

        bottomSheetDialog?.findViewById<View>(R.id.dia_category_work)?.setOnClickListener { clickCategory(1) }
        bottomSheetDialog?.findViewById<View>(R.id.dia_category_personal)?.setOnClickListener { clickCategory(2) }
        bottomSheetDialog?.findViewById<View>(R.id.dia_category_study)?.setOnClickListener { clickCategory(3) }
        bottomSheetDialog?.findViewById<View>(R.id.dia_category_etc)?.setOnClickListener { clickCategory(4) }
    }

    fun clickCategory(category:Int){
        this.category = category
        binding.tvCategory.text = categoryTitle[category]
        bottomSheetDialog?.dismiss()
    }

    fun clickComplete(){
        val db: SQLiteDatabase= openOrCreateDatabase("Todo", MODE_PRIVATE, null)
        db.execSQL("CREATE TABLE IF NOT EXISTS todo(num INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, date TEXT, category INTEGER, note TEXT, isDone INTEGER)")

        val title: String = binding.etTitle.text.toString()
        val note: String = binding.etNote.text.toString()

        db.execSQL("INSERT INTO todo(title, date, category, note, isDone) VALUES(?,?,?,?,?)", arrayOf(title, date, category, note, 0))

        onBackPressedDispatcher.onBackPressed()
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.onSupportNavigateUp()
    }
}