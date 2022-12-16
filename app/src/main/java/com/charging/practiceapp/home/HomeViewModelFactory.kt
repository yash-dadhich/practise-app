package com.charging.practiceapp.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras

/*@Suppress("unchecked_cast")
class HomeViewModelFactory(private val con: Context):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(con) as T
        }
        throw  IllegalArgumentException("Unknown ViewModel class");
    }
}*/

/*class HomeViewModelFactory(private val con: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(con) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}*/
/*
class NoteViewModelFactory(private var repository: NoteRepository):ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NoteViewModel::class.java)){
            return NoteViewModel(repository) as T
        }else{
            throw java.lang.IllegalArgumentException("unknown view model")
        }
    }
}
*/

