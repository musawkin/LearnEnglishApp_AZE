package com.example.englishwordsapp

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface Dao {

    @Insert
    fun insertWord(item: Entity)

    @Query("SELECT * FROM  Table_for_words")
    fun getAllWords(): List<Entity>
}