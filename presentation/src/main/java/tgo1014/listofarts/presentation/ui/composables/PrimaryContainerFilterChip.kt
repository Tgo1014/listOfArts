package tgo1014.listofarts.presentation.ui.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import tgo1014.listofarts.presentation.models.Filter
import tgo1014.listofarts.presentation.ui.composables.previews.PreviewDefault
import tgo1014.listofarts.presentation.ui.composables.providers.ThemeProvider
import tgo1014.listofarts.presentation.ui.theme.ListOfArtsTheme

@Composable
fun PrimaryContainerFilterChip(
    text: String,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    FilterChip(
        selected = isSelected,
        onClick = onClick,
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = MaterialTheme.colorScheme.tertiary,
            selectedLabelColor = contentColorFor(MaterialTheme.colorScheme.tertiary)
        ),
        label = {
            Text(
                text = text,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 6.dp)
            )
        },
        modifier = modifier
    )
}

@PreviewDefault
@Composable
private fun FilterChipPreview(
    @PreviewParameter(ThemeProvider::class) materialYouColors: Boolean
) = ListOfArtsTheme(materialYouColors = materialYouColors) {
    Surface(color = MaterialTheme.colorScheme.secondaryContainer) {
        PrimaryContainerFilterChip(Filter.PHOTOGRAPH.description, true) {}
    }
}

@PreviewDefault
@Composable
private fun FilterChipDisabledPreview(
    @PreviewParameter(ThemeProvider::class) materialYouColors: Boolean
) = ListOfArtsTheme(materialYouColors = materialYouColors) {
    Surface(color = MaterialTheme.colorScheme.secondaryContainer) {
        PrimaryContainerFilterChip(Filter.PHOTOGRAPH.description, false) {}
    }
}