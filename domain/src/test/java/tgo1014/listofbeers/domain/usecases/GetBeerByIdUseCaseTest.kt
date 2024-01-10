package tgo1014.listofbeers.domain.usecases

import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import tgo1014.listofbeers.domain.fakes.FakeBeerRepository
import tgo1014.listofbeers.domain.fakes.FakeCoroutineProvider
import tgo1014.listofbeers.domain.models.BeerDomain
import kotlin.test.BeforeTest
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class GetBeerByIdUseCaseTest {

    private lateinit var fakeBeerRepository: FakeBeerRepository
    private lateinit var usecase: GetBeerByIdUseCase

    private val testScope = TestScope()
    private val fakeCoroutineProvider = FakeCoroutineProvider(testScope)

    @BeforeTest
    fun setup() {
        fakeBeerRepository = FakeBeerRepository()
        usecase = GetBeerByIdUseCase(
            beersRepository = fakeBeerRepository,
            coroutineProvider = fakeCoroutineProvider
        )
    }

    @Test
    fun `GIVEN a beer request is made WHEN it's success THEN beer list is returned`() =
        testScope.runTest {
            fakeBeerRepository.beersToReturn = listOf(BeerDomain())
            val result = usecase(1)
            assertNotNull(result.getOrNull())
            assertTrue(result.isSuccess)
        }

    @Test
    fun `GIVEN a beer request is made WHEN it fails THEN error is returned`() = testScope.runTest {
        fakeBeerRepository.throwException = true
        val result = usecase(1)
        assertTrue(result.isFailure)
    }
}
