package com.example.englishwordsapp.data.di

import com.example.englishwordsapp.data.datasource.QuizDatasource
import com.example.englishwordsapp.data.repositories.QuizRepository
import com.example.englishwordsapp.data.repositories.QuizRepositoryImpl
import com.example.englishwordsapp.data.repositories.SentenceBuildRepository
import com.example.englishwordsapp.data.repositories.SentenceBuildRepositoryImpl
import com.example.englishwordsapp.data.repositories.SpeechRecognitionRepository
import com.example.englishwordsapp.data.repositories.SpeechRecognitionRepositoryImpl
import com.example.englishwordsapp.data.repositories.VocabularyRepository
import com.example.englishwordsapp.data.repositories.VocabularyRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideQuizRepository(quizDatasource: QuizDatasource): QuizRepository{
        return QuizRepositoryImpl(quizDatasource)
    }

    @Provides
    fun provideSentenceBuildRepository(): SentenceBuildRepository{
        return SentenceBuildRepositoryImpl()
    }

    @Provides
    fun provideSpeechRecognitionRepository(): SpeechRecognitionRepository{
        return SpeechRecognitionRepositoryImpl()
    }

    @Provides
    fun provideVocabularyRepository(): VocabularyRepository{
        return VocabularyRepositoryImpl()
    }


}