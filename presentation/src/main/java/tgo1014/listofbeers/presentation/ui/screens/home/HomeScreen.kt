package tgo1014.listofbeers.presentation.ui.screens.home

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.window.layout.DisplayFeature
import androidx.window.layout.FoldingFeature
import com.google.accompanist.adaptive.HorizontalTwoPaneStrategy
import com.google.accompanist.adaptive.TwoPane
import tgo1014.listofbeers.presentation.models.BeerUi
import tgo1014.listofbeers.presentation.ui.composables.previews.DefaultPreview
import tgo1014.listofbeers.presentation.ui.composables.previews.DevicePreviews
import tgo1014.listofbeers.presentation.ui.screens.details.DetailsScreen
import tgo1014.listofbeers.presentation.ui.theme.ListOfBeersTheme

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    windowSizeClass: WindowSizeClass,
    displayFeatures: List<DisplayFeature>,
    onBeerClicked: (BeerUi) -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val keyboardController = LocalSoftwareKeyboardController.current

    val foldingFeature = displayFeatures.filterIsInstance<FoldingFeature>().firstOrNull()

    // Use a two pane layout if there is a fold impacting layout (meaning it is separating
    // or non-flat) or if we have a large enough width to show both.
    if (
        windowSizeClass.widthSizeClass == WindowWidthSizeClass.Expanded/* ||
        isBookPosture(foldingFeature) ||
        isTableTopPosture(foldingFeature) ||
        isSeparatingPosture(foldingFeature)*/
    ) {
        // Determine if we are going to be using a vertical strategy (as if laying out
        // both sides in a column). We want to do so if we are in a tabletop posture,
        // or we have an impactful horizontal fold. Otherwise, we'll use a horizontal strategy.
//        val usingVerticalStrategy =
//            isTableTopPosture(foldingFeature) ||
//                (
//                    isSeparatingPosture(foldingFeature) &&
//                        foldingFeature.orientation == FoldingFeature.Orientation.HORIZONTAL
//                    )
//
//        if (usingVerticalStrategy) {
        var beerId by remember { mutableStateOf(-1) }
        TwoPane(
            first = { HomeScreenSinglePane { beerId = it.id } },
            second = { DetailsScreen(beerId = beerId) },
            strategy = HorizontalTwoPaneStrategy(splitFraction = 0.5f),
            displayFeatures = displayFeatures,
        )
//        } else {
//            Column(
//                modifier = Modifier
//                    .fillMaxSize()
////                    .verticalGradientScrim(
////                        color = MaterialTheme.colors.primary.copy(alpha = 0.50f),
////                        startYPercentage = 1f,
////                        endYPercentage = 0f
////                    )
//                    //.systemBarsPadding()
//                    .padding(horizontal = 8.dp)
//            ) {
//                // TopAppBar(onBackPress = onBackPress)
//                TwoPane(
//                    first = {
//                        HomeScreenSinglePane(
//                            state = state,
//                            onBeerClicked = {
//                                keyboardController?.hide()
//                                onBeerClicked(it)
//                            },
//                            onBottomOfScreenReached = viewModel::onBottomReached,
//                            onQueryChanged = viewModel::search,
//                            onFilterClicked = viewModel::onFilterClicked,
//                            onRetryClicked = { viewModel.fetchBeers() }
//                        )
//                    },
//                    second = {
//                       Box(Modifier.fillMaxSize().background(Color.Red))
//                    },
//                    strategy = HorizontalTwoPaneStrategy(splitFraction = 0.5f),
//                    displayFeatures = displayFeatures
//                )
//            }
//        }
    } else {
        HomeScreenSinglePane(onBeerClicked = onBeerClicked)
    }
}

private val PreviewBeer = BeerUi(
    name = "Punk IPA 2007 - 2010",
    imageUrl = "https://images.punkapi.com/v2/192.png",
    tagline = "Post Modern Classic. Spiky. Tropical. Hoppy.",
    description = "Our flagship beer that kick started the craft beer revolution. This is James and Martin's original take on an American IPA, subverted with punchy New Zealand hops. Layered with new world hops to create an all-out riot of grapefruit, pineapple and lychee before a spiky, mouth-puckering bitter finish."
)

@DefaultPreview
@Composable
private fun HomeScreenPreviewEmpty() = ListOfBeersTheme {
    HomeScreenSinglePane() {}
}

@DefaultPreview
@DevicePreviews
@Composable
private fun HomeScreenPreview() = ListOfBeersTheme {
    val beerList = List(3) { PreviewBeer.copy(id = it) }
    BoxWithConstraints {
        HomeScreenSinglePane() {}
    }

}
