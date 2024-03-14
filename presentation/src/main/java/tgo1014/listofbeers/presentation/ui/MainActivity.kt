package tgo1014.listofbeers.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.google.accompanist.navigation.material.bottomSheet
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import tgo1014.listofbeers.presentation.ui.navigation.Destinations
import tgo1014.listofbeers.presentation.ui.screens.details.DetailsScreen
import tgo1014.listofbeers.presentation.ui.screens.home.HomeScreen
import tgo1014.listofbeers.presentation.ui.theme.ListOfBeersTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        enableEdgeToEdge()
        setContent {
            val systemUiController = rememberSystemUiController()
            val useDarkIcons = isSystemInDarkTheme()
            SideEffect {
                systemUiController.setSystemBarsColor(
                    color = Color.Transparent,
                    darkIcons = useDarkIcons
                )
            }
            ListOfBeersTheme {
                val bottomSheetNavigator = rememberBottomSheetNavigator()
                val navController = rememberNavController(bottomSheetNavigator)
                ModalBottomSheetLayout(
                    bottomSheetNavigator = bottomSheetNavigator,
                    sheetShape = RoundedCornerShape(topEnd = 24.dp, topStart = 24.dp),
                    sheetBackgroundColor = MaterialTheme.colorScheme.primaryContainer,
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = Destinations.Home.route
                    ) {
                        composable(route = Destinations.Home.route) {
                            HomeScreen { beer ->
                                navController.navigate(Destinations.toDetails(beer.id))
                            }
                        }
                        bottomSheet(
                            route = Destinations.Details.route,
                            arguments = Destinations.Details.args
                        ) { navEntry ->
                            val beerId = navEntry.arguments?.getInt(Destinations.Details.id)!!
                            DetailsScreen(beerId = beerId)
                        }
                    }
                }
            }
        }
    }

}