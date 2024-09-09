package com.loeth.insight.data.datasource

import com.loeth.insight.data.entity.NewsResponse
import retrofit2.Response


interface NewsDataSource {
   suspend fun getNewsHeadline(country: String): Response<NewsResponse>
}