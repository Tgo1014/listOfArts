package tgo1014.listofbeers.presentation.ui.screens.details

import app.cash.turbine.testIn
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import tgo1014.listofbeers.domain.models.BeerDomain
import tgo1014.listofbeers.presentation.fakes.FakeGetBeerByIdUseCase
import tgo1014.listofbeers.presentation.utils.ViewModelMainCoroutineRule

class DetailsViewModelTest {

    @get:Rule
    var coroutinesRule = ViewModelMainCoroutineRule()

    private lateinit var getBeerByIdUseCase: FakeGetBeerByIdUseCase
    private lateinit var viewModel: DetailsViewModel

    private val testBeer = BeerDomain(id = 1, name = "Test beer 1")

    @Before
    fun setup() {
        getBeerByIdUseCase = FakeGetBeerByIdUseCase(testBeer)
        viewModel = DetailsViewModel(getBeerByIdUseCase)
    }

    @Test
    fun `GIVEN beer requested WHEN success THEN state updates`() = runTest {
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
        getBeerByIdUseCase.throwException = true
        viewModel.getBeerById(testBeer.id!!)
        assert(stateFlow.awaitItem() is DetailsState.Error)
        getBeerByIdUseCase.throwException = false
        viewModel.getBeerById(testBeer.id!!)
        val state = stateFlow.awaitItem()
        assert(state is DetailsState.Success)
        assert((state as DetailsState.Success).beer.id == testBeer.id)
        stateFlow.ensureAllEventsConsumed()
        stateFlow.cancel()
    }

}