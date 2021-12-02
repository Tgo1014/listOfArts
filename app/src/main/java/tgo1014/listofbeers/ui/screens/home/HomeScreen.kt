package tgo1014.listofbeers.ui.screens.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.loadingFlow
import com.google.accompanist.pager.ExperimentalPagerApi
import tgo1014.listofbeers.R
import tgo1014.listofbeers.ui.composables.BeerComposable
import tgo1014.listofbeers.ui.theme.Amber700

@OptIn(
    ExperimentalPagerApi::class,
    ExperimentalMaterial3Api::class,
    ExperimentalFoundationApi::class
)
@Composable
fun HomeScreen() {
    val viewModel = hiltViewModel<HomeViewModel>()
    val beers by viewModel.beersFlow.collectAsState()
    val isLoading by viewModel.loadingFlow.collectAsState()
    BoxWithConstraints {
        Scaffold(
            topBar = {
                SmallTopAppBar(
                    title = { Text(text = stringResource(id = R.string.app_name)) }
                )
            },
            content = {
                val cellSize = 170
                val grizSize = (maxWidth.value / cellSize).toInt()
                LazyVerticalGrid(
                    cells = GridCells.Fixed(grizSize),
                    contentPadding = PaddingValues(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    itemsIndexed(beers) { index, beer ->
                        BeerComposable(beer)
                        if (beers.lastIndex == index) {
                            SideEffect { viewModel.onBottomReached() }
                        }
                    }
                    if (isLoading) {
                        item {
                            Box(modifier = Modifier.size(cellSize.dp)) {
                                CircularProgressIndicator(
                                    color = Amber700,
                                    modifier = Modifier
                                        .size(40.dp)
                                        .align(Alignment.Center)
                                )
                            }
                        }
                    }
                }
            },
        )
    }
}