package com.example.sehatsathi.ui


import androidx.compose.animation.*
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalHospital

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.sehattsathi.navigation.Routes


// ----------------------------- TOP APP BAR ---------------------------------
@Composable
fun CustomTopAppBar(
    modifier: Modifier = Modifier,
    logoUrl: String,
    onLanguageClick: () -> Unit,
    onNotificationClick: () -> Unit,
    navController: NavController
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(120.dp), // keep surface height fixed
        color = Color.White,
        shadowElevation = 8.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight().padding(top = 20.dp, start = 30.dp)
                    .weight(1f), // take available horizontal space
                contentAlignment = Alignment.CenterStart
            )
            // using box we achived higher height of logo bec we were unable to do because of surface height constraint{
            {
                AsyncImage(
                    model = logoUrl,
                    contentDescription = "App Logo",
                    modifier = Modifier
                        .fillMaxHeight() // fills parent's height (120.dp)
                        .aspectRatio(1f)
                        .graphicsLayer {
                            scaleX = 1.8f  // scale larger horizontally
                            scaleY = 1.8f  // scale larger vertically
                        },
                    contentScale = ContentScale.Fit
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                AsyncImage(
                    model = "https://i.postimg.cc/zDwQnKNG/profilephoto.jpg",
                    contentDescription = "Profile",
                    modifier = Modifier
                        .size(45.dp)
                        .clip(CircleShape)
                        .clickable {  navController.navigate(Routes.ProfileScreen)},
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(8.dp))
                AsyncImage(
                    model = "https://i.postimg.cc/8kdg4sWy/translation.png",
                    contentDescription = "Language",
                    modifier = Modifier
                        .size(45.dp)
                        .clip(CircleShape)
                        .clickable { onLanguageClick() },
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}


// ----------------------------- HOME SCREEN ---------------------------------
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun homeScreenUi(navController: NavController) {
    val scrollState = rememberScrollState()
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { visible = true }

    Scaffold(
        topBar = {
            CustomTopAppBar(
                logoUrl = "https://i.postimg.cc/QNHmjhBF/sehatsathioriginal-removebg-preview.png",
                onLanguageClick = { /* Switch language */ },
                onNotificationClick = { /* Profile/Notifications */ },
                navController = navController
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFF7F7F7))
                    .padding(padding)
                    .verticalScroll(scrollState)
                    .padding(16.dp)
            ) {
                AnimatedVisibility(
                    visible = visible,
                    enter = fadeIn() + slideInVertically(initialOffsetY = { 100 })
                ) {
                    Column {
                        // 👋 Welcome Section
                        Text("Welcome Back,", color = Color.Gray, fontSize = 20.sp)
                        Text(
                            "Rajesh Kumar",
                            fontWeight = FontWeight.Bold,
                            fontSize = 26.sp,
                            color =  Color(0xFF004AAD)
                        )

                        Spacer(modifier = Modifier.height(10.dp))
                        LocationCard(location = "Civil Hospital Nabha, Punjab")

                        Spacer(modifier = Modifier.height(16.dp))
                        ImpactDashboard()


                        Spacer(modifier = Modifier.height(16.dp))
                        AppointmentCard(navController)

                        Spacer(modifier = Modifier.height(16.dp))
                        ServiceGrid(navController = navController)

                        Spacer(modifier = Modifier.height(16.dp))
                        AnnouncementCard()

                        Spacer(modifier = Modifier.height(16.dp))
                        AskCareAIWidget(navController)



                        Spacer(modifier = Modifier.height(16.dp))
                        CommunityStoriesCarousel()

                        Spacer(modifier = Modifier.height(16.dp))
                        FeedbackButton(navController)

                        Spacer(modifier = Modifier.height(16.dp))
                        OfflineReminderBanner()

                        Spacer(modifier = Modifier.height(20.dp))
                        OurAimSection()
                    }
                }
            }
        }
    )
}

// ----------------------------- EXISTING COMPONENTS -------------------------
@Composable
fun LocationCard(location: String) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE6F0FF)),
        modifier = Modifier.fillMaxWidth(.7f)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(8.dp)) {
            Icon(
                painter = painterResource(id = android.R.drawable.ic_menu_mylocation),
                contentDescription = "Location",
                tint = Color(0xFF004AAD)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(location, color = Color(0xFF004AAD))
        }
    }
}

@Composable
fun AppointmentCard(navController: NavController) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE4EEFD)),
        onClick = { navController.navigate(Routes.AppointmentScreenX) }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                "You currently don't have an appointment scheduled.",
                fontSize = 14.sp,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                "Book an appointment today!",
                color = Color(0xFF004AAD),
                fontWeight = FontWeight.Medium
            )
        }
    }
}

// ----------------------------- SERVICE GRID --------------------------------
@Composable
fun ServiceGrid(navController: NavController) {
    Column {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
            ServiceCard(
                title = "DOCTOR",
                iconUrl = "https://i.postimg.cc/nV30nJtr/doctor.png",
                onClick = { navController.navigate(Routes.DoctorAvailability) },
                modifier = Modifier.weight(1f),
                iconClick = {navController.navigate(Routes.DoctorAvailability)}
            )
            ServiceCard(
                title = "RECORDS",
                iconUrl = "https://i.postimg.cc/vBRwqyDX/health-report.png",
                onClick = { navController.navigate(Routes.HealthRecords) },
                modifier = Modifier.weight(1f),
                iconClick = {navController.navigate(Routes.HealthRecords)}
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
            ServiceCard(
                title = "PHARMACY",
                iconUrl = "https://i.postimg.cc/KjZVw8bV/pharmacy.png",
                onClick = { navController.navigate(Routes.MedicineInventory) },
                modifier = Modifier.weight(1f),
                iconClick = {navController.navigate(Routes.MedicineInventory)}
            )
            ServiceCard(
                title = "EMERGENCY",
                iconUrl = "https://i.postimg.cc/76Nt3rzN/alarm.png",
                onClick = { navController.navigate(Routes.SosScreen) },
                modifier = Modifier.weight(1f),
                iconClick = {navController.navigate(Routes.SosScreen)}

            )
        }
    }
}

@Composable
fun ServiceCard(
    title: String,
    iconUrl: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    iconClick: (() -> Unit)? = null  // Make it a lambda
) {
    // icon click needs to be lambda function so that we can add it in clickable
    Card(
        modifier = modifier
            .padding(4.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE2ECFB)),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 10.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(16.dp)
            ) {
                AsyncImage(
                    model = iconUrl,
                    contentDescription = title,
                    modifier = Modifier
                        .size(45.dp)
                        .clip(CircleShape)
                        .clickable { iconClick?.invoke() }, // ✅ invoke lambda
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(title, fontWeight = FontWeight.Medium, color = Color(0xFF004AAD))
            }
        }
    }
}

// ----------------------------- ANNOUNCEMENT CARD --------------------------
@Composable
fun AnnouncementCard() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().padding(10.dp)
    ) {
        Icon(Icons.Default.Campaign, contentDescription = "Announcements", tint = Color(0xFF004AAD))
        Spacer(modifier = Modifier.width(8.dp))
        Text("Announcements", fontWeight = FontWeight.Bold, color = Color(0xFF004AAD))
    }
    Card(
        modifier = Modifier.fillMaxWidth().height(180.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE0EAF9)),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
    ) {
        AsyncImage(
            model = "https://i.postimg.cc/HxLc5vZx/annoucement1.jpg",
            contentDescription = "Remote Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}

// ----------------------------- NEW COMPONENTS -----------------------------


// Ask Care AI Widget
@Composable
fun AskCareAIWidget(navController: NavController) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable{navController.navigate(Routes.ChatBot) },
        colors = CardDefaults.cardColors(containerColor = Color(0xFF004AAD)),
        shape = RoundedCornerShape(12.dp)
    ) {
        Box(contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "🤖 Sehat Sathi AI ", color = Color.White, fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Text(
                    "Get instant symptom advice and diagnosis",
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    fontSize = 15.sp
                )
            }
        }
    }
}

// Impact Dashboard
@Composable
fun ImpactDashboard() {
    val animatedVillages by animateIntAsState(targetValue = 173, label = "")
    val animatedClinics by animateIntAsState(targetValue = 30, label = "")


    Card(
        modifier = Modifier
            .fillMaxWidth().height(200.dp)
            .padding(horizontal = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE7EEFB)),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Text(
                text = "Impact Dashboard",
                style = MaterialTheme.typography.titleMedium.copy(
                    color = Color(0xFF004AAD),
                    fontWeight = FontWeight.Bold
                )
            )

            Divider(
                color = Color(0xFF004AAD).copy(alpha = 0.2f),
                thickness = 1.dp,
                modifier = Modifier.padding(top = 4.dp, bottom = 8.dp)
            )

            ImpactRow(
                icon = Icons.Default.Home,
                label = "Villages Covered",
                value = animatedVillages.toString()
            )
            ImpactRow(
                icon = Icons.Default.LocalHospital,
                label = "Partner Clinics",
                value = animatedClinics.toString()
            )

        }
    }
}

@Composable
fun ImpactRow(icon: ImageVector, label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Surface(
                color = Color(0xFF004AAD).copy(alpha = 0.1f),
                shape = CircleShape,
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = label,
                    tint = Color(0xFF004AAD),
                    modifier = Modifier.padding(8.dp)
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = label,
                color = Color.Black.copy(alpha = 0.8f),
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium
            )
        }

        Text(
            text = value,
            color = Color(0xFF004AAD),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

// Community Stories Carousel
@Composable
fun CommunityStoriesCarousel() {
    val stories = listOf(
        "Earlier, I had to travel 40 km for my mother’s checkup. Now I consult online in minutes. – Gurmeet, Bhadson",
        "Medicine updates helped me save a trip to hospital. – Pooja, Mehas",
        "I got my records offline when network was gone! – Ramesh, Nabha",
        "मेडिकल सलाह अब घर बैठे मिलती है, बहुत सुविधाजनक! – अंजलि, पटियाला",
        "ਅੱਜ ਤੋਂ ਮੈ ਆਪਣੇ ਡਾਕਟਰ ਨਾਲ ਆਨਲਾਈਨ ਮਿਲ ਸਕਦਾ ਹਾਂ, ਬਹੁਤ ਵਧੀਆ ਸੇਵਾ। – ਗੁਰਪ੍ਰੀਤ, ਲੁਧਿਆਣਾ"
    )

    Column(modifier = Modifier.fillMaxWidth()) {
        // Header
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            Icon(
                Icons.Default.Campaign,
                contentDescription = "Community Stories",
                tint = Color(0xFF004AAD),
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                "Community Stories",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color(0xFF004AAD)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        LazyRow(
            contentPadding = PaddingValues(horizontal = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(stories.size) { index ->
                StoryCard(story = stories[index])
            }
        }
    }
}

@Composable
fun StoryCard(story: String) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color(0xFFEAF1FD)),
        shape = RoundedCornerShape(14.dp),
        modifier = Modifier
            .width(280.dp)
            .heightIn(min = 100.dp)
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = story,
                color = Color(0xFF004AAD),
                fontSize = 14.sp,
                lineHeight = 18.sp
            )
        }
    }
}

// Feedback Button
@Composable
fun FeedbackButton(navController: NavController) {
    Button(
        onClick = {  },
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF004AAD)),
        modifier = Modifier.fillMaxWidth().height(50.dp),
        shape = RoundedCornerShape(10.dp)
    ) {
        Text("💬 Report Issue / Feedback", color = Color.White, fontSize = 15.sp)
    }
}

// Offline Reminder
@Composable
fun OfflineReminderBanner() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFD1E6FF)),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text("📶 App works offline!", fontWeight = FontWeight.Bold, color = Color(0xFF004AAD))
            Text("Your health records stay safe even without internet.", color = Color(0xFF004AAD), fontSize = 13.sp)
        }
    }
}

// Our Aim
@Composable
fun OurAimSection() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF004AAD))
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(16.dp)) {
            AsyncImage(
                model = "https://i.postimg.cc/QNHmjhBF/sehatsathioriginal-removebg-preview.png",
                contentDescription = "Our Aim",
                modifier = Modifier.height(160.dp).fillMaxWidth().clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                "Our vision is to help mankind live healthier, longer lives by making sustainable tech with innovative solutions.",
                color = Color.White,
                fontSize = 14.sp,
                lineHeight = 20.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text("Innovation that Heals❤️ - Team Cipher", color = Color.White, fontSize = 13.sp)
        }


    }

    Spacer(modifier = Modifier.height(35.dp))
}
