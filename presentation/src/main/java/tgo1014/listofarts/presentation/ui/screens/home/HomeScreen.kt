package tgo1014.listofarts.presentation.ui.screens.home

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
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.union
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import tgo1014.listofarts.presentation.models.ArtObjectUi
import tgo1014.listofarts.presentation.models.Filter
import tgo1014.listofarts.presentation.ui.composables.EmptyState
import tgo1014.listofarts.presentation.ui.composables.LogoText
import tgo1014.listofarts.presentation.ui.composables.SearchFab
import tgo1014.listofarts.presentation.ui.composables.SearchFabState
import tgo1014.listofarts.presentation.ui.composables.SingleSelectionFilter
import tgo1014.listofarts.presentation.ui.composables.extensions.plus
import tgo1014.listofarts.presentation.ui.composables.previews.PreviewDefault
import tgo1014.listofarts.presentation.ui.composables.providers.ThemeProvider
import tgo1014.listofarts.presentation.ui.theme.ListOfArtsTheme

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onItemClicked: (ArtObjectUi) -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
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
            // These insets below avoid clipping when using 3 buttons nav bar
            val systemBarsPadding =
                WindowInsets.systemBars.union(WindowInsets.displayCutout).asPaddingValues()
            val direction = LocalLayoutDirection.current
            val noTopPadding = WindowInsets(
                top = 0.dp,
                left = systemBarsPadding.calculateLeftPadding(direction),
                right = systemBarsPadding.calculateRightPadding(direction),
                bottom = systemBarsPadding.calculateBottomPadding()
            )
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Adaptive(130.dp),
                contentPadding = PaddingValues(spacing) + noTopPadding.asPaddingValues(),
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
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(item.imageUrl)
                            .crossfade(300)
                            .build(),
                        contentDescription = item.title,
                        contentScale = ContentScale.FillWidth,
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
                            .animateItem()
                            .animateContentSize()
                            .clickable { onItemClicked(item) },
                    )
                    if (index == state.itemList.lastIndex) {
                        SideEffect { onBottomOfScreenReached() }
                    }
                }
            }
        }
    )
}

@PreviewDefault
@Composable
private fun HomeScreenPreview(
    @PreviewParameter(ThemeProvider::class) materialYouColors: Boolean
) = ListOfArtsTheme(materialYouColors = materialYouColors) {
    HomeScreen(state = HomeState())
}