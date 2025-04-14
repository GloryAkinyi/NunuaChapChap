package com.glory.nunuachapchap.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "content")
data class Content(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val description: String
)