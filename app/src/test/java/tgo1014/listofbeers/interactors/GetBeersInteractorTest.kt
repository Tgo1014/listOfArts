package tgo1014.listofbeers.interactors

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import tgo1014.listofbeers.fakes.FakeBeerRepository
import tgo1014.listofbeers.models.Beer

@OptIn(ExperimentalCoroutinesApi::class)
class GetBeersInteractorTest {

    private lateinit var fakeBeerRepository: FakeBeerRepository
    private lateinit var interactor: GetBeersInteractor

    @Before
    fun setup() {
        fakeBeerRepository = FakeBeerRepository()
        interactor = GetBeersInteractor(fakeBeerRepository)
    }

    @Test
    fun `GIVEN a beer request is made WHEN it's success THEN beer list is returned`() = runTest {
        fakeBeerRepository.beersToReturn = listOf(Beer())
        val result = interactor(1)
        assert(result.isSuccess)
        assert(result.getOrThrow().size == 1)
    }

    @Test
    fun `GIVEN a beer request is made WHEN it fails THEN error is returned`() = runTest {
        fakeBeerRepository.throwException = true
        val result = interactor(1)
        assert(result.isFailure)
    }
}