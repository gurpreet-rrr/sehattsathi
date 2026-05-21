package com.example.sehattsathi.screens

import android.R.attr.label
import android.R.attr.singleLine
import android.R.attr.textColor
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.RemoveRedEye
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.sehattsathi.ui.theme.LeagueSpartan

@Composable
fun loginScreen(){



    Box(modifier = Modifier.fillMaxSize().background(Color(0xFFF7F4FD))) {

        val context = LocalContext.current
//    val state = viewModel.loginUser.collectAsState()

        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
//
//    when {
//        state.value.isLoading -> {
//            Box(
//                modifier = Modifier.fillMaxSize(),
//                contentAlignment = Alignment.Center
//            ) {
//                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
//            }
//        }
//
//        state.value.error != null -> {
//            Toast.makeText(context, "Incorrect email or password", Toast.LENGTH_SHORT).show()
//        }
//
//        state.value.data != null -> {
//            Toast.makeText(context, "Successfully Logged In", Toast.LENGTH_SHORT).show()
//            navController.navigate(Routes.WaitingScreen)
//        }
//    }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp).verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = "https://i.postimg.cc/xTqGKvps/sehatsathi.png",
                contentDescription = "Remote Image",
                modifier = Modifier.size(250.dp),
                alignment = Alignment.Center
            )

            Spacer(modifier = Modifier.height(10.dp))


            Text(
                text = "SEHAT SATHI",
                fontFamily = LeagueSpartan,
                fontWeight = FontWeight.Bold, // OR FontWeight.Black
                fontSize = 34.sp,

                color = Color(0xFF868686)
//                color = Color(0xFF2C2640)
            )

            Text(
                text = "Sehat Apke Ghar Tak",
                fontFamily = LeagueSpartan,
                fontSize = 20.sp,



                color = Color(0xFF868686)
            )

            Spacer(modifier = Modifier.height(2.dp))

            Box(
                modifier = Modifier.fillMaxWidth(.5f).height(1.5.dp).background(Color(0xFFF9A933)),
                contentAlignment = Alignment.Center
            ) {

            }

            Spacer(modifier = Modifier.height(50.dp))

            Text(
                text = "LOGIN",
                fontFamily = LeagueSpartan,
                fontWeight = FontWeight.Bold, // OR FontWeight.Black
                fontSize = 34.sp,

                color = Color(0xFF2C2640)
            )


            Spacer(modifier = Modifier.height(20.dp))


            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                modifier = Modifier.fillMaxWidth(0.9f),
                leadingIcon = {
                    Icon(Icons.Outlined.Email, contentDescription = "Person")
                },
                label = { Text("Email", color = MaterialTheme.colorScheme.onSurface) },
                singleLine = true,
                shape = RoundedCornerShape(10.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    focusedTextColor = MaterialTheme.colorScheme.onSurface,          // actual input
                    unfocusedTextColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                    cursorColor = MaterialTheme.colorScheme.primary,
                    focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),

                    // ✅ placeholder + label
                    focusedPlaceholderColor = MaterialTheme.colorScheme.onSurface,
                    unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                    focusedLabelColor = MaterialTheme.colorScheme.onSurface,
                    unfocusedLabelColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)

                )
            )

            Spacer(modifier = Modifier.height(25.dp))

            OutlinedTextField(
                value = password,
                modifier = Modifier.fillMaxWidth(0.9f),
                onValueChange = { password = it },
                leadingIcon = {
                    Icon(Icons.Default.LockOpen, contentDescription = "Password")
                },
                label = { Text("Password", color = MaterialTheme.colorScheme.onSurface) },
                singleLine = true,
                shape = RoundedCornerShape(10.dp),
                trailingIcon = {
                    Icon(Icons.Outlined.RemoveRedEye, contentDescription = "Password")

                },
                visualTransformation = PasswordVisualTransformation(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    focusedTextColor = MaterialTheme.colorScheme.onSurface,          // actual input
                    unfocusedTextColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                    cursorColor = MaterialTheme.colorScheme.primary,
                    focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),

                    // ✅ placeholder + label
                    focusedPlaceholderColor = MaterialTheme.colorScheme.onSurface,
                    unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                    focusedLabelColor = MaterialTheme.colorScheme.onSurface,
                    unfocusedLabelColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)

                )
            )

            Spacer(modifier = Modifier.height(20.dp))

            Row {
                Text(
                    text = "Don't have an account?",
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.clickable {
//                navController.navigate(Routes.SignUp)
                    }

                )


                Text(
                    text = " Sign up now",
                    color = Color(0xFF08AA94),
                    modifier = Modifier.clickable {
//                navController.navigate(Routes.SignUp)
                    }
                )

            }

                Spacer(modifier = Modifier.height(30.dp))


                Button(
                onClick = {
//                viewModel.loginUser(email, password)
                },
                modifier = Modifier.fillMaxWidth(0.6f),
                shape = ButtonDefaults.elevatedShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor =   Color(0xFF2C2640),
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp)
            ) {
                Text(text = "Login", fontSize = 20.sp)
            }

            Spacer(modifier = Modifier.height(10.dp))






        }


    }


}