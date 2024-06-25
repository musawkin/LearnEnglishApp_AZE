package com.example.englishwordsapp.ui.main.learn.vocabulary

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.englishwordsapp.data.paging.WordsPagingRepo
import com.example.englishwordsapp.ui.main.learn.SimpleWordsModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class VocabularyTranslationViewModel @Inject constructor(
    private val wordsPagingRepo: WordsPagingRepo
) : ViewModel() {
    val wordsModelData = MutableLiveData<VocabularyState>()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    fun getWordsWithPaging() {
        wordsModelData.postValue(VocabularyState.Loading(true))
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val cachedIn = wordsPagingRepo.getPagedWords().cachedIn(coroutineScope)
                    cachedIn.collect {
                    wordsModelData.postValue(VocabularyState.Success(it))
                }
            }
        }
    }

}