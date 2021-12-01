package tgo1014.listofbeers.extensions

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.transform
import timber.log.Timber

sealed class UseCaseResult<out T> {
    data class Success<T>(val value: T) : UseCaseResult<T>()
    data class Error(val error: Throwable) : UseCaseResult<Nothing>()
}

inline fun <T> useCaseFlow(
    crossinline block: suspend () -> T,
): Flow<UseCaseResult<T>> = flow {
    try {
        emit(UseCaseResult.Success(block()))
    } catch (e: Exception) {
        Timber.e(e)
        emit(UseCaseResult.Error(e))
    }
}

fun <T> observableFlow(block: suspend FlowCollector<T>.() -> Unit): Flow<UseCaseResult<T>> =
    flow(block)
        .catch { exception ->
            Timber.e(exception)
            UseCaseResult.Error(exception)
        }
        .map {
            UseCaseResult.Success(it)
        }

fun <T> Flow<UseCaseResult<T>>.onSuccess(action: suspend (T) -> Unit): Flow<UseCaseResult<T>> =
    transform { result ->
        if (result is UseCaseResult.Success<T>) {
            action(result.value)
        }
        return@transform emit(result)
    }

fun <T> Flow<UseCaseResult<T>>.onError(action: suspend (Throwable) -> Unit): Flow<UseCaseResult<T>> =
    transform { result ->
        if (result is UseCaseResult.Error) {
            action(result.error)
        }
        return@transform emit(result)
    }
