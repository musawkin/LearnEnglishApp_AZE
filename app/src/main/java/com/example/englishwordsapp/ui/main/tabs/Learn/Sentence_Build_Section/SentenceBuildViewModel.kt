package com.example.englishwordsapp.ui.main.tabs.Learn.Sentence_Build_Section

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.englishwordsapp.data.model.core.ResultWrapper
import com.example.englishwordsapp.data.repositories.SentenceBuildRepositoryImpl
import com.example.englishwordsapp.ui.main.tabs.Learn.Interactive_Quiz_Section.QuizQuestionsResponseState
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SentenceBuildViewModel : ViewModel() {

    val questionModelData = MutableLiveData<SentenceModelResponseState>()
    private val sentenceBuildRepository = SentenceBuildRepositoryImpl()
    fun getSentenceModel(level: String) {
        questionModelData.postValue(SentenceModelResponseState.Loading(true))
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                sentenceBuildRepository.getSentencesList(level).collect{
                    when(it){
                        is ResultWrapper.Success ->{
                            questionModelData.postValue(SentenceModelResponseState.Loading(false))
                            questionModelData.postValue(SentenceModelResponseState.Success(it.data))
                        }
                        is ResultWrapper.Error ->{
                            questionModelData.postValue(SentenceModelResponseState.Loading(false))
                            questionModelData.postValue(SentenceModelResponseState.Error(it.error.orEmpty()))
                        }
                        else -> {
                            questionModelData.postValue(SentenceModelResponseState.Loading(false))
                        }
                    }
                }
            }
        }
    }
}