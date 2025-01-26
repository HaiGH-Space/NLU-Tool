package vn.id.tool.nlu.app

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import org.jetbrains.compose.ui.tooling.preview.Preview
import vn.id.tool.nlu.Device
import vn.id.tool.nlu.core.database.AppDatabase
import vn.id.tool.nlu.core.network.createHttpClient
import vn.id.tool.nlu.data.nlu.network.KtorRemoteNLUDataSource
import vn.id.tool.nlu.presentation.home.HomeScreenRoot
import vn.id.tool.nlu.presentation.login.LoginScreenRoot
import vn.id.tool.nlu.presentation.login.LoginViewModel
import vn.id.tool.nlu.resource.AppTheme

@Composable
@Preview
fun App(database: AppDatabase,
        device: Device) {
    AppTheme {
        val navController = rememberNavController()
        val _database = remember { database }
        val api = remember {
            KtorRemoteNLUDataSource(
                client = createHttpClient(),
            )
        }
        val loginViewModel = remember {
            LoginViewModel(
                api = api,
                accountDao = _database.accountDao()
            )
        }
        NavHost(
            navController = navController,
            startDestination = Route.Login,
        ) {
            composable<Route.Login>(
                enterTransition = { fadeIn(animationSpec = tween(300)) },
                exitTransition = { fadeOut(animationSpec = tween(300)) }
            ) {
                Scaffold {
                    LoginScreenRoot(
                        viewModel = loginViewModel,
                        onLoginSuccess = { ->
                            navController.navigate(Route.Home) {
                                popUpTo(Route.Login) { inclusive = true }
                                launchSingleTop = true
                            }
                        }
                    )
                }
            }
            composable<Route.Home> (
                enterTransition = { slideInHorizontally(
                    initialOffsetX = { -it },
                    animationSpec = tween(300)
                ) },
                exitTransition = { slideOutHorizontally(
                    targetOffsetX  = { it },
                    animationSpec = tween(300)
                ) }
            ){
                HomeScreenRoot(
                    api = api,
                    device = device,
                    loginViewModel = loginViewModel,
                    onLogout = {
                        navController.navigate(Route.Login) {
                            popUpTo(Route.Home) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                )
            }

        }
//        SubjectListScreenRoot(
//            viewModel = remember { SubjectListViewModel() },
//            onSubjectClick = {
//
//            },
//        )
    }
}