package com.example.englishwordsapp.ui.Speech_Recognition_Section

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.englishwordsapp.SimpleWordsModel
import com.example.englishwordsapp.ui.Vocabulary_Section.VocabularyWordsResponseState
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SpeechRecognitionViewModel: ViewModel() {

    val wordsModelData = MutableLiveData<VocabularyWordsResponseState>()
    private val listOfWordsModel = mutableListOf<SimpleWordsModel>()

    fun getWordsList(){
        viewModelScope.launch {
            withContext(Dispatchers.IO){

                wordsModelData.postValue(VocabularyWordsResponseState.Loading(true))

                val db = Firebase.firestore
                val docRef =
                    db.collection("wordsForVocabulary").whereEqualTo("level", "beginner_level")
                docRef.get()
                    .addOnSuccessListener { documents ->

                        for(document in documents){

                            val dataList = document.data
                            val wordEng = dataList["word"] as String
                            val translation = dataList["translationToAze"] as String
                            val transcription = dataList["transcription"] as String
                            val partsOfSpeech = dataList["partOfSpeech"] as String
                            val level = dataList["level"] as String

                            listOfWordsModel.add(SimpleWordsModel(wordEng,translation,transcription,partsOfSpeech,level))

                        }
                        wordsModelData.postValue(VocabularyWordsResponseState.Success(listOfWordsModel))

                    }.addOnFailureListener { exception ->
                        wordsModelData.postValue(VocabularyWordsResponseState.Error("$exception"))
                    }
            }
        }

    }

}

