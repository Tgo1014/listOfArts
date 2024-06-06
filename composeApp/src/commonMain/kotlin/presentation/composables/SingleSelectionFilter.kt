package presentation.composables

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter
import presentation.composables.previews.ThemeProvider
import presentation.theme.ListOfArtsTheme
import presentation.models.Filter
import presentation.models.FilterState
import presentation.models.translation

@Composable
fun SingleSelectionFilter(
    filters: List<FilterState>,
    onClick: (Filter) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberLazyListState()
    LazyRow(
        state = scrollState,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = contentPadding,
        modifier = Modifier.draggable(
            orientation = Orientation.Horizontal,
            state = rememberDraggableState { delta ->
                coroutineScope.launch {
                    scrollState.scrollBy(-delta)
                }
            },
        ).then(modifier)
    ) {
        items(filters) {
            PrimaryContainerFilterChip(text = it.filter.translation(), isSelected = it.isSelected) {
                onClick(it.filter)
            }
        }
    }
}

@Preview
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