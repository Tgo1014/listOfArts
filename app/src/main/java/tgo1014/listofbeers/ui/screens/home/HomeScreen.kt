@file:OptIn(ExperimentalMaterial3Api::class)

package tgo1014.listofbeers.ui.screens.home

import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch
import tgo1014.listofbeers.models.Beer
import tgo1014.listofbeers.models.Filter
import tgo1014.listofbeers.ui.composables.BeerComposable
import tgo1014.listofbeers.ui.composables.EmptyState
import tgo1014.listofbeers.ui.composables.LogoText
import tgo1014.listofbeers.ui.composables.SearchBar
import tgo1014.listofbeers.ui.composables.SingleSelectionFilter

@OptIn(ExperimentalLifecycleComposeApi::class, ExperimentalComposeUiApi::class)
@Composable
fun HomeScreen(
    viewModel: BeerViewModel,
    onBeerClicked: (Beer) -> Unit,
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
        onFilterClicked = viewModel::onFilterClicked
    )
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreen(
    state: HomeState,
    onBottomOfScreenReached: () -> Unit = {},
    onBeerClicked: (Beer) -> Unit = {},
    onQueryChanged: (String) -> Unit = {},
    onFilterClicked: (Filter) -> Unit = {}
) {

    val lazyState = rememberLazyListState()
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
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primaryContainer,
                )
            )
        }
    ) {
        LazyColumn(
            state = lazyState,
            contentPadding = WindowInsets.navigationBars
                .add(WindowInsets(top = 16.dp))
                .add(WindowInsets(top = it.calculateTopPadding())) // TopAppBar
                .asPaddingValues(),
            modifier = Modifier.imePadding()
        ) {
            item {
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
            item {
                SingleSelectionFilter(
                    filters = state.filters,
                    onClick = { filter -> onFilterClicked(filter) },
                    contentPadding = PaddingValues(horizontal = 16.dp)
                )
            }
            if (state.beerList.isEmpty() && !state.isLoading) {
                item { EmptyState() }
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
                    androidx.compose.material3.Divider(Modifier.padding(start = 24.dp))
                }
            }
            if (state.isLoading) {
                item {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        CircularProgressIndicator(modifier = Modifier.size(40.dp))
                    }
                }
            }
        }
    }
}

