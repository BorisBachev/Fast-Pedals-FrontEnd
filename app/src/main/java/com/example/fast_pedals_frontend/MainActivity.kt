package com.example.fast_pedals_frontend

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.fast_pedals_frontend.firebase.FirebaseViewModel
import com.example.fast_pedals_frontend.navigation.NavigationHost
import com.example.fast_pedals_frontend.ui.theme.FastPedalsFrontEndTheme
import com.google.firebase.messaging.FirebaseMessaging
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContent {
            FastPedalsFrontEndTheme {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    val navController = rememberNavController()

                    NavigationHost(navController = navController)
                }
            }
        }

    }

}