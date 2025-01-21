package vn.id.tool.nlu.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import org.jetbrains.compose.ui.tooling.preview.Preview
import vn.id.tool.nlu.core.database.AppDatabase
import vn.id.tool.nlu.core.network.createHttpClient
import vn.id.tool.nlu.data.nlu.network.KtorRemoteNLUDataSource
import vn.id.tool.nlu.presentation.home.HomeScreenRoot
import vn.id.tool.nlu.presentation.login.LoginScreenRoot
import vn.id.tool.nlu.presentation.login.LoginViewModel
import vn.id.tool.nlu.resource.AppTheme

@Composable
@Preview
fun App(database: AppDatabase) {
    AppTheme {
        val navController = rememberNavController()
        val _database = remember { database }
        NavHost(
            navController = navController,
            startDestination = Route.Login,
        ) {
            composable<Route.Login> {
                LoginScreenRoot(
                    viewModel = remember {
                        LoginViewModel(
                            api = KtorRemoteNLUDataSource(
                                client = createHttpClient(),
                            ),
                            accountDao = _database.accountDao()
                        )
                    },
                    onLoginSuccess = { student ->
                        navController.navigate(Route.Home) {
                            popUpTo(Route.Login) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                )
            }
            composable<Route.Home> { entry ->
                val args = entry.toRoute<Route.Home>()
                HomeScreenRoot()
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