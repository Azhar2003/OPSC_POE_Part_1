package com.example.opscpoe.adapter

import android.R
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.AsyncTask
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.example.opscpoe.Activity.Add_Task
import com.example.opscpoe.bottomSheetFragment.CreateTask_BottomSheetFragment
import com.example.opscpoe.database.DatabaseClient
import com.example.opscpoe.model.Task
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class Task_adapter(
    context: Add_Task,
    taskList: List<Task>,
    setRefreshListener: CreateTask_BottomSheetFragment.setRefreshListener
) :
    RecyclerView.Adapter<Task_adapter.TaskViewHolder>() {
    private val context: Add_Task
    private val inflater: LayoutInflater
    private val taskList: List<Task>
    var dateFormat = SimpleDateFormat("EE dd MMM yyyy", Locale.US)
    var inputDateFormat = SimpleDateFormat("dd-M-yyyy", Locale.US)
    var date: Date? = null
    var outputDateString: String? = null
    var setRefreshListener: CreateTask_BottomSheetFragment.setRefreshListener

    init {
        this.context = context
        this.taskList = taskList
        this.setRefreshListener = setRefreshListener
        inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): TaskViewHolder {
        val view: View = inflater.inflate(R.layout.item_task, viewGroup, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = taskList[position]
        holder.title.setText(task.getTaskTitle())
        holder.description.setText(task.getTaskDescrption())
        holder.time.setText(task.getLastAlarm())
        holder.status!!.text = if (task.isComplete()) "COMPLETED" else "UPCOMING"
        holder.options!!.setOnClickListener { view: View? ->
            showPopUpMenu(
                view,
                position
            )
        }
        try {
            date = inputDateFormat.parse(task.getDate())
            outputDateString = dateFormat.format(date)
            val items1 = outputDateString.split(" ".toRegex()).dropLastWhile { it.isEmpty() }
                .toTypedArray()
            val day = items1[0]
            val dd = items1[1]
            val month = items1[2]
            holder.day!!.text = day
            holder.date!!.text = dd
            holder.month!!.text = month
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun showPopUpMenu(view: View?, position: Int) {
        val task = taskList[position]
        val popupMenu = PopupMenu(context, view)
        popupMenu.menuInflater.inflate(R.menu.menu, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { item: MenuItem ->
            when (item.itemId) {
                R.id.menuDelete -> {
                    val alertDialogBuilder =
                        AlertDialog.Builder(context, R.style.AppTheme_Dialog)
                    alertDialogBuilder.setTitle(R.string.delete_confirmation)
                        .setMessage(R.string.sureToDelete).setPositiveButton(R.string.yes,
                            DialogInterface.OnClickListener { dialog: DialogInterface?, which: Int ->
                                deleteTaskFromId(
                                    task.getTaskId(),
                                    position
                                )
                            })
                        .setNegativeButton(R.string.no,
                            DialogInterface.OnClickListener { dialog: DialogInterface, which: Int -> dialog.cancel() })
                        .show()
                }

                R.id.menuUpdate -> {
                    val createTaskBottomSheetFragment =
                        CreateTask_BottomSheetFragment()
                    createTaskBottomSheetFragment.setTaskId(
                        task.getTaskId(),
                        true,
                        context,
                        context
                    )
                    createTaskBottomSheetFragment.show(
                        context.getSupportFragmentManager(),
                        createTaskBottomSheetFragment.getTag()
                    )
                }

                R.id.menuComplete -> {
                    val completeAlertDialog =
                        AlertDialog.Builder(context, R.style.AppTheme_Dialog)
                    completeAlertDialog.setTitle(R.string.confirmation)
                        .setMessage(R.string.sureToMarkAsComplete).setPositiveButton(R.string.yes,
                            DialogInterface.OnClickListener { dialog: DialogInterface?, which: Int ->
                                showCompleteDialog(
                                    task.getTaskId(),
                                    position
                                )
                            })
                        .setNegativeButton(R.string.no,
                            DialogInterface.OnClickListener { dialog: DialogInterface, which: Int -> dialog.cancel() })
                        .show()
                }
            }
            false
        }
        popupMenu.show()
    }

    fun showCompleteDialog(taskId: Int, position: Int) {
        val dialog = Dialog(context, R.style.AppTheme)
        dialog.setContentView(R.layout.dialog_completed_theme)
        val close = dialog.findViewById<Button>(R.id.closeButton)
        close.setOnClickListener { view: View? ->
            deleteTaskFromId(taskId, position)
            dialog.dismiss()
        }
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
    }

    private fun deleteTaskFromId(taskId: Int, position: Int) {
        class GetSavedTasks :
            AsyncTask<Void?, Void?, List<Task>>() {
            protected override fun doInBackground(vararg voids: Void): List<Task> {
                DatabaseClient.getInstance(context)
                    .getAppDatabase()
                    .dataBaseAction()
                    .deleteTaskFromId(taskId)
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
        taskList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, taskList.size)
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    inner class TaskViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
        @BindView(R.id.day)
        var day: TextView? = null

        @BindView(R.id.date)
        var date: TextView? = null

        @BindView(R.id.month)
        var month: TextView? = null

        @BindView(R.id.title)
        var title: TextView? = null

        @BindView(R.id.description)
        var description: TextView? = null

        @BindView(R.id.status)
        var status: TextView? = null

        @BindView(R.id.options)
        var options: ImageView? = null

        @BindView(R.id.time)
        var time: TextView? = null

        init {
            ButterKnife.bind(this, view)
        }
    }
}