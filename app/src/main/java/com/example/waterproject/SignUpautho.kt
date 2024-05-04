package com.example.waterproject

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import dataclass.users

class SignUpautho : AppCompatActivity() {
    private lateinit var dialogBox:Dialog
    private lateinit var mfirestore: FirebaseFirestore
    private lateinit var mdatabaseref: DatabaseReference
   private lateinit var type: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_upautho)
        val items = listOf(
            "none",
            "Urban Flooding",
            "Rural Flooding",
            "Oil Spill",
            "Tsunami",
            "Polluted River",
            "Drought",
            "Drainage problems"
        )
        type=""
        val autoComplete: AutoCompleteTextView = findViewById(R.id.typeEntryField)
        val adapter = ArrayAdapter(this, R.layout.item_list, items)
        autoComplete.setAdapter(adapter)
        autoComplete.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                val itemSelected = parent.getItemAtPosition(position)
                type = itemSelected.toString()
            }


        val btnauth=findViewById<RadioButton>(R.id.rbAuthority)
        val btnresident=findViewById<RadioButton>(R.id.rbResident)
        btnresident.setOnClickListener {
            val intent= Intent(this,SignUp1::class.java)
            startActivity(intent)
            btnauth.isChecked=false
        }
        mfirestore= FirebaseFirestore.getInstance()
       val btnsignup=findViewById<Button>(R.id.btnsignupauthority)
        btnsignup.setOnClickListener {
            registerUser()
        }
        mdatabaseref= FirebaseDatabase.getInstance().getReference("user")


    }
    override fun onBackPressed() {

        super.onBackPressed()
        finishAffinity()
        val intent1=Intent(this,SignIn::class.java)
        startActivity(intent1)
    }
    private fun registerUser()
    {

        val name:String=findViewById<EditText>(R.id.nameEntryField).text.toString().trim{it<=' '}
        val email:String=findViewById<EditText>(R.id.EmailEntryField).text.toString().trim{it<=' '}
        val password:String=findViewById<EditText>(R.id.passwordEntryField).text.toString().trim{it<=' '}
        val code:String=findViewById<EditText>(R.id.codeEntryField).text.toString().trim(){it<=' '}
        if(validateForm(name,email,password,code,type))
        {
            showProgressDialog("Signing Up...")
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password
            ).addOnCompleteListener {
                    task->
                hideProgressDialog()
                if(task.isSuccessful)
                {
                    val firebaseuser: FirebaseUser =task.result!!.user!!
                    val firebaseemail=firebaseuser.email
                    val layout=layoutInflater.inflate(R.layout.custom_toast_layout,findViewById(R.id.view_layout_of_toast))
                    val toast= Toast(baseContext)
                    toast.view=layout
                    val txtmsg=layout.findViewById<TextView>(R.id.textview_toast)
                    txtmsg.setText("You have successfully registered")
                    toast.duration.toLong()
                    toast.show()

                    var user= users(name = name, email = email, code = code, ischecked = "org", type = type)
                    mdatabaseref.child(firebaseuser.uid).setValue(user).addOnCompleteListener {

                        finishAffinity()
                        val intent:Intent=Intent(this,MainActivity::class.java)
                        startActivity(intent)
                    }








                }
                else
                { val layout1=layoutInflater.inflate(R.layout.error_toast_layout,findViewById(R.id.view_layout_of_toast1))
                    val toast= Toast(this)
                    toast.view=layout1
                    val txtMsg=layout1.findViewById<TextView>(R.id.textview_toast1)
                    txtMsg.setText("${task.exception!!.message}")
                    toast.duration.toLong()
                    toast.show()


                }

            }

        }



    }
    private fun validateForm(name:String,email:String,password:String,code:String,type:String):Boolean
    {
        return when{
            TextUtils.isEmpty(name)->
            {
                errorSnackBar("Please Enter your Name")
                false
            }
            TextUtils.isEmpty(email)->
            {
                errorSnackBar("Please Enter your Email")
                false
            }
            TextUtils.isEmpty(password)->
            {
                errorSnackBar("Please Enter your Password")
                false
            }
            TextUtils.isEmpty(code)->
            {
                errorSnackBar("Please Enter your PinCode")
                false
            }
            TextUtils.isEmpty(type) ->
            {
                errorSnackBar("Please enter the type")
                false
            }
            else->
            {
                true
            }
        }

    }
    fun showProgressDialog(text:String)
    {
        dialogBox= Dialog(this)
        dialogBox.setContentView(R.layout.dialog_progress)
        dialogBox.findViewById<TextView>(R.id.progress_bar_text).text=text
        dialogBox.show()

    }
    fun errorSnackBar(message:String)
    {
        val snackbar= Snackbar.make(findViewById(android.R.id.content),message, Snackbar.LENGTH_LONG)
        val snackBarView=snackbar.view
        snackBarView.setBackgroundColor(ContextCompat.getColor(this, R.color.snackbar_error_color))
        snackbar.show()

    }
    fun hideProgressDialog()
    {
        dialogBox.dismiss()
    }


}