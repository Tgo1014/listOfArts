package tgo1014.listofbeers.presentation.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import tgo1014.listofbeers.presentation.models.Filter
import tgo1014.listofbeers.presentation.models.FilterState
import tgo1014.listofbeers.presentation.models.translation
import tgo1014.listofbeers.presentation.ui.composables.previews.DefaultPreview
import tgo1014.listofbeers.presentation.ui.composables.providers.ThemeProvider
import tgo1014.listofbeers.presentation.ui.theme.ListOfBeersTheme

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

@DefaultPreview
@Composable
private fun SingleSelectionFilterPreview(
    @PreviewParameter(ThemeProvider::class) materialYouColors: Boolean
) = ListOfBeersTheme(materialYouColors = materialYouColors) {
    Surface(color = MaterialTheme.colorScheme.secondaryContainer) {
        val items = listOf(FilterState(Filter.BLONDE, false), FilterState(Filter.LAGER, true))
        SingleSelectionFilter(filters = items, onClick = {})
    }
}