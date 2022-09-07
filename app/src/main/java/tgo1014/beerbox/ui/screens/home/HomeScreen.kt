@file:OptIn(ExperimentalMaterial3Api::class)

package tgo1014.beerbox.ui.screens.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.add
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Divider
import androidx.compose.material3.AlertDialogDefaults.titleContentColor
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch
import tgo1014.beerbox.models.Beer
import tgo1014.beerbox.ui.composables.BeerComposable
import tgo1014.beerbox.ui.composables.EmptyState
import tgo1014.beerbox.ui.composables.LogoText
import tgo1014.beerbox.ui.composables.SearchBar
import tgo1014.beerbox.ui.composables.SingleSelectionFilter
import tgo1014.beerbox.ui.theme.TypographyGray

@OptIn(
    ExperimentalFoundationApi::class,
    ExperimentalLifecycleComposeApi::class, ExperimentalMaterial3Api::class,
)
@Composable
fun HomeScreen(
    viewModel: BeerViewModel,
    onBeerClicked: (Beer) -> Unit,
) = Surface {

    val lazyState = rememberLazyListState()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val beerList = state.beerList
    val coroutineScope = rememberCoroutineScope()

    Box {
//        Box(
//            modifier = Modifier.background(
//                color = MaterialTheme.colorScheme.surface/*.copy(alpha = 0.95f)*/
//            )
//        ) {
//            Toolbar(TopAppBarDefaults.enterAlwaysScrollBehavior())
//        }
        CenterAlignedTopAppBar(
            title = { LogoText() },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(0.85f),
                titleContentColor = titleContentColor
            ),
            modifier = Modifier.zIndex(1f)
        )
        LazyColumn(
            state = lazyState,
            contentPadding = WindowInsets.navigationBars
                .add(WindowInsets.statusBars)
                .add(WindowInsets(top = 80.dp)) // TopAppBar heigh
                .asPaddingValues(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.imePadding()
        ) {
            item {
                SearchBar(
                    query = state.searchText,
                    onQueryChanged = {
                        coroutineScope.launch {
                            lazyState.scrollToItem(0)
                        }
                        viewModel.search(it)
                    },
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
            item {
                SingleSelectionFilter(
                    filters = state.filters,
                    onClick = { viewModel.onFilterClicked(it) },
                    contentPadding = PaddingValues(horizontal = 16.dp)
                )
            }
            if (beerList.isEmpty()) {
                item { EmptyState() }
            }
            items(items = beerList, key = { it.id }) { beer ->
                BeerComposable(
                    beer = beer,
                    modifier = Modifier
                        .clickable { onBeerClicked(beer) }
                        .padding(16.dp)
                        .animateItemPlacement()
                )
                if (beer == beerList.lastOrNull()) {
                    SideEffect { viewModel.onBottomReached() }
                } else {
                    Divider(startIndent = 24.dp, color = TypographyGray.copy(0.2f))
                }
            }
            if (state.isLoading) {
                item {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(modifier = Modifier.size(40.dp))
                    }
                }
            }
        }
    }
}