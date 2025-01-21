package vn.id.tool.nlu.presentation.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.ManageAccounts
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.ManageAccounts
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import vn.id.tool.nlu.app.Route
import vn.id.tool.nlu.presentation.account.AccountScreenRoot
import vn.id.tool.nlu.presentation.time_table.TimetableScreenRoot
import vn.id.tool.nlu.res.Res
import vn.id.tool.nlu.res.account
import vn.id.tool.nlu.res.timetable

@Composable
fun HomeScreenRoot() {
    var selected by remember { mutableStateOf(0) }
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            NavigationBar {
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
    ) {
        NavHost(
            navController = navController,
            startDestination = Route.Timetable,

            ) {

            composable<Route.Timetable> {
                TimetableScreenRoot()
            }
            composable<Route.Account> { entry ->
                AccountScreenRoot()
            }
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