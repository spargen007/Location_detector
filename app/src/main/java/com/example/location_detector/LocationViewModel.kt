package com.example.location_detector

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class LocationViewModel(val locationApiHelperImpl:LocationApiHelperImpl): ViewModel() {
    private val _locationList = MutableLiveData<List<Location>>()
    val locationList = _locationList


    init {
        println("paul1")
        fetchLocation()
        println("paul2")
    }
    fun refresh(){
        fetchLocation()
    }

    private fun fetchLocation() {

        viewModelScope.launch {
            locationApiHelperImpl.getlocations().flowOn(Dispatchers.IO)
                .catch { e ->
                    println("errprrong$e")
                }
                .collect {
                    _locationList.value = it.body()
                }
            println(locationList.value)

        }


    }
}
class MyViewModelFactory constructor(private val locationApiHelperImpl: LocationApiHelperImpl ): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(LocationViewModel::class.java)) {
            LocationViewModel(this.locationApiHelperImpl) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}