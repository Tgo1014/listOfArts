package tgo1014.listofbeers.presentation.ui.screens.home

import android.content.Context
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import tgo1014.listofbeers.domain.models.BeerDomain
import tgo1014.listofbeers.domain.repositories.BeersRepository
import tgo1014.listofbeers.fakes.FakeBeerRepository
import tgo1014.listofbeers.presentation.R
import tgo1014.listofbeers.presentation.ui.MainActivity
import tgo1014.listofbeers.utils.assertDoesNotExist
import tgo1014.listofbeers.utils.assertExists
import javax.inject.Inject

@HiltAndroidTest
class HomeScreenKtTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Inject
    lateinit var beerRepository: BeersRepository

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
        composeRule
            .onNodeWithText(context.getString(R.string.no_beers))
            .assertExists()
    }

    @Test
    fun should_displayBeer_when_beersAvailable() {
        val fakeBeerRepository = beerRepository as FakeBeerRepository
        fakeBeerRepository.beersToReturn = listOf(testBeer1)
        composeRule.assertDoesNotExist(context.getString(R.string.no_beers))
        composeRule.assertExists(text = testBeer1.name!!, ignoreCase = true, useUnmergedTree = true)
    }

    @Test
    fun given_emptyList_should_displayBeer_when_pressingRetryButton() {
        val fakeBeerRepository = beerRepository as FakeBeerRepository
        assert(fakeBeerRepository.beersToReturn.isEmpty())
        composeRule.assertExists(context.getString(R.string.no_beers))
        fakeBeerRepository.beersToReturn = listOf(testBeer1)
        composeRule
            .onNodeWithText(context.getString(R.string.retry), ignoreCase = true)
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
        composeRule.onNodeWithText(context.getString(R.string.blonde)).performClick()
        composeRule.assertDoesNotExist(text = testBeer1.name!!.uppercase(), useUnmergedTree = true)
        composeRule.assertExists(context.getString(R.string.no_beers))
    }

}