package com.example.englishwordsapp.adminPanel

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AddingSimpleWordsModel {

//     для загрузски слов в раздел словарь
    fun addWordsToVocabulary(){


        val db = Firebase.firestore

        val finalList = WordsForVocabulary.listOfAdvanceWordsForVocabulary

        for(i in finalList){
            val wordData = hashMapOf(
                "level" to i.level,
                "partOfSpeech" to i.partOfSpeech,
                "transcription" to i.transcription,
                "translationToAze" to i.translationToAze,
                "word" to i.word
            )

            db.collection("wordsForVocabulary")
                .add(wordData)
                .addOnSuccessListener { documentReference ->
                    Log.d("Musa","Error mesage :$documentReference")
                }
                .addOnFailureListener { e ->
                    Log.d("Musa","Error mesage :$e")
                }
        }
    }


//     для загрузски слов в раздел квиз
    fun addWordsToQuiz(){
        val db = Firebase.firestore

//        val finalList =

//        for(i in finalList){
//            val wordData = hashMapOf(
//                "answer" to i.answer,
//                "question" to i.question,
//                "variants" to i.variants
//            )
//
//            db.collection("wordsForQuiz")
//                .document("advance_level")
//                .collection("questionsForQuiz")
//                .add(wordData)
//                .addOnSuccessListener { documentReference ->
//                    Log.d("Musa","Error mesage :$documentReference")
//                }
//                .addOnFailureListener { e ->
//                    Log.d("Musa","Error mesage :$e")
//                }
//        }
    }



}