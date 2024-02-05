package com.example.fast_pedals_frontend.navigation

import androidx.compose.runtime.Composable
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
import com.example.fast_pedals_frontend.search.SearchScreen
import com.example.fast_pedals_frontend.search.SearchViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun NavigationHost(navController: NavHostController) {

    val loginViewModel: LogInViewModel = koinViewModel()
    val registerViewModel: RegisterViewModel = koinViewModel()
    val searchViewModel: SearchViewModel = koinViewModel()
    val listingViewModel: ListingViewModel = koinViewModel()
    val bikeViewModel: BikeViewModel = koinViewModel()

    NavHost(navController, startDestination = NavDestinations.WELCOME) {
        composable(NavDestinations.WELCOME) {
            WelcomeScreen(
                toRegister = { navController.navigate(NavDestinations.REGISTER) },
                toLogin = { navController.navigate(NavDestinations.LOGIN) }
            )
        }
        composable(NavDestinations.REGISTER) {
            RegisterScreen(
                registerViewModel = registerViewModel,
                onBack = { navController.navigate(NavDestinations.WELCOME) },
                onRegisterComplete = { navController.navigate(NavDestinations.SEARCH) }
            )
        }
        composable(NavDestinations.LOGIN) {
            LoginScreen(
                loginViewModel = loginViewModel,
                onBack = { navController.navigate(NavDestinations.WELCOME) },
                onLoginComplete = { navController.navigate(NavDestinations.LISTING) }
            )
        }
        composable(NavDestinations.LISTING) {
            ListingScreen(
                listingViewModel = listingViewModel,
                onNavigateToSearch = { navController.navigate(NavDestinations.SEARCH) },
                onNavigateToProfile = { navController.navigate(NavDestinations.BIKE) },
                onClick = { navController.navigate(NavDestinations.BIKE) }
            )
        }
        composable(NavDestinations.SEARCH) {
            SearchScreen(
                searchViewModel = searchViewModel,
                onBack = { navController.navigate(NavDestinations.WELCOME) },
                onSearch = { navController.navigate(NavDestinations.WELCOME) }
            )
        }
        composable(NavDestinations.BIKE) {
            BikeScreen(
                bikeViewModel = bikeViewModel,
                onBack = { navController.navigate(NavDestinations.LISTING) }
            )
        }
    }
}