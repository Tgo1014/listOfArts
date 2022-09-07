package tgo1014.beerbox.ui.composables

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import tgo1014.beerbox.models.Filter
import tgo1014.beerbox.models.FilterState
import tgo1014.beerbox.models.translation
import tgo1014.beerbox.ui.theme.BeerBoxTheme

@Composable
fun SingleSelectionFilter(
    filters: List<FilterState>,
    onClick: (Filter) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = contentPadding,
        modifier = modifier
    ) {
        items(filters) {
            PrimaryContainerFilterChip(text = it.filter.translation(), isSelected = it.isSelected) {
                onClick(it.filter)
            }
        }
    }
}

@Preview(name = "Light")
@Preview(name = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun SingleSelectionFilterPreview(
    @PreviewParameter(ThemeProvider::class) materialYouColors: Boolean
) {
    BeerBoxTheme(materialYouColors = materialYouColors) {
        Surface(color = MaterialTheme.colorScheme.secondaryContainer) {
            val items = listOf(FilterState(Filter.BLONDE, false), FilterState(Filter.LAGER, true))
            SingleSelectionFilter(filters = items, onClick = {})
        }
    }
}