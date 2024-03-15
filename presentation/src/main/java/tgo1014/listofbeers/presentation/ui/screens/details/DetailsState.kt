package tgo1014.listofbeers.presentation.ui.screens.details

import tgo1014.listofbeers.presentation.models.ArtObjectUi

sealed class DetailsState {
    data object Loading : DetailsState()
    data class Success(val item: ArtObjectUi) : DetailsState()
    data object Error : DetailsState()
}
