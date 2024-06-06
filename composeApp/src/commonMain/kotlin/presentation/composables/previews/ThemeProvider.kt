package presentation.composables.previews

import org.jetbrains.compose.ui.tooling.preview.PreviewParameterProvider

class ThemeProvider : PreviewParameterProvider<Boolean> {
    override val values = sequenceOf(true, false)
}