package extensions

import de.jensklingenberg.ktorfit.Response

fun <T> Response<T>.successOrThrow(): T {
    if (isSuccessful && body() != null) {
        return body()!!
    }
    throw Exception(this.errorBody()?.toString())
}