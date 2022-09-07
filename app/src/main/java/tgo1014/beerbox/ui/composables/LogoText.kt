package tgo1014.beerbox.ui.composables

import android.content.res.Configuration
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import tgo1014.beerbox.ui.theme.BeerBoxTheme

@Composable
fun LogoText(color: Color = MaterialTheme.colorScheme.onSecondaryContainer) {
    val title = buildAnnotatedString {
        append("Beer")
        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
            append("Box")
        }
    }
    Text(text = title, color = color)
}

@Preview(name = "Light")
@Preview(name = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun LogoTextPreview(
    @PreviewParameter(ThemeProvider::class) materialYouColors: Boolean
) {
    BeerBoxTheme(materialYouColors = materialYouColors) {
        Surface(color = MaterialTheme.colorScheme.secondaryContainer) {
            LogoText()
        }
    }
}