package com.example.englishwordsapp.data.repositories

import android.util.Log
import com.example.englishwordsapp.data.model.SimpleWordsResponse
import com.example.englishwordsapp.data.model.core.ResultWrapper
import com.example.englishwordsapp.ui.main.learn.SimpleWordsModel
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class VocabularyRepositoryImpl @Inject constructor(): VocabularyRepository {

    override suspend fun getWordsList(difficultyLevel: String) = flow<ResultWrapper<List<SimpleWordsModel>>?> {
        val db = Firebase.firestore
        val docRef = db.collection("wordsForVocabulary")
            .limit(3.toLong())
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

    suspend fun loadNextPageOfWords(difficultyLevel: String, lastWord: SimpleWordsModel?) = flow<ResultWrapper<List<SimpleWordsModel>>?> {
        val db = Firebase.firestore
        var docRef = db.collection("wordsForVocabulary")
            .orderBy("word")
            .limit(3.toLong()) // Установка размера страницы

        lastWord?.let {
            docRef = docRef.startAfter(it.word) // Присваиваем новый запрос с использованием startAfter
        }
        Log.d("MUSA!!!", "loadNextPageOfWords:${lastWord?.word} ")

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