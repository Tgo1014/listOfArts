package tgo1014.listofbeers.ui.screens.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.loadingFlow
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.navigationBarsHeight
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.google.accompanist.insets.ui.Scaffold
import com.google.accompanist.pager.ExperimentalPagerApi
import tgo1014.listofbeers.R
import tgo1014.listofbeers.ui.composables.BeerComposable
import tgo1014.listofbeers.ui.composables.InsetLargeTopAppBar
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
    val lazyState = rememberLazyListState()
    val scrollBehavior = remember { TopAppBarDefaults.enterAlwaysScrollBehavior() }
    Scaffold(
        topBar = { Toolbar(scrollBehavior) },
        bottomBar = { BottomSpacing() },
        backgroundColor = MaterialTheme.colorScheme.background
    ) { contentPadding ->
        BoxWithConstraints {
            val cellSize = 170
            val gridSize = (maxWidth.value / cellSize).toInt()
            LazyColumn(
                state = lazyState,
                contentPadding = contentPadding,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .nestedScroll(scrollBehavior.nestedScrollConnection)
            ) {
                item { Filter() }
                items(beers.chunked(gridSize)) { chunk ->
                    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        val modifier = Modifier.weight(1f)
                        chunk.forEach { beer ->
                            BeerComposable(beer, modifier)
                            if (beer == beers.lastOrNull()) {
                                SideEffect { viewModel.onBottomReached() }
                            }
                        }
                        // This avoids the last row to take all the space istead of just 1 "grid block"
                        for (i in 1..(gridSize - chunk.size)) {
                            Spacer(modifier)
                        }
                    }
                }
                item {
                    if (isLoading) {
                        Progress(Modifier.fillMaxWidth())
                    }
                }
            }
        }
    }
}

@Composable
private fun Filter() = Row {
    Text(text = "_Filter:_")
    FilledTonalButton(onClick = { /*TODO*/ }) {
        Text(text = "Init2")
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
private fun Toolbar(scrollBehavior: TopAppBarScrollBehavior) {
    InsetLargeTopAppBar(
        title = { Text(stringResource(R.string.app_name)) },
        backgroundColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.95f),
        modifier = Modifier.fillMaxWidth(),
        scrollBehavior = scrollBehavior,
        contentPadding = rememberInsetsPaddingValues(
            LocalWindowInsets.current.statusBars,
            applyBottom = false,
        ),
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