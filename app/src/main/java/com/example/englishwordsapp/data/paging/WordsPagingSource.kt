package com.example.englishwordsapp.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.englishwordsapp.ui.main.learn.SimpleWordsModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class WordsPagingSource @Inject constructor(
    private val db: FirebaseFirestore
) : PagingSource<QuerySnapshot, SimpleWordsModel>() {


    override fun getRefreshKey(state: PagingState<QuerySnapshot, SimpleWordsModel>): QuerySnapshot? {
        return null
    }

    override suspend fun load(params: LoadParams<QuerySnapshot>): LoadResult<QuerySnapshot, SimpleWordsModel> {
        return try {
            val currentPage = params.key ?: db.collection("wordsForVocabulary")
                .limit(30)
                .get()
                .await()

            val lastDocumentSnapshot = currentPage.documents[currentPage.size() - 1]

            val nextPage = db.collection("wordsForVocabulary")
                .limit(30)
                .startAfter(lastDocumentSnapshot)
                .get()
                .await()

            LoadResult.Page(
                data = currentPage.toObjects(SimpleWordsModel::class.java),
                prevKey = null,
                nextKey = nextPage
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}