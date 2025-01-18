package tgo1014.listofarts.domain

import kotlin.coroutines.CoroutineContext

interface CoroutineProvider {
    val main: CoroutineContext
    val default: CoroutineContext
    val io: CoroutineContext
}
