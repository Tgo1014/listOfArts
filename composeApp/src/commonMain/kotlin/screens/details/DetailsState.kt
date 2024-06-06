package screens.details

import tgo1014.listofarts.presentation.models.ArtObjectUi

sealed class DetailsState {
    data object Loading : DetailsState()
    data class Success(val item: ArtObjectUi) : DetailsState()
    data object Error : DetailsState()
}
