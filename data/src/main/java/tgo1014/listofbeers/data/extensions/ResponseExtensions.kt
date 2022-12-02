package tgo1014.listofbeers.data.extensions

import retrofit2.Response

fun <T> Response<T>.successOrThrow(): T {
    if (isSuccessful && body() != null) {
        return body()!!
    }
    throw Exception(this.errorBody()?.string())
}