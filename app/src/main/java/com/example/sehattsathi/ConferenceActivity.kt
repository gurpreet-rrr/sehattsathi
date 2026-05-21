package com.example.sehattsathi
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

import com.example.sehattsathi.common.constraint
import com.zegocloud.uikit.prebuilt.call.ZegoUIKitPrebuiltCallConfig
import com.zegocloud.uikit.prebuilt.call.ZegoUIKitPrebuiltCallFragment
import com.zegocloud.uikit.prebuilt.call.core.utils.Storage.userID


class ConferenceActivity : AppCompatActivity() {

    lateinit var meetingID: String
    lateinit var  userName : String

    lateinit var  meetingIDTextView : TextView
    lateinit var  shareBtn: ImageView





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_conference)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        meetingIDTextView = findViewById(R.id.meeting_id_textview)
        shareBtn = findViewById(R.id.share_btn_imageview)

        meetingID = intent.getStringExtra("MEETING_ID").toString()
        userName = intent.getStringExtra("USER_NAME").toString()

        meetingIDTextView.setText("MEETING_ID"+ meetingID)

        addCallFragment()
    }

    fun addCallFragment() {
        val appID: Long = constraint.appId
        val appSign: String = constraint.appSign

        var callID: String = meetingID
        var userID: String = userName
        var userName = userName

        // You can also use GroupVideo/GroupVoice/OneOnOneVoice to make more types of calls.
        val config = ZegoUIKitPrebuiltCallConfig.oneOnOneVideoCall()

        val fragment = ZegoUIKitPrebuiltCallFragment.newInstance(
            appID, appSign, userID, userName, callID, config
        )

        getSupportFragmentManager().beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commitNow()
    }}

