package tgo1014.beerbox.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
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
) {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = modifier) {
        items(filters) {
            Chip(text = it.filter.translation(), selected = it.isSelected) {
                onClick(it.filter)
            }
        }
    }
}

@Preview
@Composable
fun SingleSelectionFilterPreview() = BeerBoxTheme {
    val items = listOf(FilterState(Filter.BLONDE, false), FilterState(Filter.LAGER, true))
    SingleSelectionFilter(items, onClick = {})
}