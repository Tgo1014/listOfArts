package tgo1014.listofarts.presentation.ui.composables

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.PreviewParameter
import tgo1014.listofarts.presentation.ui.composables.previews.DefaultPreview
import tgo1014.listofarts.presentation.ui.composables.providers.ThemeProvider
import tgo1014.listofarts.presentation.ui.theme.ListOfArtsTheme

@Composable
fun LogoText(
    modifier: Modifier = Modifier,
    textColor: Color = Color.Unspecified,
) {
    val title = buildAnnotatedString {
        append("listOf")
        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
            append("Arts")
        }
        append("()")
    }
    Text(
        text = title,
        modifier = modifier,
        style = MaterialTheme.typography.headlineLarge,
        color = textColor,
        fontStyle = FontStyle.Italic,
        fontFamily = FontFamily.Serif
    )
}

@DefaultPreview
@Composable
private fun LogoTextPreview(
    @PreviewParameter(ThemeProvider::class) materialYouColors: Boolean
) = ListOfArtsTheme(materialYouColors = materialYouColors) {
    Surface(color = MaterialTheme.colorScheme.secondaryContainer) {
        LogoText()
    }
}