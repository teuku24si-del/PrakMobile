package com.example.has_apps.data.api

import com.example.has_apps.data.model.CatFactModel
import retrofit2.http.GET

interface CatFactApiService {
        @GET("fact")
        suspend fun getCatFact(): CatFactModel
}
