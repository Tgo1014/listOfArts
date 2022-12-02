package tgo1014.listofbeers.presentation.ui.screens.details

import tgo1014.listofbeers.presentation.models.BeerUi
import tgo1014.listofbeers.presentation.models.FilterState

data class DetailsState(
    val isLoading: Boolean = false,
    val beer: BeerUi? = null,
)
