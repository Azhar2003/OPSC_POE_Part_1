package com.example.opscpoe.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity
class Task : Serializable {
    @PrimaryKey(autoGenerate = true)
    var taskId = 0

    @ColumnInfo(name = "taskTitle")
    var taskTitle: String? = null

    @ColumnInfo(name = "date")
    var date: String? = null

    @ColumnInfo(name = "taskDescription")
    var taskDescrption: String? = null

    @ColumnInfo(name = "isComplete")
    var isComplete = false

    @ColumnInfo(name = "firstAlarmTime")
    var firstAlarmTime: String? = null

    @ColumnInfo(name = "secondAlarmTime")
    var secondAlarmTime: String? = null

    @ColumnInfo(name = "lastAlarm")
    var lastAlarm: String? = null

    @ColumnInfo(name = "event")
    var event: String? = null
}