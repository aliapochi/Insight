package com.loeth.insight.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.loeth.insight.data.AppConstants
import com.loeth.insight.data.entity.NewsResponse
import com.loeth.insight.ui.repository.NewsRepository
import com.loeth.utilities.ResourceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    val newsRepository: NewsRepository
): ViewModel() {

    private val _news : MutableStateFlow<ResourceState<NewsResponse>> = MutableStateFlow(ResourceState.Loading())
    val news : StateFlow<ResourceState<NewsResponse>> = _news

    init{
        getNews(AppConstants.COUNTRY)
    }
     private fun getNews(country: String){
        viewModelScope.launch(Dispatchers.IO){
            newsRepository.getNewsHeadline(country)
                .collectLatest { newsResponse ->
                   _news.value = newsResponse
                }

        }
    }

    companion object{
        const val TAG = "NewsViewModel"
    }
}