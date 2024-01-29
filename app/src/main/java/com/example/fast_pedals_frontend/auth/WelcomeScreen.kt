package com.example.fast_pedals_frontend.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.fast_pedals_frontend.ui.theme.FastPedalsFrontEndTheme

@Composable
fun WelcomeScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Welcome to Fast Pedals!")
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { navController.navigate("register") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text("Register")
            Icon(Icons.Default.ArrowForward, contentDescription = null)
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { navController.navigate("login") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text("Login")
            Icon(Icons.Default.ArrowForward, contentDescription = null)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WelcomeScreenPreview() {
    val navController = rememberNavController()
    FastPedalsFrontEndTheme {
        WelcomeScreen(navController = navController)
    }
}