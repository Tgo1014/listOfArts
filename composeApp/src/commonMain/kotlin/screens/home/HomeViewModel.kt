package screens.home


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import presentation.models.Filter
import presentation.models.mappers.toUi
import tgo1014.listofarts.domain.CoroutineProvider
import tgo1014.listofarts.domain.usecases.GetArtObjectsUseCase
import presentation.models.ArtObjectUi

class HomeViewModel(
    private val getArtObjectsUseCase: GetArtObjectsUseCase,
    coroutineProvider: CoroutineProvider,
) : ViewModel(
    viewModelScope = CoroutineScope(coroutineProvider.main)
) {

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
        fetchArtObjects()
    }

    fun onBottomReached() {
        if (!isLoading && !lastPageReached) {
            page += 1
            fetchArtObjects()
        }
    }

    fun fetchArtObjects() = viewModelScope.launch {
        isLoading = true
        val selectedFilter = state.value.filters.firstOrNull { it.isSelected }?.filter
        val artObjectsList = getArtObjectsUseCase(
            page = page,
            query = state.value.searchText,
            type = selectedFilter?.description
        ).getOrDefault(emptyList()).map { it.toUi() }
        isLoading = false
        handleSuccessfulResult(artObjectsList)
    }

    private fun searchInternal() {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(350) // Before performing search, wait a bit if the user is writing
            if (state.value.searchText.isBlank()) {
                resetState()
            }
            fetchArtObjects()
        }
    }

    private fun handleSuccessfulResult(artObjectsList: List<ArtObjectUi>) = viewModelScope.launch {
        when {
            page == 1 -> _state.emit(state.value.copy(itemList = artObjectsList))
            artObjectsList.isEmpty() -> lastPageReached = true
            else -> {
                val newArtObjectsList = (state.value.itemList + artObjectsList).distinctBy { it.id }
                _state.update { it.copy(itemList = newArtObjectsList) }
            }
        }
    }

    private fun resetState() {
        lastPageReached = false
        page = 1
    }

}