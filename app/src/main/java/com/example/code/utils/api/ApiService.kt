package com.example.code.utils.api

import retrofit2.Response
import retrofit2.http.*
import com.example.code.utils.api.body.jeans.JeansResponseBody

interface ApiService {

    @GET("jeans-default.json")
    suspend fun getProducts(): Response<JeansResponseBody>


}


