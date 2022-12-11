package tgo1014.listofbeers.presentation.ui.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.add
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch
import tgo1014.listofbeers.presentation.models.BeerUi
import tgo1014.listofbeers.presentation.models.Filter
import tgo1014.listofbeers.presentation.ui.composables.BeerComposable
import tgo1014.listofbeers.presentation.ui.composables.EmptyState
import tgo1014.listofbeers.presentation.ui.composables.LogoText
import tgo1014.listofbeers.presentation.ui.composables.SearchBar
import tgo1014.listofbeers.presentation.ui.composables.SingleSelectionFilter
import tgo1014.listofbeers.presentation.ui.composables.previews.DefaultPreview
import tgo1014.listofbeers.presentation.ui.composables.previews.DevicePreviews
import tgo1014.listofbeers.presentation.ui.theme.ListOfBeersTheme

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onBeerClicked: (BeerUi) -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val keyboardController = LocalSoftwareKeyboardController.current
    HomeScreen(
        state = state,
        onBeerClicked = {
            keyboardController?.hide()
            onBeerClicked(it)
        },
        onBottomOfScreenReached = viewModel::onBottomReached,
        onQueryChanged = viewModel::search,
        onFilterClicked = viewModel::onFilterClicked,
        onRetryClicked = { viewModel.fetchBeers() }
    )
}

@Composable
private fun HomeScreen(
    state: HomeState,
    onBottomOfScreenReached: () -> Unit = {},
    onBeerClicked: (BeerUi) -> Unit = {},
    onQueryChanged: (String) -> Unit = {},
    onFilterClicked: (Filter) -> Unit = {},
    onRetryClicked: () -> Unit = {}
) {

    val lazyState = rememberLazyGridState()
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        containerColor = MaterialTheme.colorScheme.secondaryContainer,
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = { LogoText() },
                scrollBehavior = scrollBehavior,
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primaryContainer,
                )
            )
        }
    ) {
        LazyVerticalGrid(
            state = lazyState,
            contentPadding = WindowInsets.navigationBars
                .add(WindowInsets(top = 16.dp))
                .add(WindowInsets(top = it.calculateTopPadding())) // TopAppBar
                .asPaddingValues(),
            columns = GridCells.Adaptive(minSize = 300.dp),
            modifier = Modifier.imePadding()
        ) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                SearchBar(
                    query = state.searchText,
                    onQueryChanged = { query ->
                        coroutineScope.launch {
                            lazyState.scrollToItem(0)
                        }
                        onQueryChanged(query)
                    },
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
            item(span = { GridItemSpan(maxLineSpan) }) {
                SingleSelectionFilter(
                    filters = state.filters,
                    onClick = { filter -> onFilterClicked(filter) },
                    contentPadding = PaddingValues(horizontal = 16.dp)
                )
            }
            if (state.beerList.isEmpty() && !state.isLoading) {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    EmptyState(onRetryClicked = onRetryClicked)
                }
            }
            itemsIndexed(
                items = state.beerList,
                key = { _, beer -> beer.id }
            ) { index, beer ->
                BeerComposable(
                    beer = beer,
                    modifier = Modifier
                        .clickable { onBeerClicked(beer) }
                        .padding(16.dp)
                        .animateItemPlacement()
                )
                if (index == state.beerList.lastIndex) {
                    SideEffect { onBottomOfScreenReached() }
                } else {
                    Divider(Modifier.padding(start = 24.dp))
                }
            }
            if (state.isLoading) {
                item {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        CircularProgressIndicator(modifier = Modifier.size(40.dp))
                    }
                }
            }
        }
    }
}

private val PreviewBeer = BeerUi(
    name = "Punk IPA 2007 - 2010",
    imageUrl = "https://images.punkapi.com/v2/192.png",
    tagline = "Post Modern Classic. Spiky. Tropical. Hoppy.",
    description = "Our flagship beer that kick started the craft beer revolution. This is James and Martin's original take on an American IPA, subverted with punchy New Zealand hops. Layered with new world hops to create an all-out riot of grapefruit, pineapple and lychee before a spiky, mouth-puckering bitter finish."
)

@DefaultPreview
@Composable
private fun HomeScreenPreviewEmpty() = ListOfBeersTheme {
    HomeScreen(state = HomeState())
}

@DefaultPreview
@DevicePreviews
@Composable
private fun HomeScreenPreview() = ListOfBeersTheme {
    val beerList = List(3) { PreviewBeer.copy(id = it) }
    HomeScreen(state = HomeState(isLoading = true, beerList = beerList))
}
