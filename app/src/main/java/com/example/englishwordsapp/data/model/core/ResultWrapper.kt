package com.example.englishwordsapp.data.model.core

sealed class ResultWrapper <out T> {
    data class Success <T>(val data: T): ResultWrapper<T>()
    data class Error (val error: String?): ResultWrapper<Nothing>()
}