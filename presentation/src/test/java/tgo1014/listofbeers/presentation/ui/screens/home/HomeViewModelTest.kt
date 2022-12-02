package tgo1014.listofbeers.presentation.ui.screens.home

import app.cash.turbine.test
import app.cash.turbine.testIn
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import tgo1014.listofbeers.domain.models.BeerDomain
import tgo1014.listofbeers.presentation.fakes.FakeGetBeersUseCase
import tgo1014.listofbeers.presentation.models.Filter
import tgo1014.listofbeers.presentation.models.mappers.toUi
import tgo1014.listofbeers.presentation.utils.ViewModelMainCoroutineRule


class HomeViewModelTest {

    @get:Rule
    var coroutinesRule = ViewModelMainCoroutineRule()

    private lateinit var getBeersUseCase: FakeGetBeersUseCase
    private lateinit var viewModel: HomeViewModel

    private val testBeer1 = BeerDomain(id = 1, name = "Test beer 1")
    private val testBeer2 = BeerDomain(id = 2, name = "Test beer 2")

    @Before
    fun setup() {
        getBeersUseCase = FakeGetBeersUseCase()
        viewModel = HomeViewModel(getBeersUseCase)
    }

    @Test
    fun `GIVEN user scrolled WHEN reached the bottom THEN fetch next page`() = runTest {
        val stateFlow = viewModel.state.testIn(this)
        assert(stateFlow.awaitItem().beerList.isEmpty()) // Init state, empty
        getBeersUseCase.beersToReturn = listOf(testBeer1)
        viewModel.fetchBeers()
        var beerList = stateFlow.awaitItem().beerList
        assert(beerList.size == 1)
        assert(beerList.contains(testBeer1.toUi()))
        assert(!beerList.contains(testBeer2.toUi()))
        getBeersUseCase.beersToReturn = listOf(testBeer2)
        viewModel.onBottomReached()
        beerList = stateFlow.awaitItem().beerList
        assert(beerList.size == 2)
        assert(beerList.contains(testBeer1.toUi()))
        assert(beerList.contains(testBeer2.toUi()))
        stateFlow.ensureAllEventsConsumed()
        stateFlow.cancel()
    }

    @Test
    fun `GIVEN user changed filter WHEN list have values THEN clear list and fetch new`() =
        runTest {
            val filter = Filter.LAGER
            val stateFlow = viewModel.state.testIn(this)
            assert(stateFlow.awaitItem().beerList.isEmpty()) // Init state, empty
            getBeersUseCase.beersToReturn = listOf(testBeer1)
            viewModel.fetchBeers()
            var beerList = stateFlow.awaitItem().beerList
            assert(beerList.size == 1)
            assert(beerList.find { it.name == testBeer1.name } != null)
            assert(beerList.find { it.name == testBeer2.name } == null)
            getBeersUseCase.beersToReturn = listOf(testBeer2)
            viewModel.onFilterClicked(filter)
            val state = stateFlow.expectMostRecentItem()
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
