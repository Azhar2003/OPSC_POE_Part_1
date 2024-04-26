package com.example.opscpoe.bottomSheetFragment

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.AsyncTask
import android.os.Build
import android.view.View
import android.widget.ImageView
import androidx.annotation.NonNull
import androidx.annotation.RequiresApi
import butterknife.ButterKnife
import butterknife.Unbinder
import com.applandeo.materialcalendarview.CalendarView
import com.applandeo.materialcalendarview.EventDay
import com.example.opscpoe.R
import com.example.opscpoe.Activity.Add_Task
import com.example.opscpoe.database.DatabaseClient
import com.example.opscpoe.model.Task
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.*
import kotlin.collections.ArrayList

class ShowCalendarView_BottomSheet : BottomSheetDialogFragment() {

    private var unbinder: Unbinder? = null
    private lateinit var Activity: Add_Task
    private lateinit var back: ImageView
    private lateinit var calendarView: CalendarView
    private var tasks: MutableList<Task> = ArrayList()

    private val mBottomSheetBehaviorCallback = object : BottomSheetBehavior.BottomSheetCallback() {
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
        calendarView.setHeaderColor(R.color.colorAccent)
        getSavedTasks()
        back.setOnClickListener { dialog.dismiss() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        unbinder?.unbind()
    }

    private fun getSavedTasks() {
        object : AsyncTask<Void?, Void?, List<Task>?>() {
            override fun doInBackground(vararg voids: Void?): List<Task>? {
                tasks = DatabaseClient.getInstance(Activity)
                    ?.appDatabase?.dataBaseAction()
                    ?.getAllTasksList() as MutableList<Task>
                return tasks
            }

            override fun onPostExecute(tasks: List<Task>?) {
                super.onPostExecute(tasks)
                calendarView.setEvents(highlightedDays)
            }
        }.execute()
    }

    private val highlightedDays: List<EventDay>
        get() {
            val events: MutableList<EventDay> = ArrayList()
            for (i in tasks.indices) {
                val calendar = Calendar.getInstance()
                val items1 = tasks[i].date.split("-".toRegex()).toTypedArray()
                val dd = items1.get(0)
                val month = items1.get(1)
                val year = items1.get(2)
                if (dd != null) {
                    calendar[Calendar.DAY_OF_MONTH] = dd.toInt()
                }
                if (month != null) {
                    calendar[Calendar.MONTH] = month.toInt() - 1
                }
                if (year != null) {
                    calendar[Calendar.YEAR] = year.toInt()
                }
                events.add(EventDay(calendar, R.drawable.dot))
            }
            return events
        }
}
