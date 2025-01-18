package screens.home

import presentation.models.FilterState
import presentation.models.ArtObjectUi

data class HomeState(
    val isLoading: Boolean = false,
    val itemList: List<ArtObjectUi> = emptyList(),
    val searchText: String = "",
    val filters: List<FilterState> = FilterState.Default
)
