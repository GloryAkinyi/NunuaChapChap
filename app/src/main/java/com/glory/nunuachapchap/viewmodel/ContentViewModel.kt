package com.glory.nunuachapchap.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glory.nunuachapchap.model.Content
import com.glory.nunuachapchap.repository.ContentRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ContentViewModel(private val repository: ContentRepository) : ViewModel() {
    val allContent = repository.allContent

    private val _selectedContent = MutableStateFlow<Content?>(null)
    val selectedContent: StateFlow<Content?> = _selectedContent.asStateFlow()

    fun insert(content: Content) = viewModelScope.launch {
        repository.insert(content)
    }

    fun update(content: Content) = viewModelScope.launch {
        repository.update(content)
    }

    fun delete(content: Content) = viewModelScope.launch {
        repository.delete(content)
    }

    fun loadContentById(id: Int) = viewModelScope.launch {
        _selectedContent.value = repository.getById(id)
    }
}