package com.example.opscpoe.Activity

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.opscpoe.R
import com.example.opscpoe.adapter.Task_adapter
import com.example.opscpoe.bottomSheetFragment.CreateTask_BottomSheetFragment
import com.example.opscpoe.bottomSheetFragment.ShowCalendarView_BottomSheet
import com.example.opscpoe.database.DatabaseClient
import com.example.opscpoe.model.Task
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.floatingactionbutton.FloatingActionButton

class Add_Task : AppCompatActivity(), CreateTask_BottomSheetFragment.SetRefreshListener {

    private lateinit var taskRecycler: RecyclerView
    private lateinit var addTask: TextView
    private lateinit var taskAdapter: Task_adapter
    private var tasks: List<Task> = ArrayList()
    private lateinit var noDataImage: ImageView
    private lateinit var calendar: ImageView
    private lateinit var imageView: ImageView
    private lateinit var button: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)
        setUpViews()
        setUpAdapter()
        addTask.setOnClickListener {
            val createTaskBottomSheetFragment = CreateTask_BottomSheetFragment()
            createTaskBottomSheetFragment.setTaskId(0, false, this)
            createTaskBottomSheetFragment.show(supportFragmentManager, createTaskBottomSheetFragment.tag)
        }
        getSavedTasks()
        calendar.setOnClickListener {
            val showCalendarViewBottomSheet = ShowCalendarView_BottomSheet()
            showCalendarViewBottomSheet.show(supportFragmentManager, showCalendarViewBottomSheet.tag)
        }

    }

    private fun setUpViews() {
        taskRecycler = findViewById(R.id.taskRecycler)
        addTask = findViewById(R.id.addTask)
        noDataImage = findViewById(R.id.noDataImage)
        calendar = findViewById(R.id.calendar)
    }

    private fun setUpAdapter() {
        taskAdapter = Task_adapter(this, tasks, this)
        taskRecycler.layoutManager = LinearLayoutManager(applicationContext)
        taskRecycler.adapter = taskAdapter
    }

    private fun getSavedTasks() {
        class GetSavedTasks : AsyncTask<Void, Void, List<Task>>() {
            override fun doInBackground(vararg voids: Void): List<Task>? {
                return DatabaseClient.getInstance(applicationContext)
                    .appDatabase
                    .dataBaseAction()
                    .getAllTasksList()
            }

            override fun onPostExecute(tasks: List<Task>) {
                super.onPostExecute(tasks)
                noDataImage.visibility = if (tasks.isEmpty()) View.VISIBLE else View.GONE
                setUpAdapter()
            }
        }
        val savedTasks = GetSavedTasks()
        savedTasks.execute()
    }

    override fun refresh() {
        getSavedTasks()
    }
    fun onCreate2(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_timesheet)

        supportActionBar!!.setBackgroundDrawable(ColorDrawable(Color.BLUE))

        imageView = findViewById(R.id.imageView2)
        button = findViewById(R.id.floatingActionButton)

        button.setOnClickListener {
            ImagePicker.with(this)
                .crop()                     //Crop image(Optional), Check Customization for more option
                .compress(1024)             //Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)  //Final image resolution will be less than 1080 x 1080(Optional)
                .start()
        }

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        imageView.setImageURI(data?.data)
    }

}
