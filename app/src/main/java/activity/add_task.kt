package activity

import android.R
import android.content.ComponentName
import android.content.pm.PackageManager
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.bumptech.glide.Glide
import com.codegama.opscpoe.adapter.TaskAdapter
import com.codegama.opscpoe.bottomSheetFragment.CreateTaskBottomSheetFragment
import com.codegama.opscpoe.bottomSheetFragment.ShowCalendarViewBottomSheet
import com.example.opscpoe.Activity.BaseActivity
import com.example.opscpoe.database.DatabaseClient
import com.example.opscpoe.model.Task


class MainActivity : BaseActivity(), CreateTaskBottomSheetFragment.setRefreshListener {
    @BindView(R.id.taskRecycler)
    var taskRecycler: RecyclerView? = null

    @BindView(R.id.addTask)
    var addTask: TextView? = null
    var taskAdapter: TaskAdapter? = null
    var tasks: List<Task> = ArrayList()

    @BindView(R.id.noDataImage)
    var noDataImage: ImageView? = null

    @BindView(R.id.calendar)
    var calendar: ImageView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)
        setUpAdapter()
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        val receiver = ComponentName(this, AlarmBroadcastReceiver::class.java)
        val pm = packageManager
        pm.setComponentEnabledSetting(
            receiver,
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
            PackageManager.DONT_KILL_APP
        )
        Glide.with(applicationContext).load(R.drawable.first_note).into(noDataImage)
        addTask!!.setOnClickListener { view: View? ->
            val createTaskBottomSheetFragment =
                CreateTaskBottomSheetFragment()
            createTaskBottomSheetFragment.setTaskId(0, false, this, this@MainActivity)
            createTaskBottomSheetFragment.show(
                supportFragmentManager,
                createTaskBottomSheetFragment.getTag()
            )
        }
        savedTasks
        calendar!!.setOnClickListener { view: View? ->
            val showCalendarViewBottomSheet =
                ShowCalendarViewBottomSheet()
            showCalendarViewBottomSheet.show(
                supportFragmentManager,
                showCalendarViewBottomSheet.getTag()
            )
        }
    }

    fun setUpAdapter() {
        taskAdapter = TaskAdapter(this, tasks, this)
        taskRecycler!!.setLayoutManager(LinearLayoutManager(applicationContext))
        taskRecycler!!.setAdapter(taskAdapter)
    }

    private val savedTasks: Unit
        private get() {
            class GetSavedTasks :
                AsyncTask<Void?, Void?, List<Task>>() {
                protected override fun doInBackground(vararg voids: Void): List<Task> {
                    tasks = DatabaseClient
                        .getInstance(applicationContext)
                        .getAppDatabase()
                        .dataBaseAction()
                        .getAllTasksList()
                    return tasks
                }

                override fun onPostExecute(tasks: List<Task>) {
                    super.onPostExecute(tasks)
                    noDataImage!!.setVisibility(if (tasks.isEmpty()) View.VISIBLE else View.GONE)
                    setUpAdapter()
                }
            }

            val savedTasks = GetSavedTasks()
            savedTasks.execute()
        }

    fun refresh() {
        savedTasks
    }
}