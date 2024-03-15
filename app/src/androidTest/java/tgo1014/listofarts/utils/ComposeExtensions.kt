package tgo1014.listofarts.utils

import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.onNodeWithText

// https://medium.com/androiddevelopers/alternatives-to-idling-resources-in-compose-tests-8ae71f9fc473

fun ComposeContentTestRule.assertExists(
    text: String,
    substring: Boolean = false,
    ignoreCase: Boolean = false,
    useUnmergedTree: Boolean = false
): SemanticsNodeInteraction {
    return this.onNodeWithText(text, substring, ignoreCase, useUnmergedTree)
        .assertExists()
}

fun ComposeContentTestRule.assertDoesNotExist(
    text: String,
    substring: Boolean = false,
    ignoreCase: Boolean = false,
    useUnmergedTree: Boolean = false
) {
    return this.onNodeWithText(text, substring, ignoreCase, useUnmergedTree)
        .assertDoesNotExist()
}
