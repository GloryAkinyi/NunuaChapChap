package com.glory.nunuachapchap.data

import androidx.room.*
import com.glory.nunuachapchap.model.Content
import kotlinx.coroutines.flow.Flow

@Dao
interface ContentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContent(content: Content)

    @Update
    suspend fun updateContent(content: Content)

    @Delete
    suspend fun deleteContent(content: Content)

    @Query("SELECT * FROM content ORDER BY id DESC")
    fun getAllContent(): Flow<List<Content>>

    @Query("SELECT * FROM content WHERE id = :id")
    suspend fun getContentById(id: Int): Content?
}