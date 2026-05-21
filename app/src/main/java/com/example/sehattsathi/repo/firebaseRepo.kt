package com.example.sehattsathi.repo

import com.example.sehattsathi.screens.UserData

import com.google.firebase.database.FirebaseDatabase

object firebaseRepo {


    private  val db = FirebaseDatabase.getInstance().getReference("Users"

    )



    fun adduserData(userData: UserData, onResult: (Boolean) -> Unit) {
        val id = db.push().key!!
        db.child(id).setValue(userData.copy(id = id))
            .addOnSuccessListener {
                // ✅ Data successfully written
                onResult(true)
            }
            .addOnFailureListener {
                // ❌ Something went wrong
                onResult(false)
            }
    }

}