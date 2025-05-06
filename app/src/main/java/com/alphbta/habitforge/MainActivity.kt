package com.alphbta.habitforge

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.Button
import android.widget.EditText
import android.content.Intent
import android.view.WindowManager
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity() {
    private lateinit var physique: TextView
    private lateinit var intelligenceText: TextView
    private lateinit var creativityText: TextView
    private lateinit var charismaText: TextView
    private lateinit var addTaskLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val db = DbHelper(this, null)
        val tasks = TaskRepository(db).getAllTasks()
        val tasksRecyclerView: RecyclerView = findViewById(R.id.taskList)
        val taskAdapter = TaskAdapter(tasks, this)
        val addTask: ImageView = findViewById(R.id.addTask)
        val addTaskLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                taskAdapter.updateTasks(TaskRepository(db).getAllTasks())
            }
        }

        tasksRecyclerView.layoutManager = LinearLayoutManager(this)
        tasksRecyclerView.adapter = taskAdapter

        addTask.setOnClickListener {
            intent = Intent(this, AddTaskActivity::class.java)
            addTaskLauncher.launch(intent)
        }

//        physique = findViewById(R.id.physique)
//        intelligenceText = findViewById(R.id.intelligence)
//        creativityText = findViewById(R.id.creativity)
//        charismaText = findViewById(R.id.charisma)
//        updateAllTexts()
//
//        tempButton.setOnClickListener {
//            val statNumber = (0..3).random()
//            when (statNumber) {
//                0 -> StatsManager.addToStat(this, "physique")
//                1 -> StatsManager.addToStat(this, "intelligence")
//                2 -> StatsManager.addToStat(this, "creativity")
//                3 -> StatsManager.addToStat(this, "charisma")
//                else -> Toast.makeText(
//                    this,
//                    "Something went wrong with adding to stat",
//                    Toast.LENGTH_LONG
//                ).show()
//            }
//            updateAllTexts()
//        }

    }
    private fun updateAllTexts() {
        val stats = StatsManager.getAllStats(this)
        physique.text = "Телосложение: ${stats["physique"]}"
        intelligenceText.text = "Интеллект: ${stats["intelligence"]}"
        creativityText.text = "Творчество: ${stats["creativity"]}"
        charismaText.text = "Харизма: ${stats["charisma"]}"
    }
}