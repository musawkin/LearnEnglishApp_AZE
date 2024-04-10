package com.example.englishwordsapp.ui.main.tabs.learn.quiz

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.englishwordsapp.data.model.core.ResultWrapper
import com.example.englishwordsapp.data.repositories.QuizRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val quizRepository: QuizRepositoryImpl
): ViewModel() {

    val questionModelData = MutableLiveData<QuizState>()

    fun getQuestionList(difficultyLevel: String){
        questionModelData.postValue(QuizState.Loading(true))
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                quizRepository.getQuestionList(difficultyLevel).collect{
                    when(it){
                        is ResultWrapper.Success ->{
                            questionModelData.postValue(QuizState.Loading(false))
                            questionModelData.postValue(QuizState.Success(it.data))
                        }
                        is ResultWrapper.Error->{
                            questionModelData.postValue(QuizState.Loading(false))
                            questionModelData.postValue(QuizState.Error(it.error.orEmpty()))
                        }
                        else -> {
                            questionModelData.postValue(QuizState.Loading(false))
                        }
                    }
                }
            }
        }
    }
}