package com.alphbta.habitforge

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
import androidx.activity.result.contract.ActivityResultContracts
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity() {
    private val addTaskLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val newTask = result.data?.getStringExtra("new_task")
            if (!newTask.isNullOrEmpty()) {
                taskList.add(newTask)
                taskAdapter.notifyItemInserted(taskList.size - 1)
            }
        }
    }
    private lateinit var humanHealthText: TextView
    private lateinit var intelligenceText: TextView
    private lateinit var creativityText: TextView
    private lateinit var charismaText: TextView
    private var isTasksExpanded = false
    private lateinit var tasksRecyclerView: RecyclerView
    private lateinit var taskAdapter: TaskAdapter
    private val taskList = mutableListOf("Прочитать книгу", "Сделать зарядку")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tasksHeader = findViewById<TextView>(R.id.tasksHeader)
        val tasksContent = findViewById<LinearLayout>(R.id.tasksContent)
        tasksRecyclerView = findViewById(R.id.tasksRecyclerView)

        // RecyclerView setup
        taskAdapter = TaskAdapter(taskList)
        tasksRecyclerView.layoutManager = LinearLayoutManager(this)
        tasksRecyclerView.adapter = taskAdapter

        tasksHeader.setOnClickListener {
            isTasksExpanded = !isTasksExpanded
            tasksContent.visibility = if (isTasksExpanded) View.VISIBLE else View.GONE
            tasksHeader.text = if (isTasksExpanded) "Задачи ▲" else "Задачи ▼"
        }
        val inputTask = findViewById<EditText>(R.id.inputTask)
        val addTaskButton = findViewById<Button>(R.id.addTaskButton)

        addTaskButton.setOnClickListener {
            val newTask = inputTask.text.toString().trim()
            if (newTask.isNotEmpty()) {
                taskList.add(newTask)
                taskAdapter.notifyItemInserted(taskList.size - 1)
                inputTask.text.clear()
                tasksRecyclerView.scrollToPosition(taskList.size - 1)
            }
        }
        val addNewTaskButton = findViewById<Button>(R.id.addNewTaskButton)
        addNewTaskButton.setOnClickListener {
            val intent = Intent(this, AddTaskActivity::class.java)
            addTaskLauncher.launch(intent)
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tempButton: FloatingActionButton = findViewById(R.id.tempButton)
        humanHealthText = findViewById(R.id.humanHealth)
        intelligenceText = findViewById(R.id.intelligence)
        creativityText = findViewById(R.id.creativity)
        charismaText = findViewById(R.id.charisma)
        updateAllTexts()

        tempButton.setOnClickListener {
            val statNumber = (0..3).random()
            when (statNumber) {
                0 -> StatsManager.addToStat(this, "human_health")
                1 -> StatsManager.addToStat(this, "intelligence")
                2 -> StatsManager.addToStat(this, "creativity")
                3 -> StatsManager.addToStat(this, "charisma")
                else -> Toast.makeText(
                    this,
                    "Something went wrong with adding to stat",
                    Toast.LENGTH_LONG
                ).show()
            }
            updateAllTexts()
        }

    }
    private fun updateAllTexts() {
        val stats = StatsManager.getAllStats(this)
        humanHealthText.text = "Здоровье: ${stats["human_health"]}"
        intelligenceText.text = "Интеллект: ${stats["intelligence"]}"
        creativityText.text = "Творчество: ${stats["creativity"]}"
        charismaText.text = "Харизма: ${stats["charisma"]}"
    }
}