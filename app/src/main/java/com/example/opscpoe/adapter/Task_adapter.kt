package com.example.opscpoe.adapter

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.AsyncTask
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.opscpoe.R
import com.example.opscpoe.Activity.Add_Task
import com.example.opscpoe.bottomSheetFragment.CreateTask_BottomSheetFragment
import com.example.opscpoe.database.DatabaseClient
import com.example.opscpoe.model.Task
import java.text.SimpleDateFormat
import java.util.*

class Task_adapter(
    private val context: Add_Task,
    private val taskList: List<Task>,
    private val setRefreshListener: CreateTask_BottomSheetFragment.setRefreshListener
) : RecyclerView.Adapter<Task_adapter.TaskViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private val dateFormat = SimpleDateFormat("EE dd MMM yyyy", Locale.US)
    private val inputDateFormat = SimpleDateFormat("dd-M-yyyy", Locale.US)

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val day: TextView = itemView.findViewById(R.id.day)
        val date: TextView = itemView.findViewById(R.id.date)
        val month: TextView = itemView.findViewById(R.id.month)
        val title: TextView = itemView.findViewById(R.id.title)
        val description: TextView = itemView.findViewById(R.id.description)
        val status: TextView = itemView.findViewById(R.id.status)
        val options: ImageView = itemView.findViewById(R.id.options)
        val time: TextView = itemView.findViewById(R.id.time)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = inflater.inflate(R.layout.activity_item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = taskList[position]
        holder.title.text = task.taskTitle
        holder.description.text = task.taskDescrption
        holder.time.text = task.lastAlarm
        holder.status.text = if (task.isComplete) "COMPLETED" else "UPCOMING"
        holder.options.setOnClickListener { showPopUpMenu(holder.options, position) }

        try {
            val date = inputDateFormat.parse(task.date)
            val outputDateString = dateFormat.format(date)

            val items1 = outputDateString.split(" ")
            val day = items1[0]
            val dd = items1[1]
            val month = items1[2]

            holder.day.text = day
            holder.date.text = dd
            holder.month.text = month

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun showPopUpMenu(view: View, position: Int) {
        val task = taskList[position]
        val popupMenu = PopupMenu(context, view)
        popupMenu.menuInflater.inflate(R.menu.menu, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.menuDelete -> {
                    val alertDialogBuilder = AlertDialog.Builder(context, R.style.AppTheme_Dialog)
                    alertDialogBuilder.setTitle(R.string.delete_confirmation).setMessage(R.string.sureToDelete)
                        .setPositiveButton(R.string.yes) { dialog, which ->
                            deleteTaskFromId(task.taskId, position)
                        }
                        .setNegativeButton(R.string.no) { dialog, which -> dialog.cancel() }.show()
                }
                R.id.menuUpdate -> {
                    val createTaskBottomSheetFragment = CreateTask_BottomSheetFragment()
                    createTaskBottomSheetFragment.setTaskId(task.taskId, true, context, context)
                    createTaskBottomSheetFragment.show(context.supportFragmentManager, createTaskBottomSheetFragment.tag)
                }
                R.id.menuComplete -> {
                    val completeAlertDialog = AlertDialog.Builder(context, R.style.AppTheme_Dialog)
                    completeAlertDialog.setTitle(R.string.confirmation).setMessage(R.string.sureToMarkAsComplete)
                        .setPositiveButton(R.string.yes) { dialog, which ->
                            showCompleteDialog(task.taskId, position)
                        }
                        .setNegativeButton(R.string.no) { dialog, which -> dialog.cancel() }.show()
                }
            }
            false
        }
        popupMenu.show()
    }

    private fun showCompleteDialog(taskId: Int, position: Int) {
        val dialog = Dialog(context, R.style.AppTheme)
        dialog.setContentView(R.layout.dialog_completed_theme)
        val close = dialog.findViewById<Button>(R.id.closeButton)
        close.setOnClickListener {
            deleteTaskFromId(taskId, position)
            dialog.dismiss()
        }
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
    }

    private fun deleteTaskFromId(taskId: Int, position: Int) {
        class GetSavedTasks : AsyncTask<Void, Void, List<Task>>() {
            override fun doInBackground(vararg voids: Void): List<Task> {
                DatabaseClient.getInstance(context)
                    ?.appDatabase
                    ?.dataBaseAction()
                    ?.deleteTaskFromId(taskId)

                return taskList
            }

            override fun onPostExecute(tasks: List<Task>) {
                super.onPostExecute(tasks)
                removeAtPosition(position)
                setRefreshListener.refresh()
            }
        }
        val savedTasks = GetSavedTasks()
        savedTasks.execute()
    }

    private fun removeAtPosition(position: Int) {
        val tasks = taskList.toMutableList()
        tasks.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, taskList.size)
    }

    override fun getItemCount(): Int {
        return taskList.size
    }
}
