package com.example.fast_pedals_frontend.navigation

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.FiberNew
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.fast_pedals_frontend.auth.login.LogInViewModel
import com.example.fast_pedals_frontend.auth.login.LoginScreen
import com.example.fast_pedals_frontend.auth.register.RegisterScreen
import com.example.fast_pedals_frontend.auth.WelcomeScreen
import com.example.fast_pedals_frontend.auth.register.RegisterViewModel
import com.example.fast_pedals_frontend.auth.start.StartState
import com.example.fast_pedals_frontend.auth.start.StartViewModel
import com.example.fast_pedals_frontend.bike.BikeScreen
import com.example.fast_pedals_frontend.bike.BikeViewModel
import com.example.fast_pedals_frontend.create.CreateScreen
import com.example.fast_pedals_frontend.create.CreateViewModel
import com.example.fast_pedals_frontend.edit.EditScreen
import com.example.fast_pedals_frontend.edit.EditViewModel
import com.example.fast_pedals_frontend.edit.SharedEditViewModel
import com.example.fast_pedals_frontend.listing.ListingScreen
import com.example.fast_pedals_frontend.listing.ListingViewModel
import com.example.fast_pedals_frontend.navigation.NavDestinations.BIKE
import com.example.fast_pedals_frontend.navigation.NavDestinations.CREATE
import com.example.fast_pedals_frontend.navigation.NavDestinations.EDIT
import com.example.fast_pedals_frontend.navigation.NavDestinations.LISTING
import com.example.fast_pedals_frontend.navigation.NavDestinations.LOADING
import com.example.fast_pedals_frontend.navigation.NavDestinations.LOGIN
import com.example.fast_pedals_frontend.navigation.NavDestinations.PROFILE
import com.example.fast_pedals_frontend.navigation.NavDestinations.REGISTER
import com.example.fast_pedals_frontend.navigation.NavDestinations.SEARCH
import com.example.fast_pedals_frontend.navigation.NavDestinations.WELCOME
import com.example.fast_pedals_frontend.profile.ProfileScreen
import com.example.fast_pedals_frontend.profile.ProfileViewModel
import com.example.fast_pedals_frontend.profile.SharedFavouriteViewModel
import com.example.fast_pedals_frontend.search.SearchScreen
import com.example.fast_pedals_frontend.search.SearchViewModel
import com.example.fast_pedals_frontend.search.SharedCriteriaViewModel
import com.example.fast_pedals_frontend.ui.theme.FastPedalsFrontEndTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun NavigationHost(navController: NavHostController) {

    val loginViewModel: LogInViewModel = koinViewModel()
    val registerViewModel: RegisterViewModel = koinViewModel()
    val searchViewModel: SearchViewModel = koinViewModel()
    val listingViewModel: ListingViewModel = koinViewModel()
    val bikeViewModel: BikeViewModel = koinViewModel()
    val sharedCriteriaViewModel: SharedCriteriaViewModel = koinViewModel()
    val createViewModel: CreateViewModel = koinViewModel()
    val editViewModel: EditViewModel = koinViewModel()
    val sharedEditViewModel: SharedEditViewModel = koinViewModel()
    val profileViewModel: ProfileViewModel = koinViewModel()
    val favouriteViewModel: SharedFavouriteViewModel = koinViewModel()
    val startViewModel: StartViewModel = koinViewModel()

    val isLogged by startViewModel.isLogged.collectAsState()
    val state by startViewModel.startState.collectAsState()

    var currentRoute by rememberSaveable { mutableStateOf(LOADING) }

    LaunchedEffect(Unit) {
        startViewModel.checkToken()
    }

    LaunchedEffect(state) {
        currentRoute = when {
            state == StartState.None || state == StartState.Loading -> {
                LOADING
            }

            state == StartState.Success && isLogged -> {
                LISTING
            }

            else -> {
                WELCOME
            }
        }
    }

    FastPedalsFrontEndTheme {
        Scaffold(
            bottomBar = {
                if (shouldShowBottomNavigation(currentRoute)) {
                    BottomNavigation(
                        modifier = Modifier.height(64.dp),
                    ) {
                        BottomNavigationItem(
                            icon = {
                                Icon(
                                    Icons.Filled.Search,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.padding(bottom = 4.dp, top = 8.dp)
                                )
                            },
                            label = {
                                Text("Search", modifier = Modifier.padding(top = 8.dp),
                                    color = Color.White
                                )
                            },
                            selected = currentRoute == SEARCH,
                            onClick = { currentRoute = SEARCH },
                        )
                        BottomNavigationItem(
                            icon = {
                                Icon(
                                    Icons.AutoMirrored.Filled.List,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.padding(bottom = 4.dp, top = 8.dp)
                                )
                            },
                            label = {
                                Text("Listing", modifier = Modifier.padding(top = 4.dp),
                                    color = Color.White
                                )
                            },
                            selected = currentRoute == LISTING,
                            onClick = { currentRoute = LISTING },
                        )
                        BottomNavigationItem(
                            icon = {
                                Icon(
                                    Icons.Default.FiberNew,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.padding(bottom = 4.dp, top = 8.dp)
                                )
                            },
                            label = {
                                Text("Create", modifier = Modifier.padding(top = 4.dp),
                                    color = Color.White
                                )
                            },
                            selected = currentRoute == CREATE,
                            onClick = { currentRoute = CREATE },
                        )
                        BottomNavigationItem(
                            icon = {
                                Icon(
                                    Icons.Default.Person,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.padding(bottom = 4.dp, top = 8.dp)
                                )
                            },
                            label = {
                                Text("Profile", modifier = Modifier.padding(top = 4.dp),
                                    color = Color.White
                                )
                            },
                            selected = currentRoute == PROFILE,
                            onClick = { currentRoute = PROFILE },
                        )
                    }
                }
            },
            content = { innerPadding ->
                NavHost(navController, startDestination = LOADING, Modifier.padding(innerPadding)) {
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
                            onRegisterComplete = { currentRoute = LISTING }
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
                            },
                            sharedFavouriteViewModel = favouriteViewModel
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
                        val listingId =
                            backStackEntry.arguments?.getString("listingId")?.toLongOrNull()

                        listingId?.let {
                            BikeScreen(
                                bikeViewModel = bikeViewModel,
                                listingId = it,
                                onBack = { currentRoute = LISTING },
                                onEdit = { currentRoute = "$EDIT/$listingId" },
                                onDelete = { currentRoute = LISTING },
                                onListingsClick = { currentRoute = LISTING },
                                sharedEditViewModel = sharedEditViewModel,
                                sharedCriteriaViewModel = sharedCriteriaViewModel
                            )
                        }
                    }
                    composable(CREATE) {
                        CreateScreen(
                            onCreate = { listingId ->
                                currentRoute = "$BIKE/$listingId"
                            },
                            createViewModel = createViewModel
                        )
                    }
                    composable("$EDIT/{listingId}") { backStackEntry ->
                        val listingId =
                            backStackEntry.arguments?.getString("listingId")?.toLongOrNull()

                        listingId?.let {
                            EditScreen(
                                editViewModel = editViewModel,
                                listingId = it,
                                onBack = { currentRoute = "$BIKE/$listingId" },
                                onEdit = { currentRoute = "$BIKE/$listingId" },
                                sharedEditViewModel = sharedEditViewModel
                            )
                        }
                    }
                    composable(PROFILE) {
                        ProfileScreen(
                            profileViewModel = profileViewModel,
                            onListingsClick = { currentRoute = LISTING },
                            onFavouritesClick = { currentRoute = LISTING },
                            onLogoutClick = { currentRoute = WELCOME },
                            sharedFavouriteViewModel = favouriteViewModel,
                            sharedCriteriaViewModel = sharedCriteriaViewModel
                        )
                    }
                    composable(LOADING) {
                        LoadingScreen()
                    }
                }
            }
        )
    }

    LaunchedEffect(currentRoute) {
        when (currentRoute) {
            WELCOME -> navController.navigate(WELCOME)
            REGISTER -> navController.navigate(REGISTER)
            LOGIN -> navController.navigate(LOGIN)
            LISTING -> navController.navigate(LISTING)
            SEARCH -> navController.navigate(SEARCH)
            CREATE -> navController.navigate(CREATE)
            PROFILE -> navController.navigate(PROFILE)
            else -> {
                if (currentRoute.startsWith(BIKE)) {
                    val listingId = currentRoute.removePrefix(BIKE).substringAfter("/")
                    navController.navigate("$BIKE/$listingId")
                }
                if (currentRoute.startsWith(EDIT)) {
                    val listingId = currentRoute.removePrefix(EDIT).substringAfter("/")
                    navController.navigate("$EDIT/$listingId")
                }
            }
        }
    }
}

@Composable
fun shouldShowBottomNavigation(currentRoute: String): Boolean {
    return currentRoute !in listOf(WELCOME, REGISTER, LOGIN, LOADING) &&
            !currentRoute.startsWith("$BIKE/") && !currentRoute.startsWith("$EDIT/")
}