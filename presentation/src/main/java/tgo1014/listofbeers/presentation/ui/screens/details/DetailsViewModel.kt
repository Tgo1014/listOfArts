package tgo1014.listofbeers.presentation.ui.screens.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import tgo1014.listofbeers.domain.usecases.GetBeerByIdUseCase
import tgo1014.listofbeers.presentation.models.mappers.toUi
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getBeerByIdUseCase: GetBeerByIdUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(DetailsState())
    val state = _state.asStateFlow()

    private var isLoading: Boolean
        get() = _state.value.isLoading
        set(value) = _state.update { it.copy(isLoading = value) }

    fun getBeerById(id: Int) = viewModelScope.launch {
        isLoading = true
        getBeerByIdUseCase(id)
            .onSuccess { beer ->
                _state.update { it.copy(beer = beer.toUi()) }
            }
        isLoading = false
    }

}