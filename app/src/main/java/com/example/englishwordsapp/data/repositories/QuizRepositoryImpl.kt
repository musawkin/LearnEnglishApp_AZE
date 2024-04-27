package com.example.englishwordsapp.data.repositories

import com.example.englishwordsapp.data.datasource.QuizDatasource
import com.example.englishwordsapp.data.model.core.ResultWrapper
import com.example.englishwordsapp.ui.main.learn.quiz.QuizQuestionsModel
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class QuizRepositoryImpl @Inject constructor(
    private val quizDatasource: QuizDatasource
): QuizRepository {

    override suspend fun getQuestionList(difficultyLevel: String) = flow<ResultWrapper<List<QuizQuestionsModel>>?> {
        when(val result = quizDatasource.getQuestions(difficultyLevel)){
            is ResultWrapper.Success ->{
                val mappedList = result.data.map {
                    QuizQuestionsModel(
                        question = it.question.orEmpty(),
                        variants = it.variants.orEmpty()
                    )
                }
                emit(ResultWrapper.Success(mappedList))
            }
            is ResultWrapper.Error ->{

            }
        }
    }
}