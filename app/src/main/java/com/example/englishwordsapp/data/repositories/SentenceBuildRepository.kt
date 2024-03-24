package com.example.englishwordsapp.data.repositories

import com.example.englishwordsapp.data.model.core.ResultWrapper
import com.example.englishwordsapp.ui.main.tabs.Learn.Sentence_Build_Section.SentenceModel
import kotlinx.coroutines.flow.Flow

interface SentenceBuildRepository {

    suspend fun getSentencesList(wordsLevel: String): Flow<ResultWrapper<List<SentenceModel>>?>
}