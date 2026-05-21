package com.example.sehattsathi.repo

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.sehattsathi.navigation.Routes
import com.example.sehattsathi.ui.theme.LeagueSpartan
import com.example.sehattsathi.viewmodel.MyViewModel

private object AppColors {
    val background = Color(0xFFF4F5F9)
    val primary = Color(0xFF0184C4)
    val textSecondary = Color.Black
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun medicineStockInventory(
    viewModel: MyViewModel,
    navController: NavController
) {
    val state = viewModel.getAllMedicines.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.getAllMedicines()
        isVisible = true
    }

    Scaffold(containerColor = AppColors.background) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(16.dp))

            Row(

                modifier = Modifier.fillMaxWidth(),

            ) {
                IconButton(
                    onClick = {navController.navigate(Routes.BottomNavbarHomeScreen)}
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "",
                        tint = Color(0xFF005885),
                        modifier = Modifier.size(30.dp)
                    )
                }
                    Text(
                    text = "Medicine Inventory",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = AppColors.primary,
                    modifier = Modifier.padding(bottom = 16.dp, top = 10.dp,start=10.dp),
                        textAlign = TextAlign.Center
                )
            }

            // Search bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Search by name or category") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = AppColors.primary,
                    unfocusedBorderColor = Color.Gray,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            AnimatedVisibility(visible = isVisible) {
                when {
                    state.value.isLoading -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }

                    state.value.error != null -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = state.value.error ?: "Something went wrong")
                        }
                    }

                    state.value.data.isEmpty() -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "No medicines available")
                        }
                    }

                    else -> {
                        // Filtered list based on search and category or diseases when added
                        val filteredList = state.value.data.filter { med ->
                            med.medName.contains(searchQuery, ignoreCase = true) ||
                                    med.category.contains(searchQuery, ignoreCase = true)
                        }

                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            modifier = Modifier.fillMaxHeight(),
                            contentPadding = PaddingValues(8.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(filteredList) { medicine ->
                                medicineCard(
                                    medName = medicine.medName,
                                    price = medicine.price,
                                    availability = medicine.availability,
                                    expiryDate = medicine.expiryDate,
                                    seller = medicine.seller,
                                    imageUrl = medicine.imageUrl,
                                    navController = navController
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun medicineCard(
    medName: String,
    price: String,
    availability: String,
    expiryDate: String,
    seller: String,
    imageUrl: String,
    navController: NavController
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(280.dp) // fixed height for uniformity
            .clickable { /* Navigate or action */ },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxHeight() // make column fill card height
        ) {
            AsyncImage(
                model = imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(RoundedCornerShape(12.dp))
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = medName,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                maxLines = 2, // limit long names
                overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "By $seller",
                maxLines = 1,
                overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "Availability: $availability",
                maxLines = 1,
                overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "Expiry: $expiryDate",
                maxLines = 1,
                overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis//If the text is longer than the space allows, it truncates the overflow and adds … (th
            )

            Spacer(modifier = Modifier.weight(1f)) // push content to top
        }
    }
}

