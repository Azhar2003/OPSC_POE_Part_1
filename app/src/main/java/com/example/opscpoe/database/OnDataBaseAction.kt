package com.example.opscpoe.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.opscpoe.model.Task

@Dao
interface OnDataBaseAction {

    @Query("SELECT * FROM Task")
    fun getAllTasksList(): List<Task>

    @Query("DELETE FROM Task")
    fun truncateTheList()

    @Insert
    fun insertDataIntoTaskList(task: Task)

    @Query("DELETE FROM Task WHERE taskId = :taskId")
    fun deleteTaskFromId(taskId: Int)

    @Query("SELECT * FROM Task WHERE taskId = :taskId")
    fun selectDataFromAnId(taskId: Int): Task

    @Update
    fun updateAnExistingRow(
        task: Int,
        toString: String,
        toString1: String,
        toString2: String,
        toString3: String,
        toString4: String
    )
}
