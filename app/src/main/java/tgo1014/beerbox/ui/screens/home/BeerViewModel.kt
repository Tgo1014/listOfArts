package tgo1014.beerbox.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.bindError
import androidx.lifecycle.bindLoading
import androidx.lifecycle.loadingFlow
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import tgo1014.beerbox.extensions.onError
import tgo1014.beerbox.extensions.onSuccess
import tgo1014.beerbox.interactors.GetBeersInteractor
import tgo1014.beerbox.models.Beer
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class BeerViewModel @Inject constructor(
    private val getBeersInteractor: GetBeersInteractor,
) : ViewModel() {

    private var page = 1
    private var lastPageReached = false
    var beerToShown: Beer? = null

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    // Search
    private var searchJob: Job? = null
    private var lastSearchString = ""
        set(value) {
            field = value
            state(_state.value.copy(searchText = value))
        }

    init {
        fetchBeers()
    }

    private fun fetchBeers() {
        getBeersInteractor(page)
            .bindLoading(this)
            .bindError(this)
            .onSuccess {
                if (it.isEmpty()) {
                    lastPageReached = true
                }
                val currentList = state.value.beerList.toMutableList()
                currentList.addAll(it)
                _state.emit(state.value.copy(beerList = currentList))
            }
            .onError(Timber::w)
            .launchIn(viewModelScope)
    }

    fun onBottomReached() {
        if (!loadingFlow.value && !lastPageReached) {
            page += 1
            fetchBeers()
        }
    }

    fun search(query: String) {
        lastSearchString = query
        resetAndFetchBeers()
    }

    private fun resetAndFetchBeers() = viewModelScope.launch {
        _state.emit(state.value.copy(beerList = emptyList()))
        lastPageReached = false
        page = 1
        fetchBeers()
    }

    private fun state(mainViewState: HomeState) = viewModelScope.launch {
        _state.emit(mainViewState)
    }

}