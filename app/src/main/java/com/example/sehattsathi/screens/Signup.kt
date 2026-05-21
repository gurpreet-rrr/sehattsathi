package com.example.sehattsathi.screens



import android.R.attr.name
import android.R.attr.password
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.runtime.livedata.observeAsState

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons

import androidx.compose.material.icons.outlined.Email

import androidx.compose.material.icons.outlined.Lock

import androidx.compose.material.icons.outlined.Person

import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.sehattsathi.navigation.Routes
import com.example.sehattsathi.ui.theme.LeagueSpartan
import com.example.sehattsathi.viewmodel.AuthState
import com.example.sehattsathi.viewmodel.MyViewModel


@Composable
fun signupScreenAdmin(viewModel : MyViewModel, navController: NavController){
    val authState = viewModel.authState.observeAsState()
    var userInitiated by remember { mutableStateOf(false) }



    Box(modifier = Modifier.fillMaxSize().background(Color(0xFFF7F4FD))) {

        val context = LocalContext.current
//    val state = viewModel.loginUser.collectAsState()

        var email by remember { mutableStateOf("") }
        var name by remember { mutableStateOf("") }


        LaunchedEffect(authState.value) {
            if (userInitiated && authState.value is AuthState.Authenticated) {
                navController.navigate(Routes.UserDataPage)
            }
        }
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
                model = "https://i.postimg.cc/QNHmjhBF/sehatsathioriginal-removebg-preview.png",
                contentDescription = "Remote Image",
                modifier = Modifier.size(270.dp),
                alignment = Alignment.Center
            )

            Spacer(modifier = Modifier.height(10.dp))



            Spacer(modifier = Modifier.height(2.dp))



            Spacer(modifier = Modifier.height(50.dp))

            Text(
                text = "SIGN UP",
                fontFamily = LeagueSpartan,
                fontWeight = FontWeight.Bold, // OR FontWeight.Black
                fontSize = 34.sp,

                color = Color(0xFF0084C5)
            )


            Spacer(modifier = Modifier.height(20.dp))


            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                modifier = Modifier.fillMaxWidth(0.9f),
                leadingIcon = {
                    Icon(Icons.Outlined.Person, contentDescription = "Person")
                },
                label = { Text("Name",   fontFamily = LeagueSpartan,
                 ) },
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


            var passwordVisible by remember { mutableStateOf(false) }
            OutlinedTextField(


                value = email,
                onValueChange = { email = it },
                modifier = Modifier.fillMaxWidth(0.9f),
                label = { Text("Password", fontFamily = LeagueSpartan) },
                singleLine = true,

                // 👇 Toggle plain text vs hidden
                visualTransformation = if (passwordVisible)
                    VisualTransformation.None
                else
                    PasswordVisualTransformation(),
                leadingIcon = {
                    Icon(Icons.Outlined.Lock, contentDescription = "Password")
                },

                trailingIcon = {
                    val image = if (passwordVisible) Icons.Outlined.VisibilityOff
                    else Icons.Outlined.Visibility
                    val description = if (passwordVisible) "Hide password" else "Show password"

                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = image, contentDescription = description)
                    }
                },
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





                ))



















            Spacer(modifier = Modifier.height(20.dp))

            Row {
                Text(
                    text = "Don't have an account?",
                    fontFamily = LeagueSpartan,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.clickable {
//                navController.navigate(Routes.SignUp)
                    }

                )


                Text(
                    text = " Sign up now",
                    color = Color(0xFF005A87),
                    fontFamily = LeagueSpartan,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable {
//                navController.navigate(Routes.SignUp)
                    }
                )

            }

            Spacer(modifier = Modifier.height(30.dp))


            Button(
                onClick = {
                    userInitiated = true
                    viewModel.signUp(name, email){
                        // onResult function, If the last parameter of a function is a lambda,
//                        you can move it outside the parentheses. //
                            success->

                        if (success) {
                            Toast.makeText(context, "Successfully Signup", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, "Failed to save data", Toast.LENGTH_SHORT).show()
                        }
                    }
//                viewModel.loginUser(email, password)
                },
                modifier = Modifier.fillMaxWidth(0.6f),
                shape = ButtonDefaults.elevatedShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor =   Color(0xFF005A87),
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp)
            ) {
                Text(text = "Continue", fontSize = 20.sp,   fontFamily = LeagueSpartan,
                   )
            }

            Spacer(modifier = Modifier.height(10.dp))






        }


    }


}

@Composable
fun signupScreenPatient(navController: NavController, viewModel: MyViewModel){



    Box(modifier = Modifier.fillMaxSize().background(Color(0xFFF7F4FD))) {

        val context = LocalContext.current
//    val state = viewModel.loginUser.collectAsState()

        val authState = viewModel.authState.observeAsState()
        var userInitiated by remember { mutableStateOf(false) }

        var email  by remember { mutableStateOf("") }
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


        LaunchedEffect(authState.value) {
            if (userInitiated && authState.value is AuthState.Authenticated) {
                navController.navigate(Routes.UserDataPage)
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp).verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = "https://i.postimg.cc/QNHmjhBF/sehatsathioriginal-removebg-preview.png",
                contentDescription = "Remote Image",
                modifier = Modifier.size(270.dp),
                alignment = Alignment.Center
            )

            Spacer(modifier = Modifier.height(10.dp))



            Spacer(modifier = Modifier.height(2.dp))



            Spacer(modifier = Modifier.height(50.dp))

            Text(
                text = "SIGN UP",
                fontFamily = LeagueSpartan,
                fontWeight = FontWeight.Bold, // OR FontWeight.Black
                fontSize = 34.sp,

                color = Color(0xFF0084C5)
            )


            Spacer(modifier = Modifier.height(20.dp))


            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                modifier = Modifier.fillMaxWidth(0.9f),
                leadingIcon = {
                    Icon(Icons.Outlined.Email, contentDescription = "Person")
                },
                label = { Text("Email",   fontFamily = LeagueSpartan,
                 ) },
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


            var passwordVisible by remember { mutableStateOf(false) }
            OutlinedTextField(


                value = password,
                onValueChange = { password = it },
                modifier = Modifier.fillMaxWidth(0.9f),
                label = { Text("Password", fontFamily = LeagueSpartan) },
                singleLine = true,

                // 👇 Toggle plain text vs hidden
                visualTransformation = if (passwordVisible)
                    VisualTransformation.None
                else
                    PasswordVisualTransformation(),
                leadingIcon = {
                    Icon(Icons.Outlined.Lock, contentDescription = "Password")
                },

                trailingIcon = {
                    val image = if (passwordVisible) Icons.Outlined.VisibilityOff
                    else Icons.Outlined.Visibility
                    val description = if (passwordVisible) "Hide password" else "Show password"

                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = image, contentDescription = description)
                    }
                },
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





                ))



















            Spacer(modifier = Modifier.height(20.dp))

            Row {
                Text(
                    text = "Don't have an account?",
                    fontFamily = LeagueSpartan,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.clickable {
//                navController.navigate(Routes.SignUp)
                    }

                )


                Text(
                    text = " Sign up now",
                    color = Color(0xFF005986),
                    fontFamily = LeagueSpartan,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable {
//                navController.navigate(Routes.SignUp)
                    }
                )

            }

            Spacer(modifier = Modifier.height(30.dp))


            Button(
                onClick = {
                    userInitiated = true
                    viewModel.signUp(email,password ){
                        // onResult function, If the last parameter of a function is a lambda,
//                        you can move it outside the parentheses. //
                            success->

                        if (success) {
                            Toast.makeText(context, "Successfully Signed Up", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, "Failed to save data", Toast.LENGTH_SHORT).show()
                        }
                    }

//                viewModel.loginUser(email, password)
                },
                modifier = Modifier.fillMaxWidth(0.6f),
                shape = ButtonDefaults.elevatedShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor =   Color(0xFF005A87),
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp)
            ) {
                Text(text = "Continue", fontSize = 20.sp,   fontFamily = LeagueSpartan,
                )
            }

            Spacer(modifier = Modifier.height(10.dp))






        }


    }
    }




@Composable
fun signupScreenChemist(){



    Box(modifier = Modifier.fillMaxSize().background(Color(0xFFF7F4FD))) {

        val context = LocalContext.current
//    val state = viewModel.loginUser.collectAsState()

        var email by remember { mutableStateOf("") }
        var name by remember { mutableStateOf("") }
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
                model = "https://i.postimg.cc/QNHmjhBF/sehatsathioriginal-removebg-preview.png",
                contentDescription = "Remote Image",
                modifier = Modifier.size(270.dp),
                alignment = Alignment.Center
            )

            Spacer(modifier = Modifier.height(10.dp))



            Spacer(modifier = Modifier.height(2.dp))



            Spacer(modifier = Modifier.height(50.dp))

            Text(
                text = "SIGN UP",
                fontFamily = LeagueSpartan,
                fontWeight = FontWeight.Bold, // OR FontWeight.Black
                fontSize = 34.sp,

                color = Color(0xFF0084C5)
            )


            Spacer(modifier = Modifier.height(20.dp))


            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                modifier = Modifier.fillMaxWidth(0.9f),
                leadingIcon = {
                    Icon(Icons.Outlined.Person, contentDescription = "Person")
                },
                label = { Text("Name",   fontFamily = LeagueSpartan,
                    ) },
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


            var passwordVisible by remember { mutableStateOf(false) }
            OutlinedTextField(


                value = email,
                onValueChange = { email = it },
                modifier = Modifier.fillMaxWidth(0.9f),
                label = { Text("Password", fontFamily = LeagueSpartan) },
                singleLine = true,

                // 👇 Toggle plain text vs hidden
                visualTransformation = if (passwordVisible)
                    VisualTransformation.None
                else
                    PasswordVisualTransformation(),
                leadingIcon = {
                    Icon(Icons.Outlined.Lock, contentDescription = "Password")
                },

                trailingIcon = {
                    val image = if (passwordVisible) Icons.Outlined.VisibilityOff
                    else Icons.Outlined.Visibility
                    val description = if (passwordVisible) "Hide password" else "Show password"

                    IconButton(onClick = { passwordVisible = !passwordVisible }) {// this makes passvisible on off
                        Icon(imageVector = image, contentDescription = description)
                    }
                },
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





                ))



















            Spacer(modifier = Modifier.height(20.dp))

            Row {
                Text(
                    text = "Don't have an account?",
                    fontFamily = LeagueSpartan,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.clickable {
//                navController.navigate(Routes.SignUp)
                    }

                )


                Text(
                    text = " Sign up now",
                    color = Color(0xFF005986),
                    fontFamily = LeagueSpartan,
                    fontWeight = FontWeight.Bold,
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
                    containerColor =   Color(0xFF005A87),
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp)
            ) {
                Text(text = "Continue", fontSize = 20.sp,   fontFamily = LeagueSpartan,
                )
            }

            Spacer(modifier = Modifier.height(10.dp))






        }


    }


}







