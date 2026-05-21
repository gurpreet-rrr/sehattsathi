package com.example.medicalapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.SupportAgent
import androidx.compose.material.icons.filled.VideoCall
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.sehatsathi.ui.homeScreenUi
import com.example.sehattsathi.screens.chatBot
import com.example.sehattsathi.screens.joinMeet

@Composable
fun bottomnavbarhomescreen(navController: NavController) {

    val navItemList = listOf(
        navItem("Home", Icons.Default.Home, 0),
        navItem("Sehat Sathi AI", Icons.Default.SupportAgent, 2),
        navItem("Video Consultancy", Icons.Default.VideoCall, 1)
    )

    var selectedIndex by remember { mutableStateOf(0) }

    Scaffold(
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp).background(Color(0xFF0049AC)), // Set your desired height
                verticalAlignment = Alignment.CenterVertically
            ) {
                navItemList.forEachIndexed { index, item ->
                    IconButton(onClick = { selectedIndex = index }, modifier = Modifier.weight(1f)) {
                        BadgedBox(
                            badge = {
                                if (item.badge > 0) {
                                    Badge { Text(item.badge.toString()) }
                                }
                            }
                        ) {
                            Icon(item.icon, tint = Color(0xFFFDFDFD), contentDescription = item.label)
                        }
                    }
                }
            }
        }
    ) { innerPadding ->
        contentScreen(modifier = Modifier.padding(innerPadding), selectedIndex, navController = navController )
    }
}

@Composable
fun contentScreen(modifier: Modifier, selectedIndex: Int,
                  navController: NavController) {
    when (selectedIndex) {
        0 -> homeScreenUi(navController = navController)
        1 -> chatBot()
        2 -> joinMeet(navController = navController) // Add screens as needed
    }
}

data class navItem(

    val label : String,
    val icon : ImageVector,
    val badge : Int
)