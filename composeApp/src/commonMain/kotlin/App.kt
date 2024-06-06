import androidx.compose.runtime.Composable
import di.appModule
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication
import presentation.theme.ListOfArtsTheme
import screens.home.HomeScreen

@Composable
@Preview
fun App() {
    KoinApplication(application = { modules(appModule()) }) {
        ListOfArtsTheme {
            HomeScreen {}
        }
    }
}