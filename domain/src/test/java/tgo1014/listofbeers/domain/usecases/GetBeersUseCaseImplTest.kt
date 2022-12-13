package tgo1014.listofbeers.domain.usecases

import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import tgo1014.listofbeers.domain.fakes.FakeBeerRepository
import tgo1014.listofbeers.domain.fakes.FakeCoroutineProvider
import tgo1014.listofbeers.domain.models.BeerDomain
import kotlin.test.BeforeTest

class GetBeersUseCaseImplTest {

    private lateinit var fakeBeerRepository: FakeBeerRepository
    private lateinit var usecase: GetBeersUseCase

    private val testScope = TestScope()
    private val fakeCoroutineProvider = FakeCoroutineProvider(testScope)

    @BeforeTest
    fun setup() {
        fakeBeerRepository = FakeBeerRepository()
        usecase = GetBeersUseCaseImpl(fakeBeerRepository, fakeCoroutineProvider)
    }

    @Test
    fun `GIVEN a beer request is made WHEN it's success THEN beer list is returned`() =
        testScope.runTest {
            fakeBeerRepository.beersToReturn = listOf(BeerDomain())
            val result = usecase(1)
            assert(result.isSuccess)
            assert(result.getOrThrow().size == 1)
        }

    @Test
    fun `GIVEN a beer request is made WHEN it fails THEN error is returned`() = testScope.runTest {
        fakeBeerRepository.throwException = true
        val result = usecase(1)
        assert(result.isFailure)
    }

    @Test
    fun `GIVEN a input with spaces WHEN usecase executed THEN spaces are replace with _`() =
        testScope.runTest {
            val input = "This is a input"
            assert(fakeBeerRepository.search == null)
            usecase(page = 1, search = input)
            assert(fakeBeerRepository.search == input.replace(" ", "_"))
        }

    @Test
    fun `GIVEN a blank input WHEN usecase executed THEN query is null`() = testScope.runTest {
        val input = "                  "
        usecase(page = 1, search = input)
        assert(fakeBeerRepository.search == null)
    }

}