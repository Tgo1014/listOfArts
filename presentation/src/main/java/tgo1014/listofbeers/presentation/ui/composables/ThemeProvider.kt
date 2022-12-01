package tgo1014.listofbeers.presentation.ui.composables

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

class ThemeProvider : PreviewParameterProvider<Boolean> {
    override val values = sequenceOf(true, false)
}