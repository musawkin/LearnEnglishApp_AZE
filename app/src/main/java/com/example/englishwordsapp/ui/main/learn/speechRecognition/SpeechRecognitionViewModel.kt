package com.example.englishwordsapp.ui.main.learn.speechRecognition

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.englishwordsapp.data.model.core.ResultWrapper
import com.example.englishwordsapp.data.repositories.SpeechRecognitionRepositoryImpl
import com.example.englishwordsapp.ui.main.learn.quiz.SimpleWordsModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SpeechRecognitionViewModel @Inject constructor(
    private val speechRecognitionRepository: SpeechRecognitionRepositoryImpl,
    private val soundPlayer: SoundPlayer,
    private val wordsAnswerChecker: SpeechRecognitionAnswerChecker,
) : ViewModel() {

    private val _word = MutableLiveData<SimpleWordsModel>()
    val word: LiveData<SimpleWordsModel> = _word

    private val _state = MutableLiveData<SpeechRecognitionAnswerWordState>()
    val state: LiveData<SpeechRecognitionAnswerWordState> = _state

    private val _progress = MutableLiveData(0)
    val progress: LiveData<Int> = _progress

    private var countOfCorrectAnswer = 0

    private val _countOfQuestions = MutableLiveData(0)
    val countOfQuestions: LiveData<Int> = _countOfQuestions

    private val wordsList = mutableListOf<SimpleWordsModel>()
    private var questionModelForSkipButton: SimpleWordsModel? = null

    val wordsModelData = MutableLiveData<WordRecognitionState>()


    fun startRecognition() {
        val word = wordsList.removeLast()
        questionModelForSkipButton = word
        _word.value = word
        _progress.value = wordsList.size
    }

    fun checkAnswer(userAnswer: String) {
        val word = _word.value?.word
        val isCorrect = wordsAnswerChecker.checkAnswer(word.toString(), userAnswer)

        when{
            wordsList.isEmpty() && isCorrect ->{
                countOfCorrectAnswer++
                _state.value = _countOfQuestions.value?.let { SpeechRecognitionAnswerWordState.CorrectLast(it,countOfCorrectAnswer) }
                _progress.value = wordsList.size
            }
            wordsList.isEmpty() && !isCorrect ->{
                _state.value = _countOfQuestions.value?.let { SpeechRecognitionAnswerWordState.WrongLast(it,countOfCorrectAnswer) }
                _progress.value = wordsList.size
            }
            wordsList.isNotEmpty() && isCorrect ->{
                countOfCorrectAnswer++
                _state.value = SpeechRecognitionAnswerWordState.CORRECT
                _progress.value = wordsList.size
            }
            wordsList.isNotEmpty() && !isCorrect ->{
                _state.value = SpeechRecognitionAnswerWordState.WRONG
                _progress.value = wordsList.size
            }
        }
    }

    fun skipWord() {
        if (wordsList.isNotEmpty()) {
            questionModelForSkipButton?.let { wordsList.add(0, it) }
            val word = wordsList.removeLast()
            questionModelForSkipButton = word
            _word.value = word
        }
    }

//    fun playSound(word: String){
//        soundPlayer.playSound(word, context)
//    }

    fun loadWords(difficultyLevel: String) {
        wordsModelData.postValue(WordRecognitionState.Loading(true))
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                speechRecognitionRepository.getWordsList(difficultyLevel).collect {
                    when (it) {
                        is ResultWrapper.Success -> {
                            wordsModelData.postValue(WordRecognitionState.Loading(false))
                            wordsList.addAll(it.data)
                            withContext(Dispatchers.Main) {
                                _countOfQuestions.value = wordsList.size
                                val word = wordsList.removeLast()
                                questionModelForSkipButton = word
                                _word.value = word
                                _progress.value = wordsList.size
                            }
                            wordsModelData.postValue(WordRecognitionState.Success(_word.value!!))
                        }

                        is ResultWrapper.Error -> {
                            wordsModelData.postValue(WordRecognitionState.Loading(false))
                            wordsModelData.postValue(WordRecognitionState.Error(it.error.orEmpty()))
                        }

                        else -> {
                            wordsModelData.postValue(WordRecognitionState.Loading(false))
                        }
                    }
                }
            }
        }
    }


}

