package tgo1014.listofbeers.data.repositories

import kotlinx.coroutines.test.runTest
import mockwebserver3.MockResponse
import mockwebserver3.MockWebServer
import org.junit.Before
import org.junit.Test
import tgo1014.listofbeers.data.network.PunkApi
import tgo1014.listofbeers.data.utils.filePathContentAsString
import tgo1014.listofbeers.data.utils.getService

class BeersRepositoryImplTest {

    private val mockWebServer = MockWebServer()
    private val punkApi = mockWebServer.getService<PunkApi>()
    private val responseJson = "response.json".filePathContentAsString()

    private lateinit var beerRepository: BeersRepositoryImpl

    @Before
    fun setup() {
        beerRepository = BeersRepositoryImpl(punkApi)
    }

    @Test
    fun `GIVEN a request is made WHEN response is successful THEN return beer list`() = runTest {
        mockWebServer.enqueue(MockResponse().setBody(responseJson))
        val result = beerRepository.getBeers(1)
        assert(result.isNotEmpty())
    }

    @Test(expected = Exception::class)
    fun `GIVEN a request is made WHEN response is invalid THEN throw exception`() = runTest {
        mockWebServer.enqueue(MockResponse().setBody(""))
        beerRepository.getBeers(1)
    }

    @Test
    fun `GIVEN a request by Id is made WHEN response is successful THEN return one beer`() = runTest {
        mockWebServer.enqueue(MockResponse().setBody(responseJson))
        val result = beerRepository.getBeerById(1)
        assert(result.name != null)
    }

    @Test(expected = Exception::class)
    fun `GIVEN a request by id is made WHEN response is invalid THEN throw exception`() = runTest {
        mockWebServer.enqueue(MockResponse().setBody(""))
        beerRepository.getBeerById(1)
    }

}