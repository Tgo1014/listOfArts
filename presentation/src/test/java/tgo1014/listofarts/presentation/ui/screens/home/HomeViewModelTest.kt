package tgo1014.listofarts.presentation.ui.screens.home

import app.cash.turbine.test
import app.cash.turbine.turbineScope
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.wheneverBlocking
import tgo1014.listofarts.domain.models.ArtObjectDomain
import tgo1014.listofarts.domain.usecases.GetArtObjectsUseCase
import tgo1014.listofarts.presentation.models.Filter
import tgo1014.listofarts.presentation.models.mappers.toUi
import tgo1014.listofarts.presentation.utils.ViewModelMainCoroutineRule


class HomeViewModelTest {

    @get:Rule
    var coroutinesRule = ViewModelMainCoroutineRule()

    private var getArtObjectsUseCase = mock<GetArtObjectsUseCase>()
    private lateinit var viewModel: HomeViewModel

    private val testArt1 = ArtObjectDomain(id = "id1", title = "Test art 1")
    private val testArt2 = ArtObjectDomain(id = "id2", title = "Test art 2")

    @Before
    fun setup() {
        wheneverBlocking {
            getArtObjectsUseCase(anyOrNull(), anyOrNull(), anyOrNull())
        } doReturn Result.success(listOf(testArt1))
        viewModel = HomeViewModel(getArtObjectsUseCase)
    }

    @Test
    fun `GIVEN user scrolled WHEN reached the bottom THEN fetch next page`() = runTest {
        turbineScope {
            val stateFlow = viewModel.state.testIn(this)
            assertTrue(stateFlow.awaitItem().itemList.isEmpty()) // Init state, empty
            wheneverBlocking {
                getArtObjectsUseCase(
                    anyOrNull(),
                    anyOrNull(),
                    anyOrNull()
                )
            } doReturn Result.success(listOf(testArt1))

            viewModel.fetchArtObjects()
            var artList = stateFlow.awaitItem().itemList
            assertTrue(artList.size == 1)
            assertTrue(artList.contains(testArt1.toUi()))
            assertTrue(!artList.contains(testArt2.toUi()))

            wheneverBlocking {
                getArtObjectsUseCase(
                    anyOrNull(),
                    anyOrNull(),
                    anyOrNull()
                )
            } doReturn Result.success(listOf(testArt2))
            viewModel.onBottomReached()
            artList = stateFlow.awaitItem().itemList
            assertTrue(artList.size == 2)
            assertTrue(artList.contains(testArt1.toUi()))
            assertTrue(artList.contains(testArt2.toUi()))

            stateFlow.ensureAllEventsConsumed()
            stateFlow.cancel()
        }
    }

    @Test
    fun `GIVEN user changed filter WHEN list have values THEN clear list and fetch new`() = runTest {
            turbineScope {
                val filter = Filter.PRINT
                val stateFlow = viewModel.state.testIn(this)
                assertTrue(stateFlow.awaitItem().itemList.isEmpty()) // Init state, empty
                wheneverBlocking {
                    getArtObjectsUseCase(
                        anyOrNull(),
                        anyOrNull(),
                        anyOrNull()
                    )
                } doReturn Result.success(listOf(testArt1))

                viewModel.fetchArtObjects()
                var artList = stateFlow.awaitItem().itemList
                assertTrue(artList.size == 1)
                assertNotNull(artList.find { it.title == testArt1.title })
                assertNull(artList.find { it.title == testArt2.title })

                wheneverBlocking {
                    getArtObjectsUseCase(
                        anyOrNull(),
                        anyOrNull(),
                        anyOrNull()
                    )
                } doReturn Result.success(listOf(testArt2))
                viewModel.onFilterClicked(filter)
                val state = stateFlow.expectMostRecentItem()
                assertTrue(state.filters.any { it.filter == filter })
                artList = state.itemList
                assertTrue(artList.size == 1)
                assertNull(artList.find { it.title == testArt1.title })
                assertNotNull(artList.find { it.title == testArt2.title })

                stateFlow.ensureAllEventsConsumed()
                stateFlow.cancel()
            }
        }

    @Test
    fun `GIVEN user changed filter WHEN updating filters THEN just last one is selected`() = runTest {
            turbineScope {
                val filter1 = Filter.DRAWING
                val filter2 = Filter.PAINTING
                viewModel.state.test {
                    assertTrue(awaitItem().filters.first { it.filter == Filter.PAINTING }.isSelected)

                    viewModel.onFilterClicked(filter1)
                    var filterList = expectMostRecentItem().filters
                    assertTrue(filterList.find { it.filter == filter1 }?.isSelected == true)
                    assertTrue(filterList.find { it.filter == filter2 }?.isSelected == false)

                    viewModel.onFilterClicked(filter2)
                    filterList = expectMostRecentItem().filters
                    assertTrue(filterList.find { it.filter == filter1 }?.isSelected == false)
                    assertTrue(filterList.find { it.filter == filter2 }?.isSelected == true)
                }
            }
        }

    @Test
    fun `GIVEN user changed filter WHEN is the same as previous THEN its deselected`() = runTest {
        val filter = Filter.PAINTING
        viewModel.state.test {
            assertTrue(awaitItem().filters.first { it.filter == filter }.isSelected)

            viewModel.onFilterClicked(filter)
            assertTrue(expectMostRecentItem().filters.none { it.isSelected })

            viewModel.onFilterClicked(filter)
            assertTrue(expectMostRecentItem().filters.find { it.filter == filter }?.isSelected == true)
        }
    }
}
