package tgo1014.listofbeers.ui.screens.home

import app.cash.turbine.testIn
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import tgo1014.listofbeers.MainCoroutineRule
import tgo1014.listofbeers.fakes.FakeBeerRepository
import tgo1014.listofbeers.interactors.GetBeersInteractor
import tgo1014.listofbeers.models.Beer
import tgo1014.listofbeers.toJsonString

@ExperimentalCoroutinesApi
class BeerViewModelTest {

    // Set the main coroutines dispatcher for unit testing
    @get:Rule
    var coroutinesRule = MainCoroutineRule()

    private lateinit var fakeBeerRepository: FakeBeerRepository
    private lateinit var viewModel: BeerViewModel

    @Before
    fun setup() {
        fakeBeerRepository = FakeBeerRepository()
        val interactor = GetBeersInteractor(FakeBeerRepository())
        viewModel = BeerViewModel(interactor)
    }

    @Test
    fun `GIVEN user scrolled WHEN reached the bottom THEN fetch next page`() = runTest {
        val beer1 = Beer(name = "Test beer 1")
        val beer2 = Beer(name = "Test beer 2")
        val stateFlow = viewModel.state.testIn(this)
        assert(stateFlow.awaitItem().beerList.isEmpty()) // Init state, empty
        fakeBeerRepository.beersToReturn = listOf(beer1)
        viewModel.fetchBeers()
        var beerList = stateFlow.awaitItem().beerList
        assert(beerList.size == 1)
        assert(beerList.find { it.name == beer1.name } != null)
        assert(beerList.find { it.name == beer2.name } == null)
        fakeBeerRepository.beersToReturn = listOf(beer2)
        viewModel.onBottomReached()
        beerList = stateFlow.awaitItem().beerList
        assert(beerList.size == 2)
        assert(beerList.find { it.name == beer1.name } != null)
        assert(beerList.find { it.name == beer2.name } != null)
    }

//    @Test
//    fun `GIVEN user changed filter WHEN list have values THEN clear list and fetch new`() =
//        runTest {
//            val filter = Filter.LAGER
//            val beer1 = Beer(name = "Test beer 1")
//            val beer2 = Beer(name = "Test beer 2")
//            viewModel.state.test {
//                assert(awaitItem().beerList.isEmpty()) // Init state, empty
//                mockWebServer.enqueue(MockResponse().setBody(listOf(beer1).toJsonString()))
//                viewModel.fetchBeers()
//                var beerList = awaitItem().beerList
//                assert(beerList.size == 1)
//                assert(beerList.find { it.name == beer1.name } != null)
//                assert(beerList.find { it.name == beer2.name } == null)
//                mockWebServer.enqueue(MockResponse().setBody(listOf(beer2).toJsonString()))
//                viewModel.onFilterClicked(filter)
//                assert(awaitItem().filters.any { it.filter == filter })
//                beerList = awaitItem().beerList
//                assert(beerList.size == 1)
//                assert(beerList.find { it.name == beer1.name } == null)
//                assert(beerList.find { it.name == beer2.name } != null)
//            }
//        }
//
//    @Test
//    fun `GIVEN user changed filter WHEN updating filters THEN just last one is selected`() =
//        runTest {
//            val filter1 = Filter.LAGER
//            val filter2 = Filter.BLONDE
//            viewModel.state.test {
//                assert(awaitItem().filters.none { it.isSelected })
//                viewModel.onFilterClicked(filter1)
//                var filterList = awaitItem().filters
//                assert(filterList.find { it.filter == filter1 }?.isSelected == true)
//                assert(filterList.find { it.filter == filter2 }?.isSelected == false)
//                viewModel.onFilterClicked(filter2)
//                filterList = awaitItem().filters
//                assert(filterList.find { it.filter == filter1 }?.isSelected == false)
//                assert(filterList.find { it.filter == filter2 }?.isSelected == true)
//            }
//        }
//
//    @Test
//    fun `GIVEN user changed filter WHEN is the same as previous THEN its deselected`() =
//        runTest {
//            val filter = Filter.LAGER
//            viewModel.state.test {
//                assert(awaitItem().filters.none { it.isSelected })
//                viewModel.onFilterClicked(filter)
//                assert(awaitItem().filters.find { it.filter == filter }?.isSelected == true)
//                viewModel.onFilterClicked(filter)
//                assert(awaitItem().filters.none { it.isSelected })
//            }
//        }
}
