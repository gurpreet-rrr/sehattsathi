package com.example.sehattsathi.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import androidx.navigation.compose.rememberNavController
import com.example.emergencysos.ui.EmergencySOSScreen
import com.example.medicalapp.screens.bottomnavbarhomescreen
import com.example.sehattsathi.repo.medicineStockInventory
import com.example.sehattsathi.screens.DoctorsScreen
import com.example.sehattsathi.screens.ProfileScreen
import com.example.sehattsathi.screens.appointmentScreen
import com.example.sehattsathi.screens.chatBot
import com.example.sehattsathi.screens.healthRecordScreen
import com.example.sehattsathi.screens.joinMeet
import com.example.sehattsathi.screens.languageScreen
import com.example.sehattsathi.screens.tabScreenSignUp
import com.example.sehattsathi.screens.userDataScreen
import com.example.sehattsathi.viewmodel.MyViewModel
import com.google.firebase.Firebase

@Composable

fun appNavigation(){

    val navController = rememberNavController()
    val sharedViewModel: MyViewModel = viewModel() // shared viewmodel shared by all the screens to fetch the data//Key Benefits

//    Data survives navigation → you don’t lose it when moving between screens.
//
//    No repeated Firebase calls → load once, use everywhere.
//
//    Easy state sharing → multiple screens stay in sync automatically.

    NavHost(navController = navController, startDestination = Routes.BottomNavbarHomeScreen





    ){

        composable<Routes.LanguageScreen> {
            languageScreen(navController)


        }

        composable<Routes.SignUpPage> {
            tabScreenSignUp(navController = navController, viewModel = MyViewModel())
            }


        composable<Routes.LoginPage> {

            }

        composable<Routes.UserDataPage> {

            userDataScreen(navController)





        }

        composable<Routes.BottomNavbarHomeScreen> {

            bottomnavbarhomescreen(navController)





        }


        composable<Routes.SosScreen> {

            EmergencySOSScreen(navController)




        }


        composable<Routes.MedicineInventory> {

            medicineStockInventory(viewModel = sharedViewModel, navController = navController)





        }

        composable<Routes.ChatBot> {

            chatBot()




        }


        composable<Routes.DoctorAvailability> {

            DoctorsScreen(navController)




        }



        composable<Routes.VideoMeeting> {

            joinMeet(navController)



        }


        composable<Routes.HealthRecords> {

            healthRecordScreen(navController)



        }


        composable<Routes.AppointmentScreenX> {

            appointmentScreen(navController)



        }

        composable<Routes.ProfileScreen> {

            ProfileScreen(navController)



        }










    }}




