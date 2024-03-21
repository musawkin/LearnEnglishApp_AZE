package com.example.englishwordsapp.data.repositories

import com.example.englishwordsapp.data.model.core.ResultWrapper
import com.example.englishwordsapp.ui.main.tabs.Learn.Interactive_Quiz_Section.QuizQuestionsModel
import kotlinx.coroutines.flow.Flow

interface QuizRepository {
    suspend fun getQuestionList(wordsLevel: String): Flow<ResultWrapper<List<QuizQuestionsModel>>?>
}