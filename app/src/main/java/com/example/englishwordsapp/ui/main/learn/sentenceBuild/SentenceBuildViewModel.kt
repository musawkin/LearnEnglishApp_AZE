package com.example.englishwordsapp.ui.main.learn.sentenceBuild

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.englishwordsapp.data.model.core.ResultWrapper
import com.example.englishwordsapp.data.repositories.SentenceBuildRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SentenceBuildViewModel @Inject constructor(
    private val sentenceBuildRepository: SentenceBuildRepositoryImpl
) : ViewModel() {

    val questionModelData = MutableLiveData<SentenceBuildState>()

    fun getSentenceModel(level: String) {
        questionModelData.postValue(SentenceBuildState.Loading(true))
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                sentenceBuildRepository.getSentencesList(level).collect{
                    when(it){
                        is ResultWrapper.Success ->{
                            questionModelData.postValue(SentenceBuildState.Loading(false))
                            questionModelData.postValue(SentenceBuildState.Success(it.data))
                        }
                        is ResultWrapper.Error ->{
                            questionModelData.postValue(SentenceBuildState.Loading(false))
                            questionModelData.postValue(SentenceBuildState.Error(it.error.orEmpty()))
                        }
                        else -> {
                            questionModelData.postValue(SentenceBuildState.Loading(false))
                        }
                    }
                }
            }
        }
    }
}