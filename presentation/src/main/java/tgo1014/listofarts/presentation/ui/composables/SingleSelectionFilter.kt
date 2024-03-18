package tgo1014.listofarts.presentation.ui.composables

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
import tgo1014.listofarts.presentation.models.Filter
import tgo1014.listofarts.presentation.models.FilterState
import tgo1014.listofarts.presentation.models.translation
import tgo1014.listofarts.presentation.ui.composables.previews.PreviewDefault
import tgo1014.listofarts.presentation.ui.composables.providers.ThemeProvider
import tgo1014.listofarts.presentation.ui.theme.ListOfArtsTheme

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

@PreviewDefault
@Composable
private fun SingleSelectionFilterPreview(
    @PreviewParameter(ThemeProvider::class) materialYouColors: Boolean
) = ListOfArtsTheme(materialYouColors = materialYouColors) {
    Surface(color = MaterialTheme.colorScheme.secondaryContainer) {
        val items =
            listOf(FilterState(Filter.PAINTING, false), FilterState(Filter.PHOTOGRAPH, true))
        SingleSelectionFilter(filters = items, onClick = {})
    }
}