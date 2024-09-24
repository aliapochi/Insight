package com.loeth.insight.data.api

import com.loeth.insight.data.entity.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query



interface ApiService {

@GET("v2/top-headlines")
    suspend fun getNewsHeadline(
        @Query("country") country: String,
        @Query("apiKey") apiKey:String = Api_Key.api_key
    ) : Response<NewsResponse>
}
