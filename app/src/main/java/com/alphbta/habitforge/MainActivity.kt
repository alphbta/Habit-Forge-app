package com.alphbta.habitforge

import android.graphics.Rect
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
import android.widget.FrameLayout
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts


class MainActivity : AppCompatActivity() {
    private lateinit var physique: TextView
    private lateinit var intelligenceText: TextView
    private lateinit var creativityText: TextView
    private lateinit var charismaText: TextView
    private lateinit var taskAdapter: TaskAdapter
    private lateinit var habitAdapter: HabitAdapter
    private lateinit var regularAdapter: RegularAdapter
    private lateinit var menuOverlay: FrameLayout
    private lateinit var menuLayout: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val menuIcon = findViewById<ImageView>(R.id.menu)
        menuOverlay = findViewById(R.id.menuOverlay)
        menuLayout = findViewById(R.id.navigationMenu)

        menuIcon.setOnClickListener {
            openMenu()
        }

        menuOverlay.setOnClickListener {
            closeMenu()
        }

        val db = DbHelper.getInstance(this)
        HabitRepository(db).checkHabitsForReset(this)
        val tasks = TaskRepository(db).getAllTasks()
        val tasksRecyclerView: RecyclerView = findViewById(R.id.taskList)
        val habits = HabitRepository(db).getAllHabits()
        val habitsRecyclerView: RecyclerView = findViewById(R.id.habitsList)
        val regulars = RegularRepository(db).getAllRegulars()
        val regularsRecyclerView: RecyclerView = findViewById(R.id.regularList)

        taskAdapter = TaskAdapter(tasks, this) { task: Task ->
            completeTask(task)
        }

        habitAdapter = HabitAdapter(habits, this) { habit: Habit ->
            completeHabit(habit)
        }

        regularAdapter = RegularAdapter(regulars, this) { regular: Regular ->
            completeRegular(regular)
        }

        tasksRecyclerView.layoutManager = LinearLayoutManager(this)
        tasksRecyclerView.adapter = taskAdapter

        habitsRecyclerView.layoutManager = LinearLayoutManager(this)
        habitsRecyclerView.adapter = habitAdapter

        regularsRecyclerView.layoutManager = LinearLayoutManager(this)
        regularsRecyclerView.adapter = regularAdapter

        tasksRecyclerView.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                val position = parent.getChildAdapterPosition(view)
                if (position == 0) outRect.top = 0
                outRect.bottom = 32
            }
        })
        val regularTaskList: RecyclerView = findViewById(R.id.regularList)

        regularTaskList.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                val position = parent.getChildAdapterPosition(view)
                if (position == 0) outRect.top = 0
                outRect.bottom = 32
            }
        })
        habitsRecyclerView.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                val position = parent.getChildAdapterPosition(view)
                if (position == 0) outRect.top = 0
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

        val addRegular: ImageView = findViewById(R.id.addRegularTask)
        val addRegularLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                regularAdapter.updateRegulars(RegularRepository(db).getAllRegulars())
            }
        }

        addRegular.setOnClickListener {
            intent = Intent(this, AddRegularActivity::class.java)
            addRegularLauncher.launch(intent)
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

        val menuAccount = findViewById<TextView>(R.id.menuAccount)
        menuAccount.setOnClickListener {
            val intent = Intent(this, AccountActivity::class.java)
            startActivity(intent)
            menuOverlay.visibility = View.GONE
        }
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
        val complete = habitRepository.completeHabit(this, habit)
        if (!complete) {
            Toast.makeText(
                this,
                "Произошла ошибка с выполнением",
                Toast.LENGTH_LONG
            ).show()
        }

        habitAdapter.updateHabits(habitRepository.getAllHabits())
    }

    private fun completeRegular(regular: Regular) {
        val db = DbHelper.getInstance(this)
        val regularRepository = RegularRepository(db)
        val delete = regularRepository.deleteRegular(regular.id.toString())
        if (!delete) {
            Toast.makeText(
                this,
                "Произошла ошибка с удалением",
                Toast.LENGTH_LONG
            ).show()
        }

        regularAdapter.updateRegulars(regularRepository.getAllRegulars())
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

    private fun openMenu() {
        menuOverlay.visibility = View.VISIBLE
    }

    private fun closeMenu() {
        menuOverlay.visibility = View.GONE
    }
}
