package com.example.englishwordsapp.data.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.englishwordsapp.ui.main.learn.SimpleWordsModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.tasks.await

//class VocabularyPageSource(
//    private val db: FirebaseFirestore,
//): PagingSource<QuerySnapshot, SimpleWordsModel>() {
//
//
//
//
//
//}