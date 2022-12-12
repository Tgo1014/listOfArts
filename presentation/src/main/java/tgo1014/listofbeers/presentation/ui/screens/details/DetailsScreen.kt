package tgo1014.listofbeers.presentation.ui.screens.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.coerceAtLeast
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import tgo1014.listofbeers.presentation.R
import tgo1014.listofbeers.presentation.models.BeerUi
import tgo1014.listofbeers.presentation.ui.composables.BeerImage
import tgo1014.listofbeers.presentation.ui.composables.previews.DefaultPreview
import tgo1014.listofbeers.presentation.ui.composables.providers.ThemeProvider
import tgo1014.listofbeers.presentation.ui.composables.simpleVerticalScrollbar
import tgo1014.listofbeers.presentation.ui.theme.ListOfBeersTheme

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun DetailsScreen(
    beerId: Int,
    modifier: Modifier = Modifier,
    viewModel: DetailsViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(beerId) { viewModel.getBeerById(beerId) }
    DetailsScreen(state = state, onRetryClicked = { viewModel.getBeerById(beerId) }, modifier = modifier)
}

@Composable
private fun DetailsScreen(
    state: DetailsState,
    modifier: Modifier = Modifier,
    onRetryClicked: () -> Unit = {},
) = Box(modifier) {
    when (state) {
        DetailsState.Error -> DetailScreenError(onRetryClicked)
        DetailsState.Loading -> DetailScreenLoading()
        DetailsState.NoBeerSelected -> DetailEmpty()
        is DetailsState.Success -> DetailScreenContent(beer = state.beer)
    }
    if (state  is DetailsState.Success) {
        Icon(
            painter = painterResource(id = R.drawable.ic_bookmark),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .width(45.dp)
                .padding(end = 16.dp)
        )
    }
}

@Composable
private fun DetailScreenLoading() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .height(200.dp)
            .fillMaxWidth()
    ) {
        CircularProgressIndicator()
    }
}

@DefaultPreview
@Composable
private fun DetailScreenLoadingPreview() = ListOfBeersTheme {
    Surface(color = MaterialTheme.colorScheme.primaryContainer) {
        DetailScreenLoading()
    }
}

@Composable
private fun DetailEmpty() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = stringResource(R.string.select_a_beer_to_see_it_details))
        }
    }
}

@DefaultPreview
@Composable
private fun DetailEmptyPreview() = ListOfBeersTheme {
    Surface(color = MaterialTheme.colorScheme.primaryContainer) {
        DetailEmpty()
    }
}


@Composable
private fun DetailScreenError(
    onRetryClicked: () -> Unit = {}
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .height(200.dp)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = stringResource(R.string.failed_to_load_beer))
            Button(onClick = onRetryClicked) {
                Text(text = stringResource(R.string.try_again))
            }
        }
    }
}

@DefaultPreview
@Composable
private fun DetailScreenErrorPreview() = ListOfBeersTheme {
    Surface(color = MaterialTheme.colorScheme.primaryContainer) {
        DetailScreenError()
    }
}

@Composable
private fun DetailScreenContent(beer: BeerUi) {
    val scrollState = rememberLazyListState()
    val layoutDirection = LocalLayoutDirection.current
    Row {
        Box(
            modifier = Modifier
                .padding(
                    PaddingValues(
                        top = WindowInsets.safeContent
                            .asPaddingValues()
                            .calculateTopPadding(),
                    )
                )
        ) {
            BeerImage(
                beer = beer,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(start = 16.dp)
                    .widthIn(max = 100.dp)
            )
        }
        Spacer(modifier = Modifier.size(16.dp))
        LazyColumn(
            state = scrollState,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(
                top = WindowInsets.safeContent.asPaddingValues().calculateTopPadding(),
                bottom = WindowInsets.safeContent.asPaddingValues().calculateBottomPadding(),
                end = WindowInsets.safeContent
                    .asPaddingValues()
                    .calculateEndPadding(layoutDirection)
                    .coerceAtLeast(16.dp)
            ),
            modifier = Modifier
                .weight(1f)
                .simpleVerticalScrollbar(
                    state = scrollState,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(0.5f),
                )
        ) {
            item {
                Text(
                    text = beer.name,
                    style = MaterialTheme.typography.displayMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Text(
                    text = beer.tagline,
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            item {
                Text(
                    text = stringResource(R.string.first_brewed),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Text(
                    text = beer.firstBrewed,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            item {
                Text(
                    text = stringResource(R.string.description),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Text(
                    text = beer.description,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            item {
                Text(
                    text = stringResource(R.string.food_pairing),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                beer.foodParingList.forEach {
                    Text(
                        text = " • $it",
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            item {
                Text(
                    text = stringResource(R.string.brewer_tips),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Text(
                    text = beer.brewersTips,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@DefaultPreview
@Composable
private fun DetailsScreenPreview(
    @PreviewParameter(ThemeProvider::class) materialYouColors: Boolean
) = ListOfBeersTheme(materialYouColors = materialYouColors) {
    Surface(color = MaterialTheme.colorScheme.primaryContainer) {
        DetailsScreen(
            DetailsState.Success(
                beer = BeerUi(
                    name = "Punk IPA 2007 - 2010",
                    tagline = "This is a test",
                    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur mollis magna urna, eu tincidunt leo sagittis ut. Pellentesque tempus nulla ac elit pharetra, eu facilisis quam blandit. Morbi vehicula neque mauris, ut tincidunt lacus ultrices eu. Nam laoreet, purus ac tempus maximus, ante ligula scelerisque lacus, sed gravida nulla enim id erat."
                )
            )
        )
    }
}