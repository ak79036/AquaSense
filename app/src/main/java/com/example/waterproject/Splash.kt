package com.example.waterproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import com.airbnb.lottie.LottieAnimationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import dataclass.users

class Splash : AppCompatActivity() {
    private lateinit var titleTextView: TextView
    private lateinit var logoView: LottieAnimationView
    private val splash_Timeout: Int = 2000
    private  var mauth: FirebaseUser?=null
    private lateinit var     type:String
    private lateinit var mdatabaseref: DatabaseReference
    var i = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        supportActionBar?.hide()
        titleTextView = findViewById(R.id.titleview)
        type=""
        mdatabaseref= FirebaseDatabase.getInstance().getReference("user")

        logoView = findViewById(R.id.logoview)
        animateText(titleTextView.text.toString())
        mauth= FirebaseAuth.getInstance().currentUser
        if(mauth!=null)
        {
            mdatabaseref.child(mauth!!.uid).addValueEventListener(object:
                ValueEventListener
            {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val user1=snapshot?.getValue(users::class.java)
                    type=user1?.ischecked.toString()
                    if(type=="org")
                    {
                        Handler(Looper.getMainLooper()).postDelayed({
                            val splashIntent = Intent(this@Splash, MainActivity::class.java)
                            startActivity(splashIntent)
                            overridePendingTransition(R.anim.fadein, R.anim.fadeout)
                            finish()
                        }, splash_Timeout.toLong())
                    }
                    else
                    {
                        Handler(Looper.getMainLooper()).postDelayed({
                            val splashIntent = Intent(this@Splash, MainActivity2::class.java)
                            startActivity(splashIntent)
                            overridePendingTransition(R.anim.fadein, R.anim.fadeout)
                            finish()
                        }, splash_Timeout.toLong())
                    }

                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })



        }
        else
        {
            Handler(Looper.getMainLooper()).postDelayed({
                val splashIntent = Intent(this, SignIn::class.java)
                startActivity(splashIntent)
                overridePendingTransition(R.anim.fadein, R.anim.fadeout)
                finish()
            }, splash_Timeout.toLong())
        }

    }
    private fun animateText(text: String) {
        if (i <= text.length) {
            val fetchtext: String = text.substring(0, i);
            titleTextView.text = fetchtext
            Handler(Looper.getMainLooper()).postDelayed(
                {
                    i++;
                    animateText(text)
                }, 100
            )
        }

    }
}