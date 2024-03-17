package tgo1014.listofarts.domain.usecases

import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import tgo1014.listofarts.domain.fakes.FakeArtRepository
import tgo1014.listofarts.domain.fakes.FakeCoroutineProvider
import tgo1014.listofarts.domain.models.ArtObjectDomain
import kotlin.test.BeforeTest
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class GetArtObjectByIdUseCaseTest {

    private lateinit var fakeArtRepository: FakeArtRepository
    private lateinit var usecase: GetArtObjectByIdUseCase

    private val testScope = TestScope()
    private val fakeCoroutineProvider = FakeCoroutineProvider(testScope)

    @BeforeTest
    fun setup() {
        fakeArtRepository = FakeArtRepository()
        usecase = GetArtObjectByIdUseCase(
            artRepository = fakeArtRepository,
            coroutineProvider = fakeCoroutineProvider
        )
    }

    @Test
    fun `GIVEN a art request is made WHEN it's success THEN art list is returned`() =
        testScope.runTest {
            fakeArtRepository.artsToReturn = listOf(ArtObjectDomain())
            val result = usecase("Lorem")
            assertNotNull(result.getOrNull())
            assertTrue(result.isSuccess)
        }

    @Test
    fun `GIVEN a art request is made WHEN it fails THEN error is returned`() = testScope.runTest {
        fakeArtRepository.throwException = true
        val result = usecase("Lorem")
        assertTrue(result.isFailure)
    }
}
