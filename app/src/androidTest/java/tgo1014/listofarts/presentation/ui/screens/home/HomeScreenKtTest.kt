package tgo1014.listofarts.presentation.ui.screens.home

import android.content.Context
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import tgo1014.listofarts.domain.repositories.ArtRepository
import tgo1014.listofarts.fakes.FakeBeerRepository
import tgo1014.listofarts.presentation.R
import tgo1014.listofarts.presentation.ui.MainActivity
import tgo1014.listofarts.utils.assertDoesNotExist
import tgo1014.listofarts.utils.assertExists
import javax.inject.Inject

@HiltAndroidTest
class HomeScreenKtTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Inject
    lateinit var beerRepository: ArtRepository

    @Inject
    @ApplicationContext
    lateinit var context: Context

    private val testBeer1 = BeerDomain(id = 1, name = "Test beer 1")
    private val testBeer2 = BeerDomain(id = 2, name = "Test beer 2")

    @Before
    fun setup() {
        hiltRule.inject()
    }

    // Display all composables: composeRule.onRoot().printToLog("TAG")

    @Test
    fun should_displayErrorMessage_when_noBeersAvailable() {
        val fakeBeerRepository = beerRepository as FakeBeerRepository
        assert(fakeBeerRepository.beersToReturn.isEmpty())
        composeRule.assertExists(context.getString(R.string.no_items))
    }

    @Test
    fun should_displayBeer_when_beersAvailable() {
        val fakeBeerRepository = beerRepository as FakeBeerRepository
        fakeBeerRepository.beersToReturn = listOf(testBeer1)
        composeRule.assertDoesNotExist(text = context.getString(R.string.no_items))
        composeRule.assertExists(text = testBeer1.name!!, ignoreCase = true, useUnmergedTree = true)
    }

    @Test
    fun given_emptyList_should_displayBeer_when_pressingRetryButton() {
        val fakeBeerRepository = beerRepository as FakeBeerRepository
        assert(fakeBeerRepository.beersToReturn.isEmpty())
        composeRule.assertExists(context.getString(R.string.no_items))
        fakeBeerRepository.beersToReturn = listOf(testBeer1)
        composeRule
            .onNodeWithText(text = context.getString(R.string.retry), ignoreCase = true)
            .performClick()
        composeRule.assertExists(text = testBeer1.name!!, ignoreCase = true, useUnmergedTree = true)
    }

    @Test
    fun when_filtering_then_refreshesList() {
        val fakeBeerRepository = beerRepository as FakeBeerRepository
        val beerList = listOf(testBeer1, testBeer2)
        fakeBeerRepository.beersToReturn = beerList
        beerList.forEach {
            composeRule.assertExists(text = it.name!!, ignoreCase = true, useUnmergedTree = true)
        }
        composeRule
            .onNodeWithText(text = context.getString(R.string.search))
            .performTextInput(testBeer1.name!!)
        composeRule.assertExists(text = testBeer1.name!!.uppercase(), useUnmergedTree = true)
        composeRule.onNodeWithText(text = context.getString(R.string.blonde)).performClick()
        composeRule.assertDoesNotExist(text = testBeer1.name!!.uppercase(), useUnmergedTree = true)
        composeRule.assertExists(text = context.getString(R.string.no_items))
    }

    @Test
    fun when_clickingBeer_then_openDetailScreen() {
        val fakeBeerRepository = beerRepository as FakeBeerRepository
        val beerList = listOf(testBeer1, testBeer2)
        fakeBeerRepository.beersToReturn = beerList
        composeRule.assertExists(text = testBeer1.name!!.uppercase(), useUnmergedTree = true)
        composeRule
            .onNodeWithText(text = testBeer1.name!!.uppercase(), useUnmergedTree = true)
            .performClick()
        composeRule.assertExists(text = context.getString(R.string.first_brewed)) // Detail screen
        composeRule.assertExists(text = testBeer1.name!!)
        runBlocking(Dispatchers.Main) {
            composeRule.activity.onBackPressedDispatcher.onBackPressed()
        }
        composeRule.assertDoesNotExist(text = context.getString(R.string.first_brewed))
    }

}