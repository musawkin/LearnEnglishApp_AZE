package com.example.englishwordsapp.ui.main.tabs.Learn.Interactive_Quiz_Section

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.englishwordsapp.data.model.core.ResultWrapper
import com.example.englishwordsapp.data.repositories.QuizRepositoryImpl
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class QuizViewModel: ViewModel() {

    val questionModelData = MutableLiveData<QuizQuestionsResponseState>()
    private val quizRepository = QuizRepositoryImpl()

    fun getQuestionList(wordsLevel: String){
        questionModelData.postValue(QuizQuestionsResponseState.Loading(true))
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                quizRepository.getQuestionList(wordsLevel).collect{
                    when(it){
                        is ResultWrapper.Success ->{
                            questionModelData.postValue(QuizQuestionsResponseState.Loading(false))
                            questionModelData.postValue(QuizQuestionsResponseState.Success(it.data))
                        }
                        is ResultWrapper.Error->{
                            questionModelData.postValue(QuizQuestionsResponseState.Loading(false))
                            questionModelData.postValue(QuizQuestionsResponseState.Error(it.error.orEmpty()))
                        }
                        else -> {
                            questionModelData.postValue(QuizQuestionsResponseState.Loading(false))
                        }
                    }
                }
            }
        }
    }
}