package vn.id.tool.nlu.navigation


import androidx.compose.runtime.Composable

import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import vn.id.tool.nlu.api.nlu.NLUAPI

import vn.id.tool.nlu.network.createHttpClient
import vn.id.tool.nlu.ui.home.HomeScreen
import vn.id.tool.nlu.ui.login.LoginScreen

sealed class Route() {
    data class LoginScreen(val name: String = "login") : Route()
    data class HomeScreen(val name: String = "Home") : Route()
}
@Composable
fun Navigation(navHostController: NavHostController) {
    NavHost(navController = navHostController, startDestination = Route.LoginScreen().name) {
        composable(Route.LoginScreen().name) {
            LoginScreen(client = NLUAPI(createHttpClient()), onLoginClick = {
                navHostController.navigate(Route.HomeScreen().name)
            })
        }
        composable(Route.HomeScreen().name) {
            HomeScreen()
        }
    }
}