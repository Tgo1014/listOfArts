package tgo1014.listofbeers.presentation.ui.screens.details

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.coerceAtLeast
import androidx.compose.ui.unit.dp
import tgo1014.listofbeers.presentation.R
import tgo1014.listofbeers.presentation.models.BeerUi
import tgo1014.listofbeers.presentation.ui.composables.BeerImage
import tgo1014.listofbeers.presentation.ui.composables.ThemeProvider
import tgo1014.listofbeers.presentation.ui.composables.simpleVerticalScrollbar
import tgo1014.listofbeers.ui.theme.ListOfBeersTheme

@Composable
fun BeerDetails(
    beer: BeerUi,
    modifier: Modifier = Modifier
) = Box(modifier) {
    val scrollState = rememberLazyListState()
    val layoutDirection = LocalLayoutDirection.current
    Icon(
        painter = painterResource(id = R.drawable.ic_bookmark),
        contentDescription = null,
        tint = MaterialTheme.colorScheme.tertiary,
        modifier = Modifier
            .align(Alignment.TopEnd)
            .width(45.dp)
            .padding(end = 16.dp)
    )
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

@Preview(name = "Light")
@Preview(name = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun BeerDetailsPreview(
    @PreviewParameter(ThemeProvider::class) materialYouColors: Boolean
) {
    ListOfBeersTheme(materialYouColors = materialYouColors) {
        Surface(color = MaterialTheme.colorScheme.primaryContainer) {
            BeerDetails(
                beer = BeerUi(
                    name = "Punk IPA 2007 - 2010",
                    tagline = "This is a test",
                    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur mollis magna urna, eu tincidunt leo sagittis ut. Pellentesque tempus nulla ac elit pharetra, eu facilisis quam blandit. Morbi vehicula neque mauris, ut tincidunt lacus ultrices eu. Nam laoreet, purus ac tempus maximus, ante ligula scelerisque lacus, sed gravida nulla enim id erat."
                )
            )
        }
    }
}