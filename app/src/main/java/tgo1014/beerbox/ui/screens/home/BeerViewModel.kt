package tgo1014.beerbox.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.bindError
import androidx.lifecycle.bindLoading
import androidx.lifecycle.loadingFlow
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
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
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
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

    fun search(query: String) {
        resetVariables()
        lastSearchString = query
        searchInternal()
    }


    fun onBottomReached() {
        if (!loadingFlow.value && !lastPageReached) {
            page += 1
            fetchBeers()
        }
    }

    private fun fetchBeers(scope: CoroutineScope? = null) {
        getBeersInteractor(page, lastSearchString)
            .bindLoading(this)
            .bindError(this)
            .onSuccess(::handleSuccessfulResult)
            .onError(Timber::w)
            .launchIn(scope ?: viewModelScope)
    }

    private fun searchInternal() {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(350)
            if (lastSearchString.isNotBlank()) {
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

    private fun resetVariables() {
        lastPageReached = false
        page = 1
    }

    private fun state(mainViewState: HomeState) = viewModelScope.launch {
        _state.emit(mainViewState)
    }

}