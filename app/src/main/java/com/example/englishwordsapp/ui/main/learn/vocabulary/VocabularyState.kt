package com.example.englishwordsapp.ui.main.learn.vocabulary

import androidx.paging.PagingData
import com.example.englishwordsapp.ui.main.learn.quiz.SimpleWordsModel


sealed class VocabularyState {
    data class Error(val errorException: String): VocabularyState()
    data class Success(val listOfWords: PagingData<SimpleWordsModel>): VocabularyState()
    class Loading(val isLoading: Boolean): VocabularyState()
}