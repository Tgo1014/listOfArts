package tgo1014.beerbox.ui.screens.home

import tgo1014.beerbox.models.Beer

data class HomeState(
    val beerList: List<Beer> = emptyList(),
    val searchText: String = ""
)
