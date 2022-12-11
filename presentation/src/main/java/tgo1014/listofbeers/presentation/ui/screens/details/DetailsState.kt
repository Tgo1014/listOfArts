package tgo1014.listofbeers.presentation.ui.screens.details

import tgo1014.listofbeers.presentation.models.BeerUi

sealed class DetailsState {
    object NoBeerSelected : DetailsState()
    object Loading : DetailsState()
    data class Success(val beer: BeerUi) : DetailsState()
    object Error : DetailsState()
}
