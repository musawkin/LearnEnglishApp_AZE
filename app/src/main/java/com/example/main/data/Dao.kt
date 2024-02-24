package com.example.main.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.main.data.Entity

@Dao
interface Dao {

    @Insert
    fun insertWord(item: Entity)

    @Query("SELECT * FROM  Table_for_words")
    fun getAllWords(): List<Entity>
}