package com.example.sehattsathi.navigation

import kotlinx.serialization.Serializable

sealed class Routes(){

    @Serializable
    object LanguageScreen : Routes()


    @Serializable
    object SignUpPage : Routes()


    @Serializable
    object LoginPage : Routes()

    @Serializable
    object UserDataPage  : Routes()

    @Serializable
    object HomePage : Routes()

    @Serializable
    object BottomNavbarHomeScreen : Routes()

    @Serializable
    object SosScreen : Routes()

    @Serializable
    object MedicineInventory : Routes()

    @Serializable
    object ChatBot : Routes()

    @Serializable
    object DoctorAvailability : Routes()

    @Serializable
    object  VideoMeeting : Routes()

    @Serializable
    object  HealthRecords : Routes()

    @Serializable
    object  AppointmentScreenX : Routes()


    @Serializable
    object ProfileScreen : Routes()






}