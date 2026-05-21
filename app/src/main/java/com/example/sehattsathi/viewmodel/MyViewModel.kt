    package com.example.sehattsathi.viewmodel

    import android.R.attr.content
    import android.R.attr.password
    import android.R.attr.text
    import android.graphics.Bitmap
    import android.graphics.BitmapFactory
    import android.graphics.Insets.add

    import androidx.lifecycle.LiveData
    import androidx.lifecycle.MutableLiveData
    import androidx.lifecycle.ViewModel
    import androidx.lifecycle.viewModelScope
    import com.example.sehattsathi.common.ResultState
    import com.example.sehattsathi.models.MedicineModel
    import com.example.sehattsathi.repo.repo
    import com.google.firebase.Firebase
    import com.google.firebase.ai.Chat
    import com.google.firebase.ai.ai

    import com.google.firebase.ai.type.Content

    import com.google.firebase.ai.type.GenerativeBackend
    import com.google.firebase.ai.type.ResponseModality
    import com.google.firebase.ai.type.content
    import com.google.firebase.ai.type.generationConfig
    import com.google.firebase.auth.FirebaseAuth
    import com.google.firebase.database.FirebaseDatabase
    import kotlinx.coroutines.Dispatchers
    import kotlinx.coroutines.flow.MutableStateFlow
    import kotlinx.coroutines.flow.StateFlow
    import kotlinx.coroutines.flow.asStateFlow
    import kotlinx.coroutines.flow.update
    import kotlinx.coroutines.launch
    import kotlin.collections.toMutableList


    class MyViewModel : ViewModel() {
        // instance of repo without hilt //

        private val repo = repo(FirebaseDatabase.getInstance())

        // medicines stock inventory

        private val _getAllMedicines = MutableStateFlow(GetAllMedicines())

        //  val getAllBooks = _getAllBooks.value //val getAllBooks = _getAllBooks.value // ❌ This makes a snapshot, not observable state

        // this public var is made for using in composable screen through collect as state
        val getAllMedicines = _getAllMedicines.asStateFlow()


        // GET INSTANCE
        private val _auth: FirebaseAuth = FirebaseAuth.getInstance()


        private val _authState = MutableLiveData<AuthState>()
        val authState: LiveData<AuthState> = _authState


        init {
            checkAuthStatus()
        }

        fun checkAuthStatus() {
            if (_auth.currentUser == null) {
                _authState.value = AuthState.Unauthenticated
            } else {
                _authState.value = AuthState.Authenticated
            }
        }


        fun login(
            email: String,
            password: String
        ) {

            if (email.isEmpty() || password.isEmpty()) {
                _authState.value = AuthState.Error("Email or password cant be empty")
            }
            _authState.value = AuthState.Loading



            _auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _authState.value = AuthState.Authenticated
                } else {
                    _authState.value =
                        AuthState.Error(task.exception?.message ?: "Something went wrong")
                }
            }


        }

        fun signUp(
            email: String,
            password: String,
            onResult: (Boolean) -> Unit
        ) {

            if (email.isEmpty() || password.isEmpty()) {
                _authState.value = AuthState.Error("Email or password cant be empty")
            }
            _authState.value = AuthState.Loading



            _auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _authState.value = AuthState.Authenticated
                    onResult(true)
                } else {
                    _authState.value =
                        AuthState.Error(task.exception?.message ?: "Something went wrong")
                }
            }


        }


        fun signOut() {
            _auth.signOut()
            _authState.value = AuthState.Unauthenticated
        }

        fun getAllMedicines() {
            viewModelScope.launch(Dispatchers.IO) {
                repo.getAllMedicines().collect {
                    when (it) {
                        is ResultState.Success -> {
                            _getAllMedicines.value = GetAllMedicines(data = it.data)
                        }

                        is ResultState.Loading -> {
                            _getAllMedicines.value = GetAllMedicines(isLoading = true)
                        }

                        is ResultState.Error -> {
                            _getAllMedicines.value = GetAllMedicines(error = it.message)
                        }

                        else -> {}
                    }


                }
            }
        }


    }
        class firebaseAiLogicChatViewModel() : ViewModel() {
            val systemInstruction = content {
                text("""
 val systemInstruction = content {
    text(""${'"'}
    dont show any aplhabets at starting only talk in other langugae when user gives input in that language or english type hindi or punjabi
    
    
    talk in english by default and switch the language only on user response in starting always introduce your self as sehat sathi ai chat bot for health services 
        You are a female multilingual AI health assistant  named Sehat Sathi designed for rural Indian users, especially from Nabha and nearby villages.
        
        Display the desired language alphabets like punjabi has gurumukhi , hindi has devnagri and english normal alphabets even if a person talks in any of language through switch to their language 
        and respond welcome statement of sehat ai after user say hi or greeting

        Your responsibilities include:
        - Understanding health symptoms and recommending general over-the-counter (OTC) medicines where appropriate.
        - Explaining the general use, side effects, and safety precautions of specific medicines when asked.
        - Responding in the language the user uses (Punjabi, Hindi, or English).
        - Always recommending that serious symptoms be evaluated by a doctor.
        - Providing information in short, easy-to-understand sentences suitable for mobile screens.

        For symptom-based queries:
        - Suggest simple OTC medicines if relevant (e.g., paracetamol, ORS, antacids).
        - Include advice like resting, hydration, or seeing a doctor if symptoms persist.
        - Use local terms when possible, and explain everything simply.

        For medicine-specific queries (e.g., “What is amoxicillin for?” or “Side effects of paracetamol”):
        - Briefly explain the common uses (diagnostic use).
        - List common side effects in simple language.
        - Mention precautions, such as if it's not safe during pregnancy or for children.
        - Clarify if the medicine requires a doctor’s prescription or if it's OTC.
        take medicine information from https://www.drugs.com/ ,https://www.drugs.com/sfx/, https://www.fda.gov/drugs/find-information-about-drug/finding-and-learning-about-side-effects-adverse-reactions

        Always use caution:
        - NEVER prescribe medicines or suggest doses.
        - NEVER provide emergency advice or diagnosis.
        - Encourage users to see a doctor for severe symptoms like chest pain, unconsciousness, difficulty breathing, or blood in stool/vomit.

        If the user types in Punjabi or Hindi, respond in that language. Use simple, empathetic, and helpful language. Don’t use complex medical terms.

        Example queries you can handle:
        - “Pet dard ke liye kya lo?” (Hindi)
        - “What is ibuprofen used for?”
        - “Side effects of dolo 650?”
        - “Goli khane ke baad ulti ho gayi”
        - “Am I having food poisoning?”

        Important:
        - Be friendly and supportive.
        - Dont reply on topics other than medical support or anything related to it , reply in a very humble and professional way
        - Give useful health info, but never replace a real doctor.
        - Avoid medical jargon or long paragraphs.
    ""${'"'}.trimIndent())
}

     .
    """.trimIndent())
            }



            val generationConfig = generationConfig {
                responseModalities = listOf(ResponseModality.TEXT)

            }


            val modelName = "gemini-2.5-flash"

            val chatHistory: List<Content> = listOf()

            private val _isLoading = MutableStateFlow(false)
            val isLoading: StateFlow<Boolean> = _isLoading


            private val _errorMessage = MutableStateFlow<String?>(null)

            val errorMessage: StateFlow<String?> = _errorMessage


            private val _messages = MutableStateFlow<List<Content>>(emptyList())
            val messages: StateFlow<List<Content>> = _messages.asStateFlow()


            private val _attachements = MutableStateFlow<List<Attachement>>(emptyList())
            val attachement: StateFlow<List<Attachement>> = _attachements.asStateFlow()


            private var contentBuilder = Content.Builder()


            lateinit var chat: Chat


            init {
                val generativeModel = Firebase.ai(
                    backend = GenerativeBackend.googleAI(),
                ).generativeModel(
                    modelName = modelName,
                    generationConfig = generationConfig,
                    systemInstruction = systemInstruction

                )

                chat = generativeModel.startChat()
            }

            fun sendMessage(message: String) {


                val prompt =  contentBuilder.text(message).build()


                _messages.update {
                    it.toMutableList().apply {
                        add(prompt)
                    }
                }


                viewModelScope.launch {
                    _isLoading.value = true
                    try {

                        val response = chat.sendMessage(prompt)

                        _messages.update { it.toMutableList().apply {
                            add(response.candidates.first().content)
                        } }


                        _errorMessage.value = null
                    }

                    catch (e: Exception){
                        _errorMessage.value = e.message
                    }
                    finally {
                        _isLoading.value = false
                        contentBuilder = Content.Builder()
                        _attachements.value = listOf()
                    }
                }

            }

            fun addAttachement (

                fileinBytes : ByteArray,
                mimeType : String?,
                fileName: String? ="unamed File"
            ){

                if(mimeType?.contains("image")==true){

                    contentBuilder.image(gernerateBitMapFromByteArray(fileinBytes))
                }

                else{

                }

                _attachements.update {
                    it.toMutableList().apply {
                        add(Attachement(fileName))
                    }
                }

            }


            fun gernerateBitMapFromByteArray(fileInByte : ByteArray): Bitmap
            {
                return BitmapFactory.decodeByteArray(fileInByte,0,fileInByte.size)
            }
        }
















        data class  Attachement(
            val fileName : String?,
            val image : Bitmap?=null
        )

        sealed class AuthState {

            object Authenticated : AuthState()
            object Unauthenticated : AuthState()
            object Loading : AuthState()
            data class Error(val message: String) : AuthState()
        }



    data class GetAllMedicines(

        val isLoading : Boolean = false,
        val error : String? = null,
        val data : List<MedicineModel> = emptyList()
    )