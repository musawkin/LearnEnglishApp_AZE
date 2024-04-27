package com.example.englishwordsapp.ui.main.learn.sentenceBuild

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.englishwordsapp.data.model.core.ResultWrapper
import com.example.englishwordsapp.data.repositories.SentenceBuildRepositoryImpl
import com.example.englishwordsapp.ui.main.learn.quiz.QuizQuestionsModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SentenceBuildViewModel @Inject constructor(
    private val sentenceBuildRepository: SentenceBuildRepositoryImpl
) : ViewModel() {

    private val _responseModelData = MutableLiveData<SentenceBuildState>()
    val responseModelData: LiveData<SentenceBuildState> = _responseModelData

    private val _questionModel = MutableLiveData<SentenceModel>()
    val questionModel: LiveData<SentenceModel> = _questionModel

    private var questionModelForSkipButton: SentenceModel? = null
    val sentencesList = mutableListOf<SentenceModel>()

    fun chekAnswer(answer: List<String>){
        if (answer == _questionModel.value?.answerWordsList){

        }else{

        }
    }
    fun getSentenceModel(level: String) {
        _responseModelData.postValue(SentenceBuildState.Loading(true))
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                sentenceBuildRepository.getSentencesList(level).collect{
                    when(it){
                        is ResultWrapper.Success ->{
                            _responseModelData.postValue(SentenceBuildState.Loading(false))
                            sentencesList.addAll(it.data)
                            withContext(Dispatchers.Main){
//                                _countOfQuestions.value = questionsList.size
                                val word = sentencesList.removeLast()
                                questionModelForSkipButton = word
                                _questionModel.value = word
//                                _progress.value = questionsList.size
                            }
                            _responseModelData.postValue(SentenceBuildState.Success(_questionModel.value!!))
                        }
                        is ResultWrapper.Error ->{
                            _responseModelData.postValue(SentenceBuildState.Loading(false))
                            _responseModelData.postValue(SentenceBuildState.Error(it.error.orEmpty()))
                        }
                        else -> {
                            _responseModelData.postValue(SentenceBuildState.Loading(false))
                        }
                    }
                }
            }
        }
    }
}