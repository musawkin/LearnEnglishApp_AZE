package com.example.englishwordsapp.data.repositories

import com.example.englishwordsapp.data.model.core.ResultWrapper
import com.example.englishwordsapp.ui.main.learn.quiz.QuizQuestionsModel
import kotlinx.coroutines.flow.Flow

interface QuizRepository {
    suspend fun getQuestionList(difficultyLevel: String): Flow<ResultWrapper<List<QuizQuestionsModel>>?>
}