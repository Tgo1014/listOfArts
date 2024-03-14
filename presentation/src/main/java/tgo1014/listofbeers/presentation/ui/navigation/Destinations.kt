package tgo1014.listofbeers.presentation.ui.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Destinations(val route: String) {

    data object Home : Destinations("Home")
    data object Details : Destinations("details/{id}") {
        const val id = "id"
        val args = listOf(navArgument(id) { type = NavType.IntType })
    }

    companion object {
        fun toDetails(id: String) = Details.route.replace("{${Details.id}}", id)
    }
}