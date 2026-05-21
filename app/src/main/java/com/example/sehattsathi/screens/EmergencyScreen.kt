package com.example.emergencysos.ui

import android.R.attr.contentDescription
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.sehattsathi.navigation.Routes

@Composable
fun EmergencySOSScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F6FA))
    ) {
        // 🔹 Top Bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF0077B6))
                .padding(top = 44.dp, bottom = 16.dp, start = 18.dp, end = 10.dp)
        ) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {

                    IconButton(
                        onClick = {navController.navigate(Routes.BottomNavbarHomeScreen)}
                    ) {
                      Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "", tint = Color.White)


                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Emergency SOS",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Text(
                    text = "Rural Health Emergency Services\nआपातकालीन स्वास्थ्य सेवाएँ",
                    color = Color.White.copy(alpha = 0.9f),
                    fontSize = 14.sp,
                    modifier = Modifier.padding(top = 6.dp)
                )

                // 🔸 Emergency Call Box
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFF5C5C)),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                Icons.Default.Call,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(
                                    text = "Emergency Helpline: 108",
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp
                                )
                                Text(
                                    text = "24/7 Civil Hospital Connect",
                                    color = Color.White.copy(alpha = 0.9f),
                                    fontSize = 13.sp
                                )
                            }
                        }
                        val context = LocalContext.current
                        val phoneNumber = "9876543210" // Replace with your number

                        Button(
                            onClick = {
                                val intent = Intent(Intent.ACTION_DIAL)
                                intent.data = Uri.parse("tel:$phoneNumber")
                                context.startActivity(intent)
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White
                            ),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                text = "Call Now",
                                color = Color(0xFFFF5C5C),
                                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 🔹 Common Emergencies Section
        Text(
            text = "Common Rural Emergencies",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // 🔸making content data for the emergency card function and lazy column list
        val emergencies = listOf(
            CardData(
                "https://i.postimg.cc/HxLc5vZx/annoucement1.jpg",
                "Heart Attack",
                "दिल का दौरा"
            ),
            CardData(
                "https://i.postimg.cc/HxLc5vZx/annoucement1.jpg",
                "Snake Bite",
                "साँप के काटने की स्थिति"
            ),
            CardData(
                "https://i.postimg.cc/HxLc5vZx/annoucement1.jpg",
                "Heat Stroke",
                "लू लगना"
            ),
            CardData(
                "https://i.postimg.cc/HxLc5vZx/annoucement1.jpg",
                "Heart Attack",
                "दिल का दौरा"
            ),
            CardData(
                "https://i.postimg.cc/HxLc5vZx/annoucement1.jpg",
                "Snake Bite",
                "साँप के काटने की स्थिति"
            ),
            CardData(
                "https://i.postimg.cc/HxLc5vZx/annoucement1.jpg",
                "Heat Stroke",
                "लू लगना"
            ),    CardData(
                "https://i.postimg.cc/HxLc5vZx/annoucement1.jpg",
                "Heart Attack",
                "दिल का दौरा"
            ),
            CardData(
                "https://i.postimg.cc/HxLc5vZx/annoucement1.jpg",
                "Snake Bite",
                "साँप के काटने की स्थिति"
            ),
            CardData(
                "https://i.postimg.cc/HxLc5vZx/annoucement1.jpg",
                "Heat Stroke",
                "लू लगना"
            )
        )

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .background(Color(0xFFF5F6FA))
        ) {
            items(emergencies) { (image, title, subtitle) ->
                EmergencyCard(
                    imageUrl = image,
                    title = title,
                    subtitle = subtitle
                )
            }
            // here emergencies is the list that we made out of data class CardData
        }

        Spacer(modifier = Modifier.height(12.dp))

        // 🔹 Bottom Button
        Button(
            onClick = { /* Navigate to specialists */ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .height(52.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0077B6))
        ) {
            Icon(
                Icons.Default.LocalHospital,
                contentDescription = null,
                tint = Color.White
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Find Civil Hospital Specialists",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun EmergencyCard(
    imageUrl: String,
    title: String,
    subtitle: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp).clickable{},
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column {
            AsyncImage(
                model = imageUrl,
                contentDescription = title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            )

            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Text(
                    text = subtitle,
                    fontSize = 14.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 4.dp)
                )

                Row(
                    modifier = Modifier.padding(top = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text("🏥 Civil Hospital Ready", color = Color(0xFF0077B6), fontSize = 13.sp)
                    Text("📘 Quick Guide", color = Color(0xFF0077B6), fontSize = 13.sp)
                }
            }
        }
    }
}


data class CardData(

   val  model: String,
    val title: String,
    val subtitle: String


)