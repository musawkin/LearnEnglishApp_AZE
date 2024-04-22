package com.example.englishwordsapp.ui.main.learn.speechRecognition

sealed class SpeechRecognitionAnswerWordState{
    data object CORRECT : SpeechRecognitionAnswerWordState()
    data object WRONG : SpeechRecognitionAnswerWordState()
    data class Last(val countOfCorrectAnswer: Int) : SpeechRecognitionAnswerWordState()
}