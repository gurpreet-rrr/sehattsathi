package com.example.sehattsathi.common

sealed class ResultState<out T>{
    // out t means result can be view but cant be inserted
    //The keyword out is used to specify that T is only produced (returned) from the class, not consumed (passed in).

    data class Success<out T>(val data: T) : ResultState<T>()

    data class Error(val message : String) : ResultState<Nothing>()
    object Loading : ResultState<Nothing>()
    object Empty : ResultState<Nothing>()
}