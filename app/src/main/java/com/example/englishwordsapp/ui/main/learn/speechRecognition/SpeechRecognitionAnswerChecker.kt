package com.example.englishwordsapp.ui.main.learn.speechRecognition

import javax.inject.Inject

class SpeechRecognitionAnswerChecker @Inject constructor() {

    fun checkAnswer(rightAnswer: String, userAnswer: String): Boolean{
        return rightAnswer == userAnswer
    }
}