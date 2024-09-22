package com.loeth.insight.ui.screens

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.loeth.insight.ui.components.EmptyStateComponent
import com.loeth.insight.ui.components.Loader
import com.loeth.insight.ui.components.NewsList
import com.loeth.insight.ui.components.NewsRowComponent
import com.loeth.insight.ui.viewmodel.NewsViewModel
import com.loeth.utilities.ResourceState


const val TAG = "Home Screen"
@Composable
fun HomeScreen(
    newsViewModel: NewsViewModel = hiltViewModel()
) {
    val newsRes by newsViewModel.news.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        when(newsRes) {
            is ResourceState.Loading -> {
                Log.d(TAG, "Inside Loading")
                Loader()
            }

            is ResourceState.Success -> {
                val response = (newsRes as ResourceState.Success).data

                Log.d(TAG, "Inside Success: ${response.status} = ${response.totalResults}")

                if (response.articles.isNotEmpty()) {
                    LazyRow(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(horizontal = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(response.articles) { article ->

                            Log.d(TAG, "Inside Success: ${response.status} = ${response.totalResults}")
                            NewsRowComponent(article = article)
                        }
                    }
                } else {
                    EmptyStateComponent()
                }
            }

            is ResourceState.Error -> {
                val error = (newsRes as ResourceState.Error)
                Log.d(TAG, "Inside Error: $error")
                EmptyStateComponent() // You can display an empty state here
            }
        }
    }
}




@Preview
@Composable
fun HomeScreenPreview(){
    HomeScreen()
}
