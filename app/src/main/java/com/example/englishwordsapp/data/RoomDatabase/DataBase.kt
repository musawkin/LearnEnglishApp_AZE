package com.example.englishwordsapp.data.RoomDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Entity::class],
    version = 1
)
abstract class DataBase: RoomDatabase(){

    abstract fun getDao(): DaoForRoom

    companion object{
        fun getDb(context: Context): DataBase {
            return Room.databaseBuilder(
                context.applicationContext,
                DataBase::class.java,
                "my.DB"
            ).build()
        }
    }
}