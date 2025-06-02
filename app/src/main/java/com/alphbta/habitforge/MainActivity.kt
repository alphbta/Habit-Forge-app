package com.alphbta.habitforge

import android.graphics.Rect
import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.ImageView
import android.content.Intent
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var physique: TextView
    private lateinit var intelligenceText: TextView
    private lateinit var creativityText: TextView
    private lateinit var charismaText: TextView
    private var isTasksExpanded = true
    private lateinit var taskAdapter: TaskAdapter
    private lateinit var habitAdapter: HabitAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = DbHelper.getInstance(this)
        val tasks = TaskRepository(db).getAllTasks()
        val tasksRecyclerView: RecyclerView = findViewById(R.id.taskList)
        val habits = HabitRepository(db).getAllHabits()
        val habitsRecyclerView: RecyclerView = findViewById(R.id.habitsList)

        taskAdapter = TaskAdapter(tasks, this) { task: Task ->
            completeTask(task)
        }

        habitAdapter = HabitAdapter(habits, this) { habit: Habit ->
            completeHabit(habit)
        }

        tasksRecyclerView.layoutManager = LinearLayoutManager(this)
        tasksRecyclerView.adapter = taskAdapter

        habitsRecyclerView.layoutManager = LinearLayoutManager(this)
        habitsRecyclerView.adapter = habitAdapter

        tasksRecyclerView.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                val position = parent.getChildAdapterPosition(view)
                if (position == 0) {
                    outRect.top = 0
                }
                outRect.bottom = 32
            }
        })

        val addTask: ImageView = findViewById(R.id.addTask)
        val addTaskLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                taskAdapter.updateTasks(TaskRepository(db).getAllTasks())
            }
        }

        addTask.setOnClickListener {
            intent = Intent(this, AddTaskActivity::class.java)
            addTaskLauncher.launch(intent)
        }

        val addHabit: ImageView = findViewById(R.id.addHabits)
        val addHabitLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                habitAdapter.updateHabits(HabitRepository(db).getAllHabits())
            }
        }

        addHabit.setOnClickListener {
            intent = Intent(this, AddHabitActivity::class.java)
            addHabitLauncher.launch(intent)
        }

        var isTasksExpanded = false
        var isRegularExpanded = false
        var isHabitsExpanded = false

        val tasksContent = findViewById<LinearLayout>(R.id.tasksContent)
        val toggleTasksIcon = findViewById<ImageView>(R.id.toggleTasks)
        tasksContent.visibility = View.GONE
        toggleTasksIcon.setImageResource(R.drawable.ic_expand_more)

        toggleTasksIcon.setOnClickListener {
            isTasksExpanded = !isTasksExpanded
            if (isTasksExpanded) {
                tasksContent.expand()
                toggleTasksIcon.setImageResource(R.drawable.ic_expand_less)
            } else {
                tasksContent.collapse()
                toggleTasksIcon.setImageResource(R.drawable.ic_expand_more)
            }
        }

        val regularContent = findViewById<LinearLayout>(R.id.regularContent)
        val toggleRegular = findViewById<ImageView>(R.id.toggleRegular)
        regularContent.visibility = View.GONE
        toggleRegular.setImageResource(R.drawable.ic_expand_more)

        toggleRegular.setOnClickListener {
            isRegularExpanded = !isRegularExpanded
            if (isRegularExpanded) {
                regularContent.expand()
                toggleRegular.setImageResource(R.drawable.ic_expand_less)
            } else {
                regularContent.collapse()
                toggleRegular.setImageResource(R.drawable.ic_expand_more)
            }
        }

        val habitsContent = findViewById<LinearLayout>(R.id.habitsContent)
        val toggleHabits = findViewById<ImageView>(R.id.toggleHabits)
        habitsContent.visibility = View.GONE
        toggleHabits.setImageResource(R.drawable.ic_expand_more)

        toggleHabits.setOnClickListener {
            isHabitsExpanded = !isHabitsExpanded
            if (isHabitsExpanded) {
                habitsContent.expand()
                toggleHabits.setImageResource(R.drawable.ic_expand_less)
            } else {
                habitsContent.collapse()
                toggleHabits.setImageResource(R.drawable.ic_expand_more)
            }
        }

        // physique = findViewById(R.id.physique)
        // intelligenceText = findViewById(R.id.intelligence)
        // creativityText = findViewById(R.id.creativity)
        // charismaText = findViewById(R.id.charisma)
        // updateAllTexts()
    }

    private fun updateAllTexts() {
        val stats = StatsManager.getAllStats(this)
        physique.text = "Телосложение: ${stats["physique"]}"
        intelligenceText.text = "Интеллект: ${stats["intelligence"]}"
        creativityText.text = "Творчество: ${stats["creativity"]}"
        charismaText.text = "Харизма: ${stats["charisma"]}"
    }

    private fun completeTask(task: Task) {
        val db = DbHelper.getInstance(this)
        val taskRepository = TaskRepository(db)
        val delete = taskRepository.deleteTask(task.id.toString())
        if (!delete) {
            Toast.makeText(
                this,
                "Произошла ошибка с удалением",
                Toast.LENGTH_LONG
            ).show()
        }

        taskAdapter.updateTasks(taskRepository.getAllTasks())
    }

    private fun completeHabit(habit: Habit) {
        val db = DbHelper.getInstance(this)
        val habitRepository = HabitRepository(db)
        val delete = habitRepository.deleteHabit(habit.id.toString())
        if (!delete) {
            Toast.makeText(
                this,
                "Произошла ошибка с удалением",
                Toast.LENGTH_LONG
            ).show()
        }

        habitAdapter.updateHabits(habitRepository.getAllHabits())
    }

    private fun View.expand(duration: Long = 200) {
        this.visibility = View.VISIBLE
        this.alpha = 0f
        this.translationY = -20f
        animate()
            .alpha(1f)
            .translationY(0f)
            .setDuration(duration)
            .start()
    }

    private fun View.collapse(duration: Long = 200) {
        animate()
            .alpha(0f)
            .translationY(-20f)
            .setDuration(duration)
            .withEndAction {
                this.visibility = View.GONE
            }
            .start()
    }
}
