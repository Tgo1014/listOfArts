package tgo1014.listofbeers.presentation.ui.screens.details

import app.cash.turbine.testIn
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.wheneverBlocking
import tgo1014.listofbeers.domain.usecases.GetArtObjectByIdUseCase
import tgo1014.listofbeers.presentation.utils.ViewModelMainCoroutineRule

class DetailsViewModelTest {

    @get:Rule
    var coroutinesRule = ViewModelMainCoroutineRule()

    private var getArtObjectByIdUseCase = mock<GetArtObjectByIdUseCase>()
    private lateinit var viewModel: DetailsViewModel

    private val testBeer = BeerDomain(id = 1, name = "Test beer 1")

    @Before
    fun setup() {
        viewModel = DetailsViewModel(getArtObjectByIdUseCase)
    }

    @Test
    fun `GIVEN beer requested WHEN success THEN state updates`() = runTest {
        wheneverBlocking { getArtObjectByIdUseCase(any()) } doReturn Result.success(testBeer)
        val stateFlow = viewModel.state.testIn(this)
        assert(stateFlow.awaitItem() is DetailsState.Loading)

        viewModel.getBeerById(1)
        assert(stateFlow.awaitItem() is DetailsState.Success)

        stateFlow.ensureAllEventsConsumed()
        stateFlow.cancel()
    }

    @Test
    fun `GIVEN failed to get beer WHEN trying again THEN success`() = runTest {
        val stateFlow = viewModel.state.testIn(this)
        assert(stateFlow.awaitItem() is DetailsState.Loading)

        wheneverBlocking { getArtObjectByIdUseCase(any()) } doReturn Result.failure(Exception())
        viewModel.getBeerById(testBeer.id!!)
        assert(stateFlow.awaitItem() is DetailsState.Error)

        wheneverBlocking { getArtObjectByIdUseCase(any()) } doReturn Result.success(testBeer)
        viewModel.getBeerById(testBeer.id!!)
        val state = stateFlow.awaitItem()
        assert(state is DetailsState.Success)
        assert((state as DetailsState.Success).beer.id == testBeer.id)
        stateFlow.ensureAllEventsConsumed()
        stateFlow.cancel()
    }

}