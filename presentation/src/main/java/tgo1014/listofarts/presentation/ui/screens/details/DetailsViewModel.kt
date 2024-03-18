package tgo1014.listofarts.presentation.ui.screens.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import tgo1014.listofarts.domain.usecases.GetArtObjectByIdUseCase
import tgo1014.listofarts.presentation.models.mappers.toUi
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getArtObjectByIdUseCase: GetArtObjectByIdUseCase,
) : ViewModel() {

    private val _state: MutableStateFlow<DetailsState> = MutableStateFlow(DetailsState.Loading)
    val state = _state.asStateFlow()

    fun getArtObjectById(id: String) = viewModelScope.launch {
        _state.update { DetailsState.Loading }
        getArtObjectByIdUseCase(id)
            .onSuccess { item -> _state.update { DetailsState.Success(item.toUi()) } }
            .onFailure { _state.update { DetailsState.Error } }
    }

}