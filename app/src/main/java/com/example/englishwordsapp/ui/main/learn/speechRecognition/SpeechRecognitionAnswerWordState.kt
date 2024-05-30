package com.example.englishwordsapp.ui.main.learn.speechRecognition

sealed class SpeechRecognitionAnswerWordState{
    data object CORRECT : SpeechRecognitionAnswerWordState()
    data object WRONG : SpeechRecognitionAnswerWordState()
    data class WrongLast(val countOfAllQuestions: Int, val countOfCorrectAnswer: Int) : SpeechRecognitionAnswerWordState()
    data class CorrectLast(val countOfAllQuestions: Int, val countOfCorrectAnswer: Int) : SpeechRecognitionAnswerWordState()
}