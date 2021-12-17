package tgo1014.beerbox.repositories

import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.assertThrows
import org.junit.Test
import tgo1014.beerbox.getService
import tgo1014.beerbox.models.Beer
import tgo1014.beerbox.network.PunkApi
import tgo1014.beerbox.toJsonString

class BeersRepositoryTest {

    private val mockWebServer = MockWebServer()
    private val instagramApi by lazy { mockWebServer.getService<PunkApi>() }
    private val beerRepository by lazy { BeersRepository(instagramApi) }

    @Test
    fun `GIVEN a request is made WHEN response is successful THEN return beer list`() =
        runBlocking {
            mockWebServer.enqueue(MockResponse().setBody(listOf(Beer()).toJsonString()))
            val result = beerRepository.getBeers(1)
            assert(result.isNotEmpty())
        }

    @Test
    fun `GIVEN a request is made WHEN response is invalid THEN throw exception`() {
        mockWebServer.enqueue(MockResponse().setBody(""))
        assertThrows(Exception::class.java) {
            runBlocking { beerRepository.getBeers(1) }
        }
    }
}