package com.example.englishwordsapp.ui.main.tabs.Learn.Sentence_Build_Section

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SentenceBuildViewModel : ViewModel() {

    val questionModelData = MutableLiveData<SentenceModelResponseState>()
    private val listOfSentenceModel = mutableListOf<SentenceModel>()
    fun getSentenceModel() {
        questionModelData.postValue(SentenceModelResponseState.Loading(true))
        viewModelScope.launch {
            withContext(Dispatchers.IO) {

                val db = Firebase.firestore
                val docRef =
                    db.collection("sentences")
                        .document("beginner_level")
                        .collection("sentences_beginner_level")

                docRef.get()
                    .addOnSuccessListener { documents ->

                        for (document in documents) {
                            val data = document.data
                            val question = data["question"] as String
                            val answerWordsList = data["answerWordsList"] as List<String>

                            listOfSentenceModel.add(SentenceModel(question, answerWordsList))
                        }
                        questionModelData.postValue(
                            SentenceModelResponseState.Success(
                                listOfSentenceModel
                            )
                        )

                }.addOnFailureListener { exception ->
                    questionModelData.postValue(SentenceModelResponseState.Error("$exception"))

                }
            }
        }
    }
}