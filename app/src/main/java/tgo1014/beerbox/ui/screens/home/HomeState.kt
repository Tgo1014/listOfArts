package tgo1014.beerbox.ui.screens.home

import tgo1014.beerbox.models.Beer
import tgo1014.beerbox.models.Filter
import tgo1014.beerbox.models.FilterState

data class HomeState(
    val beerList: List<Beer> = emptyList(),
    val searchText: String = "",
    val filters: List<FilterState> = Filter.values().map { FilterState(it, false) }
)
