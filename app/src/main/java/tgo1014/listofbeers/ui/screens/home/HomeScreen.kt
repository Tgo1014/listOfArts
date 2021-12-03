package tgo1014.listofbeers.ui.screens.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.navigationBarsHeight
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.google.accompanist.insets.ui.Scaffold
import com.google.accompanist.insets.ui.TopAppBar
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
fun HomeScreen() = Surface {
    val viewModel = hiltViewModel<HomeViewModel>()
    val beers by viewModel.beersFlow.collectAsState()
    val isLoading by viewModel.loadingFlow.collectAsState()
    Scaffold(
        topBar = { Toolbar() },
        bottomBar = { BottomSpacing() },
    ) { contentPadding ->
        BoxWithConstraints {
            val cellSize = 170
            val grizSize = (maxWidth.value / cellSize).toInt()
            LazyVerticalGrid(
                cells = GridCells.Fixed(grizSize),
                contentPadding = contentPadding,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                itemsIndexed(beers) { index, beer ->
                    BeerComposable(beer)
                    if (beers.lastIndex == index) {
                        SideEffect { viewModel.onBottomReached() }
                    }
                }
                if (isLoading) {
                    item {
                        Progress(Modifier.size(cellSize.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun Progress(modifier: Modifier) {
    Box(modifier = modifier) {
        CircularProgressIndicator(
            color = Amber700,
            modifier = Modifier
                .size(40.dp)
                .align(Alignment.Center)
        )
    }
}

@Composable
private fun Toolbar() {
    TopAppBar(
        title = { Text(stringResource(R.string.app_name)) },
        backgroundColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.95f),
        contentPadding = rememberInsetsPaddingValues(
            LocalWindowInsets.current.statusBars,
            applyBottom = false,
        ),
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
private fun BottomSpacing() {
    Spacer(
        modifier = Modifier
            .navigationBarsHeight()
            .fillMaxWidth()
    )
}