package com.example.englishwordsapp.ui.main.tabs.learn.vocabulary

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.englishwordsapp.data.model.core.ResultWrapper
import com.example.englishwordsapp.data.repositories.VocabularyRepository
import com.example.englishwordsapp.data.repositories.VocabularyRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class VocabularyTranslationViewModel @Inject constructor(
    private val vocabularyRepository: VocabularyRepositoryImpl
) : ViewModel() {

    val wordsModelData = MutableLiveData<VocabularyState>()
    fun getWordsList(difficultyLevel: String) {
        wordsModelData.postValue(VocabularyState.Loading(true))
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                vocabularyRepository.getWordsList(difficultyLevel).collect {
                    when (it) {
                        is ResultWrapper.Success -> {
                            wordsModelData.postValue(VocabularyState.Loading(false))
                            wordsModelData.postValue(VocabularyState.Success(it.data))
                        }
                        is ResultWrapper.Error -> {
                            wordsModelData.postValue(VocabularyState.Loading(false))
                            wordsModelData.postValue(VocabularyState.Error(it.error.orEmpty()))
                        }
                        else -> {}
                    }
                }
            }
        }
    }
}