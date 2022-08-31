@file:OptIn(ExperimentalMaterial3Api::class)

package tgo1014.beerbox.ui.screens.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.add
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch
import tgo1014.beerbox.R
import tgo1014.beerbox.models.Beer
import tgo1014.beerbox.ui.composables.BeerComposable
import tgo1014.beerbox.ui.composables.InsetCenterAlignedTopAppBar
import tgo1014.beerbox.ui.composables.SearchBar
import tgo1014.beerbox.ui.composables.SingleSelectionFilter
import tgo1014.beerbox.ui.theme.TypographyGray

@OptIn(
    ExperimentalFoundationApi::class,
    ExperimentalLifecycleComposeApi::class,
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

    Scaffold(
        topBar = { Toolbar(TopAppBarDefaults.enterAlwaysScrollBehavior()) },
        backgroundColor = MaterialTheme.colorScheme.background
    ) {
        LazyColumn(
            state = lazyState,
            contentPadding = WindowInsets.navigationBars.asPaddingValues(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
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
                    modifier = Modifier.padding(horizontal = 16.dp)
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
                        .padding(horizontal = 16.dp)
                        .animateItemPlacement()
                )
                if (beer == beerList.lastOrNull()) {
                    SideEffect { viewModel.onBottomReached() }
                } else {
                    Divider(startIndent = 24.dp, color = TypographyGray.copy(0.2f))
                }
            }
            if (state.isLoading) {
                item { Progress(Modifier.fillMaxWidth()) }
            }
        }
    }
}

@Composable
private fun Progress(modifier: Modifier) {
    Box(modifier = modifier) {
        CircularProgressIndicator(
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier
                .size(40.dp)
                .align(Alignment.Center)
        )
    }
}

@Composable
private fun Toolbar(scrollBehavior: TopAppBarScrollBehavior) {
    InsetCenterAlignedTopAppBar(
        title = {
            val title = buildAnnotatedString {
                append("Beer")
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append("Box")
                }
            }
            Text(title, color = Color.White)

        },
        contentPadding = WindowInsets.statusBars.asPaddingValues(),
        backgroundColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.95f),
        scrollBehavior = scrollBehavior,
    )
}

@Preview
@Composable
private fun EmptyState() {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(R.string.no_beers),
            textAlign = TextAlign.Center,
        )
    }
}