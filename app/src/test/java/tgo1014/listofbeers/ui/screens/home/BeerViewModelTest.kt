package tgo1014.listofbeers.ui.screens.home

import app.cash.turbine.test
import app.cash.turbine.testIn
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import tgo1014.listofbeers.ViewModelMainCoroutineRule
import tgo1014.listofbeers.fakes.FakeBeerRepository
import tgo1014.listofbeers.interactors.GetBeersInteractor
import tgo1014.listofbeers.models.Beer
import tgo1014.listofbeers.models.Filter
import tgo1014.listofbeers.presentation.ui.screens.home.BeerViewModel

@ExperimentalCoroutinesApi
class BeerViewModelTest {

    @get:Rule
    var coroutinesRule = ViewModelMainCoroutineRule()

    private lateinit var fakeBeerRepository: FakeBeerRepository
    private lateinit var viewModel: BeerViewModel

    private val testBeer1 = Beer(id = 1, name = "Test beer 1")
    private val testBeer2 = Beer(id = 2, name = "Test beer 2")

    @Before
    fun setup() {
        fakeBeerRepository = FakeBeerRepository()
        val interactor = GetBeersInteractor(fakeBeerRepository)
        viewModel = BeerViewModel(interactor)
    }

    @Test
    fun `GIVEN user scrolled WHEN reached the bottom THEN fetch next page`() = runTest {
        val stateFlow = viewModel.state.testIn(this)
        assert(stateFlow.awaitItem().beerList.isEmpty()) // Init state, empty
        fakeBeerRepository.beersToReturn = listOf(testBeer1)
        viewModel.fetchBeers()
        var beerList = stateFlow.awaitItem().beerList
        assert(beerList.size == 1)
        assert(beerList.find { it.name == testBeer1.name } != null)
        assert(beerList.find { it.name == testBeer2.name } == null)
        fakeBeerRepository.beersToReturn = listOf(testBeer2)
        viewModel.onBottomReached()
        beerList = stateFlow.awaitItem().beerList
        assert(beerList.size == 2)
        assert(beerList.find { it.name == testBeer1.name } != null)
        assert(beerList.find { it.name == testBeer2.name } != null)
        stateFlow.ensureAllEventsConsumed()
        stateFlow.cancel()
    }

    @Test
    fun `GIVEN user changed filter WHEN list have values THEN clear list and fetch new`() =
        runTest {
            val filter = Filter.LAGER
            val stateFlow = viewModel.state.testIn(this)
            assert(stateFlow.awaitItem().beerList.isEmpty()) // Init state, empty
            fakeBeerRepository.beersToReturn = listOf(testBeer1)
            viewModel.fetchBeers()
            var beerList = stateFlow.awaitItem().beerList
            assert(beerList.size == 1)
            assert(beerList.find { it.name == testBeer1.name } != null)
            assert(beerList.find { it.name == testBeer2.name } == null)
            fakeBeerRepository.beersToReturn = listOf(testBeer2)
            viewModel.onFilterClicked(filter)
            val state = stateFlow.awaitItem()
            assert(state.filters.any { it.filter == filter })
            beerList = state.beerList
            assert(beerList.size == 1)
            assert(beerList.find { it.name == testBeer1.name } == null)
            assert(beerList.find { it.name == testBeer2.name } != null)
            stateFlow.ensureAllEventsConsumed()
            stateFlow.cancel()
        }

    @Test
    fun `GIVEN user changed filter WHEN updating filters THEN just last one is selected`() =
        runTest {
            val filter1 = Filter.LAGER
            val filter2 = Filter.BLONDE
            viewModel.state.test {
                assert(awaitItem().filters.none { it.isSelected })
                viewModel.onFilterClicked(filter1)
                var filterList = awaitItem().filters
                assert(filterList.find { it.filter == filter1 }?.isSelected == true)
                assert(filterList.find { it.filter == filter2 }?.isSelected == false)
                viewModel.onFilterClicked(filter2)
                filterList = awaitItem().filters
                assert(filterList.find { it.filter == filter1 }?.isSelected == false)
                assert(filterList.find { it.filter == filter2 }?.isSelected == true)
            }
        }

    @Test
    fun `GIVEN user changed filter WHEN is the same as previous THEN its deselected`() =
        runTest {
            val filter = Filter.LAGER
            viewModel.state.test {
                assert(awaitItem().filters.none { it.isSelected })
                viewModel.onFilterClicked(filter)
                assert(awaitItem().filters.find { it.filter == filter }?.isSelected == true)
                viewModel.onFilterClicked(filter)
                assert(awaitItem().filters.none { it.isSelected })
            }
        }
}
