package tgo1014.listofbeers.presentation.ui.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TextFieldDefaults.TextFieldDecorationBox
import androidx.compose.material3.TextFieldDefaults.indicatorLine
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.sharp.Close
import androidx.compose.material.icons.sharp.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import tgo1014.listofbeers.presentation.ui.composables.previews.DefaultPreview
import tgo1014.listofbeers.presentation.ui.composables.previews.isPreviewMode
import tgo1014.listofbeers.presentation.ui.composables.providers.ThemeProvider
import tgo1014.listofbeers.presentation.ui.screens.home.HomeScreen
import tgo1014.listofbeers.presentation.ui.screens.home.HomeState
import tgo1014.listofbeers.presentation.ui.theme.ListOfBeersTheme
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.ExperimentalTime

@Suppress("LocalVariableName")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchFab(
    modifier: Modifier = Modifier,
    searchText: String,
    buttonState: SearchFabState,
    isLoading: Boolean,
    onCloseClicked: () -> Unit = {},
    onSearchTextChanged: (String) -> Unit = {},
    onButtonClicked: () -> Unit = {},
) = BoxWithConstraints(modifier) {
    val initValue = if (isPreviewMode()) isLoading else false
    // Works based in the ContentLoadingProgressBar, it waits a minimum time to show but if it does,
    // it just switch states again after a minimum time to minimize UI flickering
    val _isLoading by produceState(initValue, isLoading) {
        delay(500.milliseconds)
        value = if (isLoading != value) {
            isLoading
        } else {
            value
        }
    }
    val keyboardController = LocalSoftwareKeyboardController.current
    val fabSize = 56.dp
    val itemsSize = 32.dp
    RoundedCornerShape(0)
    val interactionSource = remember { MutableInteractionSource() }
    val transition = updateTransition(buttonState, label = "Width")
    val height by transition.animateDp(
        targetValueByState = {
            when (it) {
                SearchFabState.FAB -> fabSize
                SearchFabState.SEARCH -> 65.dp
            }
        }, label = "Height"
    )
    val width by transition.animateDp(
        targetValueByState = {
            when {
                it == SearchFabState.FAB && _isLoading -> fabSize * 2.2f
                it == SearchFabState.FAB -> fabSize
                else -> maxWidth - fabSize / 2
            }
        },
        transitionSpec = { tween() },
        label = "width"
    )
    Surface(
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.primaryContainer,
        shadowElevation = androidx.compose.material.FloatingActionButtonDefaults.elevation().elevation(
            interactionSource = interactionSource
        ).value,
        modifier = Modifier
            .width(width)
            .height(height)
            .align(Alignment.BottomEnd)
            .clickable { onButtonClicked() }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(12.dp)
        ) {
            val paddingEnd by animateDpAsState(
                targetValue = if (buttonState == SearchFabState.FAB) 24.dp else 12.dp,
                label = "paddingEnd"
            )
            AnimatedVisibility(visible = _isLoading) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier
                        .padding(end = paddingEnd)
                        .size(itemsSize)
                )
            }
            val focusRequest = remember { FocusRequester() }
            if (buttonState == SearchFabState.FAB) {
                LocalFocusManager.current.clearFocus(true)
                Icon(
                    imageVector = Icons.Sharp.Search,
                    contentDescription = null,
                    modifier = Modifier.requiredSize(itemsSize),
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            } else {
                val singleLine = true
                val colors = TextFieldDefaults.colors(
                    cursorColor = MaterialTheme.colorScheme.tertiary,
                    focusedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    focusedIndicatorColor = MaterialTheme.colorScheme.tertiary,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                )
                val selectionColors = TextSelectionColors(
                    handleColor = MaterialTheme.colorScheme.tertiary,
                    backgroundColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.4f)
                )
                SideEffect { focusRequest.requestFocus() }
                CompositionLocalProvider(LocalTextSelectionColors provides selectionColors) {
                    // BasicTextField to be able to removed the default padding inside other TextField components
                    BasicTextField(
                        value = searchText,
                        onValueChange = { onSearchTextChanged(it) },
                        interactionSource = interactionSource,
                        textStyle = TextStyle(MaterialTheme.colorScheme.onPrimaryContainer),
                        singleLine = singleLine,
                        cursorBrush = SolidColor(MaterialTheme.colorScheme.secondary),
                        decorationBox = {
                            TextFieldDefaults.DecorationBox(
                                value = searchText,
                                innerTextField = it,
                                enabled = true,
                                singleLine = singleLine,
                                visualTransformation = VisualTransformation.None,
                                interactionSource = interactionSource,
                                label = { Text(text = "TODO") },
                                trailingIcon = {
                                    IconButton(
                                        onClick = {
                                            keyboardController?.hide()
                                            onCloseClicked()
                                        },
                                        content = {
                                            Icon(
                                                imageVector = Icons.Sharp.Close,
                                                contentDescription = null,
                                                tint = MaterialTheme.colorScheme.onPrimaryContainer,
                                            )
                                        }
                                    )
                                },
                                colors = colors,
                                contentPadding = PaddingValues(0.dp), // Remove default padding
                            )
                        },
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Done,
                            capitalization = KeyboardCapitalization.Sentences
                        ),
                        keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
                        modifier = Modifier
                            .fillMaxWidth()
                            .requiredHeight(45.dp)
                            .focusRequester(focusRequest)
                            .indicatorLine(
                                enabled = true,
                                isError = false,
                                interactionSource = interactionSource,
                                colors = colors
                            )
                    )
                }
            }
        }
    }
}

enum class SearchFabState { FAB, SEARCH }

@Preview
@Composable
private fun SearchFabPreviewFab() = ListOfBeersTheme {
    var state by remember { mutableStateOf(SearchFabState.FAB) }
    SearchFab(buttonState = state, searchText = "", isLoading = false) {
        state = if (state == SearchFabState.FAB) SearchFabState.SEARCH else SearchFabState.FAB
    }
}

@Preview
@Composable
private fun SearchFabPreviewFabLoading() = ListOfBeersTheme {
    var state by remember { mutableStateOf(SearchFabState.FAB) }
    SearchFab(buttonState = state, searchText = "", isLoading = true) {
        state = if (state == SearchFabState.FAB) SearchFabState.SEARCH else SearchFabState.FAB
    }
}

@Preview
@Composable
private fun SearchFabPreviewSearch() = ListOfBeersTheme {
    var state by remember { mutableStateOf(SearchFabState.FAB) }
    SearchFab(buttonState = SearchFabState.SEARCH, searchText = "123", isLoading = false) {
        state = if (state == SearchFabState.FAB) SearchFabState.SEARCH else SearchFabState.FAB
    }
}

@Preview
@Composable
private fun SearchFabPreviewSearchLoading() = ListOfBeersTheme {
    var state by remember { mutableStateOf(SearchFabState.FAB) }
    SearchFab(buttonState = SearchFabState.SEARCH, searchText = "123", isLoading = true) {
        state = if (state == SearchFabState.FAB) SearchFabState.SEARCH else SearchFabState.FAB
    }
}

@DefaultPreview
@Composable
private fun HomeScreenPreview(
    @PreviewParameter(ThemeProvider::class) materialYouColors: Boolean
) = ListOfBeersTheme(materialYouColors = materialYouColors) {
    var state by remember { mutableStateOf(SearchFabState.FAB) }
    SearchFab(buttonState = SearchFabState.SEARCH, searchText = "123", isLoading = true) {
        state = if (state == SearchFabState.FAB) SearchFabState.SEARCH else SearchFabState.FAB
    }
}