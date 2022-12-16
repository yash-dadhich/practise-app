package com.charging.practiceapp.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.charging.practiceapp.database.DetailsRoomDB
import kotlinx.coroutines.runBlocking
import com.charging.practiceapp.model.Details

class HomeViewModel(val db: DetailsRoomDB) : ViewModel() {

    private val _listDetails:MutableLiveData<List<Details>> = MutableLiveData();

    val listDetails:LiveData<List<Details>> = _listDetails;

    fun getAllDetailsList(){
        runBlocking {
            val data:List<Details> = db.detailsDao().getAlphabetizedWords();
            _listDetails.value=data;
        }
    }
}

class HomeViewModelFactory(var db: DetailsRoomDB) :ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(db) as T
//            return super.create(modelClass)
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
