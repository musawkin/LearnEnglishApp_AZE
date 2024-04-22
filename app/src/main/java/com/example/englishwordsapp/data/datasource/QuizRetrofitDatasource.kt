package com.example.englishwordsapp.data.datasource

import com.example.englishwordsapp.data.model.QuizQuestionsResponse
import com.example.englishwordsapp.data.model.core.ResultWrapper

class QuizRetrofitDatasource: QuizDatasource {
    override suspend fun getQuestions(difficultyLevel: String): ResultWrapper<List<QuizQuestionsResponse>> {
        TODO("Not yet implemented")
    }
}