package tgo1014.listofbeers.ui.screens.home

import app.cash.turbine.test
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import tgo1014.listofbeers.MainCoroutineRule
import tgo1014.listofbeers.getService
import tgo1014.listofbeers.interactors.GetBeersInteractor
import tgo1014.listofbeers.models.Beer
import tgo1014.listofbeers.network.PunkApi
import tgo1014.listofbeers.repositories.BeersRepository
import tgo1014.listofbeers.toJsonString
import java.util.Date

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    // Set the main coroutines dispatcher for unit testing
    @get:Rule
    var coroutinesRule = MainCoroutineRule()

    private val mockWebServer = MockWebServer()
    private val instagramApi by lazy { mockWebServer.getService<PunkApi>() }
    private val beerRepository by lazy { BeersRepository(instagramApi) }
    private val interactor by lazy { GetBeersInteractor(beerRepository) }
    private lateinit var viewModel: HomeViewModel

    @Before
    fun setup() {
        viewModel = HomeViewModel(interactor)
    }

    @Test
    fun `GIVEN user clicks after filter WHEN filter is empty THEN after calendar opens`() =
        runBlocking {
            viewModel.state.test {
                awaitItem() // Init state, ignore
                viewModel.onAfterFilterClicked()
                assert(awaitItem().isCalendarAfterOpen)
            }
        }

    @Test
    fun `GIVEN user clicks before filter WHEN filter is empty THEN before calendar opens`() =
        runBlocking {
            viewModel.state.test {
                awaitItem() // Init state, ignore
                viewModel.onBeforeFilterClicked()
                assert(awaitItem().isCalendarBeforeOpen)
            }
        }

    @Test
    fun `GIVEN after filter is not null WHEN filter clicked THEN filter is cleared`() =
        runBlocking {
            viewModel.state.test {
                awaitItem() // Init state, ignore
                viewModel.onAfterFilterClicked(Date())
                assert(awaitItem().afterFilter != null)
                viewModel.onAfterFilterClicked()
                assert(awaitItem().afterFilter == null)
            }
        }

    @Test
    fun `GIVEN before filter is not null WHEN filter clicked THEN filter is clear`() = runBlocking {
        viewModel.state.test {
            awaitItem() // Init state, ignore
            viewModel.onBeforeFilterSelected(Date())
            assert(awaitItem().beforeFilter != null)
            viewModel.onBeforeFilterClicked()
            assert(awaitItem().beforeFilter == null)
        }
    }

    @Test
    fun `GIVEN after calendar is open WHEN user cancel THEN calendar is closed`() = runBlocking {
        viewModel.state.test {
            awaitItem() // Init state, ignore
            viewModel.onAfterFilterClicked()
            assert(awaitItem().isCalendarAfterOpen)
            viewModel.onCalendarCancel()
            assert(!awaitItem().isCalendarAfterOpen)
        }
    }

    @Test
    fun `GIVEN before calendar is open WHEN user cancel THEN calendar is closed`() = runBlocking {
        viewModel.state.test {
            awaitItem() // Init state, ignore
            viewModel.onBeforeFilterClicked()
            assert(awaitItem().isCalendarBeforeOpen)
            viewModel.onCalendarCancel()
            assert(!awaitItem().isCalendarBeforeOpen)
        }
    }

    @Test
    fun `GIVEN viewmodel is created WHEN it's initialized THEN fetch beers`() = runBlocking {
        val beerName = "Test beer"
        val beer = Beer(name = beerName)
        viewModel.state.test {
            assert(awaitItem().beerList.isEmpty()) // Init state, empty
            mockWebServer.enqueue(MockResponse().setBody(listOf(beer).toJsonString()))
            val beerList = awaitItem().beerList
            assert(beerList.isNotEmpty())
            assert(beerList.size == 1)
            assert(beerList.first().name == beerName)
        }
    }

    @Test
    fun `GIVEN user scrolled WHEN reached the bottom THEN fetch next page`() = runBlocking {
        val beer1 = Beer(name = "Test beer 1")
        val beer2 = Beer(name = "Test beer 2")
        mockWebServer.enqueue(MockResponse().setBody(listOf(beer1).toJsonString()))
        mockWebServer.enqueue(MockResponse().setBody(listOf(beer2).toJsonString()))
        viewModel.state.test {
            var beerList = awaitItem().beerList
            assert(beerList.size == 1)
            assert(beerList.find { it.name == beer1.name } != null)
            assert(beerList.find { it.name == beer2.name } == null)
            viewModel.onBottomReached()
            beerList = awaitItem().beerList
            assert(beerList.size == 2)
            assert(beerList.find { it.name == beer1.name } != null)
            assert(beerList.find { it.name == beer2.name } != null)
        }
    }

    @Test
    fun `GIVEN user changed filter WHEN list have values THEN clear list and fetch new`() {
        val beer1 = Beer(name = "Test beer 1")
        val beer2 = Beer(name = "Test beer 2")
        val filterDate = Date()
        mockWebServer.enqueue(MockResponse().setBody(listOf(beer1).toJsonString()))
        mockWebServer.enqueue(MockResponse().setBody(listOf(beer2).toJsonString()))
        runBlocking {
            viewModel.state.test {
                var beerList = awaitItem().beerList
                assert(beerList.size == 1)
                assert(beerList.find { it.name == beer1.name } != null)
                assert(beerList.find { it.name == beer2.name } == null)
                viewModel.onAfterFilterClicked(filterDate)
                assert(awaitItem().afterFilter == filterDate)
                assert(awaitItem().beerList.isEmpty()) // List empty before fetching new data
                beerList = awaitItem().beerList
                assert(beerList.size == 1)
                assert(beerList.find { it.name == beer1.name } == null)
                assert(beerList.find { it.name == beer2.name } != null)
            }
        }
    }
}
