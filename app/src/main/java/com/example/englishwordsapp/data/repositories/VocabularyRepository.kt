package com.example.englishwordsapp.data.repositories

import com.example.englishwordsapp.data.model.core.ResultWrapper
import com.example.englishwordsapp.ui.main.tabs.learn.SimpleWordsModel
import kotlinx.coroutines.flow.Flow

interface VocabularyRepository {

    suspend fun getWordsList(difficultyLevel: String): Flow<ResultWrapper<List<SimpleWordsModel>>?>
}