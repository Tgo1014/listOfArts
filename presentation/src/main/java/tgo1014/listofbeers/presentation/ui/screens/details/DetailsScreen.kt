package tgo1014.listofbeers.presentation.ui.screens.details

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import tgo1014.listofbeers.presentation.R
import tgo1014.listofbeers.presentation.models.ArtObjectUi
import tgo1014.listofbeers.presentation.ui.composables.PrimaryContainerFilterChip
import tgo1014.listofbeers.presentation.ui.composables.previews.DefaultPreview
import tgo1014.listofbeers.presentation.ui.composables.providers.ThemeProvider
import tgo1014.listofbeers.presentation.ui.theme.ListOfArtsTheme

@Composable
fun DetailsScreen(
    id: String,
    viewModel: DetailsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(viewModel) { viewModel.getArtObjectById(id) }
    DetailsScreen(state = state, onRetryClicked = { viewModel.getArtObjectById(id) })
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
        is DetailsState.Success -> DetailScreenContent(item = state.item)
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
            Text(text = stringResource(R.string.failed_to_load))
            Button(onClick = onRetryClicked, shape = MaterialTheme.shapes.small) {
                Text(stringResource(id = R.string.retry))
            }
        }
    }
}

@Composable
private fun DetailScreenContent(item: ArtObjectUi) {
    val scrollState = rememberLazyListState()
    LazyColumn(
        state = scrollState,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = WindowInsets.navigationBars.asPaddingValues(),
        modifier = Modifier.fillMaxWidth()
    ) {
        item {
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
                            .aspectRatio(item.safeAspectRatio)
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(bgColor)
                    .aspectRatio(item.safeAspectRatio)
            )
        }
        item {
            Column(Modifier.padding(horizontal = 8.dp)) {
                Text(
                    text = item.title,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    fontStyle = FontStyle.Italic,
                    fontFamily = FontFamily.Serif,
                )
                Text(
                    text = stringResource(R.string.by).format(item.principalMaker),
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    fontSize = 18.sp,
                    fontStyle = FontStyle.Italic,
                    fontFamily = FontFamily.Serif,
                )
            }
        }
        item {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 8.dp)
            ) {
                item.materialsList.forEach {
                    PrimaryContainerFilterChip(
                        text = it,
                        isSelected = false,
                    )
                }
            }
        }
        if (item.description.isNotBlank()) {
            item {
                Text(
                    text = item.description,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    fontFamily = FontFamily.Serif,
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .padding(bottom = 8.dp),
                )
            }
        }
    }
}

@DefaultPreview
@Composable
private fun DetailsScreenPreview(
    @PreviewParameter(ThemeProvider::class) materialYouColors: Boolean
) = ListOfArtsTheme(materialYouColors = materialYouColors) {
    Surface(color = MaterialTheme.colorScheme.primaryContainer) {
        DetailsScreen(
            DetailsState.Success(
                item = ArtObjectUi(
                    title = "Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum",
                )
            )
        )
    }
}

@DefaultPreview
@Composable
private fun DetailScreenLoadingPreview() = ListOfArtsTheme {
    Surface(color = MaterialTheme.colorScheme.primaryContainer) {
        DetailScreenLoading()
    }
}

@DefaultPreview
@Composable
private fun DetailScreenErrorPreview() = ListOfArtsTheme {
    Surface(color = MaterialTheme.colorScheme.primaryContainer) {
        DetailScreenError()
    }
}
