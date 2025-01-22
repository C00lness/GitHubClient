package com.example.core

sealed class NavRoutes(val route: String) {
    object Search : NavRoutes("search")
    object RepositoryDetails : NavRoutes("details")
}