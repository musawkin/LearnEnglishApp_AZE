package com.example.englishwordsapp.ui.main.learn.vocabulary

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import com.example.englishwordsapp.data.model.core.ResultWrapper
import com.example.englishwordsapp.data.repositories.VocabularyRepositoryImpl
import com.example.englishwordsapp.ui.main.learn.SimpleWordsModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class VocabularyTranslationViewModel @Inject constructor(
    private val vocabularyRepository: VocabularyRepositoryImpl,
) : ViewModel() {
    val wordsModelData = MutableLiveData<VocabularyState>()

    var isLoading: Boolean = false
    var isLastPage: Boolean = false

    fun getWordsList(difficultyLevel: String) {
        isLoading = true
        wordsModelData.postValue(VocabularyState.Loading(true))
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                vocabularyRepository.getWordsList(difficultyLevel).collect {
                    when (it) {
                        is ResultWrapper.Success -> {
                            wordsModelData.postValue(VocabularyState.Loading(false))
                            isLoading = false
                            isLastPage = it.data.size < 3
                            wordsModelData.postValue(VocabularyState.Success(it.data))
                        }
                        is ResultWrapper.Error -> {
                            wordsModelData.postValue(VocabularyState.Loading(false))
                            isLoading = false
                            wordsModelData.postValue(VocabularyState.Error(it.error.orEmpty()))
                        }
                        else -> {}
                    }
                }
            }
        }
    }

    fun loadNextPageOfWords(difficultyLevel: String, lastWord: SimpleWordsModel?) {
        isLoading = true // Устанавливаем флаг isLoading в true перед началом загрузки
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                vocabularyRepository.loadNextPageOfWords(difficultyLevel, lastWord).collect {
                    when (it) {
                        is ResultWrapper.Success -> {
                            isLoading = false // Устанавливаем флаг isLoading в false после завершения загрузки
                            // Проверяем, есть ли данные на следующей странице, и устанавливаем соответствующий флаг isLastPage
                            isLastPage = it.data.size < 3
                            wordsModelData.postValue(VocabularyState.Success(it.data))
                        }
                        is ResultWrapper.Error -> {
                            isLoading = false // Устанавливаем флаг isLoading в false в случае ошибки загрузки
                            wordsModelData.postValue(VocabularyState.Error(it.error.orEmpty()))
                        }
                        else -> {}
                    }
                }
            }
        }
    }
}