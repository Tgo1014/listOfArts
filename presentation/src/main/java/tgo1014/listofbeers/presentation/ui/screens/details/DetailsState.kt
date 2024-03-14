package tgo1014.listofbeers.presentation.ui.screens.details

import tgo1014.listofbeers.presentation.models.BeerUi

sealed class DetailsState {
    data object Loading : DetailsState()
    data class Success(val beer: BeerUi) : DetailsState()
    data object Error : DetailsState()
}
