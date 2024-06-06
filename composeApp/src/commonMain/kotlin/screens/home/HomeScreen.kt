package screens.home

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.LocalPlatformContext
import coil3.compose.SubcomposeAsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter
import org.koin.compose.viewmodel.koinViewModel
import presentation.composables.EmptyState
import presentation.composables.LogoText
import presentation.composables.SearchFab
import presentation.composables.SearchFabState
import presentation.composables.SingleSelectionFilter
import presentation.composables.previews.ThemeProvider
import presentation.models.Filter
import presentation.theme.ListOfArtsTheme
import tgo1014.listofarts.presentation.models.ArtObjectUi


@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel(),
    onItemClicked: (ArtObjectUi) -> Unit,
) {
    val lifecycleOwner = androidx.compose.ui.platform.LocalLifecycleOwner.current
    val state by viewModel.state.collectAsStateWithLifecycle(lifecycleOwner)
    val keyboardController = LocalSoftwareKeyboardController.current
    LaunchedEffect(Unit) {
        viewModel.fetchArtObjects()
    }
    HomeScreen(
        state = state,
        onItemClicked = {
            keyboardController?.hide()
            onItemClicked(it)
        },
        onBottomOfScreenReached = viewModel::onBottomReached,
        onQueryChanged = viewModel::search,
        onFilterClicked = viewModel::onFilterClicked,
        onRetryClicked = { viewModel.fetchArtObjects() }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun HomeScreen(
    state: HomeState,
    onBottomOfScreenReached: () -> Unit = {},
    onItemClicked: (ArtObjectUi) -> Unit = {},
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
                    fabState =
                        if (fabState == SearchFabState.FAB) SearchFabState.SEARCH else SearchFabState.FAB
                },
                modifier = Modifier
                    .imePadding()
                    .navigationBarsPadding()
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
                item(span = StaggeredGridItemSpan.FullLine) {
                    SingleSelectionFilter(
                        filters = state.filters,
                        onClick = { filter -> onFilterClicked(filter) },
                        contentPadding = PaddingValues(horizontal = 8.dp)
                    )
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
                    val bgColor = MaterialTheme.colorScheme.background
                    SubcomposeAsyncImage(
                        model = ImageRequest.Builder(LocalPlatformContext.current)
                            .data(item.imageUrl)
                            .crossfade(300)
                            .build(),
                        contentDescription = item.title,
                        contentScale = ContentScale.FillWidth,
                        onError = { println(it.result.throwable) },
                        loading = {
                            Box(
                                modifier = Modifier
                                    .background(bgColor)
                                    .aspectRatio(item.safeAspectRatio)
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(bgColor)
                            .aspectRatio(item.safeAspectRatio)
                            .animateItemPlacement()
                            .animateContentSize()
                            .clickable { onItemClicked(item) },
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

@Preview
@Composable
private fun HomeScreenPreview(
    @PreviewParameter(ThemeProvider::class) materialYouColors: Boolean
) = ListOfArtsTheme(materialYouColors = materialYouColors) {
    HomeScreen(state = HomeState())
}