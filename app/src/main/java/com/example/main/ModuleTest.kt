package com.example.main

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ModuleTest {

    @Singleton
    @Provides
    fun providesTransport(cars : Car): Transport{
        return Transport(cars)
    }

    @Singleton
    @Provides
    fun providesCar(engine : Engine): Car{
        return Car(engine)
    }

    @Singleton
    @Provides
    fun providesEngine(oil: Oil): Engine{
        return Engine(oil)
    }

    @Singleton
    @Provides
    fun providesOil(name: String): Oil{
        return Oil(name)
    }

    @Singleton
    @Provides
    fun providesString(): String{
        return String()
    }






}