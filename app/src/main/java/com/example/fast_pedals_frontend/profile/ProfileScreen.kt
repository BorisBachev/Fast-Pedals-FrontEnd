package com.example.fast_pedals_frontend.profile

import android.hardware.lights.Light
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fast_pedals_frontend.search.SharedCriteriaViewModel
import com.example.fast_pedals_frontend.ui.theme.FastPedalsFrontEndTheme

@Composable
fun ProfileScreen(

    profileViewModel: ProfileViewModel,
    onListingsClick: () -> Unit,
    onFavouritesClick: () -> Unit,
    onLogoutClick: () -> Unit,
    sharedFavouriteViewModel: SharedFavouriteViewModel,
    sharedCriteriaViewModel: SharedCriteriaViewModel

) {

    val profileState by profileViewModel.profileState.collectAsState()

    val userInfo by profileViewModel.userInfo.collectAsState()

    LaunchedEffect(Unit) {
        profileViewModel.getUserByEmail()
    }

    if (profileState == ProfileState.Loading) {
        FastPedalsFrontEndTheme {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Spacer(modifier = Modifier.height(200.dp))
                CircularProgressIndicator()
            }
        }
    }

    FastPedalsFrontEndTheme {
        LazyColumn {
            item {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Spacer(modifier = Modifier.height(75.dp))
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            Icons.Default.Person,
                            contentDescription = "Profile Picture",
                            modifier = Modifier
                                .size(96.dp)
                                .padding(bottom = 8.dp)
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        Column(
                            modifier = Modifier.padding(start = 16.dp)
                        ) {
                            Surface(
                                modifier = Modifier.fillMaxWidth(),
                                elevation = 4.dp,
                                shape = MaterialTheme.shapes.medium,
                                color = Gray
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                                ) {
                                    Icon(Icons.Default.PersonOutline, contentDescription = "Name")
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "Name: ${userInfo?.name}",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 18.sp
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Surface(
                                modifier = Modifier.fillMaxWidth(),
                                elevation = 4.dp,
                                shape = MaterialTheme.shapes.medium,
                                color = Gray
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                                ) {
                                    Icon(Icons.Default.Email, contentDescription = "Email")
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "Email: ${userInfo?.email}",
                                        fontSize = 16.sp
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Surface(
                                modifier = Modifier.fillMaxWidth(),
                                elevation = 4.dp,
                                shape = MaterialTheme.shapes.medium,
                                color = Gray
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                                ) {
                                    Icon(Icons.Default.PersonOutline, contentDescription = "Full Name")
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "Full Name: ${userInfo?.fullName}",
                                        fontSize = 16.sp
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Surface(
                                modifier = Modifier.fillMaxWidth(),
                                elevation = 4.dp,
                                shape = MaterialTheme.shapes.medium,
                                color = Gray
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                                ) {
                                    Icon(Icons.Default.Phone, contentDescription = "Phone Number")
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "Phone Number: ${userInfo?.phoneNumber}",
                                        fontSize = 16.sp
                                    )
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(60.dp))
                    Button(
                        onClick = {
                            sharedCriteriaViewModel.resetSearchCriteria()
                            sharedCriteriaViewModel.updateUserId(userId = userInfo?.id ?: 0L)
                            onListingsClick()
                        },
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Icon(Icons.Default.LocationOn, contentDescription = null)
                        Text("Listings")
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {
                            sharedFavouriteViewModel.setIsFavourite(true)
                            onFavouritesClick()
                        },
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Icon(Icons.Default.Favorite, contentDescription = null)
                        Text("Favorites")
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {
                            profileViewModel.logout()
                            onLogoutClick()
                                  },
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = null)
                        Text("Logout")
                    }
                }
            }
        }
    }
}