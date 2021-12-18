package tgo1014.beerbox.ui.screens.home

import app.cash.turbine.test
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import tgo1014.beerbox.MainCoroutineRule
import tgo1014.beerbox.getService
import tgo1014.beerbox.interactors.GetBeersInteractor
import tgo1014.beerbox.models.Beer
import tgo1014.beerbox.models.Filter
import tgo1014.beerbox.network.PunkApi
import tgo1014.beerbox.repositories.BeersRepository
import tgo1014.beerbox.toJsonString
import timber.log.Timber

@ExperimentalCoroutinesApi
class BeerViewModelTest {

    // Set the main coroutines dispatcher for unit testing
    @get:Rule
    var coroutinesRule = MainCoroutineRule()

    private val mockWebServer = MockWebServer()
    private val beerApi by lazy { mockWebServer.getService<PunkApi>() }
    private val beerRepository by lazy { BeersRepository(beerApi) }
    private val interactor by lazy { GetBeersInteractor(beerRepository) }
    private lateinit var viewModel: BeerViewModel

    init {
        Timber.plant(Timber.DebugTree())
    }

    @Before
    fun setup() {
        viewModel = BeerViewModel(interactor)
    }

    @Test
    fun `GIVEN user scrolled WHEN reached the bottom THEN fetch next page`() = runBlocking {
        val beer1 = Beer(name = "Test beer 1")
        val beer2 = Beer(name = "Test beer 2")
        viewModel.state.test {
            assert(awaitItem().beerList.isEmpty()) // Init state, empty
            mockWebServer.enqueue(MockResponse().setBody(listOf(beer1).toJsonString()))
            viewModel.fetchBeers()
            var beerList = awaitItem().beerList
            assert(beerList.size == 1)
            assert(beerList.find { it.name == beer1.name } != null)
            assert(beerList.find { it.name == beer2.name } == null)
            mockWebServer.enqueue(MockResponse().setBody(listOf(beer2).toJsonString()))
            viewModel.onBottomReached()
            beerList = awaitItem().beerList
            assert(beerList.size == 2)
            assert(beerList.find { it.name == beer1.name } != null)
            assert(beerList.find { it.name == beer2.name } != null)
        }
    }

    @Test
    fun `GIVEN user changed filter WHEN list have values THEN clear list and fetch new`() =
        runBlocking {
            val filter = Filter.LAGER
            val beer1 = Beer(name = "Test beer 1")
            val beer2 = Beer(name = "Test beer 2")
            viewModel.state.test {
                assert(awaitItem().beerList.isEmpty()) // Init state, empty
                mockWebServer.enqueue(MockResponse().setBody(listOf(beer1).toJsonString()))
                viewModel.fetchBeers()
                var beerList = awaitItem().beerList
                assert(beerList.size == 1)
                assert(beerList.find { it.name == beer1.name } != null)
                assert(beerList.find { it.name == beer2.name } == null)
                mockWebServer.enqueue(MockResponse().setBody(listOf(beer2).toJsonString()))
                viewModel.onFilterClicked(filter)
                assert(awaitItem().filters.any { it.filter == filter })
                beerList = awaitItem().beerList
                assert(beerList.size == 1)
                assert(beerList.find { it.name == beer1.name } == null)
                assert(beerList.find { it.name == beer2.name } != null)
            }
        }

    @Test
    fun `GIVEN user changed filter WHEN updating filters THEN just last one is selected`() =
        runBlocking {
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
        runBlocking {
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
