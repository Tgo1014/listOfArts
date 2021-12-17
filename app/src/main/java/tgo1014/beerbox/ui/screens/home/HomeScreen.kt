package tgo1014.beerbox.ui.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.loadingFlow
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.navigationBarsHeight
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.google.accompanist.insets.ui.Scaffold
import kotlinx.coroutines.launch
import tgo1014.beerbox.R
import tgo1014.beerbox.models.Beer
import tgo1014.beerbox.ui.composables.BeerComposable
import tgo1014.beerbox.ui.composables.InsetCenterAlignedTopAppBar
import tgo1014.beerbox.ui.composables.SearchBar
import tgo1014.beerbox.ui.theme.TypographyGray

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: BeerViewModel,
    onBeerClicked: (Beer) -> Unit,
) = Surface {

    val isLoading by viewModel.loadingFlow.collectAsState()
    val lazyState = rememberLazyListState()
    val scrollBehavior = remember { TopAppBarDefaults.enterAlwaysScrollBehavior() }

    val state = viewModel.state.collectAsState().value
    val beerList = state.beerList
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = { Toolbar(scrollBehavior) },
        bottomBar = { BottomSpacing() },
        backgroundColor = MaterialTheme.colorScheme.background
    ) { contentPadding ->
        LazyColumn(
            state = lazyState,
            contentPadding = contentPadding
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
            if (beerList.isEmpty()) {
                item { EmptyState() }
            }
            items(beerList) { beer ->
                BeerComposable(
                    beer = beer,
                    modifier = Modifier
                        .clickable { onBeerClicked(beer) }
                        .padding(16.dp)
                )
                if (beer == beerList.lastOrNull()) {
                    SideEffect { viewModel.onBottomReached() }
                } else {
                    Divider(startIndent = 24.dp, color = TypographyGray.copy(0.2f))
                }
            }
            if (isLoading) {
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
                append("Beer ")
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append("Box")
                }
            }
            Text(title, color = Color.White)

        },
        contentPadding = rememberInsetsPaddingValues(
            LocalWindowInsets.current.statusBars,
            applyBottom = false,
        ),
        backgroundColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.95f),
        scrollBehavior = scrollBehavior,
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

@Composable
private fun EmptyState() {
    Surface(Modifier.fillMaxSize()) {
        Text(
            text = stringResource(R.string.no_beers),
            textAlign = TextAlign.Center,
        )
    }
}