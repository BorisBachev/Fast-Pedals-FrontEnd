package com.example.fast_pedals_frontend.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.fast_pedals_frontend.auth.AuthViewModel
import com.example.fast_pedals_frontend.auth.login.LoginScreen
import com.example.fast_pedals_frontend.auth.register.RegisterScreen
import com.example.fast_pedals_frontend.auth.WelcomeScreen
import com.example.fast_pedals_frontend.mainPage.MainScreen

@Composable
fun NavigationHost(navController: NavHostController) {
    NavHost(navController, startDestination = NavDestinations.WELCOME) {
        composable(NavDestinations.WELCOME) {
            WelcomeScreen(
                toRegister = { navController.navigate(NavDestinations.REGISTER) },
                toLogin = { navController.navigate(NavDestinations.LOGIN) }
            )
        }
        composable(NavDestinations.REGISTER) {
            RegisterScreen(
                authViewModel = AuthViewModel(),
                onBack = { navController.navigate(NavDestinations.WELCOME) },
                onRegisterComplete = { navController.navigate(NavDestinations.WELCOME) }
            )
        }
        composable(NavDestinations.LOGIN) {
            LoginScreen(
                authViewModel = AuthViewModel(),
                onBack = { navController.navigate(NavDestinations.WELCOME) },
                onLoginComplete = { navController.navigate(NavDestinations.WELCOME) }
            )
        }
        composable(NavDestinations.MAIN) { MainScreen() }
    }
}