package com.example.sehattsathi.screens



import android.R.attr.name
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.sehattsathi.navigation.Routes
import com.example.sehattsathi.repo.firebaseRepo
import com.example.sehattsathi.ui.theme.LeagueSpartan
import com.google.firebase.Firebase
import java.util.Calendar

// Centralized color theme for easier management and consistency.
private object AppColors {
    val background = Color(0xFFF4F5F9)
    val primary = Color(0xFF3C3A5B)
    val primaryVariant = Color(0xFF5A587A)
    val textPrimary = Color.Black
    val textSecondary = Color.Black
    val textFieldBackground = Color.White
    val unfocusedBorder = Color(0xFFDCD8E3)
    val genderUnselectedBackground = Color(0xFFCADFF2)
}

// Data class to hold the state of the user input.
data class UserData(
    val id : String = "",
    val firstName: String = "",
    val lastName: String = "",
    val dobMonth: String = "",
    val dobDay: String = "",
    val dobYear: String = "",
    val phoneNumber: String = "",
    val gender: String = "Male", // Default to Male
    val address: String = ""
)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun userDataScreen(navController: NavController) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var dobMonth by remember { mutableStateOf("") }
    var dobDay by remember { mutableStateOf("") }
    var dobYear by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("Male") }
    var address by remember { mutableStateOf("") }

    val scrollState = rememberScrollState()
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) { isVisible = true }

    Scaffold(containerColor = AppColors.background) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 15.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Basic Information",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF005986),
                modifier = Modifier.padding(bottom = 24.dp)
            )

            AnimatedVisibility(
                visible = isVisible,
                enter = fadeIn(animationSpec = tween(500)) + slideInVertically(
                    initialOffsetY = { it / 2 },
                    animationSpec = tween(500)
                )
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {

                    // First Name
                    OutlinedTextField(
                        value = firstName,
                        onValueChange = { firstName = it },
                        label = { Text("First Name") },
                        leadingIcon = { Icon(Icons.Default.Person, contentDescription = "First Name") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = AppColors.primary,
                            unfocusedBorderColor = AppColors.unfocusedBorder,
                            focusedContainerColor = AppColors.textFieldBackground,
                            unfocusedContainerColor = AppColors.textFieldBackground,
                            focusedLeadingIconColor = AppColors.primary,
                            unfocusedLeadingIconColor = AppColors.textSecondary,
                            focusedLabelColor = AppColors.primary,
                            unfocusedLabelColor = AppColors.textSecondary
                        ),
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    // Last Name
                    OutlinedTextField(
                        value = lastName,
                        onValueChange = { lastName = it },
                        label = { Text("Last Name") },
                        leadingIcon = { Icon(Icons.Default.Person, contentDescription = "Last Name") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = AppColors.primary,
                            unfocusedBorderColor = AppColors.unfocusedBorder,
                            focusedContainerColor = AppColors.textFieldBackground,
                            unfocusedContainerColor = AppColors.textFieldBackground,
                            focusedLeadingIconColor = AppColors.primary,
                            unfocusedLeadingIconColor = AppColors.textSecondary,
                            focusedLabelColor = AppColors.primary,
                            unfocusedLabelColor = AppColors.textSecondary
                        ),
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    // Date of Birth
                    Text("Date of Birth", modifier = Modifier.fillMaxWidth(), color = AppColors.textSecondary)
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {

                        // Month
                        var monthExpanded by remember { mutableStateOf(false) }
                        val months = listOf(
                            "January", "February", "March", "April", "May", "June",
                            "July", "August", "September", "October", "November", "December"
                        )
                        ExposedDropdownMenuBox(expanded = monthExpanded, onExpandedChange = { monthExpanded = !monthExpanded }, modifier = Modifier.weight(1.2f)) {
                            OutlinedTextField(
                                value = dobMonth,
                                onValueChange = { },
                                readOnly = true,
                                label = { Text("Month") },
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = monthExpanded) },
                                modifier = Modifier.menuAnchor(),
                                shape = RoundedCornerShape(16.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = AppColors.primary,
                                    unfocusedBorderColor = AppColors.unfocusedBorder,
                                    focusedContainerColor = AppColors.textFieldBackground,
                                    unfocusedContainerColor = AppColors.textFieldBackground,
                                    focusedLabelColor = AppColors.primary,
                                    unfocusedLabelColor = AppColors.textSecondary
                                )
                            )
                            ExposedDropdownMenu(expanded = monthExpanded, onDismissRequest = { monthExpanded = false }) {
                                months.forEach { item ->
                                    DropdownMenuItem(
                                        text = { Text(item) },
                                        onClick = {
                                            dobMonth = item
                                            monthExpanded = false
                                        }
                                    )
                                }
                            }
                        }

                        // Day
                        var dayExpanded by remember { mutableStateOf(false) }
                        val days = (1..31).map { it.toString() }
                        ExposedDropdownMenuBox(expanded = dayExpanded, onExpandedChange = { dayExpanded = !dayExpanded }, modifier = Modifier.weight(1f)) {
                            OutlinedTextField(
                                value = dobDay,
                                onValueChange = { },
                                readOnly = true,
                                label = { Text("Day") },
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = dayExpanded) },
                                modifier = Modifier.menuAnchor(),
                                shape = RoundedCornerShape(16.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = AppColors.primary,
                                    unfocusedBorderColor = AppColors.unfocusedBorder,
                                    focusedContainerColor = AppColors.textFieldBackground,
                                    unfocusedContainerColor = AppColors.textFieldBackground,
                                    focusedLabelColor = AppColors.primary,
                                    unfocusedLabelColor = AppColors.textSecondary
                                )
                            )
                            ExposedDropdownMenu(expanded = dayExpanded, onDismissRequest = { dayExpanded = false }) {
                                days.forEach { item ->
                                    DropdownMenuItem(
                                        text = { Text(item) },
                                        onClick = {
                                            dobDay = item
                                            dayExpanded = false
                                        }
                                    )
                                }
                            }
                        }

                        // Year
                        var yearExpanded by remember { mutableStateOf(false) }
                        val currentYear = Calendar.getInstance().get(Calendar.YEAR)
                        val years = (currentYear downTo currentYear - 100).map { it.toString() }
                        ExposedDropdownMenuBox(expanded = yearExpanded, onExpandedChange = { yearExpanded = !yearExpanded }, modifier = Modifier.weight(1f)) {
                            OutlinedTextField(
                                value = dobYear,
                                onValueChange = { },
                                readOnly = true,
                                label = { Text("Year") },
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = yearExpanded) },
                                modifier = Modifier.menuAnchor(),
                                shape = RoundedCornerShape(16.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = AppColors.primary,
                                    unfocusedBorderColor = AppColors.unfocusedBorder,
                                    focusedContainerColor = AppColors.textFieldBackground,
                                    unfocusedContainerColor = AppColors.textFieldBackground,
                                    focusedLabelColor = AppColors.primary,
                                    unfocusedLabelColor = AppColors.textSecondary
                                )
                            )
                            ExposedDropdownMenu(expanded = yearExpanded, onDismissRequest = { yearExpanded = false }) {
                                years.forEach { item ->
                                    DropdownMenuItem(
                                        text = { Text(item) },
                                        onClick = {
                                            dobYear = item
                                            yearExpanded = false
                                        }
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Phone
                    OutlinedTextField(
                        value = phoneNumber,
                        onValueChange = { newValue ->
                            if (newValue.length <= 10) {   // limit to 10 chars
                                phoneNumber = newValue
                            } },
                        label = { Text("Phone Number") },
                        leadingIcon = { Icon(Icons.Default.Phone, contentDescription = "Phone") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        shape = RoundedCornerShape(16.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = AppColors.primary,
                            unfocusedBorderColor = AppColors.unfocusedBorder,
                            focusedContainerColor = AppColors.textFieldBackground,
                            unfocusedContainerColor = AppColors.textFieldBackground
                        )
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    // Gender
                    Text("Gender", modifier = Modifier.fillMaxWidth(), color = AppColors.textSecondary)
                    Row(modifier = Modifier.fillMaxWidth()) {
                        // Male
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = if (gender == "Male") Color(0xFFCADFF2) else Color.White,
                            tonalElevation = if (gender == "Male") 2.dp else 0.dp,
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp)
                                .clickable { gender = "Male" }
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    "Male",
                                    fontWeight = if (gender == "Male") FontWeight.Bold else FontWeight.Normal
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        // Female
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = if (gender == "Female")Color(0xFFCADFF2) else Color.White,
                            tonalElevation = if (gender == "Female") 2.dp else 0.dp,
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp)
                                .clickable { gender = "Female" }
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    "Female",
                                    fontWeight = if (gender == "Female") FontWeight.Bold else FontWeight.Normal
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Address
                    OutlinedTextField(
                        value = address,
                        onValueChange = { address = it },
                        label = { Text("Address") },
                        leadingIcon = { Icon(Icons.Default.Home, contentDescription = "Address") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = AppColors.primary,
                            unfocusedBorderColor = AppColors.unfocusedBorder,
                            focusedContainerColor = AppColors.textFieldBackground,
                            unfocusedContainerColor = AppColors.textFieldBackground
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            val context = LocalContext.current
            Button(
                onClick = {
                    if (firstName.isBlank() || lastName.isBlank() || phoneNumber.isBlank()) {
                        Toast.makeText(context, "Please fill all required fields", Toast.LENGTH_SHORT).show()
                        return@Button
                    }
                    firebaseRepo.adduserData(
                        UserData(
                            firstName = firstName,
                            lastName = lastName,
                            dobDay = dobDay,
                            dobMonth = dobMonth,
                            dobYear = dobYear,
                            phoneNumber = phoneNumber,
                            gender = gender,
                            address = address,


                        )
                    )
                    { // onResult function, If the last parameter of a function is a lambda,
//                        you can move it outside the parentheses. //
                         success->

                        if (success) {
                            Toast.makeText(context, "Data saved successfully", Toast.LENGTH_SHORT).show()
                            navController.navigate(Routes.BottomNavbarHomeScreen)

                        } else {
                            Toast.makeText(context, "Failed to save data", Toast.LENGTH_SHORT).show()
                        }
                    }



                },
                modifier = Modifier.fillMaxWidth(0.6f),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF005A87),
                    contentColor = Color.White
                ),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp)
            ) {
                Text("Continue", fontFamily = LeagueSpartan, fontSize = 22.sp)
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

