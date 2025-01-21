package vn.id.tool.nlu.app

import kotlinx.serialization.Serializable

sealed interface Route {
    @Serializable
    data object AppGraph : Route

    @Serializable
    data object Login : Route

    @Serializable
    data object Home : Route

    @Serializable
    data object Timetable : Route

    @Serializable
    data object Account : Route

}