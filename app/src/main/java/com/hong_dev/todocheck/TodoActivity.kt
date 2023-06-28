package com.hong_dev.todocheck

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.hong_dev.todocheck.databinding.ActivityTodoBinding

class TodoActivity : AppCompatActivity() {

    lateinit var binding: ActivityTodoBinding

    var category:Int=0
    var categoryTitle:Array<String> = arrayOf("ALL", "PERSONAL", "WORK", "STUDY", "ETC", "DONE")

    var todoItems:MutableList<TodoItem> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTodoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent
        category = intent.getIntExtra("category", 0)

        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        supportActionBar!!.title = categoryTitle[category]

        binding.btnAddTodo.setOnClickListener { v: View? ->
            val i = Intent(this, EditActivity::class.java)
            i.putExtra("category", category)
            startActivity(i) //addTodo버튼을 누르면, intent로 카테고리 정보 전송, eidtActivity 열어줌.
        }

        binding.recyclerView.adapter= TodoAdapter(this, todoItems)

        binding.btnDone.setOnClickListener { clickDone() }

    }

    var position:Int= -1

    private fun clickDone(){
        val num:Int= todoItems[position].num
        val db:SQLiteDatabase= openOrCreateDatabase("Todo", MODE_PRIVATE, null)
        db.execSQL("UPDATE todo SET isDone=1 WHERE num=?", arrayOf(num.toString()))

        todoItems.removeAt(position)
        binding.recyclerView.adapter?.notifyItemRemoved(position)
        BottomSheetBehavior.from(binding.bs).state= BottomSheetBehavior.STATE_COLLAPSED

    }

    override fun onResume() {
        super.onResume()
        loadDB()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun loadDB(){

        todoItems.clear()
        binding.recyclerView.adapter?.notifyDataSetChanged()

        val db: SQLiteDatabase = openOrCreateDatabase("Todo", MODE_PRIVATE, null)

        var cursor:Cursor=
            if(category==0) db.rawQuery("SELECT * FROM todo WHERE isDone=0", null)
            else if (category==5) db.rawQuery("SELECT * FROM todo WHERE isDone=1", null)
            else db.rawQuery("SELECT * FROM todo WHERE isDone=0 and category=?", arrayOf(category.toString()))

        while (cursor.moveToNext()){
            val num:Int     = cursor.getInt(0)
            val title:String = cursor.getString(1)
            val date:String = cursor.getString(2)
            val category:Int = cursor.getInt(3)
            val note:String = cursor.getString(4)
            val isDone:Int = cursor.getInt(5)

            todoItems.add(TodoItem(num, title, date, category, note, isDone))
        }

        binding.recyclerView.adapter?.notifyDataSetChanged()

    }

    fun showBottomSheet(position:Int){

        this.position= position

        if (BottomSheetBehavior.from(binding.bs).state == BottomSheetBehavior.STATE_COLLAPSED) {
            updateBottomSheetData(position)
            BottomSheetBehavior.from(binding.bs).state = BottomSheetBehavior.STATE_EXPANDED
        }else{

            BottomSheetBehavior.from(binding.bs).state = BottomSheetBehavior.STATE_COLLAPSED
            BottomSheetBehavior.from(binding.bs).addBottomSheetCallback(object :BottomSheetBehavior.BottomSheetCallback(){
                override fun onStateChanged(bottomSheet: View, newState: Int) {

                    updateBottomSheetData(position)
                    BottomSheetBehavior.from(binding.bs).state = BottomSheetBehavior.STATE_EXPANDED
                    BottomSheetBehavior.from(binding.bs).removeBottomSheetCallback(this)
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                }

            })
        }
    }

    fun updateBottomSheetData(position: Int){
        binding.tvBsTitle.text= todoItems.get(position).title
        binding.tvBsDate.text= todoItems.get(position).date
        binding.tvBsCategory.text = categoryTitle[todoItems[position].category]
        binding.etBsNote.setText(todoItems[position].note)

        if(category==5) binding.btnDone.visibility= View.INVISIBLE
    }

}