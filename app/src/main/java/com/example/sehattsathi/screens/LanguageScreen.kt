package com.example.sehattsathi.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.sehattsathi.navigation.Routes
import com.example.sehattsathi.ui.theme.LeagueSpartan

@Composable
fun languageScreen(navController: NavController){

    Column(
        modifier = Modifier.fillMaxSize().padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,

    ){


        AsyncImage(
            model = "https://i.postimg.cc/QNHmjhBF/sehatsathioriginal-removebg-preview.png",
            contentDescription = "Remote Image",
            modifier = Modifier.size(270.dp),
            alignment = Alignment.Center
        )

        Text(text = " SELECT LANGUAGE " ,
            fontFamily = LeagueSpartan,
            fontSize = 35.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF005A87)
           )


        Spacer(modifier = Modifier.height(90.dp))
        Button(
            onClick = {
                navController.navigate(Routes.SignUpPage)


            },
            modifier = Modifier.fillMaxWidth(0.6f).size(60.dp),
            shape = ButtonDefaults.elevatedShape,
            colors = ButtonDefaults.buttonColors(
                containerColor =   Color(0xFF0185C3),
                contentColor = MaterialTheme.colorScheme.onPrimary
            ),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp)

        ){
            Text(text = "ENGLISH ",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold)

        }


        Spacer(modifier = Modifier.height(50.dp))
        Button(
            onClick = {
                navController.navigate(Routes.SignUpPage)

            },
            modifier = Modifier.fillMaxWidth(0.6f).size(60.dp),
            shape = ButtonDefaults.elevatedShape,
            colors = ButtonDefaults.buttonColors(
                containerColor =   Color(0xFF0185C3),
                contentColor = MaterialTheme.colorScheme.onPrimary
            ),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp)

        ){
            Text(text = "हिंदी",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold)

        }




        Spacer(modifier = Modifier.height(50.dp))
        Button(
            onClick = {
                navController.navigate(Routes.SignUpPage)

            },
            modifier = Modifier.fillMaxWidth(0.6f).size(60.dp),
            shape = ButtonDefaults.elevatedShape,
            colors = ButtonDefaults.buttonColors(
                containerColor =   Color(0xFF0185C3),
                contentColor = MaterialTheme.colorScheme.onPrimary
            ),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp)

        ){
            Text(text = "पंजाबी",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold)

        }




    }




}