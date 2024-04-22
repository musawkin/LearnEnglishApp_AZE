package com.example.englishwordsapp.ui.main.learn.sentenceBuild



sealed class SentenceBuildState {

    data class Error(val errorException: String): SentenceBuildState()
    data class Success(val listOfQuestions: List<SentenceModel>): SentenceBuildState()
    class Loading(val isLoading: Boolean): SentenceBuildState()
}