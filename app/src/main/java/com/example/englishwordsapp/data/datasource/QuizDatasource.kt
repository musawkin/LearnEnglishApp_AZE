package com.example.englishwordsapp.data.datasource

import com.example.englishwordsapp.data.model.QuizQuestionsResponse
import com.example.englishwordsapp.data.model.core.ResultWrapper

interface QuizDatasource {
    suspend fun getQuestions(difficultyLevel: String): ResultWrapper<List<QuizQuestionsResponse>>
}