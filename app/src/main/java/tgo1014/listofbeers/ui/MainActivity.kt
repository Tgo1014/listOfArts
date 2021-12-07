package tgo1014.listofbeers.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.google.accompanist.navigation.material.bottomSheet
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import tgo1014.listofbeers.ui.screens.details.BeerDetails
import tgo1014.listofbeers.ui.screens.home.BeerViewModel
import tgo1014.listofbeers.ui.screens.home.HomeScreen
import tgo1014.listofbeers.ui.theme.ListOfBeersTheme

@ExperimentalMaterialNavigationApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            val systemUiController = rememberSystemUiController()
            val useDarkIcons = !isSystemInDarkTheme()
            SideEffect {
                systemUiController.setSystemBarsColor(
                    color = Color.Transparent,
                    darkIcons = useDarkIcons
                )
            }
            ListOfBeersTheme {
                ProvideWindowInsets {
                    val bottomSheetNavigator = rememberBottomSheetNavigator()
                    val navController = rememberNavController(bottomSheetNavigator)
                    val viewModel: BeerViewModel = hiltViewModel()
                    ModalBottomSheetLayout(
                        bottomSheetNavigator = bottomSheetNavigator,
                        sheetShape = RoundedCornerShape(topEnd = 12.dp, topStart = 12.dp),
                        sheetBackgroundColor = MaterialTheme.colorScheme.primary,
                    ) {
                        NavHost(navController, Destinations.Home) {
                            composable(route = Destinations.Home) {
                                HomeScreen(viewModel) {
                                    viewModel.beerToShown = it
                                    navController.navigate(Destinations.Details)
                                }
                            }
                            bottomSheet(route = Destinations.Details) {
                                Column(Modifier.fillMaxWidth()) {
                                    Divider(
                                        modifier = Modifier
                                            .width(50.dp)
                                            .padding(8.dp)
                                            .align(Alignment.CenterHorizontally),
                                        thickness = 3.dp
                                    )
                                    if (viewModel.beerToShown != null) {
                                        BeerDetails(viewModel.beerToShown!!)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private object Destinations {
        const val Home = "HOME"
        const val Details = "DETAILS"
    }
}