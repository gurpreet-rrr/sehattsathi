package com.example.sehattsathi.screens



import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.sehattsathi.navigation.Routes

@Composable
fun ProfileScreen(navController: NavController) {
    // Reusing the established color theme
    val darkBlue = Color(0xFF005B88)
    val lightBlue = Color(0xFF0086C5)
    val softGreen = Color(0xFF4CAF50)
    val backgroundGradient = Brush.verticalGradient(
        colors = listOf(Color.White, Color(0xFFE6F0FF))
    )

    Scaffold(
        containerColor = Color.Transparent, // Allows gradient to show through
        modifier = Modifier.background(backgroundGradient)
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Top action buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = { /* Handle settings */ }) {
                    Icon(Icons.Outlined.Settings, contentDescription = "Settings", tint = darkBlue)
                }
                IconButton(onClick = { navController.navigate(Routes.BottomNavbarHomeScreen)}) {
                    Icon(Icons.Outlined.Logout, contentDescription = "Logout", tint = darkBlue)
                }
            }

            // User Info Section
            UserInfoSection(name = "Rajesh Kumar", age = 32, gender = "Male", darkBlue = darkBlue, softGreen = softGreen)

            Spacer(modifier = Modifier.height(24.dp))
            HealthSummaryCard(darkBlue = darkBlue, softGreen = softGreen)

            Spacer(modifier = Modifier.height(24.dp))
            AppointmentsSection(darkBlue = darkBlue, lightBlue = lightBlue)

            Spacer(modifier = Modifier.height(24.dp))
            MedicalRecordsSection(darkBlue = darkBlue, lightBlue = lightBlue, navController = navController)

            Spacer(modifier = Modifier.height(32.dp))
            EditProfileButton(lightBlue = lightBlue, darkBlue = darkBlue)

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun UserInfoSection(name: String, age: Int, gender: String, darkBlue: Color, softGreen: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box {
            Icon(
                Icons.Outlined.Person,
                contentDescription = "User Avatar",
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFE6F0FF))
                    .padding(20.dp),
                tint = darkBlue
            )
            Icon(
                Icons.Outlined.VerifiedUser,
                contentDescription = "Verified",
                tint = softGreen,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .size(30.dp)
                    .background(Color.White, CircleShape)
                    .padding(4.dp)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(name, fontWeight = FontWeight.Bold, fontSize = 22.sp, color = darkBlue)
        Spacer(modifier = Modifier.height(4.dp))
        Text("$age years  |  $gender", color = Color.Gray, fontSize = 14.sp)
    }
}

@Composable
fun HealthSummaryCard(darkBlue: Color, softGreen: Color) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            HealthStat(title = "Height", value = "175 cm", icon = Icons.Outlined.Height, iconColor = darkBlue)
            HealthStat(title = "Weight", value = "72 kg", icon = Icons.Outlined.MonitorWeight, iconColor = darkBlue)
            HealthStat(title = "BMI", value = "23.5", icon = Icons.Outlined.Speed, iconColor = softGreen)
            HealthStat(title = "Blood", value = "O+", icon = Icons.Outlined.Bloodtype, iconColor = darkBlue)
        }
    }
}

@Composable
fun HealthStat(title: String, value: String, icon: ImageVector, iconColor: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(icon, contentDescription = title, tint = iconColor, modifier = Modifier.size(28.dp))
        Spacer(modifier = Modifier.height(8.dp))
        Text(value, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        Text(title, color = Color.Gray, fontSize = 12.sp)
    }
}

@Composable
fun AppointmentsSection(darkBlue: Color, lightBlue: Color) {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Upcoming", "Past")

    Column(modifier = Modifier.fillMaxWidth()) {
        Text("My Appointments", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = darkBlue)
        Spacer(modifier = Modifier.height(12.dp))
        TabRow(
            selectedTabIndex = selectedTab,
            containerColor = Color.Transparent,
            contentColor = darkBlue,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                    color = darkBlue,
                    height = 3.dp
                )
            }
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = { Text(title, fontWeight = FontWeight.SemiBold) }
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        if (selectedTab == 0) {
            AppointmentInfoCard("Dr. ChanpreetSingh", "Dermalogist", "Oct 28, 2025", darkBlue, lightBlue)
        } else {
            AppointmentInfoCard("Dr. Sam Bishnoi", "Orthopedic", "Sep 15, 2025", darkBlue, lightBlue, isPast = true)
        }
    }
}

@Composable
fun AppointmentInfoCard(name: String, specialty: String, date: String, darkBlue: Color, lightBlue: Color, isPast: Boolean = false) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Outlined.Person,
                contentDescription = "Doctor",
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFE6F0FF))
                    .padding(12.dp),
                tint = darkBlue
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(name, fontWeight = FontWeight.Bold)
                Text(specialty, color = Color.Gray, fontSize = 14.sp)
            }
            Text(date, color = if (isPast) Color.Gray else lightBlue, fontWeight = FontWeight.SemiBold)
        }
    }
}

@Composable
fun MedicalRecordsSection(darkBlue: Color, lightBlue: Color,navController: NavController) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text("Medical Records", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = darkBlue)
        Spacer(modifier = Modifier.height(12.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { navController.navigate(Routes.HealthRecords)},
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(2.dp)
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Outlined.Description, contentDescription = "Records", tint = lightBlue)
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text("My Records", fontWeight = FontWeight.Bold)
                    Text("Prescriptions, Reports, Lab Results", color = Color.Gray, fontSize = 12.sp)
                }
                Icon(Icons.Outlined.ArrowForwardIos, contentDescription = "View", tint = Color.Gray)
            }
        }
    }
}

@Composable
fun EditProfileButton(lightBlue: Color, darkBlue: Color) {
    Button(
        onClick = { /* Handle edit profile */ },
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        shape = RoundedCornerShape(25.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        contentPadding = PaddingValues()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(lightBlue, darkBlue)
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Text("Edit Profile", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
    }
}

