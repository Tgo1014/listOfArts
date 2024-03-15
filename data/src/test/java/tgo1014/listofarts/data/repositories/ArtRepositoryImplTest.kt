package tgo1014.listofarts.data.repositories

import kotlinx.coroutines.test.runTest
import mockwebserver3.MockResponse
import mockwebserver3.MockWebServer
import org.junit.Before
import org.junit.Test
import tgo1014.listofarts.data.utils.filePathContentAsString
import tgo1014.listofarts.data.utils.getService

class ArtRepositoryImplTest {

    private val mockWebServer = MockWebServer()
    private val punkApi = mockWebServer.getService<PunkApi>()
    private val responseJson = "response.json".filePathContentAsString()

    private lateinit var beerRepository: ArtRepositoryImpl

    @Before
    fun setup() {
        beerRepository = ArtRepositoryImpl(punkApi)
    }

    @Test
    fun `GIVEN a request is made WHEN response is successful THEN return beer list`() = runTest {
        mockWebServer.enqueue(responseJson.asMockResponse)
        val result = beerRepository.getArt(1)
        assert(result.isNotEmpty())
    }

    @Test(expected = Exception::class)
    fun `GIVEN a request is made WHEN response is invalid THEN throw exception`() = runTest {
        mockWebServer.enqueue("".asMockResponse)
        beerRepository.getArt(1)
    }

    @Test
    fun `GIVEN a request by Id is made WHEN response is successful THEN return one beer`() = runTest {
        mockWebServer.enqueue(responseJson.asMockResponse)
        val result = beerRepository.getBeerById(1)
        assert(result.name != null)
    }

    @Test(expected = Exception::class)
    fun `GIVEN a request by id is made WHEN response is invalid THEN throw exception`() = runTest {
        mockWebServer.enqueue("".asMockResponse)
        beerRepository.getBeerById(1)
    }

    private val String.asMockResponse get() = MockResponse.Builder().body(this).build()

}