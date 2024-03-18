package tgo1014.listofarts.domain.usecases

import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import tgo1014.listofarts.domain.fakes.FakeArtRepository
import tgo1014.listofarts.domain.fakes.FakeCoroutineProvider
import tgo1014.listofarts.domain.models.ArtObjectDomain
import kotlin.test.BeforeTest
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

class GetArtObjectsUseCaseTest {

    private lateinit var fakeArtRepository: FakeArtRepository
    private lateinit var usecase: GetArtObjectsUseCase

    private val testScope = TestScope()
    private val fakeCoroutineProvider = FakeCoroutineProvider(testScope)

    @BeforeTest
    fun setup() {
        fakeArtRepository = FakeArtRepository()
        usecase = GetArtObjectsUseCase(
            artRepository = fakeArtRepository,
            coroutineProvider = fakeCoroutineProvider,
        )
    }

    @Test
    fun `GIVEN a art request is made WHEN it's success THEN art list is returned`() =
        testScope.runTest {
            fakeArtRepository.artsToReturn = listOf(ArtObjectDomain())
            val result = usecase(1)
            assertTrue(result.isSuccess)
            assertTrue(result.getOrThrow().size == 1)
        }

    @Test
    fun `GIVEN a art request is made WHEN it fails THEN error is returned`() = testScope.runTest {
        fakeArtRepository.throwException = true
        val result = usecase(1)
        assertTrue(result.isFailure)
    }

    @Test
    fun `GIVEN a input with spaces WHEN usecase executed THEN spaces are replace with _`() =
        testScope.runTest {
            val input = "This is a input"
            assert(fakeArtRepository.search == null)
            usecase(page = 1, query = input)
            assertEquals(input.replace(" ", "_"), fakeArtRepository.search)
        }

    @Test
    fun `GIVEN a blank input WHEN usecase executed THEN query is null`() = testScope.runTest {
        val input = "                  "
        usecase(page = 1, query = input)
        assertNull(fakeArtRepository.search)
    }
}
