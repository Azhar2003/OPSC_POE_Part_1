package com.example.opscpoe.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Task(
    @PrimaryKey(autoGenerate = true) var taskId: Int = 0,
    @ColumnInfo(name = "taskTitle") var taskTitle: String = "",
    @ColumnInfo(name = "date") var date: String = "",
    @ColumnInfo(name = "taskDescription") var taskDescription: String = "",
    @ColumnInfo(name = "isComplete") var isComplete: Boolean = false,
    @ColumnInfo(name = "firstAlarmTime") var firstAlarmTime: String = "",
    @ColumnInfo(name = "secondAlarmTime") var secondAlarmTime: String = "",
    @ColumnInfo(name = "lastAlarm") var lastAlarm: String = "",
    @ColumnInfo(name = "event") var event: String = ""
) : Serializable
