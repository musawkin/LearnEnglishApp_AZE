package com.example.englishwordsapp.ui.main.tabs.Learn.Vocabulary_Section

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.englishwordsapp.data.model.core.ResultWrapper
import com.example.englishwordsapp.data.repositories.VocabularyRepositoryImpl
import com.example.englishwordsapp.ui.main.tabs.Learn.SimpleWordsModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class VocabularyTranslationViewModel : ViewModel() {

    val wordsModelData = MutableLiveData<VocabularyWordsResponseState>()
    private val vocabularyRepository = VocabularyRepositoryImpl()
    fun getWordsList(difficultyLevel: String) {
        wordsModelData.postValue(VocabularyWordsResponseState.Loading(true))
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                vocabularyRepository.getWordsList(difficultyLevel).collect {
                    when (it) {
                        is ResultWrapper.Success -> {
                            wordsModelData.postValue(VocabularyWordsResponseState.Loading(false))
                            wordsModelData.postValue(VocabularyWordsResponseState.Success(it.data))
                        }
                        is ResultWrapper.Error -> {
                            wordsModelData.postValue(VocabularyWordsResponseState.Loading(false))
                            wordsModelData.postValue(VocabularyWordsResponseState.Error(it.error.orEmpty()))
                        }
                        else -> {}
                    }
                }
            }
        }
    }
}