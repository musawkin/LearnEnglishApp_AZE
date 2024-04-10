package com.example.englishwordsapp.data.repositories

import com.example.englishwordsapp.data.model.QuizQuestionsResponse
import com.example.englishwordsapp.data.model.core.ResultWrapper
import com.example.englishwordsapp.ui.main.tabs.learn.quiz.QuizQuestionsModel
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class QuizRepositoryImpl @Inject constructor(): QuizRepository {

    override suspend fun getQuestionList(difficultyLevel: String) = flow<ResultWrapper<List<QuizQuestionsModel>>?> {
        val db = Firebase.firestore
        val docRef =
            db.collection("wordsForQuiz")
                .document(difficultyLevel)
                .collection("questionsModel")
        val result = kotlin.runCatching {
            Tasks.await(docRef.get())
        }
        val data = result.getOrNull()?.toObjects(QuizQuestionsResponse::class.java)
        val mappedList = data?.map {
            QuizQuestionsModel(
                question = it.question.orEmpty(),
                correctAnswer = it.correctAnswer.orEmpty(),
                answers = it.answers.orEmpty()
            )
        }.orEmpty()
        emit(ResultWrapper.Success(mappedList))
    }
}