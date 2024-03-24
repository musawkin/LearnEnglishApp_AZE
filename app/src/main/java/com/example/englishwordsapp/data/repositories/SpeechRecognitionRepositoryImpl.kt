package com.example.englishwordsapp.data.repositories

import android.util.Log
import com.example.englishwordsapp.data.model.core.ResultWrapper
import com.example.englishwordsapp.data.model.SimpleWordsResponse
import com.example.englishwordsapp.ui.main.tabs.Learn.SimpleWordsModel
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.flow

class SpeechRecognitionRepositoryImpl: SpeechRecognitionRepository {
    override suspend fun getWordsList(difficultyLevel: String) = flow<ResultWrapper<List<SimpleWordsModel>>?> {
        val db = Firebase.firestore
        val docRef =
            db.collection("wordsForVocabulary")
                .whereEqualTo("level", difficultyLevel)

        val result = kotlin.runCatching {
            Tasks.await(docRef.get())
        }
        val data = result.getOrNull()?.toObjects(SimpleWordsResponse::class.java)
        val mappedList = data?.map {
            SimpleWordsModel(
                word = it.word.orEmpty(),
                translationToAze = it.translationToAze.orEmpty(),
                transcription = it.transcription.orEmpty(),
                partOfSpeech = it.partOfSpeech.orEmpty(),
                level = it.level.orEmpty()
            )
        }.orEmpty()
        emit(ResultWrapper.Success(mappedList))
    }
}