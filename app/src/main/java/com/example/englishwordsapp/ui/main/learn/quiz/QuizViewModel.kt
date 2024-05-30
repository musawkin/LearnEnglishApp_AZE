package com.example.englishwordsapp.ui.main.learn.quiz

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.englishwordsapp.data.model.core.ResultWrapper
import com.example.englishwordsapp.data.repositories.QuizRepositoryImpl
import com.example.englishwordsapp.ui.main.learn.SimpleWordsModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val quizRepository: QuizRepositoryImpl
): ViewModel() {

    private val _countOfQuestions = MutableLiveData(0)
    val countOfQuestions: LiveData<Int> = _countOfQuestions

    private val _question = MutableLiveData<QuizQuestionsModel>()
    val question: LiveData<QuizQuestionsModel> = _question

    private val _progress = MutableLiveData(0)
    val progress: LiveData<Int> = _progress

    private val _questionModelData = MutableLiveData<QuizState>()
    val questionModelData: LiveData<QuizState> = _questionModelData

    private var questionModelForSkipButton: QuizQuestionsModel? = null
    val questionsList = mutableListOf<QuizQuestionsModel>()

    fun setNewQuestion() {
        val word = questionsList.removeLast()
        questionModelForSkipButton = word
        _question.value = word
        _progress.value = questionsList.size
    }

    fun skipQuestion(){
        questionModelForSkipButton?.let { questionsList.add(0, it) }
        val word = questionsList.removeLast()
        questionModelForSkipButton = word
        _question.value = word
    }

    //TODO : Buna baxmaq lazımdır
    fun getQuestionList(difficultyLevel: String){
        _questionModelData.postValue(QuizState.Loading(true))
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                quizRepository.getQuestionList(difficultyLevel).collect{
                    when(it){
                        is ResultWrapper.Success ->{
                            _questionModelData.postValue(QuizState.Loading(false))
                            questionsList.addAll(it.data)
                            withContext(Dispatchers.Main){
                                _countOfQuestions.value = questionsList.size
                                val word = questionsList.removeLast()
                                questionModelForSkipButton = word
                                _question.value = word
                                _progress.value = questionsList.size
                            }
                            _questionModelData.postValue(QuizState.Success(_question.value!!))
                        }
                        is ResultWrapper.Error->{
                            _questionModelData.postValue(QuizState.Loading(false))
                            _questionModelData.postValue(QuizState.Error(it.error.orEmpty()))
                        }
                        else -> {
                            _questionModelData.postValue(QuizState.Loading(false))
                        }
                    }
                }
            }
        }
    }
}