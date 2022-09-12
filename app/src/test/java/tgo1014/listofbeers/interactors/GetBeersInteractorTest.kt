package tgo1014.listofbeers.interactors

import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Test
import tgo1014.listofbeers.getService
import tgo1014.listofbeers.models.Beer
import tgo1014.listofbeers.network.PunkApi
import tgo1014.listofbeers.repositories.BeersRepository
import tgo1014.listofbeers.toJsonString

class GetBeersInteractorTest {

    private val mockWebServer = MockWebServer()
    private val punkApi by lazy { mockWebServer.getService<PunkApi>() }
    private val beerRepository by lazy { BeersRepository(punkApi) }
    private val interactor by lazy { GetBeersInteractor(beerRepository) }

    @Test
    fun `GIVEN a beer request is made WHEN it's success THEN beer list is returned`() =
        runTest {
            mockWebServer.enqueue(MockResponse().setBody(listOf(Beer()).toJsonString()))
            val result = interactor(1)
            assert(result.isSuccess)
            assert(result.getOrThrow().size == 1)
        }

    @Test
    fun `GIVEN a beer request is made WHEN it fails THEN error is returned`() = runTest {
        mockWebServer.enqueue(MockResponse().setResponseCode(400))
        val result = interactor(1)
        assert(result.isFailure)
    }
}