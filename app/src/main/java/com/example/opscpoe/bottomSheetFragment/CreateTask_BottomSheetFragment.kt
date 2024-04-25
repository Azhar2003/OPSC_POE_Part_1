package com.example.opscpoe.bottomSheetFragment

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import butterknife.ButterKnife
import butterknife.Unbinder
import com.example.opscpoe.R
import com.example.opscpoe.Activity.Add_Task
import com.example.opscpoe.database.DatabaseClient
import com.example.opscpoe.model.Task
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.text.SimpleDateFormat
import java.util.*

class CreateTask_BottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var unbinder: Unbinder
    private lateinit var addTaskTitle: EditText
    private lateinit var addTaskDescription: EditText
    private lateinit var taskDate: EditText
    private lateinit var taskTime: EditText
    private lateinit var taskEvent: EditText
    private lateinit var addTask: Button
    private var taskId: Int = 0
    private var isEdit: Boolean = false
    private lateinit var task: Task
    private var mYear: Int = 0
    private var mMonth: Int = 0
    private var mDay: Int = 0
    private var mHour: Int = 0
    private var mMinute: Int = 0
    private lateinit var setRefreshListener: SetRefreshListener
    private lateinit var timePickerDialog: TimePickerDialog
    private lateinit var datePickerDialog: DatePickerDialog
    private lateinit var Activity: Add_Task
    private var count: Int = 0

    private val mBottomSheetBehaviorCallback = object : BottomSheetBehavior.BottomSheetCallback() {
        override fun onStateChanged(bottomSheet: View, newState: Int) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                dismiss()
            }
        }

        override fun onSlide(bottomSheet: View, slideOffset: Float) {}
    }

    fun setTaskId(taskId: Int, isEdit: Boolean, setRefreshListener: SetRefreshListener, activity: Add_Task) {
        this.taskId = taskId
        this.isEdit = isEdit
        this.Activity = activity
        this.setRefreshListener = setRefreshListener
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("RestrictedApi", "ClickableViewAccessibility")
    override fun setupDialog(dialog: Dialog, style: Int) {
        super.setupDialog(dialog, style)
        val contentView = View.inflate(context, R.layout.activity_create_timesheet, null)
        unbinder = ButterKnife.bind(this, contentView)
        dialog.setContentView(contentView)
        addTask.setOnClickListener {
            if (validateFields()) createTask()
        }
        if (isEdit) showTaskFromId()

        taskDate.setOnTouchListener { view, motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_UP) {
                val c = Calendar.getInstance()
                mYear = c[Calendar.YEAR]
                mMonth = c[Calendar.MONTH]
                mDay = c[Calendar.DAY_OF_MONTH]
                datePickerDialog = DatePickerDialog(
                    Activity,
                    DatePickerDialog.OnDateSetListener { view1, year, monthOfYear, dayOfMonth ->
                        taskDate.setText("$dayOfMonth-${monthOfYear + 1}-$year")
                        datePickerDialog.dismiss()
                    },
                    mYear, mMonth, mDay
                )
                datePickerDialog.datePicker.minDate = System.currentTimeMillis() - 1000
                datePickerDialog.show()
            }
            true
        }

        taskTime.setOnTouchListener { view, motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_UP) {
                val c = Calendar.getInstance()
                mHour = c[Calendar.HOUR_OF_DAY]
                mMinute = c[Calendar.MINUTE]
                timePickerDialog = TimePickerDialog(
                    activity,
                    TimePickerDialog.OnTimeSetListener { view12, hourOfDay, minute ->
                        taskTime.setText("$hourOfDay:$minute")
                        timePickerDialog.dismiss()
                    }, mHour, mMinute, false
                )
                timePickerDialog.show()
            }
            true
        }
    }

    private fun validateFields(): Boolean {
        if (addTaskTitle.text.toString().isEmpty()) {
            Toast.makeText(activity, "Please enter a valid title", Toast.LENGTH_SHORT).show()
            return false
        } else if (addTaskDescription.text.toString().isEmpty()) {
            Toast.makeText(activity, "Please enter a valid description", Toast.LENGTH_SHORT).show()
            return false
        } else if (taskDate.text.toString().isEmpty()) {
            Toast.makeText(activity, "Please enter date", Toast.LENGTH_SHORT).show()
            return false
        } else if (taskTime.text.toString().isEmpty()) {
            Toast.makeText(activity, "Please enter time", Toast.LENGTH_SHORT).show()
            return false
        } else if (taskEvent.text.toString().isEmpty()) {
            Toast.makeText(activity, "Please enter an event", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun createTask() {
        object : AsyncTask<Void, Void, Void>() {
            @SuppressLint("WrongThread")
            override fun doInBackground(vararg voids: Void): Void? {
                val createTask = Task()
                createTask.taskDescrption = addTaskDescription.text.toString()
                createTask.date = taskDate.text.toString()
                createTask.lastAlarm = taskTime.text.toString()
                createTask.event = taskEvent.text.toString()
                if (!isEdit) {
                    DatabaseClient.getInstance(Activity)?.appDatabase
                        ?.dataBaseAction()
                        ?.insertDataIntoTaskList(createTask)
                } else {
                    DatabaseClient.getInstance(Activity)?.appDatabase
                        ?.dataBaseAction()
                        ?.updateAnExistingRow(
                            taskId, addTaskTitle.text.toString(),
                            addTaskDescription.text.toString(),
                            taskDate.text.toString(),
                            taskTime.text.toString(),
                            taskEvent.text.toString()
                        )
                }
                return null
            }

            override fun onPostExecute(aVoid: Void?) {
                super.onPostExecute(aVoid)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                }
                setRefreshListener.refresh()
                Toast.makeText(Activity, "Your event is been added", Toast.LENGTH_SHORT).show()
                dismiss()
            }
        }.execute()
    }


    private fun showTaskFromId() {
        object : AsyncTask<Void, Void, Void>() {
            @SuppressLint("WrongThread")
            override fun doInBackground(vararg voids: Void): Void? {
                task = DatabaseClient.getInstance(Activity)?.appDatabase
                    ?.dataBaseAction()?.selectDataFromAnId(taskId)!!
                return null
            }

            override fun onPostExecute(aVoid: Void?) {
                super.onPostExecute(aVoid)
                setDataInUI()
            }
        }.execute()
    }

    private fun setDataInUI() {
        addTaskTitle.setText(task.taskTitle)
        addTaskDescription.setText(task.taskDescrption)
        taskDate.setText(task.date)
        taskTime.setText(task.lastAlarm)
        taskEvent.setText(task.event)
    }

    interface SetRefreshListener {
        fun refresh()
    }
}
