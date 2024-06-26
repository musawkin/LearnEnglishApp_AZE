package com.example.englishwordsapp.ui.main.learn.speechRecognition

import com.example.englishwordsapp.ui.main.learn.quiz.SimpleWordsModel

sealed class WordRecognitionState {

    data class Error(val errorException: String): WordRecognitionState()
    data class Success(val listOfQuestions: SimpleWordsModel): WordRecognitionState()
    class Loading(val isLoading: Boolean): WordRecognitionState()

}