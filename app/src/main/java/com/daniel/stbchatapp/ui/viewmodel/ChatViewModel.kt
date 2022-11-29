package com.daniel.stbchatapp.ui.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.daniel.stbchatapp.model.FastChat
import com.daniel.stbchatapp.ui.UiState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class ChatViewModel : ViewModel() {
    private val database = Firebase.database

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var databaseRef: DatabaseReference = database.getReference("Chat")

    private val _uiState: MutableLiveData<UiState> = MutableLiveData(UiState.Loading)
    val uiState: LiveData<UiState> get() = _uiState

    fun getFirebaseDb() {
        databaseRef.get().addOnCompleteListener {
            val value = it.result.getValue<HashMap<String, FastChat>>()
            value!!.values.let { collection ->
                val sortedList = collection.sortedBy { fastChat ->
                    fastChat.timeMillis
                }.toList()
                _uiState.value = UiState.Success(sortedList)
            }
        }.addOnFailureListener {
            _uiState.value = UiState.Error(it)
        }
    }

    fun auth(toastInvoker: (String) -> Unit) {
        auth.signInAnonymously()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    getFirebaseDb()
                } else {
                    toastInvoker.invoke("AUTHENTICATION WAS NOT SUCCESSFUL")
                }
            }
    }

    fun writeMessage(message: String, userName: String?) {
        val refMillis = System.currentTimeMillis()
        val myRef = database.getReference("Chat/${refMillis}")
        myRef.setValue(FastChat(userName ?: "default", message, refMillis))
        myRef.get()
        databaseRef.addValueEventListener(FirebaseListener())
    }

    inner class FirebaseListener() : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            val value = snapshot.getValue<HashMap<String, FastChat>>()
            value!!.values.let { collection ->
                val sortedList = collection.sortedBy { fastChat ->
                    fastChat.timeMillis
                }.toList()
                _uiState.value = UiState.Success(sortedList)
            }
        }
        override fun onCancelled(error: DatabaseError) {
            _uiState.value = UiState.Error(error.toException())
        }
    }
}