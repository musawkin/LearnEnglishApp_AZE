package com.example.englishwordsapp.ui.main.learn.speechRecognition

import javax.inject.Inject

class SpeechRecognitionAnswerChecker @Inject constructor() {

    fun checkAnswer(wrightAnswer: String, userAnswer: String): Boolean{
        return wrightAnswer == userAnswer
    }
}