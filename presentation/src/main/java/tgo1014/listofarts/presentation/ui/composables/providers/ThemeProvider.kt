package tgo1014.listofarts.presentation.ui.composables.providers

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

class ThemeProvider : PreviewParameterProvider<Boolean> {
    override val values = sequenceOf(true, false)
}