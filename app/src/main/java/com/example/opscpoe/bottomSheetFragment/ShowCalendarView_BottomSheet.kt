package com.example.opscpoe.bottomSheetFragment

import android.R
import android.annotation.SuppressLint
import android.app.Dialog
import android.os.AsyncTask
import android.os.Build
import android.view.View
import android.widget.ImageView
import androidx.annotation.RequiresApi
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.Unbinder
import com.applandeo.materialcalendarview.CalendarView
import com.applandeo.materialcalendarview.EventDay
import com.example.opscpoe.Activity.Add_Task
import com.example.opscpoe.database.DatabaseClient
import com.example.opscpoe.model.Task
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.Calendar


class ShowCalendarView_BottomSheet : BottomSheetDialogFragment() {
    var unbinder: Unbinder? = null
    var activity: Add_Task? = null

    @BindView(R.id.back)
    var back: ImageView? = null

    @BindView(R.id.calendarView)
    var calendarView: CalendarView? = null
    var tasks: List<Task> = ArrayList()
    private val mBottomSheetBehaviorCallback: BottomSheetCallback = object : BottomSheetCallback() {
        override fun onStateChanged(bottomSheet: View, newState: Int) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                dismiss()
            }
        }

        override fun onSlide(bottomSheet: View, slideOffset: Float) {}
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("RestrictedApi", "ClickableViewAccessibility")
    override fun setupDialog(dialog: Dialog, style: Int) {
        super.setupDialog(dialog, style)
        val contentView = View.inflate(context, R.layout.activity_calendar, null)
        unbinder = ButterKnife.bind(this, contentView)
        dialog.setContentView(contentView)
        savedTasks
        back!!.setOnClickListener { view: View? -> dialog.dismiss() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    private val savedTasks: Unit
        private get() {
            class GetSavedTasks :
                AsyncTask<Void?, Void?, List<Task>>() {
                protected override fun doInBackground(vararg voids: Void): List<Task> {
                    tasks = DatabaseClient
                        .getInstance(getActivity())
                        .getAppDatabase()
                        .dataBaseAction()
                        .getAllTasksList()
                    return tasks
                }

                override fun onPostExecute(tasks: List<Task>) {
                    super.onPostExecute(tasks)
                    calendarView!!.setEvents(highlitedDays)
                }
            }

            val savedTasks = GetSavedTasks()
            savedTasks.execute()
        }
    val highlitedDays: List<EventDay>
        get() {
            val events: MutableList<EventDay> = ArrayList()
            for (i in tasks.indices) {
                val calendar = Calendar.getInstance()
                val items1: Array<String> = tasks[i].getDate().split("-")
                val dd = items1[0]
                val month = items1[1]
                val year = items1[2]
                calendar[Calendar.DAY_OF_MONTH] = dd.toInt()
                calendar[Calendar.MONTH] = month.toInt() - 1
                calendar[Calendar.YEAR] = year.toInt()
                events.add(EventDay(calendar, R.drawable.dot))
            }
            return events
        }
}