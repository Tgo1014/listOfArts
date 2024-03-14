package tgo1014.listofbeers.presentation.ui.screens.home

import tgo1014.listofbeers.presentation.models.ArtObjectUi
import tgo1014.listofbeers.presentation.models.FilterState

data class HomeState(
    val isLoading: Boolean = false,
    val itemList: List<ArtObjectUi> = emptyList(),
    val searchText: String = "",
    val filters: List<FilterState> = FilterState.Default
)
