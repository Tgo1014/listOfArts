package tgo1014.listofbeers.presentation.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpSize
import androidx.window.layout.DisplayFeature
import androidx.window.layout.FoldingFeature
import com.google.accompanist.adaptive.HorizontalTwoPaneStrategy
import com.google.accompanist.adaptive.TwoPane
import com.google.accompanist.adaptive.VerticalTwoPaneStrategy
import tgo1014.listofbeers.presentation.models.BeerUi
import tgo1014.listofbeers.presentation.ui.composables.LogoText
import tgo1014.listofbeers.presentation.ui.composables.previews.DevicePreviews
import tgo1014.listofbeers.presentation.ui.composables.utils.isBookPosture
import tgo1014.listofbeers.presentation.ui.composables.utils.isSeparatingPosture
import tgo1014.listofbeers.presentation.ui.composables.utils.isTableTopPosture
import tgo1014.listofbeers.presentation.ui.screens.details.DetailsScreen
import tgo1014.listofbeers.presentation.ui.theme.ListOfBeersTheme

@Composable
fun HomeScreen(
    windowSizeClass: WindowSizeClass,
    displayFeatures: List<DisplayFeature>,
    onBeerClicked: (BeerUi) -> Unit,
) = Column(
    modifier = Modifier.background(color = MaterialTheme.colorScheme.secondaryContainer)
) {
    TopAppBar(
        title = { LogoText() },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.onPrimaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primaryContainer,
        )
    )
    val foldingFeature = displayFeatures.filterIsInstance<FoldingFeature>().firstOrNull()
    // Use a two pane layout if there is a fold impacting layout (meaning it is separating
    // or non-flat) or if we have a large enough width to show both.
    if (
        windowSizeClass.widthSizeClass == WindowWidthSizeClass.Expanded ||
        isBookPosture(foldingFeature) ||
        isTableTopPosture(foldingFeature) ||
        isSeparatingPosture(foldingFeature)
    ) {
        // Determine if we are going to be using a vertical strategy (as if laying out
        // both sides in a column). We want to do so if we are in a tabletop posture,
        // or we have an impactful horizontal fold. Otherwise, we'll use a horizontal strategy.
        val usingVerticalStrategy = isTableTopPosture(foldingFeature) ||
            (isSeparatingPosture(foldingFeature) && foldingFeature.orientation == FoldingFeature.Orientation.HORIZONTAL)
        var beerId by remember { mutableStateOf(-1) }
        if (usingVerticalStrategy) {
            // For vertical lets keep the details at the top so the main list is easier to scroll
            TwoPane(
                first = { DetailsScreen(beerId = beerId) },
                second = { HomeScreenSinglePane { beerId = it.id } },
                strategy = VerticalTwoPaneStrategy(0.5f),
                displayFeatures = displayFeatures,
            )
        } else {
            TwoPane(
                first = { HomeScreenSinglePane { beerId = it.id } },
                second = { DetailsScreen(beerId = beerId) },
                strategy = HorizontalTwoPaneStrategy(0.5f),
                displayFeatures = displayFeatures,
            )
        }
    } else {
        HomeScreenSinglePane(onBeerClicked = onBeerClicked)
    }
}

// In order to avoid having this HomeScreen class to handle multiple states, the preview below
// should simulate in which cases the screen will be split by replication the logic written above
// for splitting the panels. This avoids a refactor to make the private DetailScreen with state be
// accessible just for the preview to work here and keep it more reusable

@DevicePreviews
@Composable
private fun HomeScreenPreview() = ListOfBeersTheme {
    BoxWithConstraints {
        val windowSizeClass = WindowSizeClass.calculateFromSize(DpSize(maxWidth, maxHeight))
        if (windowSizeClass.widthSizeClass == WindowWidthSizeClass.Expanded) {
            Row {
                Box(Modifier.fillMaxSize().background(Color.Red).weight(1f))
                Box(Modifier.fillMaxSize().background(Color.Blue).weight(1f))
            }
        } else {
            Box(Modifier.fillMaxSize().background(Color.Red))
        }
    }
}
