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
import com.example.fast_pedals_frontend.navigation.NavDestinations.BIKE
import com.example.fast_pedals_frontend.navigation.NavDestinations.LISTING
import com.example.fast_pedals_frontend.navigation.NavDestinations.LOGIN
import com.example.fast_pedals_frontend.navigation.NavDestinations.REGISTER
import com.example.fast_pedals_frontend.navigation.NavDestinations.SEARCH
import com.example.fast_pedals_frontend.navigation.NavDestinations.WELCOME
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

    NavHost(navController, startDestination = WELCOME) {
        composable(WELCOME) {
            WelcomeScreen(
                toRegister = { navController.navigate(REGISTER) },
                toLogin = { navController.navigate(LOGIN) }
            )
        }
        composable(REGISTER) {
            RegisterScreen(
                registerViewModel = registerViewModel,
                onBack = { navController.navigate(WELCOME) },
                onRegisterComplete = { navController.navigate(SEARCH) }
            )
        }
        composable(LOGIN) {
            LoginScreen(
                loginViewModel = loginViewModel,
                onBack = { navController.navigate(WELCOME) },
                onLoginComplete = { navController.navigate(LISTING) }
            )
        }
        composable(LISTING) {
            ListingScreen(
                listingViewModel = listingViewModel,
                onNavigateToSearch = { navController.navigate(SEARCH) },
                onNavigateToProfile = { navController.navigate(BIKE) },
                onClick = { listingId ->
                    navController.navigate(BIKE + "/$listingId") }
            )
        }
        composable(SEARCH) {
            SearchScreen(
                searchViewModel = searchViewModel,
                onBack = { navController.navigate(WELCOME) },
                onSearch = { navController.navigate(WELCOME) }
            )
        }
        composable(BIKE + "/{listingId}") { backStackEntry ->
            val listingId = backStackEntry.arguments?.getString("listingId")?.toLongOrNull()

            listingId?.let {
                BikeScreen(
                    bikeViewModel = bikeViewModel,
                    listingId = it,
                    onBack = { navController.navigate(LISTING) }
                )
            }
        }
    }
}