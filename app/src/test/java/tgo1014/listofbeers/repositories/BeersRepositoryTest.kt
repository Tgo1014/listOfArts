package tgo1014.listofbeers.repositories

import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Test
import tgo1014.listofbeers.getService
import tgo1014.listofbeers.models.Beer
import tgo1014.listofbeers.network.PunkApi
import tgo1014.listofbeers.toJsonString

class BeersRepositoryTest {

    private val mockWebServer = MockWebServer()
    private val punkApi by lazy { mockWebServer.getService<PunkApi>() }
    private val beerRepository by lazy { BeersRepository(punkApi) }

    @Test
    fun `GIVEN a request is made WHEN response is successful THEN return beer list`() =
        runTest {
            mockWebServer.enqueue(MockResponse().setBody(listOf(Beer()).toJsonString()))
            val result = beerRepository.getBeers(1)
            assert(result.isNotEmpty())
        }

    @Test(expected = Exception::class)
    fun `GIVEN a request is made WHEN response is invalid THEN throw exception`() = runTest {
        mockWebServer.enqueue(MockResponse().setBody(""))
        beerRepository.getBeers(1)
    }
}