package tgo1014.beerbox.ui.composables

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

class ThemeProvider : PreviewParameterProvider<Boolean> {
    override val values = sequenceOf(true, false)
}