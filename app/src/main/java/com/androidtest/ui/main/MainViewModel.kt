package com.androidtest.ui.main

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.ViewModel
import com.androidtest.model.MainModel
import com.androidtest.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.concurrent.TimeUnit
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

    @SuppressLint("CheckResult")
    public fun getList() {
        mainRepository.getData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnComplete {
                Log.e("Complete","Testing complete 1")
            }
//            .debounce(400, TimeUnit.MILLISECONDS)
            .subscribe(
                { list ->
                    Log.e("Name","Thread: ${Thread.currentThread().name}3")
                        _uiState.value = transform(list)
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