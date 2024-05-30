package com.example.englishwordsapp.data.paging

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.englishwordsapp.data.paging.WordsPagingSource
import com.example.englishwordsapp.ui.main.learn.SimpleWordsModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WordsPagingRepo @Inject constructor(
    private val wordsPagingSource: WordsPagingSource,
) {
    fun getPagedWords(): Flow<PagingData<SimpleWordsModel>> {
        val pagingConfig = PagingConfig(pageSize = 30, initialLoadSize = 30)
        val pager = Pager(
            config = pagingConfig,
            pagingSourceFactory = {wordsPagingSource}
        )
        return pager.flow
    }

}