package com.example.yolla_v3.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.yolla_v3.repostiory.ItemData
import com.example.yolla_v3.repostiory.MainRepository
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private var isLoading : MutableLiveData<Boolean> = MutableLiveData()
    val _isLoading : LiveData<Boolean> = isLoading

    private val myDataSet : MutableLiveData<ArrayList<ItemData>> = MutableLiveData(ArrayList())
    private var myRepo : MainRepository = MainRepository(application)
    init {
        syncArraySet()
    }

    fun syncArraySet() {
        viewModelScope.launch {
            myDataSet.postValue(myRepo.jsonStringToArray())
        }

    }

    fun getMyDataSet() : LiveData<ArrayList<ItemData>> = myDataSet

    fun addItem(itemData: ItemData) {
        viewModelScope.launch {
            myDataSet.value?.let {
                myRepo. arrayToJsonString((it + ItemData(itemData.name, itemData.phoneNum, null)) as ArrayList<ItemData>)
                it.add(itemData)
            }
        }

    }

    fun checkAndAddContact(itemData: ItemData) : Boolean {
        myDataSet.value?.let {
            for (itemContact in it) {
                if (itemData.name == itemContact.name
                    && itemData.phoneNum == itemContact.phoneNum) {

                    itemContact.status = true
                    return true
                }
            }
            itemData.status = false
            addItem(itemData)
        }
        return false
    }

    fun getContactByPosition(position: Int) : ItemData? {
        myDataSet.value?.let {
            if (it.size > 0) {
                return it[position]
            }
        }
        return null
    }
}