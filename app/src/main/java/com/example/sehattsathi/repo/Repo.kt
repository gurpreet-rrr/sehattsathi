package com.example.sehattsathi.repo

import android.util.Log
import com.example.sehattsathi.common.MEDICNE_REALTIMEDATABASE
import com.example.sehattsathi.common.ResultState
import com.example.sehattsathi.models.MedicineModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import kotlin.jvm.java

class repo (val firebaseDatabase: FirebaseDatabase){

    fun getAllMedicines(): Flow<ResultState<List<MedicineModel>>> = callbackFlow {

        trySend(ResultState.Loading)

        // understand this again
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val medicines = mutableListOf<MedicineModel>()

                for (snapshot in dataSnapshot.children) {
                    val medicine = snapshot.getValue(MedicineModel::class.java)
                    medicine?.let {
                        medicines.add(it)
                        Log.d("Firebase", "Fetched book: $medicine")
                    }
                }

                trySend(ResultState.Success(medicines))
            }

            override fun onCancelled(error: DatabaseError) {
                trySend(ResultState.Error(error.message))
            }
        }

        firebaseDatabase.reference.child(MEDICNE_REALTIMEDATABASE)
            .addValueEventListener(postListener)
// avoids crashing
        awaitClose {
            firebaseDatabase.reference.child(MEDICNE_REALTIMEDATABASE)
                .removeEventListener(postListener)
        }
    }}