package com.example.englishwordsapp.data.roomDatabase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "Table_for_words"
)
class Entity(

    @PrimaryKey(autoGenerate = true)
    val id: Int?,

    @ColumnInfo
    val wordInEnglish: String?,

    @ColumnInfo
    val wordInRussian: String?,

    @ColumnInfo
    val wordTranscription: String?,

    @ColumnInfo
    val pronunciation: String?,


) {


}