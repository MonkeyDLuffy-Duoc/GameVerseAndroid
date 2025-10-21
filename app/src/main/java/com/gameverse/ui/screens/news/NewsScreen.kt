package com.gameverse.ui.screens.news

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gameverse.ui.components.FullScreenLoader
import com.gameverse.ui.components.NewsCard
import com.gameverse.viewmodel.MainViewModel

@Composable
fun NewsScreen(mainViewModel: MainViewModel = viewModel()) {
    val uiState by mainViewModel.uiState.collectAsState()

    if (uiState.isLoading) {
        FullScreenLoader()
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(uiState.news) { newsItem ->
                NewsCard(newsItem = newsItem)
            }
        }
    }
}