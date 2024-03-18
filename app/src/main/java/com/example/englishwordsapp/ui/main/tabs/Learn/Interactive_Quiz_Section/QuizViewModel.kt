package com.example.englishwordsapp.ui.main.tabs.Learn.Interactive_Quiz_Section

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class QuizViewModel: ViewModel() {

    val questionModelData = MutableLiveData<QuizQuestionsResponseState>()
    private val listOfQuestionModel = mutableListOf<QuizQuestionsModel>()

    fun getQuestionList(wordsLevel: String){
        questionModelData.postValue(QuizQuestionsResponseState.Loading(true))
        viewModelScope.launch {
            withContext(Dispatchers.IO){

                val db = Firebase.firestore
                val docRef =
                    db.collection("wordsForQuiz")
                        .document(wordsLevel)
                        .collection("questionsModel")
                docRef.get().addOnSuccessListener { documents ->

                        for(document in documents){

                            val dataList = document.data
                            val question = dataList["question"] as String
                            val correctAnswer = dataList["correctAnswer"] as String
                            val answersList = dataList["answers"] as List<String>

                            listOfQuestionModel.add(QuizQuestionsModel(question,correctAnswer,answersList))
                        }
                        questionModelData.postValue(
                            QuizQuestionsResponseState.Success(
                                listOfQuestionModel
                            )
                        )

                    }.addOnFailureListener { exception ->
                        questionModelData.postValue(QuizQuestionsResponseState.Error("$exception"))
                    }
            }
        }

    }
}