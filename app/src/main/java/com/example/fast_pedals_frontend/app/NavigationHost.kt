package com.example.fast_pedals_frontend.app

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.fast_pedals_frontend.auth.AuthViewModel
import com.example.fast_pedals_frontend.auth.RegisterScreen
import com.example.fast_pedals_frontend.auth.WelcomeScreen
import com.example.fast_pedals_frontend.mainPage.MainScreen

@Composable
fun NavigationHost(navController: NavHostController) {
    NavHost(navController, startDestination = "welcome") {
        composable("welcome") { WelcomeScreen(navController = navController) }
        composable("register") { RegisterScreen(authViewModel = AuthViewModel(), onRegisterComplete = { navController.navigate("main") }) }
        composable("login") { RegisterScreen(authViewModel = AuthViewModel(), onRegisterComplete = { navController.navigate("main") }) }
        composable("main") { MainScreen() }
    }
}