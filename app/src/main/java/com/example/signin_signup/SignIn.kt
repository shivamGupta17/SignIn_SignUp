package com.example.signin_signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignIn : AppCompatActivity() {

    val Auth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        textViewNo.setOnClickListener {
            val intent = Intent(this,SIgnUp::class.java)
            startActivity(intent)
        }
        val signIn = findViewById(R.id.buttonLogin) as Button
        signIn.setOnClickListener {
            login();
        }
    }
    private fun login()
    {
        //prgsIn.visibility = View.VISIBLE
        val username = findViewById(R.id.emailLogin) as EditText
        val pass = findViewById(R.id.passwordLogin) as EditText

        var user = username.getText().toString().trim()
        var Pass = pass.getText().toString().trim()


        if(user.isEmpty() && Pass.isEmpty())
            Toast.makeText(this,"Enter Username or password", Toast.LENGTH_SHORT).show()
        else
        {
            Auth.signInWithEmailAndPassword(user,Pass).addOnCompleteListener(this,
                OnCompleteListener { task ->
                    if(task.isSuccessful)
                    {
                        var User: FirebaseUser? = Auth.getCurrentUser()
                        if(User == null)
                        {
                            Toast.makeText(this, "Error occured", Toast.LENGTH_SHORT).show()
                        }
                        else
                            if(User.isEmailVerified) {
                                startActivity(Intent(this, SignIn::class.java))
                                finish()
                                Toast.makeText(this, "Sign in successful", Toast.LENGTH_SHORT).show()
                            }
                            else
                            {
                                Toast.makeText(this, "Please Verify Email", Toast.LENGTH_SHORT).show()
                                User.sendEmailVerification()
                            }
                    }
                    else
                    {
                        Toast.makeText(this,"Sign In Failed", Toast.LENGTH_SHORT).show()
                    }
                })
        }
        //prgsIn.visibility = View.VISIBLE
    }

}
