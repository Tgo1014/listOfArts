package tgo1014.listofarts.presentation.ui.screens.home

import android.content.Context
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onParent
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToIndex
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeUp
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import tgo1014.listofarts.domain.models.ArtObjectDomain
import tgo1014.listofarts.domain.repositories.ArtRepository
import tgo1014.listofarts.fakes.FakeArtRepository
import tgo1014.listofarts.presentation.R
import tgo1014.listofarts.presentation.models.Filter
import tgo1014.listofarts.presentation.ui.MainActivity
import tgo1014.listofarts.utils.assertContentDescriptionDoesNotExists
import tgo1014.listofarts.utils.assertContentDescriptionExists
import tgo1014.listofarts.utils.assertDoesNotExist
import tgo1014.listofarts.utils.assertExists
import tgo1014.listofarts.utils.logAllComposableNodes
import javax.inject.Inject

@HiltAndroidTest
class HomeScreenKtTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Inject
    lateinit var artRepository: ArtRepository

    @Inject
    @ApplicationContext
    lateinit var context: Context

    private val fakeArtRepository by lazy { artRepository as FakeArtRepository }
    private val testArt1 = ArtObjectDomain(id = "1", title = "Test art 1", principalMaker = "Lorem Ipsum")
    private val testArt2 = ArtObjectDomain(id = "2", title = "Test art 2", longTitle = "Darude Sandstorm")

    @Before
    fun setup() {
        hiltRule.inject()
        // composeTestRule.logAllComposableNodes()
    }

    @Test
    fun should_displayEmptyMessage_when_noArtsAvailable() {
        assertTrue(fakeArtRepository.artsToReturn.isEmpty())
        composeTestRule.assertExists(context.getString(R.string.no_items))
    }

    @Test
    fun should_displayArt_when_artsAvailable() {
        fakeArtRepository.artsToReturn = listOf(testArt1)
        clickRetry()
        composeTestRule.assertDoesNotExist(context.getString(R.string.no_items))
        composeTestRule.assertContentDescriptionExists(testArt1.title)
    }

    @Test
    fun given_emptyList_should_displayArt_when_pressingRetryButton() {
        assertTrue(fakeArtRepository.artsToReturn.isEmpty())
        clickRetry()
        composeTestRule.assertExists(context.getString(R.string.no_items))
        fakeArtRepository.artsToReturn = listOf(testArt1)

        clickRetry()

        composeTestRule.assertContentDescriptionExists(testArt1.title)
    }

    @Test
    fun when_filtering_then_refreshesList() {
        val artList = listOf(testArt1, testArt2)
        fakeArtRepository.artsToReturn = artList
        clickRetry()
        artList.map { it.title }.forEach(composeTestRule::assertContentDescriptionExists)

        fakeArtRepository.artsToReturn = artList.take(1)
        composeTestRule.onNodeWithText(Filter.PRINT.description).performClick()
        composeTestRule.assertContentDescriptionExists(testArt1.title)
        composeTestRule.assertContentDescriptionDoesNotExists(testArt2.title)

        fakeArtRepository.artsToReturn = emptyList()
        composeTestRule.onNodeWithText(Filter.PRINT.description)
            .onParent()
            .performScrollToIndex(Filter.entries.lastIndex)
        composeTestRule.onNodeWithText(Filter.HISTORY_MEDAL.description).performClick()

        composeTestRule.assertContentDescriptionDoesNotExists(testArt1.title)
        composeTestRule.assertContentDescriptionDoesNotExists(testArt2.title)
        composeTestRule.assertExists(context.getString(R.string.no_items))
    }

    @Test
    fun when_clickingArt_then_openDetailScreen() {
        val maker = context.getString(R.string.by).format(testArt1.principalMaker)
        val artList = listOf(testArt1, testArt2)
        fakeArtRepository.artsToReturn = artList
        clickRetry()

        composeTestRule
            .onNodeWithContentDescription(testArt1.title)
            .performClick()
        composeTestRule.onRoot().performTouchInput { swipeUp() }
        composeTestRule.assertExists(testArt1.title)
        composeTestRule.assertExists(maker)

        runBlocking(Dispatchers.Main) {
            composeTestRule.activity.onBackPressedDispatcher.onBackPressed()
        }
        composeTestRule.assertDoesNotExist(testArt1.title)
        composeTestRule.assertDoesNotExist(maker)
    }

    private fun clickRetry() {
        composeTestRule
            .onNodeWithText(context.getString(R.string.retry))
            .performClick()
    }

}