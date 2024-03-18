package tgo1014.listofarts.data.repositories

import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import mockwebserver3.MockResponse
import mockwebserver3.MockWebServer
import org.junit.Before
import org.junit.Test
import tgo1014.listofarts.data.network.RijksmMuseumApi
import tgo1014.listofarts.data.utils.filePathContentAsString
import tgo1014.listofarts.data.utils.getService

class ArtRepositoryImplTest {

    private val mockWebServer = MockWebServer()
    private val api = mockWebServer.getService<RijksmMuseumApi>()
    private val responseGetListJson = "responseGetList.json".filePathContentAsString()
    private val responseGetByIdJson = "responseGetById.json".filePathContentAsString()

    private lateinit var artRepository: ArtRepositoryImpl

    @Before
    fun setup() {
        artRepository = ArtRepositoryImpl(api)
    }

    @Test
    fun `GIVEN a request is made WHEN response is successful THEN return art list`() = runTest {
        mockWebServer.enqueue(responseGetListJson.asMockResponse)
        val result = artRepository.getArt(1)
        assertTrue(result.isNotEmpty())
    }

    @Test(expected = Exception::class)
    fun `GIVEN a request is made WHEN response is invalid THEN throw exception`() = runTest {
        mockWebServer.enqueue("".asMockResponse)
        artRepository.getArt(1)
    }

    @Test
    fun `GIVEN a request by Id is made WHEN response is successful THEN return one art`() =
        runTest {
            mockWebServer.enqueue(responseGetByIdJson.asMockResponse)
            val result = artRepository.getArtById("Lorem")
            assertTrue(result.title.isNotBlank())
        }

    @Test(expected = Exception::class)
    fun `GIVEN a request by id is made WHEN response is invalid THEN throw exception`() = runTest {
        mockWebServer.enqueue("".asMockResponse)
        artRepository.getArtById("Lorem")
    }

    private val String.asMockResponse get() = MockResponse.Builder().body(this).build()

}