package com.example.location_detector

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

class LocationApiHelperImpl(private val apiservice:LocationApiservice):LocationApiHelper{
    override fun getlocations(): Flow<Response<List<Location>>> =flow{
      emit(apiservice.getlocations())
    }

}