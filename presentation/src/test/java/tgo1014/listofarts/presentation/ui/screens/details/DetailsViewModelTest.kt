package tgo1014.listofarts.presentation.ui.screens.details

import app.cash.turbine.turbineScope
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.wheneverBlocking
import tgo1014.listofarts.domain.models.ArtObjectDomain
import tgo1014.listofarts.domain.usecases.GetArtObjectByIdUseCase
import tgo1014.listofarts.presentation.utils.ViewModelMainCoroutineRule

class DetailsViewModelTest {

    @get:Rule
    var coroutinesRule = ViewModelMainCoroutineRule()

    private var getArtObjectByIdUseCase = mock<GetArtObjectByIdUseCase>()
    private lateinit var viewModel: DetailsViewModel

    private val testArt = ArtObjectDomain(id = "id", title = "Test art 1")

    @Before
    fun setup() {
        viewModel = DetailsViewModel(getArtObjectByIdUseCase)
    }

    @Test
    fun `GIVEN art requested WHEN success THEN state updates`() = runTest {
        turbineScope {
            wheneverBlocking { getArtObjectByIdUseCase(any()) } doReturn Result.success(testArt)
            val stateFlow = viewModel.state.testIn(this)
            assert(stateFlow.awaitItem() is DetailsState.Loading)

            viewModel.getArtObjectById("id")
            assert(stateFlow.awaitItem() is DetailsState.Success)

            stateFlow.ensureAllEventsConsumed()
            stateFlow.cancel()
        }
    }

    @Test
    fun `GIVEN failed to get art WHEN trying again THEN success`() = runTest {
        turbineScope {
            val stateFlow = viewModel.state.testIn(this)
            assert(stateFlow.awaitItem() is DetailsState.Loading)

            wheneverBlocking { getArtObjectByIdUseCase(any()) } doReturn Result.failure(Exception())
            viewModel.getArtObjectById(testArt.id)
            assert(stateFlow.awaitItem() is DetailsState.Error)

            wheneverBlocking { getArtObjectByIdUseCase(any()) } doReturn Result.success(testArt)
            viewModel.getArtObjectById(testArt.id)
            val state = stateFlow.awaitItem()
            assert(state is DetailsState.Success)
            assert((state as DetailsState.Success).item.id == testArt.id)
            stateFlow.ensureAllEventsConsumed()
            stateFlow.cancel()
        }
    }

}