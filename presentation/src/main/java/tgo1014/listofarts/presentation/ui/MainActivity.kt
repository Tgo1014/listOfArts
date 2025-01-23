package tgo1014.listofarts.presentation.ui

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.navigation.ModalBottomSheetLayout
import androidx.compose.material.navigation.bottomSheet
import androidx.compose.material.navigation.rememberBottomSheetNavigator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.DisposableEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import tgo1014.listofarts.presentation.ui.navigation.Destinations
import tgo1014.listofarts.presentation.ui.screens.details.DetailsScreen
import tgo1014.listofarts.presentation.ui.screens.home.HomeScreen
import tgo1014.listofarts.presentation.ui.theme.ListOfArtsTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val useDarkIcons = isSystemInDarkTheme()
            DisposableEffect(useDarkIcons) {
                enableEdgeToEdge(
                    statusBarStyle = SystemBarStyle.auto(
                        Color.TRANSPARENT,
                        Color.TRANSPARENT,
                    ) { useDarkIcons },
                    navigationBarStyle = SystemBarStyle.auto(
                        lightScrim,
                        darkScrim,
                    ) { useDarkIcons },
                )
                onDispose {}
            }
            ListOfArtsTheme {
                val bottomSheetNavigator = rememberBottomSheetNavigator()
                val navController = rememberNavController(bottomSheetNavigator)
                ModalBottomSheetLayout(
                    bottomSheetNavigator = bottomSheetNavigator,
                    sheetBackgroundColor = MaterialTheme.colorScheme.primaryContainer,
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = Destinations.Home.route
                    ) {
                        composable(route = Destinations.Home.route) {
                            HomeScreen { item ->
                                navController.navigate(Destinations.toDetails(item.id))
                            }
                        }
                        bottomSheet(
                            route = Destinations.Details.route,
                            arguments = Destinations.Details.args
                        ) { navEntry ->
                            val itemId = navEntry.arguments?.getString(Destinations.Details.ID)!!
                            DetailsScreen(id = itemId)
                        }
                    }
                }
            }
        }
    }

    /**
     * The default light scrim, as defined by androidx and the platform:
     * https://cs.android.com/androidx/platform/frameworks/support/+/androidx-main:activity/activity/src/main/java/androidx/activity/EdgeToEdge.kt;l=35-38;drc=27e7d52e8604a080133e8b842db10c89b4482598
     */
    private val lightScrim = Color.argb(0xe6, 0xFF, 0xFF, 0xFF)

    /**
     * The default dark scrim, as defined by androidx and the platform:
     * https://cs.android.com/androidx/platform/frameworks/support/+/androidx-main:activity/activity/src/main/java/androidx/activity/EdgeToEdge.kt;l=40-44;drc=27e7d52e8604a080133e8b842db10c89b4482598
     */
    private val darkScrim = Color.argb(0x80, 0x1b, 0x1b, 0x1b)

}