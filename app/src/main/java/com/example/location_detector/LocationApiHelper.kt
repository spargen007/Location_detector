package com.example.location_detector

import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface LocationApiHelper {
    fun getlocations(): Flow<Response<List<Location>>>
}