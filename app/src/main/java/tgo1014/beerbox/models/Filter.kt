package tgo1014.beerbox.models

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import tgo1014.beerbox.R

enum class Filter(val yeast: String) {
    BLONDE("blonde"),
    LAGER("lager"),
    MALT("malt");
}

@Composable
fun Filter.translation(): String = when (this) {
    Filter.BLONDE -> stringResource(R.string.blonde)
    Filter.LAGER -> stringResource(R.string.lager)
    Filter.MALT -> stringResource(R.string.malt)
}
