package com.androidtest.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(emptyList<MainUiModel>())
    val uiState: StateFlow<List<MainUiModel>> = _uiState.asStateFlow()

    private val _items = MutableLiveData<List<MainUiModel>>()
    val items: LiveData<List<MainUiModel>> get() = _items

    init {
        getList()
    }

    private fun getList() {
        _uiState.value = listOf(
            MainUiModel(title = "Test1", description = "Test", imageUrl = "Test"),
            MainUiModel(title = "Test2", description = "Test", imageUrl = "Test"),
            MainUiModel(title = "Test3", description = "Test", imageUrl = "Test"),
            )
    }

    data class MainUiModel(
        val title: String = "",
        val description: String = "",
        val imageUrl: String= "",
    )
}