package tgo1014.listofbeers.presentation.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import tgo1014.listofbeers.domain.usecases.GetBeersUseCase
import tgo1014.listofbeers.presentation.models.BeerUi
import tgo1014.listofbeers.presentation.models.Filter
import tgo1014.listofbeers.presentation.models.mappers.toUi
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getBeersUseCase: GetBeersUseCase,
) : ViewModel() {

    private var searchJob: Job? = null
    private var page = 1
    private var lastPageReached = false

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    private var isLoading: Boolean
        get() = _state.value.isLoading
        set(value) = _state.update { it.copy(isLoading = value) }

    fun search(query: String) {
        resetState()
        _state.update { it.copy(searchText = query) }
        searchInternal()
    }

    fun onFilterClicked(filter: Filter) {
        val updatedFilters = state.value.filters.map { filterState ->
            if (filterState.filter == filter) {
                filterState.copy(isSelected = !filterState.isSelected)
            } else {
                filterState.copy(isSelected = false)
            }
        }
        _state.update { it.copy(filters = updatedFilters) }
        resetState()
        fetchBeers()
    }

    fun onBottomReached() {
        if (!isLoading && !lastPageReached) {
            page += 1
            fetchBeers()
        }
    }

    fun fetchBeers() = viewModelScope.launch {
        isLoading = true
        val filters = state.value.filters.firstOrNull { it.isSelected }
        val beerList = getBeersUseCase(
            page = page,
            search = state.value.searchText,
            yeast = filters?.filter?.yeast
        ).getOrDefault(emptyList()).map { it.toUi() }
        isLoading = false
        handleSuccessfulResult(beerList)
    }

    private fun searchInternal() {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(350) // Before performing search, wait a bit if the user is writing
            if (state.value.searchText.isBlank()) {
                resetState()
            }
            fetchBeers()
        }
    }

    private fun handleSuccessfulResult(beerList: List<BeerUi>) = viewModelScope.launch {
        when {
            page == 1 -> _state.emit(state.value.copy(itemList = beerList))
            beerList.isEmpty() -> lastPageReached = true
            else -> {
                val newBeerList = (state.value.itemList + beerList).distinctBy { it.id }
                _state.update { it.copy(itemList = newBeerList) }
            }
        }
    }

    private fun resetState() {
        lastPageReached = false
        page = 1
    }

}