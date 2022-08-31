package tgo1014.beerbox.interactors

import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Test
import tgo1014.beerbox.getService
import tgo1014.beerbox.models.Beer
import tgo1014.beerbox.network.PunkApi
import tgo1014.beerbox.repositories.BeersRepository
import tgo1014.beerbox.toJsonString

class GetBeersInteractorTest {

    private val mockWebServer = MockWebServer()
    private val punkApi by lazy { mockWebServer.getService<PunkApi>() }
    private val beerRepository by lazy { BeersRepository(punkApi) }
    private val interactor by lazy { GetBeersInteractor(beerRepository) }

    @Test
    fun `GIVEN a beer request is made WHEN it's success THEN beer list is returned`() =
        runBlocking {
            mockWebServer.enqueue(MockResponse().setBody(listOf(Beer()).toJsonString()))
            val result = interactor(1)
            assert(result.isSuccess)
            assert(result.getOrThrow().size == 1)
        }

    @Test
    fun `GIVEN a beer request is made WHEN it fails THEN error is returned`() = runBlocking {
        mockWebServer.enqueue(MockResponse().setResponseCode(400))
        val result = interactor(1)
        assert(result.isFailure)
    }
}