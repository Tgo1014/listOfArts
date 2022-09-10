package tgo1014.listofbeers.ui.screens.home

import tgo1014.listofbeers.models.Beer
import tgo1014.listofbeers.models.FilterState

data class HomeState(
    val isLoading: Boolean = false,
    val beerList: List<Beer> = emptyList(),
    val searchText: String = "",
    val filters: List<FilterState> = FilterState.Empty
)
