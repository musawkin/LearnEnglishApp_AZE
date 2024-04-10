package com.example.englishwordsapp.ui.main.tabs.learn.vocabulary

import com.example.englishwordsapp.ui.main.tabs.learn.SimpleWordsModel


sealed class VocabularyState {
    data class Error(val errorException: String): VocabularyState()
    data class Success(val listOfQuestions: List<SimpleWordsModel>): VocabularyState()
    class Loading(val isLoading: Boolean): VocabularyState()
}