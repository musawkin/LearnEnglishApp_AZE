package com.example.englishwordsapp.ui.main.tabs.Learn.Sentence_Build_Section



sealed class SentenceModelResponseState {

    data class Error(val errorException: String): SentenceModelResponseState()
    data class Success(val listOfQuestions: List<SentenceModel>): SentenceModelResponseState()
    class Loading(val isLoading: Boolean): SentenceModelResponseState()
}