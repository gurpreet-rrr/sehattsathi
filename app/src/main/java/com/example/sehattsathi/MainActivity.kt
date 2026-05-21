package com.example.sehattsathi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold

import androidx.compose.ui.Modifier

import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.example.emergencysos.ui.EmergencySOSScreen


import com.example.sehattsathi.navigation.appNavigation
import com.example.sehattsathi.screens.DoctorsScreen
import com.example.sehattsathi.screens.ProfileScreen
import com.example.sehattsathi.screens.chatBot
import com.example.sehattsathi.screens.healthRecordScreen
import com.example.sehattsathi.screens.joinMeet


import com.example.sehattsathi.ui.theme.SehattSathiTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // ✅ Splash screen must be installed before super.onCreate and setContent
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // ✅ Splash screen must be installed before super.onCreate and setContent

            SehattSathiTheme {
                Scaffold(modifier =
                    Modifier.fillMaxSize()) { innerPadding ->
                   appNavigation()

                }
            }
        }
    }
}

