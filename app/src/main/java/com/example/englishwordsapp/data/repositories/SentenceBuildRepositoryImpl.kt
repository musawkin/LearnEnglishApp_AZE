package com.example.englishwordsapp.data.repositories

import com.example.englishwordsapp.data.model.core.ResultWrapper
import com.example.englishwordsapp.data.model.SentenceResponseModel
import com.example.englishwordsapp.ui.main.learn.sentenceBuild.SentenceModel
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SentenceBuildRepositoryImpl @Inject constructor(): SentenceBuildRepository {
    override suspend fun getSentencesList(wordsLevel: String) = flow<ResultWrapper<List<SentenceModel>>?> {
        val db = Firebase.firestore
        val docRef =
            db.collection("sentences")
                .document(wordsLevel)
                .collection("sentence_model")

        val result = kotlin.runCatching {
            Tasks.await(docRef.get())
        }
        val data = result.getOrNull()?.toObjects(SentenceResponseModel::class.java)
        val mappedList = data?.map {
            SentenceModel(
                question = it.question.orEmpty(),
                answerWordsList = it.answerWordsList.orEmpty()
            )
        }.orEmpty()
        emit(ResultWrapper.Success(mappedList))
    }
}