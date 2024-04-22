package com.example.englishwordsapp.data.di

import com.example.englishwordsapp.data.datasource.QuizDatasource
import com.example.englishwordsapp.data.datasource.QuizFirebaseDatasource
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn (SingletonComponent::class)
object FirebaseModule {

    @Provides
    fun provideFirestore(): FirebaseFirestore{
        return Firebase.firestore
    }
}