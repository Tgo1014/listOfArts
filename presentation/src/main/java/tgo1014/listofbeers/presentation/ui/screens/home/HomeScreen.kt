package tgo1014.listofbeers.presentation.ui.screens.home

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import tgo1014.listofbeers.presentation.models.BeerUi
import tgo1014.listofbeers.presentation.models.Filter
import tgo1014.listofbeers.presentation.ui.composables.EmptyState
import tgo1014.listofbeers.presentation.ui.composables.LogoText
import tgo1014.listofbeers.presentation.ui.composables.PrimaryContainerFilterChip
import tgo1014.listofbeers.presentation.ui.composables.SearchFab
import tgo1014.listofbeers.presentation.ui.composables.SearchFabState
import tgo1014.listofbeers.presentation.ui.composables.previews.DefaultPreview
import tgo1014.listofbeers.presentation.ui.composables.providers.ThemeProvider
import tgo1014.listofbeers.presentation.ui.theme.ListOfBeersTheme

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onBeerClicked: (BeerUi) -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val keyboardController = LocalSoftwareKeyboardController.current
    LaunchedEffect(Unit) {
        viewModel.fetchBeers()
    }
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun HomeScreen(
    state: HomeState,
    onBottomOfScreenReached: () -> Unit = {},
    onBeerClicked: (BeerUi) -> Unit = {},
    onQueryChanged: (String) -> Unit = {},
    onFilterClicked: (Filter) -> Unit = {},
    onRetryClicked: () -> Unit = {}
) {
    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            var fabState by remember { mutableStateOf(SearchFabState.FAB) }
            SearchFab(
                searchText = state.searchText,
                buttonState = fabState,
                isLoading = state.isLoading,
                onSearchTextChanged = onQueryChanged,
                onCloseClicked = {
                    fabState = SearchFabState.FAB
                    onQueryChanged("")
                },
                onButtonClicked = {
                    fabState = if (fabState == SearchFabState.FAB) SearchFabState.SEARCH else SearchFabState.FAB
                },
                modifier = Modifier.imePadding()
            )
        },
        content = {
            val spacing = 2.dp
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Adaptive(130.dp),
                contentPadding = PaddingValues(spacing),
                verticalItemSpacing = spacing,
                horizontalArrangement = Arrangement.spacedBy(spacing),
                modifier = Modifier.padding(it)
            ) {
                item(span = StaggeredGridItemSpan.FullLine) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.primaryContainer)
                            .padding(WindowInsets.statusBars.asPaddingValues())
                    ) {
                        LogoText(
                            textColor = MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(top = 16.dp, start = 16.dp, bottom = 8.dp)
                        )
                    }
                }
                if (state.itemList.isEmpty() && !state.isLoading) {
                    item(span = StaggeredGridItemSpan.FullLine) {
                        EmptyState(onRetryClicked = onRetryClicked)
                    }
                }
                itemsIndexed(
                    items = state.itemList,
                    key = { _, item -> item.id }
                ) { index, item ->
                    val ratio = remember {
                        val height = item.imageHeight
                        var r = 1f
                        if (height > 0) {
                            r = item.imageWidth.toFloat() / height
                        }
                        r
                    }
                    val bgColor = MaterialTheme.colorScheme.background
                    SubcomposeAsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(item.imageUrl)
                            .crossfade(300)
                            .build(),
                        contentDescription = null,
                        contentScale = ContentScale.FillWidth,
                        loading = {
                            Box(
                                modifier = Modifier
                                    .background(bgColor)
                                    .aspectRatio(ratio)
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(bgColor)
                            .aspectRatio(ratio)
                            .animateItemPlacement()
                            .animateContentSize(),
                    )
                    // TODO add title of the piece
                    if (index == state.itemList.lastIndex) {
                        SideEffect { onBottomOfScreenReached() }
                    }
                }
            }
        }
    )
}

@DefaultPreview
@Composable
private fun HomeScreenPreview(
    @PreviewParameter(ThemeProvider::class) materialYouColors: Boolean
) = ListOfBeersTheme(materialYouColors = materialYouColors) {
    HomeScreen(state = HomeState())
}