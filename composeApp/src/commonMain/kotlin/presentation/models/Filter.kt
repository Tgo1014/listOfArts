package presentation.models

import androidx.compose.runtime.Composable

enum class Filter(val description: String) {
    PAINTING("painting"),
    PRINT("print"),
    PHOTOGRAPH("photograph"),
    DRAWING("drawing"),
    PHOTOMECHANICAL_PRINT("photomechanical print"),
    CARTE_DE_VISITE("carte-de-visite"),
    STEREOGRAPH("stereograph"),
    LETTER("letter"),
    HISTORY_MEDAL("history medal"),
    COIN("coin"),
}

@Composable
fun Filter.translation(): String = when (this) {
    // TODO stringResource(R.string.blonde)
    Filter.PAINTING -> this.description
    Filter.PRINT -> this.description
    Filter.PHOTOGRAPH -> this.description
    Filter.DRAWING -> this.description
    Filter.PHOTOMECHANICAL_PRINT -> this.description
    Filter.CARTE_DE_VISITE -> this.description
    Filter.STEREOGRAPH -> this.description
    Filter.LETTER -> this.description
    Filter.HISTORY_MEDAL -> this.description
    Filter.COIN -> this.description
}
