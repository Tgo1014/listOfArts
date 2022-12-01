package tgo1014.listofbeers.domain

import kotlin.coroutines.CoroutineContext

interface CoroutineProvider {
    val main: CoroutineContext
    val io: CoroutineContext
}