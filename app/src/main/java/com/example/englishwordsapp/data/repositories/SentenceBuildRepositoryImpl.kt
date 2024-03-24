package com.example.englishwordsapp.data.repositories

import com.example.englishwordsapp.data.model.core.ResultWrapper
import com.example.englishwordsapp.data.model.core.SentenceResponseModel
import com.example.englishwordsapp.ui.main.tabs.Learn.Sentence_Build_Section.SentenceModel
import com.example.englishwordsapp.ui.main.tabs.Learn.Sentence_Build_Section.SentenceModelResponseState
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.flow

class SentenceBuildRepositoryImpl: SentenceBuildRepository {
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