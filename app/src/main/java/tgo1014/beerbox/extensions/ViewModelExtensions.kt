@file:Suppress("PackageDirectoryMismatch")

package androidx.lifecycle // Package set to androidx.lifecycle so we can have access to package private methods

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import tgo1014.beerbox.extensions.UseCaseResult
import tgo1014.beerbox.extensions.onError

private const val ERROR_FLOW_KEY = "androidx.lifecycle.ErrorFlow"
private const val LOADING_FLOW_KEY = "androidx.lifecycle.LoadingFlow"

fun ViewModel.sendError(throwable: Throwable) {
    viewModelScope.launch {
        getErrorMutableSharedFlow().emit(throwable)
    }
}

suspend fun ViewModel.emitThrowable(throwable: Throwable) {
    getErrorMutableSharedFlow().emit(throwable)
}

val ViewModel.throwableFlow: SharedFlow<Throwable>
    get() {
        return getErrorMutableSharedFlow()
    }

val ViewModel.loadingFlow: StateFlow<Boolean>
    get() {
        return getLoadingMutableStateFlow()
    }

fun ViewModel.getLoadingMutableStateFlow(): MutableStateFlow<Boolean> {
    val flow: MutableStateFlow<Boolean>? = getTag(LOADING_FLOW_KEY)
    return flow ?: setTagIfAbsent(LOADING_FLOW_KEY, MutableStateFlow(false))
}

fun ViewModel.getErrorMutableSharedFlow(): MutableSharedFlow<Throwable> {
    val flow: MutableSharedFlow<Throwable>? = getTag(ERROR_FLOW_KEY)
    return flow ?: setTagIfAbsent(ERROR_FLOW_KEY, MutableSharedFlow())
}

fun <T> Flow<T>.bindLoading(viewModel: ViewModel): Flow<T> {
    return this
        .onStart {
            viewModel.getLoadingMutableStateFlow().value = true
        }
        .onCompletion {
            viewModel.getLoadingMutableStateFlow().value = false
        }
}

fun <T> Flow<UseCaseResult<T>>.bindError(viewModel: ViewModel): Flow<UseCaseResult<T>> {
    return this
        .onError {
            viewModel.emitThrowable(it)
        }
}
