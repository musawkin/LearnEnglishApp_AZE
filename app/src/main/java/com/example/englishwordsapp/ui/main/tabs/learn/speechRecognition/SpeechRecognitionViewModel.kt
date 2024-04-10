package com.example.englishwordsapp.ui.main.tabs.learn.speechRecognition

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.englishwordsapp.data.model.core.ResultWrapper
import com.example.englishwordsapp.data.repositories.SpeechRecognitionRepositoryImpl
import com.example.englishwordsapp.ui.main.tabs.learn.vocabulary.VocabularyState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SpeechRecognitionViewModel @Inject constructor(
    private val speechRecognitionRepository: SpeechRecognitionRepositoryImpl
): ViewModel() {

    val wordsModelData = MutableLiveData<VocabularyState>()

    fun getWordsList(difficultyLevel: String){
        wordsModelData.postValue(VocabularyState.Loading(true))
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                speechRecognitionRepository.getWordsList(difficultyLevel).collect{
                    when(it){
                        is ResultWrapper.Success ->{
                            wordsModelData.postValue(VocabularyState.Loading(false))
                            wordsModelData.postValue(VocabularyState.Success(it.data))
                        }
                        is ResultWrapper.Error->{
                            wordsModelData.postValue(VocabularyState.Loading(false))
                            wordsModelData.postValue(VocabularyState.Error(it.error.orEmpty()))
                        }
                        else -> {
                            wordsModelData.postValue(VocabularyState.Loading(false))
                        }
                    }
                }
            }
        }
    }
}

