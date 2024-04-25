package com.example.opscpoe.bottomSheetFragment

import android.R
import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Build
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TimePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.Unbinder
import com.example.opscpoe.Activity.Add_Task
import com.example.opscpoe.database.DatabaseClient
import com.example.opscpoe.model.Task
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.Calendar
import java.util.GregorianCalendar


class CreateTask_BottomSheetFragment : BottomSheetDialogFragment() {
    var unbinder: Unbinder? = null

    @BindView(R.id.addTaskTitle)
    var addTaskTitle: EditText? = null

    @BindView(R.id.addTaskDescription)
    var addTaskDescription: EditText? = null

    @BindView(R.id.taskDate)
    var taskDate: EditText? = null

    @BindView(R.id.taskTime)
    var taskTime: EditText? = null

    @BindView(R.id.taskEvent)
    var taskEvent: EditText? = null

    @BindView(R.id.addTask)
    var addTask: Button? = null
    var taskId = 0
    var isEdit = false
    var task: Task? = null
    var mYear = 0
    var mMonth = 0
    var mDay = 0
    var mHour = 0
    var mMinute = 0
    var setRefreshListener: setRefreshListener? = null
    var alarmManager: AlarmManager? = null
    var timePickerDialog: TimePickerDialog? = null
    var datePickerDialog: DatePickerDialog? = null
    var activity: Add_Task? = null
    private val mBottomSheetBehaviorCallback: BottomSheetCallback = object : BottomSheetCallback() {
        override fun onStateChanged(bottomSheet: View, newState: Int) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                dismiss()
            }
        }

        override fun onSlide(bottomSheet: View, slideOffset: Float) {}
    }

    fun setTaskId(
        taskId: Int,
        isEdit: Boolean,
        setRefreshListener: setRefreshListener?,
        activity: Add_Task?
    ) {
        this.taskId = taskId
        this.isEdit = isEdit
        this.activity = activity
        this.setRefreshListener = setRefreshListener
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("RestrictedApi", "ClickableViewAccessibility")
    override fun setupDialog(dialog: Dialog, style: Int) {
        super.setupDialog(dialog, style)
        val contentView = View.inflate(context, R.layout.activitty_create_timesheet, null)
        unbinder = ButterKnife.bind(this, contentView)
        dialog.setContentView(contentView)
        alarmManager = getActivity()!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        addTask!!.setOnClickListener { view: View? -> if (validateFields()) createTask() }
        if (isEdit) {
            showTaskFromId()
        }
        taskDate!!.setOnTouchListener { view: View?, motionEvent: MotionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_UP) {
                val c = Calendar.getInstance()
                mYear = c[Calendar.YEAR]
                mMonth = c[Calendar.MONTH]
                mDay = c[Calendar.DAY_OF_MONTH]
                datePickerDialog = DatePickerDialog(
                    getActivity()!!,
                    { view1: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                        taskDate!!.setText(dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)
                        datePickerDialog!!.dismiss()
                    }, mYear, mMonth, mDay
                )
                datePickerDialog!!.datePicker.minDate = System.currentTimeMillis() - 1000
                datePickerDialog!!.show()
            }
            true
        }
        taskTime!!.setOnTouchListener { view: View?, motionEvent: MotionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_UP) {
                // Get Current Time
                val c = Calendar.getInstance()
                mHour = c[Calendar.HOUR_OF_DAY]
                mMinute = c[Calendar.MINUTE]

                // Launch Time Picker Dialog
                timePickerDialog = TimePickerDialog(
                    getActivity(),
                    { view12: TimePicker?, hourOfDay: Int, minute: Int ->
                        taskTime!!.setText("$hourOfDay:$minute")
                        timePickerDialog!!.dismiss()
                    }, mHour, mMinute, false
                )
                timePickerDialog!!.show()
            }
            true
        }
    }

    fun validateFields(): Boolean {
        return if (addTaskTitle!!.getText().toString().equals("", ignoreCase = true)) {
            Toast.makeText(activity, "Please enter a valid title", Toast.LENGTH_SHORT).show()
            false
        } else if (addTaskDescription!!.getText().toString().equals("", ignoreCase = true)) {
            Toast.makeText(activity, "Please enter a valid description", Toast.LENGTH_SHORT).show()
            false
        } else if (taskDate!!.getText().toString().equals("", ignoreCase = true)) {
            Toast.makeText(activity, "Please enter date", Toast.LENGTH_SHORT).show()
            false
        } else if (taskTime!!.getText().toString().equals("", ignoreCase = true)) {
            Toast.makeText(activity, "Please enter time", Toast.LENGTH_SHORT).show()
            false
        } else if (taskEvent!!.getText().toString().equals("", ignoreCase = true)) {
            Toast.makeText(activity, "Please enter an event", Toast.LENGTH_SHORT).show()
            false
        } else {
            true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    private fun createTask() {
        class saveTaskInBackend : AsyncTask<Void?, Void?, Void?>() {
            @SuppressLint("WrongThread")
            protected override fun doInBackground(vararg voids: Void): Void? {
                val createTask = Task()
                createTask.setTaskTitle(addTaskTitle!!.getText().toString())
                createTask.setTaskDescrption(addTaskDescription!!.getText().toString())
                createTask.setDate(taskDate!!.getText().toString())
                createTask.setLastAlarm(taskTime!!.getText().toString())
                createTask.setEvent(taskEvent!!.getText().toString())
                if (!isEdit) DatabaseClient.getInstance(getActivity()).getAppDatabase()
                    .dataBaseAction()
                    .insertDataIntoTaskList(createTask) else DatabaseClient.getInstance(getActivity())
                    .getAppDatabase()
                    .dataBaseAction()
                    .updateAnExistingRow(
                        taskId, addTaskTitle!!.getText().toString(),
                        addTaskDescription!!.getText().toString(),
                        taskDate!!.getText().toString(),
                        taskTime!!.getText().toString(),
                        taskEvent!!.getText().toString()
                    )
                return null
            }

            override fun onPostExecute(aVoid: Void?) {
                super.onPostExecute(aVoid)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    createAnAlarm()
                }
                setRefreshListener!!.refresh()
                Toast.makeText(getActivity(), "Your event is been added", Toast.LENGTH_SHORT).show()
                dismiss()
            }
        }

        val st = saveTaskInBackend()
        st.execute()
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    fun createAnAlarm() {
        try {
            val items1 =
                taskDate!!.getText().toString().split("-".toRegex()).dropLastWhile { it.isEmpty() }
                    .toTypedArray()
            val dd = items1[0]
            val month = items1[1]
            val year = items1[2]
            val itemTime =
                taskTime!!.getText().toString().split(":".toRegex()).dropLastWhile { it.isEmpty() }
                    .toTypedArray()
            val hour = itemTime[0]
            val min = itemTime[1]
            val cur_cal: Calendar = GregorianCalendar()
            cur_cal.setTimeInMillis(System.currentTimeMillis())
            val cal: Calendar = GregorianCalendar()
            cal[Calendar.HOUR_OF_DAY] = hour.toInt()
            cal[Calendar.MINUTE] = min.toInt()
            cal[Calendar.SECOND] = 0
            cal[Calendar.MILLISECOND] = 0
            cal[Calendar.DATE] = dd.toInt()
            val alarmIntent: Intent = Intent(
                activity,
                AlarmBroadcastReceiver::class.java
            )
            alarmIntent.putExtra("TITLE", addTaskTitle!!.getText().toString())
            alarmIntent.putExtra("DESC", addTaskDescription!!.getText().toString())
            alarmIntent.putExtra("DATE", taskDate!!.getText().toString())
            alarmIntent.putExtra("TIME", taskTime!!.getText().toString())
            val pendingIntent = PendingIntent.getBroadcast(
                activity,
                count,
                alarmIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager!!.setAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    cal.getTimeInMillis(),
                    pendingIntent
                )
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    alarmManager!!.setExact(
                        AlarmManager.RTC_WAKEUP,
                        cal.getTimeInMillis(),
                        pendingIntent
                    )
                } else {
                    alarmManager!![AlarmManager.RTC_WAKEUP, cal.getTimeInMillis()] = pendingIntent
                }
                count++
                val intent = PendingIntent.getBroadcast(activity, count, alarmIntent, 0)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    alarmManager!!.setAndAllowWhileIdle(
                        AlarmManager.RTC_WAKEUP,
                        cal.getTimeInMillis() - 600000,
                        intent
                    )
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        alarmManager!!.setExact(
                            AlarmManager.RTC_WAKEUP,
                            cal.getTimeInMillis() - 600000,
                            intent
                        )
                    } else {
                        alarmManager!![AlarmManager.RTC_WAKEUP, cal.getTimeInMillis() - 600000] =
                            intent
                    }
                }
                count++
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun showTaskFromId() {
        class showTaskFromId : AsyncTask<Void?, Void?, Void?>() {
            @SuppressLint("WrongThread")
            protected override fun doInBackground(vararg voids: Void): Void? {
                task = DatabaseClient.getInstance(getActivity()).getAppDatabase()
                    .dataBaseAction().selectDataFromAnId(taskId)
                return null
            }

            override fun onPostExecute(aVoid: Void?) {
                super.onPostExecute(aVoid)
                setDataInUI()
            }
        }

        val st = showTaskFromId()
        st.execute()
    }

    private fun setDataInUI() {
        addTaskTitle.setText(task.getTaskTitle())
        addTaskDescription.setText(task.getTaskDescrption())
        taskDate.setText(task.getDate())
        taskTime.setText(task.getLastAlarm())
        taskEvent.setText(task.getEvent())
    }

    interface setRefreshListener {
        fun refresh()
    }

    companion object {
        var count = 0
    }
}