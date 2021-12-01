package tgo1014.listofbeers

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import tgo1014.listofbeers.ui.screens.home.HomeScreen
import tgo1014.listofbeers.ui.theme.ListOfBeersTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ListOfBeersTheme {
                HomeScreen()
            }
        }
    }
}