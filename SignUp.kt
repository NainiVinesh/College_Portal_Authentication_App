package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUp : AppCompatActivity() {

    private lateinit var name_val : EditText
    private lateinit var email_val : EditText
    private lateinit var category_val : EditText
    private lateinit var contact_val : EditText
    private lateinit var branch_val : EditText
    private lateinit var pass_val : EditText
    private lateinit var btn_signup : Button
    private lateinit var mAuth : FirebaseAuth
    private lateinit var mDbRef : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        mAuth = FirebaseAuth.getInstance()


        name_val = findViewById(R.id.name_text)
        email_val = findViewById(R.id.email_text)
        category_val = findViewById(R.id.category_text)
        contact_val = findViewById(R.id.contactno_text)
        branch_val = findViewById(R.id.branch_text)
        pass_val = findViewById(R.id.password_text)
        btn_signup = findViewById(R.id.btn_signup)

        btn_signup.setOnClickListener{
            val name = name_val.text.toString()
            val email = email_val.text.toString()
            val category = category_val.text.toString()
            val contactno = contact_val.text.toString()
            val branch = branch_val.text.toString()
            val password = pass_val.text.toString()

            signUp(name,email,category,contactno,branch,password)
        }
    }

    private fun signUp(name : String,email : String,category : String,contactno : String,branch : String,password : String)
    {
      //logic for creating a new user
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    // code to jump to home page
                    addUsertoDb(name,email,category,contactno,branch,mAuth.currentUser?.uid!!)
                    //val intent = Intent(this@SignUp, MainActivity::class.java)
                    //startActivity(intent)
                    if(category == "Student" || category == "student")
                    {
                        val intent = Intent(this@SignUp, StudentInterface::class.java)
                        startActivity(intent)
                    }
                    else
                    {
                        val intent = Intent(this@SignUp, HodInterface::class.java)
                        startActivity(intent)
                    }

                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this@SignUp, "some error occurred", Toast.LENGTH_SHORT).show()
                }
            }

    }
    private fun addUsertoDb(name: String,email: String,category: String,contactno: String,branch: String,uid : String)
    {
        mDbRef = FirebaseDatabase.getInstance().getReference()
        mDbRef.child("users").child(uid).setValue(User(name,email,category,contactno,branch,uid))
    }
}