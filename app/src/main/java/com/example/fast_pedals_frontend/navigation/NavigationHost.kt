package com.example.fast_pedals_frontend.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.fast_pedals_frontend.auth.login.LogInViewModel
import com.example.fast_pedals_frontend.auth.login.LoginScreen
import com.example.fast_pedals_frontend.auth.register.RegisterScreen
import com.example.fast_pedals_frontend.auth.WelcomeScreen
import com.example.fast_pedals_frontend.auth.register.RegisterViewModel
import com.example.fast_pedals_frontend.bike.BikeScreen
import com.example.fast_pedals_frontend.bike.BikeViewModel
import com.example.fast_pedals_frontend.listing.ListingScreen
import com.example.fast_pedals_frontend.listing.ListingViewModel
import com.example.fast_pedals_frontend.navigation.NavDestinations.BIKE
import com.example.fast_pedals_frontend.navigation.NavDestinations.LISTING
import com.example.fast_pedals_frontend.navigation.NavDestinations.LOGIN
import com.example.fast_pedals_frontend.navigation.NavDestinations.REGISTER
import com.example.fast_pedals_frontend.navigation.NavDestinations.SEARCH
import com.example.fast_pedals_frontend.navigation.NavDestinations.WELCOME
import com.example.fast_pedals_frontend.search.SearchScreen
import com.example.fast_pedals_frontend.search.SearchViewModel
import com.example.fast_pedals_frontend.search.SharedCriteriaViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun NavigationHost(navController: NavHostController) {

    val loginViewModel: LogInViewModel = koinViewModel()
    val registerViewModel: RegisterViewModel = koinViewModel()
    val searchViewModel: SearchViewModel = koinViewModel()
    val listingViewModel: ListingViewModel = koinViewModel()
    val bikeViewModel: BikeViewModel = koinViewModel()
    val sharedCriteriaViewModel: SharedCriteriaViewModel = koinViewModel()

    var currentRoute by rememberSaveable { mutableStateOf(WELCOME) }

    Scaffold(
        bottomBar = {
            if (shouldShowBottomNavigation(currentRoute)) {
                BottomNavigation {
                    BottomNavigationItem(
                        icon = { Icon(Icons.Filled.Search, contentDescription = null) },
                        label = { Text("Search") },
                        selected = currentRoute == SEARCH,
                        onClick = { currentRoute = SEARCH }
                    )
                    BottomNavigationItem(
                        icon = { Icon(Icons.AutoMirrored.Filled.List, contentDescription = null) },
                        label = { Text("Listing") },
                        selected = currentRoute == LISTING,
                        onClick = { currentRoute = LISTING }
                    )
                }
            }
        },
        content = { innerPadding ->
            NavHost(navController, startDestination = WELCOME, Modifier.padding(innerPadding)) {
                composable(WELCOME) {
                    WelcomeScreen(
                        toRegister = { currentRoute = REGISTER },
                        toLogin = { currentRoute = LOGIN }
                    )
                }
                composable(REGISTER) {
                    RegisterScreen(
                        registerViewModel = registerViewModel,
                        onBack = { currentRoute = WELCOME },
                        onRegisterComplete = { currentRoute = SEARCH }
                    )
                }
                composable(LOGIN) {
                    LoginScreen(
                        loginViewModel = loginViewModel,
                        onBack = { currentRoute = WELCOME },
                        onLoginComplete = { currentRoute = LISTING }
                    )
                }
                composable(LISTING) {
                    ListingScreen(
                        listingViewModel = listingViewModel,
                        sharedCriteriaViewModel = sharedCriteriaViewModel,
                        onClick = { listingId ->
                            currentRoute = "$BIKE/$listingId"
                        }
                    )
                }
                composable(SEARCH) {
                    SearchScreen(
                        searchViewModel = searchViewModel,
                        sharedCriteriaViewModel = sharedCriteriaViewModel,
                        onSearch = { currentRoute = LISTING }
                    )
                }
                composable("$BIKE/{listingId}") { backStackEntry ->
                    val listingId = backStackEntry.arguments?.getString("listingId")?.toLongOrNull()

                    listingId?.let {
                        BikeScreen(
                            bikeViewModel = bikeViewModel,
                            listingId = it,
                            onBack = { currentRoute = LISTING }
                        )
                    }
                }
            }
        }
    )

    LaunchedEffect(currentRoute) {
        when (currentRoute) {
            WELCOME -> navController.navigate(WELCOME)
            REGISTER -> navController.navigate(REGISTER)
            LOGIN -> navController.navigate(LOGIN)
            LISTING -> navController.navigate(LISTING)
            SEARCH -> navController.navigate(SEARCH)
            else -> {
                if (currentRoute.startsWith(BIKE)) {
                    val listingId = currentRoute.removePrefix(BIKE).substringAfter("/")
                    navController.navigate("$BIKE/$listingId")
                }
            }
        }
    }
}

@Composable
fun shouldShowBottomNavigation(currentRoute: String): Boolean {
    return currentRoute !in listOf(WELCOME, REGISTER, LOGIN) &&
            !currentRoute.startsWith("$BIKE/")
}