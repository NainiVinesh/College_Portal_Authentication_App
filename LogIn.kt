package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class LogIn : AppCompatActivity() {

    private lateinit var email_val: EditText
    private lateinit var pass_val: EditText
    private lateinit var btn_login: Button
    private lateinit var btn_sign_up: Button

    public lateinit var account_type : String

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference

    val database = FirebaseDatabase.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        mAuth = FirebaseAuth.getInstance()
        mDbRef = FirebaseDatabase.getInstance().getReference("user")

        email_val = findViewById(R.id.email_text)
        pass_val = findViewById(R.id.pass_text)
        btn_login = findViewById(R.id.login_btn)
        btn_sign_up = findViewById(R.id.signup_btn)

        btn_sign_up.setOnClickListener {
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }

        btn_login.setOnClickListener {
            val email = email_val.text.toString()
            val password = pass_val.text.toString()
            logIn(email, password)
        }
    }

    private fun logIn(email: String, password: String) {
        //logic for login
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    // code for logging user

                    val uid = FirebaseAuth.getInstance().currentUser?.uid
                    // Construct a reference to the user's data in the database
                    val userRef = database.getReference("users").child("$uid")

// Read data from the database
                    userRef.addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            // Handle the data
                            val userData = snapshot.getValue(User::class.java)
                            // Do something with userData
                            if(userData?.category == "Student" || userData?.category == "student") {
                                val intent = Intent(this@LogIn, StudentInterface::class.java)
                                startActivity(intent)
                            }
                            else {
                                val intent = Intent(this@LogIn, HodInterface::class.java)
                                startActivity(intent)
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                            // Handle errors
                            println("Data retrieval cancelled:")
                        }
                    })
                    }    else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(this@LogIn, "user does't exist", Toast.LENGTH_SHORT).show()
                    }
                }

            }
    }

    //getdata
