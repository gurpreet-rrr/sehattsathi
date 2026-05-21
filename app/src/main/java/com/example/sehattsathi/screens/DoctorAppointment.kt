package com.example.sehattsathi.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.sehattsathi.navigation.Routes
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun appointmentScreen(navController: NavController) {
    // Define the color palette
    val darkBlue = Color(0xFF005B88)
    val lightBlue = Color(0xFF0086C5)
    val lightGrey = Color(0xFFF6F7FB)
    val pageBackground = Color.White

    var selectedDate by remember { mutableStateOf(Calendar.getInstance()) }
    var selectedHour by remember { mutableStateOf("10.00 AM") }
    var showConfirmationDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("APPOINTMENT", fontWeight = FontWeight.Bold, color = lightBlue) },
                navigationIcon = {
                    IconButton(onClick = {navController.navigate(Routes.BottomNavbarHomeScreen) }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back", tint = lightBlue)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = pageBackground)
            )
        },
      
        containerColor = pageBackground
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
            DoctorAppointmentCard(darkBlue, lightBlue, lightGrey)
            Spacer(modifier = Modifier.height(24.dp))
            SectionHeader("Select Date")
            Spacer(modifier = Modifier.height(16.dp))
            CalendarView(selectedDate, onDateSelected = { selectedDate = it }, darkBlue = darkBlue)
            Spacer(modifier = Modifier.height(24.dp))
            SectionHeader("Select Hour")
            Spacer(modifier = Modifier.height(16.dp))
            HourSelector(selectedHour = selectedHour, onHourSelected = { selectedHour = it }, darkBlue = darkBlue, lightGrey = lightGrey)
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = { showConfirmationDialog = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(30.dp),
                colors = ButtonDefaults.buttonColors(containerColor = darkBlue)
            ) {
                Text("Book Appointment", fontSize = 16.sp)
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        if (showConfirmationDialog) {
            val formattedDate = SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault()).format(selectedDate.time)
            AlertDialog(
                onDismissRequest = { showConfirmationDialog = false },
                title = { Text("Appointment Booked!") },
                text = { Text("Your appointment is confirmed for $formattedDate at $selectedHour.") },
                confirmButton = {
                    Button(onClick = { showConfirmationDialog = false }) {
                        Text("OK")
                    }
                }
            )
        }
    }
}

@Composable
fun DoctorAppointmentCard(darkBlue: Color, lightBlue: Color, lightGrey: Color) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.Person,
                    "Doctor Avatar",
                    modifier = Modifier
                        .size(72.dp)
                        .clip(CircleShape)
                        .background(lightGrey)
                        .padding(12.dp),
                    tint = darkBlue
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text("Dr. Chanpreet Singh ", fontWeight = FontWeight.Bold, color = darkBlue, fontSize = 18.sp)
                    Text("Dermatologist", color = Color.Gray, fontSize = 14.sp)
                    Text("MD, MBBS, MS", color = Color.Gray, fontSize = 12.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "9am - 12pm | Mon to Thurs",
                        color = lightBlue,
                        fontSize = 12.sp,
                        modifier = Modifier
                            .border(1.dp, lightBlue, RoundedCornerShape(12.dp))
                            .padding(horizontal = 12.dp, vertical = 4.dp)
                    )
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        lightBlue,
                        shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
                    )
                    .padding(12.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.LocationOn, "Location", tint = Color.White, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Civil Hospital Nabha", color = Color.White, fontSize = 14.sp)
                }
            }
        }
    }
}

@Composable
fun SectionHeader(title: String) {
    Text(
        text = title,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        color = Color(0xFF005B88),
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun CalendarView(
    selectedDate: Calendar,
    onDateSelected: (Calendar) -> Unit,
    darkBlue: Color
) {
    var currentMonth by remember { mutableStateOf( (selectedDate.clone() as Calendar).apply { set(Calendar.DAY_OF_MONTH, 1) } ) }

    // This effect syncs the displayed month with the selected date from outside
    LaunchedEffect(selectedDate) {
        val newCal = selectedDate.clone() as Calendar
        newCal.set(Calendar.DAY_OF_MONTH, 1)
        if (newCal.get(Calendar.MONTH) != currentMonth.get(Calendar.MONTH) || newCal.get(Calendar.YEAR) != currentMonth.get(Calendar.YEAR)) {
            currentMonth = newCal
        }
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {
                    val newMonth = currentMonth.clone() as Calendar
                    newMonth.add(Calendar.MONTH, -1)
                    currentMonth = newMonth
                }) {
                    Icon(Icons.Default.KeyboardArrowLeft, contentDescription = "Previous Month")
                }
                Text(
                    text = "${currentMonth.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())} ${currentMonth.get(Calendar.YEAR)}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                IconButton(onClick = {
                    val newMonth = currentMonth.clone() as Calendar
                    newMonth.add(Calendar.MONTH, 1)
                    currentMonth = newMonth
                }) {
                    Icon(Icons.Default.KeyboardArrowRight, contentDescription = "Next Month")
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            val daysOfWeek = listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                daysOfWeek.forEach { day ->
                    Text(text = day, color = Color.Gray, fontSize = 12.sp)
                }
            }
            Spacer(modifier = Modifier.height(8.dp))

            val firstDayOfMonthOffset = currentMonth.get(Calendar.DAY_OF_WEEK) - 1
            val daysInMonth = currentMonth.getActualMaximum(Calendar.DAY_OF_MONTH)
            val gridHeight = 40.dp * 6 // Fixed height for 6 weeks max

            LazyVerticalGrid(
                columns = GridCells.Fixed(7),
                modifier = Modifier.height(gridHeight),
                userScrollEnabled = false // Scrolling is handled by the parent Column
            ) {
                items(firstDayOfMonthOffset) {
                    Spacer(modifier = Modifier.size(40.dp))
                }
                items(daysInMonth) { day ->
                    val date = (currentMonth.clone() as Calendar).apply { set(Calendar.DAY_OF_MONTH, day + 1) }
                    val isSelected = date.get(Calendar.YEAR) == selectedDate.get(Calendar.YEAR) &&
                            date.get(Calendar.MONTH) == selectedDate.get(Calendar.MONTH) &&
                            date.get(Calendar.DAY_OF_MONTH) == selectedDate.get(Calendar.DAY_OF_MONTH)

                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(if (isSelected) darkBlue else Color.Transparent)
                            .clickable { onDateSelected(date) },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "${day + 1}",
                            color = if (isSelected) Color.White else Color.Black
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HourSelector(
    selectedHour: String,
    onHourSelected: (String) -> Unit,
    darkBlue: Color,
    lightGrey: Color
) {
    val hours = listOf(
        "09.00 AM", "09.30 AM", "10.00 AM",
        "10.30 AM", "11.00 AM", "11.30 AM"
    )

    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        hours.forEach { hour ->
            val isSelected = hour == selectedHour
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(if (isSelected) darkBlue else lightGrey)
                    .clickable { onHourSelected(hour) }
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = hour,
                    color = if (isSelected) Color.White else Color.Gray,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

