package com.example.englishwordsapp

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
object DI {

    @Provides
    fun provideQuizRepository(): QuizRepository{
        return QuizRepositoryImpl()
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

    //Test
//    @Singleton
//    @Provides
//    fun providesTransport(cars : Car): Transport{
//        return Transport(cars)
//    }
//
//    @Singleton
//    @Provides
//    fun providesCar(engine : Engine): Car{
//        return Car(engine)
//    }
//
//    @Singleton
//    @Provides
//    fun providesEngine(oil: Oil): Engine{
//        return Engine(oil)
//    }
//
//    @Singleton
//    @Provides
//    fun providesOil(name: String): Oil{
//        return Oil(name)
//    }
//
//    @Singleton
//    @Provides
//    fun providesString(): String{
//        return String()
//    }

}