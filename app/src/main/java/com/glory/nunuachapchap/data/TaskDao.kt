package com.glory.nunuachapchap.data

import androidx.room.*
import com.glory.nunuachapchap.model.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task)

    @Update
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(content: Task)

    @Query("SELECT * FROM task ORDER BY id DESC")
    fun getAllTask(): Flow<List<Task>>

    @Query("SELECT * FROM task WHERE id = :id")
    suspend fun getTaskById(id: Int): Task?
}