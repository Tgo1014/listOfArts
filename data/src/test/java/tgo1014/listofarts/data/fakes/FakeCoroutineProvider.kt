package tgo1014.listofarts.data.fakes

import kotlinx.coroutines.test.TestScope
import tgo1014.listofarts.domain.CoroutineProvider

class FakeCoroutineProvider(scope: TestScope) : CoroutineProvider {
    override val main = scope.coroutineContext
    override val io = scope.coroutineContext
}