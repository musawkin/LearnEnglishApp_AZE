package com.example.englishwordsapp.ui.main.tabs.Learn.Interactive_Quiz_Section

import androidx.lifecycle.MutableLiveData
import java.sql.SQLSyntaxErrorException

sealed class QuizQuestionsResponseState {
    data class Error(val errorException: String): QuizQuestionsResponseState()
    data class Success(val listOfQuestions: List<QuizQuestionsModel>): QuizQuestionsResponseState()
    class Loading(val isLoading: Boolean): QuizQuestionsResponseState()
}