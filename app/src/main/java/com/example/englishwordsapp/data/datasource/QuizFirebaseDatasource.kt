package com.example.englishwordsapp.data.datasource

import com.example.englishwordsapp.data.model.QuizQuestionsResponse
import com.example.englishwordsapp.data.model.core.ResultWrapper
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import javax.inject.Inject

class QuizFirebaseDatasource @Inject constructor(
    private val db: FirebaseFirestore
): QuizDatasource {
    override suspend fun getQuestions(difficultyLevel: String): ResultWrapper<List<QuizQuestionsResponse>> {
        val docRef =
            db.collection("wordsForQuiz")
                .document(difficultyLevel)
                .collection("questionModel_map")
        val result = kotlin.runCatching {
            Tasks.await(docRef.get())
        }
        val data = result.getOrNull()?.toObjects(QuizQuestionsResponse::class.java).orEmpty()

        return ResultWrapper.Success(data)
    }
}