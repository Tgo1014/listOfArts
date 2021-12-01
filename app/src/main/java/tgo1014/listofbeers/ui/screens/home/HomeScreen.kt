package tgo1014.listofbeers.ui.screens.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import tgo1014.listofbeers.ui.composables.BeerComposable

@Composable
fun HomeScreen() {
    val viewModel = hiltViewModel<HomeViewModel>()
    val beers by viewModel.beersFlow.collectAsState()
    Surface(Modifier.fillMaxSize()) {
        LazyColumn(Modifier.fillMaxSize()) {
            items(beers) {
                BeerComposable(beer = it)
            }
        }
    }
}