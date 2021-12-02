package tgo1014.listofbeers.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.bindError
import androidx.lifecycle.bindLoading
import androidx.lifecycle.loadingFlow
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import tgo1014.listofbeers.extensions.onError
import tgo1014.listofbeers.extensions.onSuccess
import tgo1014.listofbeers.interactors.GetBeersInteractor
import tgo1014.listofbeers.models.Beer
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getBeersInteractor: GetBeersInteractor,
) : ViewModel() {

    private val _beersFlow = MutableStateFlow<List<Beer>>(emptyList())
    val beersFlow = _beersFlow.asStateFlow()

    private var page = 1
    private var lastPageReached = false

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
                val currentList = _beersFlow.value.toMutableList()
                currentList.addAll(it)
                _beersFlow.emit(currentList)
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
}