package com.androidtest.ui.main

import androidx.lifecycle.ViewModel
import com.androidtest.model.MainModel
import com.androidtest.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository
    ) : ViewModel() {
    private val _uiState = MutableStateFlow(emptyList<MainUiModel>())
    val uiState: StateFlow<List<MainUiModel>> = _uiState.asStateFlow()

    init {
        getList()
    }

    private fun getList() {
        mainRepository.fetchApplicationList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { list ->
                    _uiState.value = transform(list)
//                    _uiState.value = listOf(
//                        MainUiModel(title = "Test1", description = "Test", imageUrl = "Test"),
//                        MainUiModel(title = "Test2", description = "Test", imageUrl = "Test"),
//                        MainUiModel(title = "Test3", description = "Test", imageUrl = "Test"),
//                    )
                },
                { throwable ->
                    throwable.printStackTrace()
                },
            )

    }

    private fun transform(mainModelList: List<MainModel>?): List<MainUiModel> {
            val mainUiModelList = mutableListOf<MainUiModel>()

            mainModelList?.forEach {
                mainUiModelList.add(
                    MainUiModel(it.title, it.description, it.imageSrc)
                )
            }
        return mainUiModelList
    }

    data class MainUiModel(
        val title: String = "",
        val description: String = "",
        val imageUrl: String = "",
    )
}