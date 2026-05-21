package com.example.sehattsathi.screens

import android.content.Intent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.sehattsathi.ConferenceActivity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun joinMeet(navController: NavController) {

    val context = LocalContext.current
    var meetingID by remember { mutableStateOf("") }
    var userName by remember { mutableStateOf("") }

    val isJoinEnabled = meetingID.isNotBlank() && userName.isNotBlank()
    val isCreateEnabled = userName.isNotBlank()

    // Colors
    val primaryBlue = Color(0xFF004AAD)
    val lightBlue = Color(0xFFE8F0FE)
    val animatedJoinScale by animateFloatAsState(if (isJoinEnabled) 1f else 0.96f)
    val animatedCreateScale by animateFloatAsState(if (isCreateEnabled) 1f else 0.96f)

    val joinButtonColor by animateColorAsState(
        if (isJoinEnabled) primaryBlue else Color.Gray.copy(alpha = 0.4f)
    )
    val createButtonColor by animateColorAsState(
        if (isCreateEnabled) Color(0xFF0077FF) else Color.Gray.copy(alpha = 0.4f)
    )

    // 🔥 Entry animation states
    var visible by remember { mutableStateOf(false) }

    // Trigger animation once when composable enters composition
    LaunchedEffect(Unit) {
        visible = true
    }

    // AnimatedVisibility adds smooth fade + slide-in effect
    AnimatedVisibility(
        visible = visible,
        enter = androidx.compose.animation.fadeIn() + androidx.compose.animation.slideInVertically(
            initialOffsetY = { it / 4 } // starts from bottom a bit
        ),
        exit = androidx.compose.animation.fadeOut()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = "https://cdni.iconscout.com/illustration/premium/thumb/doctor-giving-prescription-illustration-download-in-svg-png-gif-file-formats--medical-recommendation-medication-guidance-pack-healthcare-illustrations-9626376.png",
                contentDescription = "Remote Image",
                modifier = Modifier.size(250.dp),
                alignment = Alignment.Center
            )

            Text(
                text = "Join or Create Meeting",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = primaryBlue,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(30.dp))

            OutlinedTextField(
                value = meetingID,
                onValueChange = { meetingID = it },
                label = { Text("Meeting ID") },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(lightBlue.copy(alpha = 0.1f), RoundedCornerShape(12.dp))
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = userName,
                onValueChange = { userName = it },
                label = { Text("Your Name") },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(lightBlue.copy(alpha = 0.1f), RoundedCornerShape(12.dp))
            )

            Spacer(modifier = Modifier.height(30.dp))

            Button(
                onClick = {
                    val intent = Intent(context, ConferenceActivity::class.java)
                    intent.putExtra("MEETING_ID", meetingID)
                    intent.putExtra("USER_NAME", userName)
                    context.startActivity(intent)
                },
                enabled = isJoinEnabled,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .scale(animatedJoinScale),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = joinButtonColor)
            ) {
                Text("Join Meeting", fontSize = 18.sp, color = Color.White)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val randomMeetingID = (1..10)
                        .map { (0..9).random() }
                        .joinToString(separator = "")

                    val intent = Intent(context, ConferenceActivity::class.java)
                    intent.putExtra("MEETING_ID", randomMeetingID)
                    intent.putExtra("USER_NAME", userName)
                    context.startActivity(intent)
                },
                enabled = isCreateEnabled,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .scale(animatedCreateScale),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = createButtonColor)
            ) {
                Text("Create Meeting", fontSize = 18.sp, color = Color.White)
            }

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = "Make sure you enter a valid meeting ID or create your own.",
                fontSize = 14.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )
        }
    }
}
