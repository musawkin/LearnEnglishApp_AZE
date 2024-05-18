package com.example.englishwordsapp.ui.main.learn.vocabulary

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import com.example.englishwordsapp.data.model.core.ResultWrapper
import com.example.englishwordsapp.data.paging.WordsPagingRepo
import com.example.englishwordsapp.data.repositories.VocabularyRepositoryImpl
import com.example.englishwordsapp.ui.main.learn.SimpleWordsModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class VocabularyTranslationViewModel @Inject constructor(
    private val wordsPagingRepo: WordsPagingRepo
) : ViewModel() {
    val wordsModelData = MutableLiveData<VocabularyState>()

    var isLoading: Boolean = false
    var isLastPage: Boolean = false


    fun getWordsWithPaging() {
        isLoading = true
        wordsModelData.postValue(VocabularyState.Loading(true))
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                wordsPagingRepo.getPagedWords().collect {
                    wordsModelData.postValue(VocabularyState.Success(it))

                }
            }
        }
    }

}