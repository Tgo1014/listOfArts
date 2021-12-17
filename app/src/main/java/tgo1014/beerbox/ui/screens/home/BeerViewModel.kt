package tgo1014.beerbox.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.bindError
import androidx.lifecycle.bindLoading
import androidx.lifecycle.loadingFlow
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import tgo1014.beerbox.extensions.onError
import tgo1014.beerbox.extensions.onSuccess
import tgo1014.beerbox.interactors.GetBeersInteractor
import tgo1014.beerbox.models.Beer
import timber.log.Timber
import java.util.Date
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

    init {
        fetchBeers()
    }

    private fun fetchBeers() {
        getBeersInteractor(page, state.value.afterFilter, state.value.beforeFilter)
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

    fun onAfterFilterClicked() = viewModelScope.launch {
        if (state.value.afterFilter != null) {
            _state.emit(state.value.copy(afterFilter = null))
        } else {
            _state.emit(state.value.copy(isCalendarAfterOpen = true))
        }
        resetAndFetchBeers()
    }

    fun onBeforeFilterClicked() = viewModelScope.launch {
        if (state.value.beforeFilter != null) {
            _state.emit(state.value.copy(beforeFilter = null))
        } else {
            _state.emit(state.value.copy(isCalendarBeforeOpen = true))
        }
        resetAndFetchBeers()
    }

    fun onCalendarCancel() = viewModelScope.launch {
        _state.emit(state.value.copy(isCalendarBeforeOpen = false, isCalendarAfterOpen = false))
    }

    fun onAfterFilterClicked(date: Date?) = viewModelScope.launch {
        _state.emit(state.value.copy(afterFilter = date, isCalendarAfterOpen = false))
        resetAndFetchBeers()
    }

    fun onBeforeFilterSelected(date: Date?) = viewModelScope.launch {
        _state.emit(state.value.copy(beforeFilter = date, isCalendarBeforeOpen = false))
        resetAndFetchBeers()
    }

    private fun resetAndFetchBeers() = viewModelScope.launch {
        _state.emit(state.value.copy(beerList = emptyList()))
        lastPageReached = false
        page = 1
        fetchBeers()
    }
}