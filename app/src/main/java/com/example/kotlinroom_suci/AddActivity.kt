package com.example.kotlinroom_suci

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.kotlinroom_suci.room.Constant
import com.example.kotlinroom_suci.room.Storybook
import com.example.kotlinroom_suci.room.StorybookDb
import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddActivity : AppCompatActivity() {

    private val db by lazy {StorybookDb(this)}
    private var storybookId:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        setupView()
        setupListener()
        storybookId = intent.getIntExtra("intent_id", 0)
        Toast.makeText(this, storybookId.toString(),  Toast.LENGTH_SHORT).show()
    }

    fun setupView(){
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val intentType = intent.getIntExtra("intent_type", 0)
        when (intentType){
            Constant.TYPE_CREATE -> {
                supportActionBar!!.title = "CREATE NEW STORYBOOK"
                btn_save.visibility = View.VISIBLE
                btn_update.visibility = View.GONE
            }
            Constant.TYPE_READ -> {
                supportActionBar!!.title = "READ STORYBOOK"
                btn_save.visibility = View.GONE
                btn_update.visibility = View.GONE
                getStorybook()
            }
            Constant.TYPE_UPDATE -> {
                supportActionBar!!.title = "UPDATE STORYBOOK"
                btn_save.visibility = View.GONE
                getStorybook()
            }
        }
    }


    fun setupListener(){
        btn_save.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.storyDao().addStorybook(
                    Storybook(0, et_title.text.toString(),
                    et_description.text.toString())
                )

                finish()
            }
        }
        btn_update.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.storyDao().updateStorybook(
                    Storybook(storybookId, et_title.text.toString(),
                        et_description.text.toString())
                )

                finish()
            }
        }
    }

    fun getStorybook(){
        storybookId = intent.getIntExtra("intent_id", 0)
        CoroutineScope(Dispatchers.IO).launch {
            val storyes = db.storyDao().getStorybook(storybookId)[0]
            et_title.setText(storyes.title)
            et_description.setText(storyes.desc)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}