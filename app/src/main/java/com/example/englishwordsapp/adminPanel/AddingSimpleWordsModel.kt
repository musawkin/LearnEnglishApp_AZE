package com.example.englishwordsapp.adminPanel

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AddingSimpleWordsModel {

    fun addWords(){
        val db = Firebase.firestore

        val finalList = Words.listOfWords

        for(i in finalList){
            val wordData = hashMapOf(
                "level" to i.level,
                "partOfSpeech" to i.partOfSpeech,
                "transcription" to i.transcription,
                "translationToAze" to i.translationToAze,
                "word" to i.word
            )

//            db.collection("wordsForVocabulary")
//                .add(wordData)
//                .addOnSuccessListener { documentReference ->
//                    Log.d("Musa","Error mesage :$documentReference")
//                }
//                .addOnFailureListener { e ->
//                    Log.d("Musa","Error mesage :$e")
//                }
        }
    }
}