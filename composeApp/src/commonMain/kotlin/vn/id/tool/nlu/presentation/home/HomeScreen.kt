package vn.id.tool.nlu.presentation.home

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.ManageAccounts
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.ManageAccounts
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import vn.id.tool.nlu.Device
import vn.id.tool.nlu.app.Route
import vn.id.tool.nlu.core.domain.IAPI
import vn.id.tool.nlu.domain.Student
import vn.id.tool.nlu.presentation.account.AccountScreenRoot
import vn.id.tool.nlu.presentation.account.AccountViewModel
import vn.id.tool.nlu.presentation.login.LoginAction
import vn.id.tool.nlu.presentation.login.LoginScreenRoot
import vn.id.tool.nlu.presentation.login.LoginViewModel
import vn.id.tool.nlu.presentation.time_table.TimetableScreenRoot
import vn.id.tool.nlu.presentation.time_table.TimetableViewModel
import vn.id.tool.nlu.res.Res
import vn.id.tool.nlu.res.account
import vn.id.tool.nlu.res.timetable

@Composable
fun HomeScreenRoot(
    onLogout: () -> Unit,
    loginViewModel : LoginViewModel,
    api: IAPI,
    device: Device
) {
    var selected by remember { mutableStateOf(0) }
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            NavigationBar{
                bottomNavItems.forEachIndexed { index, navItem ->
                    NavigationBarItem(
                        selected = index == selected,
                        onClick = {
                            selected = index
                            navController.navigate(navItem.route) {
                                popUpTo(Route.Timetable) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        label = {
                            Text(stringResource(navItem.title))
                        },
                        icon = {
                            Icon(
                                imageVector = if (index == selected) navItem.selectedIcon else navItem.unselectedIcon,
                                contentDescription = stringResource(navItem.title)
                            )
                        }
                    )
                }
            }
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        val timetableState by remember {
            mutableStateOf(
                TimetableViewModel(
                    api = api
                )
            )
        }
        val accountState by remember {
            mutableStateOf(
                AccountViewModel(
                    api = api
                )
            )
        }
        NavHost(
            navController = navController,
            startDestination = Route.Timetable,
            modifier = Modifier.padding(innerPadding),
            ) {

            composable<Route.Timetable>(
                enterTransition = { fadeIn(animationSpec = tween(300)) },
                exitTransition = { fadeOut(animationSpec = tween(300)) }
            ) {
                TimetableScreenRoot(
                    viewModel = timetableState,
                    device = device
                )
            }
            composable<Route.Account>(
                enterTransition = { slideInHorizontally(
                    initialOffsetX = { -it },
                    animationSpec = tween(300)
                ) },
                exitTransition = { slideOutHorizontally(
                    targetOffsetX  = { it },
                    animationSpec = tween(300)
                ) }
            ) { _ ->
                AccountScreenRoot(
                    viewModel = accountState,
                    onLogout = {
                        loginViewModel.onAction(LoginAction.OnIsLoginSuccessChange(false))
                        onLogout()
                    }
                )
            }
//            composable<Route.Login>(
//                enterTransition = { slideInHorizontally(
//                    initialOffsetX = { -it },
//                    animationSpec = tween(300)
//                ) },
//                exitTransition = { slideOutHorizontally(
//                    targetOffsetX  = { it },
//                    animationSpec = tween(300)
//                ) }
//            ){ _ ->
//                LoginScreenRoot(
//                    viewModel = loginViewModel,
//                    onLoginSuccess = {
//                        navController.navigate(Route.Home) {
//                            popUpTo(Route.Login) { inclusive = true }
//                            launchSingleTop = true
//                        }
//                    }
//                )
//            }

        }
    }
}

val bottomNavItems = listOf(
    BottomNavItem(
        title = Res.string.timetable,
        route = Route.Timetable,
        selectedIcon = Icons.Filled.CalendarMonth,
        unselectedIcon = Icons.Outlined.CalendarMonth,
    ),
    BottomNavItem(
        title = Res.string.account,
        route = Route.Account,
        selectedIcon = Icons.Filled.ManageAccounts,
        unselectedIcon = Icons.Outlined.ManageAccounts,
    ),
)

data class BottomNavItem(
    val title: StringResource,
    val route: Route,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val hasNews: Boolean = false,
    val badgeCount: Int = 0
)