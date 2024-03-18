package com.example.englishwordsapp.ui.main.tabs.Learn.Vocabulary_Section

import com.example.englishwordsapp.ui.main.tabs.Learn.SimpleWordsModel


sealed class VocabularyWordsResponseState {
    data class Error(val errorException: String): VocabularyWordsResponseState()
    data class Success(val listOfQuestions: List<SimpleWordsModel>): VocabularyWordsResponseState()
    class Loading(val isLoading: Boolean): VocabularyWordsResponseState()
}