package com.example.sehattsathi.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.sehattsathi.navigation.Routes

// Data class for a doctor item.
data class Doctor(
    val id: Int,
    val name: String,
    val specialty: String,
    val qualifications: String,
    val schedule: String,
    val isAvailable: Boolean,
    val avatar: Int // Using a drawable resource ID
)

// Enum to define the various sorting options
enum class SortOption {
    NONE,
    NAME_ASC,
    NAME_DESC,
    AVAILABILITY
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DoctorsScreen(navController: NavController) { // Renamed from OurDoctorScreen
    // Define the color palette
    val darkBlue = Color(0xFF005B88)
    val lightBlue = Color(0xFF0086C5)
    val lightGrey = Color(0xFFF6F7FB)
    val pageBackground = Color.White
    val availableGreen = Color(0xFF4CAF50)
    val unavailableGrey = Color.Gray

    // --- State Management for Filtering & Sorting ---
    var searchQuery by remember { mutableStateOf("") }
    var filterExpanded by remember { mutableStateOf(false) }
    var sortExpanded by remember { mutableStateOf(false) }

    var selectedSpecialty by remember { mutableStateOf<String?>(null) }
    var availabilityFilter by remember { mutableStateOf<Boolean?>(null) }
    var selectedSortOption by remember { mutableStateOf(SortOption.NONE) }


    val allDoctors = remember {
        listOf(
            Doctor(1, "Dr. Shivam Sharma", "Dermatologist", "MD, MBBS, MS", "9am - 12pm | Mon to Thurs", true, -1),
            Doctor(2, "Dr. Rajesh Mittal", "Cardiologist", "MD, FACC", "1pm - 4pm | Mon to Fri", false, -1),
            Doctor(3, "Dr. Paresh", "Neurologist", "MD, PhD", "10am - 1pm | Tue, Wed, Fri", true, -1),
            Doctor(4, "Dr. Karam Chand", "Pediatrician", "MD, FAAP", "9am - 5pm | Mon to Fri", false, -1),
            Doctor(5, "Dr. Sumeer Sharma", "Orthopedic Surgeon", "MD, FRCS", "2pm - 6pm | Mon, Thurs", true, -1),
            Doctor(6, "Dr. Chanpreet Singh", "Dermatologist", "MD", "2pm - 5pm | Tue, Thurs", false, -1),
            Doctor(7, "Dr. Neha Kapoor", "Gynecologist", "MD, DGO", "10am - 1pm | Mon to Sat", true, -1),
            Doctor(8, "Dr. Ramesh Chauhan", "ENT Specialist", "MS (ENT)", "3pm - 7pm | Mon to Fri", false, -1),
            Doctor(9, "Dr. Nidhi Gupta", "Endocrinologist", "MD, DM (Endocrinology)", "11am - 2pm | Tue, Wed, Fri", true, -1),
            Doctor(10, "Dr. Anil Verma", "Psychiatrist", "MD (Psychiatry)", "4pm - 8pm | Mon, Wed, Fri", false, -1),
            Doctor(11, "Dr. Meenakshi Rana", "General Physician", "MBBS, MD", "9am - 1pm | Mon to Sat", true, -1),
            Doctor(12, "Dr. Harpreet Kaur", "Dentist", "BDS, MDS", "10am - 6pm | Mon to Sat", false, -1),
            Doctor(13, "Dr. Vikram Malhotra", "Urologist", "MS, MCh", "12pm - 3pm | Mon, Wed, Fri", true, -1),
            Doctor(14, "Dr. Ritu Arora", "Ophthalmologist", "MS (Ophthalmology)", "2pm - 5pm | Mon to Fri", false, -1),
            Doctor(15, "Dr. Pradeep Saini", "Oncologist", "MD, DM (Oncology)", "9am - 12pm | Tue, Thurs, Sat", true, -1),
            Doctor(16, "Dr. Kavita Sharma", "Radiologist", "MD (Radiology)", "10am - 4pm | Mon to Fri", false, -1),
            Doctor(17, "Dr. Ashish Khanna", "Gastroenterologist", "MD, DM (Gastro)", "11am - 3pm | Mon, Wed, Fri", true, -1),
            Doctor(18, "Dr. Sneha Tiwari", "Pulmonologist", "MD, DM (Pulmonology)", "3pm - 6pm | Mon to Thurs", false, -1),
            Doctor(19, "Dr. Harish Bhatia", "Nephrologist", "MD, DM (Nephrology)", "1pm - 4pm | Mon, Wed, Fri", true, -1),
            Doctor(20, "Dr. Manisha Gupta", "Dietitian", "MSc (Nutrition)", "9am - 12pm | Mon to Sat", true, -1)
        )
    }

    // --- Combined Filtering and Sorting Logic ---
    val displayedDoctors = remember(searchQuery, selectedSpecialty, availabilityFilter, selectedSortOption, allDoctors) {
        val filtered = allDoctors.filter { doctor ->
            val nameMatches = doctor.name.contains(searchQuery, ignoreCase = true) || doctor.specialty.contains(searchQuery, ignoreCase = true)
            val specialtyMatches = selectedSpecialty?.equals(doctor.specialty) ?: true
            val availabilityMatches = when (availabilityFilter) {
                true -> doctor.isAvailable
                false -> !doctor.isAvailable
                null -> true
            }
            nameMatches && specialtyMatches && availabilityMatches
        }

        when (selectedSortOption) {
            SortOption.NAME_ASC -> filtered.sortedBy { it.name }
            SortOption.NAME_DESC -> filtered.sortedByDescending { it.name }
            SortOption.AVAILABILITY -> filtered.sortedByDescending { it.isAvailable } // true comes first
            SortOption.NONE -> filtered // No sorting applied
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Our Doctors", fontWeight = FontWeight.Bold, fontSize = 24.sp, color = lightBlue) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate(Routes.BottomNavbarHomeScreen)}) {
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
                .verticalScroll(rememberScrollState()) // Added for scrollability
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Search for Doctors") },
                leadingIcon = { Icon(Icons.Default.Search, "Search Icon") },
                shape = RoundedCornerShape(30.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = lightBlue,
                    unfocusedBorderColor = Color.LightGray
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                FilterButton(
                    modifier = Modifier.weight(1f),
                    expanded = filterExpanded,
                    onClick = { filterExpanded = !filterExpanded; sortExpanded = false },
                    lightBlue = lightBlue,
                    text = "Filter by Type"
                )
                FilterButton(
                    modifier = Modifier.weight(1f),
                    expanded = sortExpanded,
                    onClick = { sortExpanded = !sortExpanded; filterExpanded = false },
                    lightBlue = lightBlue,
                    text = "Sort By",
                    isOutlined = true
                )
            }

            if (filterExpanded) {
                Spacer(modifier = Modifier.height(16.dp))
                FilterOptions(
                    lightGrey = lightGrey,
                    darkBlue = darkBlue,
                    availableColor = availableGreen,
                    unavailableColor = unavailableGrey,
                    selectedSpecialty = selectedSpecialty,
                    availabilityFilter = availabilityFilter,
                    onSpecialtySelected = { specialty ->
                        selectedSpecialty = if (selectedSpecialty == specialty) null else specialty
                    },
                    onAvailabilitySelected = { availability ->
                        availabilityFilter = if (availabilityFilter == availability) null else availability
                    }
                )
            }

            if (sortExpanded) {
                Spacer(modifier = Modifier.height(16.dp))
                SortOptions(
                    lightGrey = lightGrey,
                    darkBlue = darkBlue,
                    selectedSortOption = selectedSortOption,
                    onSortOptionSelected = { sortOption ->
                        selectedSortOption = if(selectedSortOption == sortOption) SortOption.NONE else sortOption
                    }
                )
            }


            Spacer(modifier = Modifier.height(16.dp))

            if (displayedDoctors.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth() // Changed from fillMaxSize
                        .padding(vertical = 50.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No doctors found.", fontSize = 16.sp, color = Color.Gray, textAlign = TextAlign.Center)
                }
            } else {
                // We no longer need a LazyColumn because the parent Column is now scrollable.
                // Using a Column here simplifies the layout.
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    displayedDoctors.forEach { doctor ->
                        DoctorCard(doctor, lightGrey, darkBlue, lightBlue, availableGreen, unavailableGrey)
                    }
                }
            }
        }
    }
}

@Composable
fun FilterButton(
    modifier: Modifier = Modifier,
    expanded: Boolean,
    onClick: () -> Unit,
    lightBlue: Color,
    text: String,
    isOutlined: Boolean = false
) {
    val icon = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown
    if (isOutlined) {
        OutlinedButton(
            onClick = onClick,
            modifier = modifier,
            shape = RoundedCornerShape(30.dp),
            border = ButtonDefaults.outlinedButtonBorder.copy(brush = SolidColor(lightBlue))
        ) {
            Text(text, color = lightBlue)
            Icon(icon, contentDescription = null, tint = lightBlue)
        }
    } else {
        Button(
            onClick = onClick,
            modifier = modifier,
            shape = RoundedCornerShape(30.dp),
            colors = ButtonDefaults.buttonColors(containerColor = lightBlue)
        ) {
            Text(text)
            Icon(icon, contentDescription = null)
        }
    }
}

@Composable
fun FilterOptions(
    lightGrey: Color,
    darkBlue: Color,
    availableColor: Color,
    unavailableColor: Color,
    selectedSpecialty: String?,
    availabilityFilter: Boolean?,
    onSpecialtySelected: (String) -> Unit,
    onAvailabilitySelected: (Boolean) -> Unit
) {
    val specializations = listOf(
        "Dermatologist", "Cardiologist", "Neurologist", "Pediatrician", "Orthopedic Surgeon"
    )
    var specializationSearch by remember { mutableStateOf("") }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.Top
    ) {
        Card(
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = lightGrey),
            elevation = CardDefaults.cardElevation(0.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                OutlinedTextField(
                    value = specializationSearch,
                    onValueChange = { specializationSearch = it },
                    placeholder = { Text("Specialization", fontSize = 12.sp) },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search Specialization", modifier = Modifier.size(18.dp)) },
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = Color.Transparent,
                        focusedBorderColor = Color.Transparent,
                        unfocusedContainerColor = Color.White,
                        focusedContainerColor = Color.White
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(12.dp))
                specializations.filter { it.contains(specializationSearch, ignoreCase = true) }.forEach { specialty ->
                    val isSelected = selectedSpecialty == specialty
                    Text(
                        text = specialty,
                        color = if (isSelected) Color.White else darkBlue,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(if (isSelected) darkBlue else Color.Transparent)
                            .clickable { onSpecialtySelected(specialty) }
                            .padding(vertical = 4.dp, horizontal = 8.dp)
                    )
                }
            }
        }

        Card(
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = lightGrey),
            elevation = CardDefaults.cardElevation(0.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                AvailabilityOption("Available", availableColor, availabilityFilter == true) { onAvailabilitySelected(true) }
                Spacer(modifier = Modifier.height(12.dp))
                AvailabilityOption("Unavailable", unavailableColor, availabilityFilter == false) { onAvailabilitySelected(false) }
            }
        }
    }
}

@Composable
fun SortOptions(
    lightGrey: Color,
    darkBlue: Color,
    selectedSortOption: SortOption,
    onSortOptionSelected: (SortOption) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = lightGrey),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            val sortItems = mapOf(
                "Name: A-Z" to SortOption.NAME_ASC,
                "Name: Z-A" to SortOption.NAME_DESC,
                "Availability" to SortOption.AVAILABILITY
            )
            sortItems.forEach { (text, option) ->
                val isSelected = selectedSortOption == option
                Text(
                    text = text,
                    color = if (isSelected) Color.White else darkBlue,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (isSelected) darkBlue else Color.Transparent)
                        .clickable { onSortOptionSelected(option) }
                        .padding(vertical = 8.dp, horizontal = 8.dp)
                )
            }
        }
    }
}


@Composable
fun AvailabilityOption(text: String, color: Color, isSelected: Boolean, onClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
            .padding(4.dp)
    ) {
        Box(
            modifier = Modifier
                .size(16.dp)
                .background(if (isSelected) color else Color.White, CircleShape)
                .border(1.dp, if (isSelected) Color.White else color, CircleShape),
            contentAlignment = Alignment.Center
        ){
            if(isSelected){
                Box(modifier = Modifier.size(8.dp).background(Color.White, CircleShape))
            }
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(text, color = Color.Gray)
    }
}


@Composable
fun DoctorCard(
    doctor: Doctor,
    lightGrey: Color,
    darkBlue: Color,
    lightBlue: Color,
    availableGreen: Color,
    unavailableGrey: Color
) {
    Box {
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(lightBlue, shape = RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.CenterEnd
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowForwardIos,
                contentDescription = "View Details",
                tint = Color.White,
                modifier = Modifier
                    .padding(end = 5.dp)
                    .size(20.dp)
            )
        }
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 30.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = lightGrey),
            elevation = CardDefaults.cardElevation(0.dp)
        ) {
            Row(
                modifier = Modifier.padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = doctor.name,
                        modifier = Modifier
                            .size(72.dp)
                            .clip(CircleShape)
                            .background(darkBlue.copy(alpha = 0.1f)),
                        tint = darkBlue.copy(alpha = 0.5f)
                    )
                    StatusChip(
                        isAvailable = doctor.isAvailable,
                        availableColor = availableGreen,
                        unavailableColor = unavailableGrey,
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .offset(y = 8.dp)
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(doctor.name, fontWeight = FontWeight.Bold, color = darkBlue, fontSize = 16.sp)
                    Text(doctor.specialty, color = Color.Gray, fontSize = 14.sp)
                    Text(doctor.qualifications, color = Color.Gray, fontSize = 12.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = doctor.schedule,
                        color = Color.Gray,
                        fontSize = 12.sp,
                        modifier = Modifier
                            .background(Color.White, RoundedCornerShape(12.dp))
                            .padding(horizontal = 12.dp, vertical = 4.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun StatusChip(isAvailable: Boolean, availableColor: Color, unavailableColor: Color, modifier: Modifier = Modifier) {
    val backgroundColor = if (isAvailable) availableColor else unavailableColor
    val text = if (isAvailable) "Available" else "Unavailable"

    Row(
        modifier = modifier
            .background(backgroundColor, RoundedCornerShape(12.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (isAvailable) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .background(Color.White, CircleShape)
            )
            Spacer(modifier = Modifier.width(4.dp))
        }
        Text(text, color = Color.White, fontSize = 10.sp)
    }
}
