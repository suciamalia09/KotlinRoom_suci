package com.example.kotlinroom_suci

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlinroom_suci.room.Constant
import com.example.kotlinroom_suci.room.Storybook
import com.example.kotlinroom_suci.room.StorybookDb
import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    val db by lazy { StorybookDb(this) }
    lateinit var storybookAdapter: StorybookAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupListener()
        setupRecycleView()
    }

    override fun onStart() {
        super.onStart()
        loadStorybook()
    }
    fun loadStorybook(){
        CoroutineScope(Dispatchers.IO).launch {
            val storyes = db.storyDao().getStorybooks()
            Log.d("MainActivity", "dbresponse: $storyes")
            withContext(Dispatchers.Main){
                storybookAdapter.setData(storyes)
            }
        }
    }


    fun setupListener(){
        add_storybook.setOnClickListener {
            intentEdit(0, Constant.TYPE_CREATE)
        }
    }

    fun intentEdit(storybookId: Int, intententType:Int){
        startActivity(
            Intent(applicationContext, AddActivity::class.java)
                .putExtra("intent_id", storybookId)
                .putExtra("intent_type", intententType)
        )
    }

    private fun setupRecycleView(){
        storybookAdapter = StorybookAdapter(arrayListOf(), object :
            StorybookAdapter.OnAdapterListener {
            override fun onClick(storybook: Storybook) {
                intentEdit(storybook.id, Constant.TYPE_READ)
            }

            override fun onUpdate(storybook: Storybook) {
                intentEdit(storybook.id, Constant.TYPE_UPDATE)
            }

            override fun onDelete(storybook: Storybook) {
                CoroutineScope(Dispatchers.IO).launch {
                    db.storyDao().deleteStorybook(storybook)
                    loadStorybook()
                }
            }

        })
        rv_storybook.apply{
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = storybookAdapter
        }
    }
    private fun deleteDialog(storybook: Storybook) {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.apply {
            setTitle("Konfirmasi")
            setMessage("Yakin akan menghapus ${storybook.title}?")
            setNegativeButton("Batal") {dialogInterface, i -> dialogInterface.dismiss()
            }
            setPositiveButton("Hapus") {dialogInterface, i -> dialogInterface.dismiss()
            CoroutineScope(Dispatchers.IO).launch {
                db.storyDao().deleteStorybook(storybook)
                loadStorybook()
                }
            }
        }
    }
}