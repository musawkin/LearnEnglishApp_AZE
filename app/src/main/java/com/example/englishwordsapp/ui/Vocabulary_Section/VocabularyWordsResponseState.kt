package com.example.englishwordsapp.ui.Vocabulary_Section

import com.example.englishwordsapp.SimpleWordsModel
import com.example.englishwordsapp.ui.Interactive_Quiz_Section.QuizQuestionsModel


sealed class VocabularyWordsResponseState {
    data class Error(val errorException: String): VocabularyWordsResponseState()
    data class Success(val listOfQuestions: List<SimpleWordsModel>): VocabularyWordsResponseState()
    class Loading(val isLoading: Boolean): VocabularyWordsResponseState()
}