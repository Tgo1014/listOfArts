package tgo1014.beerbox.ui.screens.home

import tgo1014.beerbox.models.Beer
import tgo1014.beerbox.models.FilterState

data class HomeState(
    val isLoading: Boolean = false,
    val beerList: List<Beer> = emptyList(),
    val searchText: String = "",
    val filters: List<FilterState> = FilterState.Empty
)
