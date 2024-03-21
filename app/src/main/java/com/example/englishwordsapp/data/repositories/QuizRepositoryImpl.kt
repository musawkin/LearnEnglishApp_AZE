package com.example.englishwordsapp.data.repositories

import android.util.Log
import com.example.englishwordsapp.data.model.QuizQuestionsResponse
import com.example.englishwordsapp.data.model.core.ResultWrapper
import com.example.englishwordsapp.ui.main.tabs.Learn.Interactive_Quiz_Section.QuizQuestionsModel
import com.example.englishwordsapp.ui.main.tabs.Learn.Interactive_Quiz_Section.QuizQuestionsResponseState
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.flow

class QuizRepositoryImpl: QuizRepository {

    override suspend fun getQuestionList(wordsLevel: String) = flow<ResultWrapper<List<QuizQuestionsModel>>?> {
        val db = Firebase.firestore
        val docRef =
            db.collection("wordsForQuiz")
                .document(wordsLevel)
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