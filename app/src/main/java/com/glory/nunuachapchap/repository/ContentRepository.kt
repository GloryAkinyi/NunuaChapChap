package com.glory.nunuachapchap.repository

import com.glory.nunuachapchap.data.ContentDao
import com.glory.nunuachapchap.model.Content

class ContentRepository(private val contentDao: ContentDao) {
    val allContent = contentDao.getAllContent()

    suspend fun insert(content: Content) = contentDao.insertContent(content)
    suspend fun update(content: Content) = contentDao.updateContent(content)
    suspend fun delete(content: Content) = contentDao.deleteContent(content)
    suspend fun getById(id: Int) = contentDao.getContentById(id)
}