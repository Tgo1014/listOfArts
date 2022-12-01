package tgo1014.listofbeers.presentation.ui.screens.home

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import tgo1014.listofbeers.domain.usecases.GetBeersUseCase
import tgo1014.listofbeers.presentation.models.BeerUi
import tgo1014.listofbeers.presentation.models.Filter
import tgo1014.listofbeers.presentation.models.mappers.toBeerUi
import javax.inject.Inject

@HiltViewModel
class BeerViewModel @Inject constructor(
    private val getBeersInteractor: GetBeersUseCase,
) : ViewModel() {

    private var searchJob: Job? = null
    private var page = 1
    private var lastPageReached = false
    var beerToShow: BeerUi? = null

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    init {
        fetchBeers()
    }

    fun search(query: String) {
        resetToFirstPage()
        _state.update { it.copy(searchText = query) }
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
        _state.update { it.copy(filters = updatedFilters) }
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
    fun fetchBeers(scope: CoroutineScope? = null) = viewModelScope.launch {
        _state.update { it.copy(isLoading = true) }
        val filters = state.value.filters.firstOrNull { it.isSelected }
        val beerList = getBeersInteractor(
            page = page,
            search = state.value.searchText,
            yeast = filters?.filter?.yeast
        ).getOrDefault(emptyList())
            .map { it.toBeerUi() }
        _state.update { it.copy(isLoading = false) }
        handleSuccessfulResult(beerList)
    }

    private fun searchInternal() {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(350) // Before performing search, wait a bit if the user is writing
            if (state.value.searchText.isNotBlank()) {
                fetchBeers(this)
                return@launch
            }
            searchJob?.cancel()
            fetchBeers()
        }
    }

    private fun handleSuccessfulResult(beerList: List<BeerUi>) {
        when {
            page == 1 -> _state.update { it.copy(beerList = beerList) }
            beerList.isEmpty() -> lastPageReached = true
            else -> {
                val newBeerList = (state.value.beerList + beerList).distinctBy { it.id }
                _state.update { it.copy(beerList = newBeerList) }
            }
        }
    }

    private fun resetToFirstPage() {
        lastPageReached = false
        page = 1
    }

}