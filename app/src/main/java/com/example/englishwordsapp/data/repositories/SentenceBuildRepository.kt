package com.example.englishwordsapp.data.repositories

import com.example.englishwordsapp.data.model.core.ResultWrapper
import com.example.englishwordsapp.ui.main.learn.sentenceBuild.SentenceModel
import kotlinx.coroutines.flow.Flow

interface SentenceBuildRepository {

    suspend fun getSentencesList(wordsLevel: String): Flow<ResultWrapper<List<SentenceModel>>?>
}