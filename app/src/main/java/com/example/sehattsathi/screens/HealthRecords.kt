package com.example.sehattsathi.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.sehattsathi.navigation.Routes

// Data class for a health record item.
data class HealthRecordInfo(
    val title: String,
    val description: String,
    val icon: ImageVector
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun healthRecordScreen(navController: NavController) {
    // Define the color palette
    val darkBlue = Color(0xFF005B88)
    val lightBlue = Color(0xFF0086C5)
    val lightGrey = Color(0xFFF6F7FB)
    val insideBoxColor = Color(0xFFD8EAF6)

    val healthRecords = listOf(
        HealthRecordInfo("Diagnostics recording", "View your diagnoses", Icons.Default.FindInPage),
        HealthRecordInfo("Chronic diseases", "The disease name.....", Icons.Default.Settings),
        HealthRecordInfo("health problem", "Lorem ipsum dolor sit amet, consectetur...", Icons.Default.Coronavirus),
        HealthRecordInfo("Surgical history", "The surgery type.....", Icons.Default.Search)
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("Health Records",  fontSize = 24.sp, fontWeight = FontWeight.Bold, color = lightBlue)
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate(Routes.BottomNavbarHomeScreen)}) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back", tint = lightBlue)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = lightGrey)
            )
        },

        containerColor = lightGrey
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            PatientInfoCard(cardBackgroundColor = insideBoxColor, darkBlue = darkBlue, lightBlue = lightBlue)
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                StatCard(
                    modifier = Modifier.weight(1f),
                    title = "Age",
                    value = "37",
                    unit = "years",
                    icon = Icons.Default.CalendarToday,
                    cardBackgroundColor = insideBoxColor,
                    darkBlue = darkBlue,
                    lightBlue = lightBlue
                )
                StatCard(
                    modifier = Modifier.weight(1f),
                    title = "Blood type",
                    value = "O+",
                    unit = "",
                    icon = Icons.Default.Bloodtype,
                    cardBackgroundColor = insideBoxColor,
                    darkBlue = darkBlue,
                    showEdit = true,
                    lightBlue = lightBlue
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            healthRecords.forEach { record ->
                HealthRecordInfoCard(record = record, cardBackgroundColor = insideBoxColor, darkBlue = darkBlue, lightBlue = lightBlue)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun PatientInfoCard(cardBackgroundColor: Color, darkBlue: Color, lightBlue: Color) {
    Box {
        Spacer(
            modifier = Modifier
                .matchParentSize()
                .offset(x = 4.dp, y = 4.dp)
                .background(lightBlue, shape = RoundedCornerShape(16.dp))
        )
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = cardBackgroundColor),
            elevation = CardDefaults.cardElevation(0.dp)
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.Person,
                    "Patient Avatar",
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(darkBlue.copy(alpha = 0.1f))
                        .padding(8.dp),
                    tint = darkBlue
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text("Rajesh Kumar", fontWeight = FontWeight.Bold, color = darkBlue, fontSize = 16.sp)
                    Text("Male", color = Color.Gray, fontSize = 14.sp)
                }
            }
        }
    }
}

@Composable
fun StatCard(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    unit: String,
    icon: ImageVector,
    cardBackgroundColor: Color,
    darkBlue: Color,
    showEdit: Boolean = false,
    lightBlue: Color
) {
    Box(modifier = modifier) {
        Spacer(
            modifier = Modifier
                .matchParentSize()
                .offset(x = 4.dp, y = 4.dp)
                .background(lightBlue, shape = RoundedCornerShape(16.dp))
        )
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = cardBackgroundColor),
            elevation = CardDefaults.cardElevation(0.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Text(title, color = Color.Gray, fontSize = 12.sp)
                    if (showEdit) {
                        Text("Edit", color = darkBlue, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Row(verticalAlignment = Alignment.Bottom) {
                        Text(value, fontWeight = FontWeight.Bold, color = darkBlue, fontSize = 24.sp)
                        Spacer(modifier = Modifier.width(4.dp))
                        if (unit.isNotEmpty()) {
                            Text(unit, color = Color.Gray, fontSize = 12.sp, modifier = Modifier.padding(bottom = 3.dp))
                        }
                    }
                    Icon(icon, contentDescription = title, tint = darkBlue, modifier = Modifier.size(24.dp))
                }
            }
        }
    }
}

@Composable
fun HealthRecordInfoCard(record: HealthRecordInfo, cardBackgroundColor: Color, darkBlue: Color, lightBlue: Color) {
    Box {
        Spacer(
            modifier = Modifier
                .matchParentSize()
                .offset(x = 4.dp, y = 4.dp)
                .background(lightBlue, shape = RoundedCornerShape(16.dp))
        )
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = cardBackgroundColor),
            elevation = CardDefaults.cardElevation(0.dp)
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(record.title, fontWeight = FontWeight.Bold, color = darkBlue, fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(record.description, color = Color.Gray, fontSize = 14.sp)
                }
                Spacer(modifier = Modifier.width(16.dp))
                Icon(
                    record.icon,
                    record.title,
                    tint = darkBlue,
                    modifier = Modifier
                        .size(40.dp)
                        .background(darkBlue.copy(alpha = 0.1f), RoundedCornerShape(8.dp))
                        .padding(8.dp)
                )
            }
        }
    }
}

