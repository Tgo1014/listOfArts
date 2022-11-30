package tgo1014.listofbeers.repositories

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Test
import tgo1014.listofbeers.data.repositories.BeersRepositoryImpl
import tgo1014.listofbeers.getService
import tgo1014.listofbeers.models.Beer
import tgo1014.listofbeers.data.network.PunkApi
import tgo1014.listofbeers.toJsonString

@OptIn(ExperimentalCoroutinesApi::class)
class BeersRepositoryImplTest {

    private val mockWebServer = MockWebServer()
    private val punkApi = mockWebServer.getService<PunkApi>()
    private lateinit var beerRepository: BeersRepositoryImpl

    @Before
    fun setup() {
        beerRepository = BeersRepositoryImpl(punkApi)
    }

    @Test
    fun `GIVEN a request is made WHEN response is successful THEN return beer list`() = runTest {
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