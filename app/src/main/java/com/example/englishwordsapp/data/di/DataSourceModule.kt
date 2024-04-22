package com.example.englishwordsapp.data.di

import com.example.englishwordsapp.data.datasource.QuizDatasource
import com.example.englishwordsapp.data.datasource.QuizFirebaseDatasource
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn (SingletonComponent::class)
object DataSourceModule {

    @Provides
    fun provideQuizDatasource(firebaseFirestore: FirebaseFirestore): QuizDatasource{
        return QuizFirebaseDatasource(firebaseFirestore)
    }
}