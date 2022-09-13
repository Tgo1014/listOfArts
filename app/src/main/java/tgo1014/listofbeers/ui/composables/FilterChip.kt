package tgo1014.listofbeers.ui.composables

import android.content.res.Configuration
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import tgo1014.listofbeers.ui.theme.ListOfBeersTheme

@OptIn(ExperimentalMaterial3Api::class)
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
                modifier = Modifier.padding(vertical = 6.dp, horizontal = 24.dp)
            )
        },
        modifier = modifier
    )
}

@Preview(name = "Light")
@Preview(name = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun FilterChipPreview(
    @PreviewParameter(ThemeProvider::class) materialYouColors: Boolean
) {
    ListOfBeersTheme(materialYouColors = materialYouColors) {
        Surface(color = MaterialTheme.colorScheme.secondaryContainer) {
            PrimaryContainerFilterChip("Blonde", true) {}
        }
    }
}

@Preview(name = "Light")
@Preview(name = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun FilterChipDisabledPreview(
    @PreviewParameter(ThemeProvider::class) materialYouColors: Boolean
) {
    ListOfBeersTheme(materialYouColors = materialYouColors) {
        Surface(color = MaterialTheme.colorScheme.secondaryContainer) {
            PrimaryContainerFilterChip("Blonde", false) {}
        }
    }
}