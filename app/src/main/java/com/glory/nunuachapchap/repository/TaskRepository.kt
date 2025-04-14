package com.glory.nunuachapchap.repository

import com.glory.nunuachapchap.data.TaskDao
import com.glory.nunuachapchap.model.Task

class TaskRepository(private val taskDao: TaskDao) {
    val allTask = taskDao.getAllTask()

    suspend fun insert(task: Task) = taskDao.insertTask(task)
    suspend fun update(task: Task) = taskDao.updateTask(task)
    suspend fun delete(task: Task) = taskDao.deleteTask(task)
    suspend fun getById(id: Int) = taskDao.getTaskById(id)
}