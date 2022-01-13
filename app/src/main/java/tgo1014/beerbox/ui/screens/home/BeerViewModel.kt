package tgo1014.beerbox.ui.screens.home

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import tgo1014.beerbox.interactors.GetBeersInteractor
import tgo1014.beerbox.models.Beer
import tgo1014.beerbox.models.Filter
import javax.inject.Inject

@HiltViewModel
class BeerViewModel @Inject constructor(
    private val getBeersInteractor: GetBeersInteractor,
) : ViewModel() {

    private var searchJob: Job? = null
    private var page = 1
    private var lastPageReached = false
    var beerToShow: Beer? = null

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    init {
        fetchBeers()
    }

    fun search(query: String) {
        resetToFirstPage()
        state(_state.value.copy(searchText = query))
        searchInternal()
    }

    fun onFilterClicked(filter: Filter) {
        val updatedFilters = state.value.filters.map {
            if (it.filter == filter) {
                it.copy(isSelected = !it.isSelected)
            } else {
                it.copy(isSelected = false)
            }
        }
        state(state.value.copy(filters = updatedFilters))
        resetToFirstPage()
        fetchBeers()
    }

    fun onBottomReached() {
        if (!state.value.isLoading && !lastPageReached) {
            page += 1
            fetchBeers()
        }
    }

    @VisibleForTesting
    fun fetchBeers(scope: CoroutineScope? = null) = (scope ?: viewModelScope).launch {
        state(state.value.copy(isLoading = true))
        val selected = state.value.filters.firstOrNull { it.isSelected }
        val beerList = getBeersInteractor(
            page = page,
            search = state.value.searchText,
            yeast = selected?.filter?.yeast
        ).getOrDefault(emptyList())
        handleSuccessfulResult(beerList)
        state(state.value.copy(isLoading = false))
    }

    private fun searchInternal() {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(350)
            if (state.value.searchText.isNotBlank()) {
                fetchBeers(this)
                return@launch
            }
            searchJob?.cancel()
            fetchBeers()
        }
    }

    private fun handleSuccessfulResult(beerList: List<Beer>) {
        when {
            page == 1 -> state(state.value.copy(beerList = beerList))
            beerList.isEmpty() -> lastPageReached = true
            else -> state(state.value.copy(beerList = state.value.beerList + beerList))
        }
    }

    private fun resetToFirstPage() {
        lastPageReached = false
        page = 1
    }

    private fun state(mainViewState: HomeState) = viewModelScope.launch {
        _state.emit(mainViewState)
    }

}