package tgo1014.listofarts.presentation.ui.screens.details

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Pinch
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import coil.request.ImageRequest
import com.github.panpf.zoomimage.CoilZoomAsyncImage
import kotlinx.coroutines.delay
import tgo1014.listofarts.presentation.R
import tgo1014.listofarts.presentation.models.ArtObjectUi
import tgo1014.listofarts.presentation.ui.composables.PrimaryContainerFilterChip
import tgo1014.listofarts.presentation.ui.composables.extensions.modifyIf
import tgo1014.listofarts.presentation.ui.composables.previews.PreviewDefault
import tgo1014.listofarts.presentation.ui.composables.previews.isPreviewMode
import tgo1014.listofarts.presentation.ui.composables.providers.ThemeProvider
import tgo1014.listofarts.presentation.ui.theme.ListOfArtsTheme
import kotlin.time.Duration.Companion.seconds

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
        contentPadding = PaddingValues(
            bottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        item {
            Box {
                CoilZoomAsyncImage(
                    model = ImageRequest.Builder(LocalContext.current).apply {
                        data(item.imageUrl)
                        crossfade(true)
                    }.build(),
                    contentDescription = item.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(item.safeAspectRatio)
                        .modifyIf(isPreviewMode()) {
                            background(Color.DarkGray)
                        }
                )
                var isVisible by remember { mutableStateOf(true) }
                LaunchedEffect(this) {
                    delay(5.seconds)
                    isVisible = false
                }
                AnimatedVisibility(
                    visible = isVisible,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Row(
                        modifier = Modifier
                            .padding(8.dp)
                            .background(MaterialTheme.colorScheme.tertiaryContainer)
                            .padding(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Sharp.Pinch,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onTertiaryContainer,
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(
                            text = stringResource(R.string.pinch_to_zoom),
                            fontStyle = FontStyle.Italic,
                            fontFamily = FontFamily.Serif,
                            color = MaterialTheme.colorScheme.onTertiaryContainer
                        )
                    }
                }
            }
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

@PreviewDefault
@Composable
private fun DetailsScreenPreview(
    @PreviewParameter(ThemeProvider::class) materialYouColors: Boolean
) = ListOfArtsTheme(materialYouColors = materialYouColors) {
    Surface(color = MaterialTheme.colorScheme.primaryContainer) {
        DetailsScreen(
            DetailsState.Success(
                item = ArtObjectUi(
                    title = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                    principalMaker = "Tgo1014",
                    description = "Nulla interdum, dui a mollis posuere, metus ex dignissim tortor, vitae laoreet felis tellus eget arcu. Cras lobortis vel lorem ut condimentum. Donec ut ex consectetur, cursus lorem at, dignissim lacus. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Quisque volutpat dolor leo, id suscipit ligula sodales vel. Nullam tortor odio, fermentum quis odio vel, congue cursus nibh. Integer a odio purus. Praesent bibendum, ipsum a varius ultrices, augue turpis blandit leo, vitae ullamcorper sem nunc sit amet orci. Proin eget suscipit nisl, at volutpat est."
                )
            )
        )
    }
}

@PreviewDefault
@Composable
private fun DetailScreenLoadingPreview() = ListOfArtsTheme {
    Surface(color = MaterialTheme.colorScheme.primaryContainer) {
        DetailScreenLoading()
    }
}

@PreviewDefault
@Composable
private fun DetailScreenErrorPreview() = ListOfArtsTheme {
    Surface(color = MaterialTheme.colorScheme.primaryContainer) {
        DetailScreenError()
    }
}
