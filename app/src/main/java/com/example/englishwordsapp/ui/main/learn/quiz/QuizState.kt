package com.example.englishwordsapp.ui.main.learn.quiz

sealed class QuizState {
    data class Error(val errorException: String): QuizState()
    data class Success(val question: QuizQuestionsModel): QuizState()
    class Loading(val isLoading: Boolean): QuizState()
}