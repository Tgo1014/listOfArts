package tgo1014.listofbeers.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.google.accompanist.navigation.material.bottomSheet
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.squareup.moshi.Moshi
import dagger.hilt.android.AndroidEntryPoint
import tgo1014.listofbeers.models.BeerJsonAdapter
import tgo1014.listofbeers.ui.screens.details.BeerDetails
import tgo1014.listofbeers.ui.screens.home.HomeScreen
import tgo1014.listofbeers.ui.theme.ListOfBeersTheme
import javax.inject.Inject

@ExperimentalMaterialNavigationApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var moshi: Moshi

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
                    ModalBottomSheetLayout(
                        bottomSheetNavigator = bottomSheetNavigator,
                        sheetShape = RoundedCornerShape(topEnd = 12.dp, topStart = 12.dp)
                    ) {
                        NavHost(navController, Destinations.Home) {
                            composable(route = Destinations.Home) {
                                HomeScreen {
                                    val arg = BeerJsonAdapter(moshi).toJson(it)
                                    navController.navigate(Destinations.Details + "?arg=$arg")
                                }
                            }
                            bottomSheet(route = Destinations.Details + "?arg={arg}") { backstackEntry ->
                                val arg = backstackEntry.arguments?.getString("arg") ?: ""
                                val beer = BeerJsonAdapter(moshi).fromJson(arg)
                                if (beer != null) {
                                    BeerDetails(beer)
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