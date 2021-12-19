package tgo1014.beerbox.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import tgo1014.beerbox.ui.theme.BeerBoxTheme
import tgo1014.beerbox.ui.theme.TypographyGray

@Composable
fun Chip(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    // define properties to the chip
    // such as color, shape, width
    Surface(
        color = when {
            selected -> MaterialTheme.colorScheme.secondary
            else -> Color.DarkGray.copy(0.4f)
        },
        contentColor = when {
            selected -> MaterialTheme.colorScheme.onSecondary
            else -> TypographyGray
        },
        shape = CircleShape,
        modifier = Modifier.clickable { onClick() }
    ) {
        // Add text to show the data that we passed
        Text(
            text = text,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(vertical = 6.dp, horizontal = 24.dp)
        )
    }
}

@Preview
@Composable
fun FilterChipPreview() = BeerBoxTheme {
    Chip("Blonde", false) {}
}

@Preview
@Composable
fun FilterChipPreviewSelected() = BeerBoxTheme {
    Chip("Blonde", true) {}
}