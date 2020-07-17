package com.example.signin_signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_s_ign_up.*

class SIgnUp : AppCompatActivity() {

    val Auth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_s_ign_up)

        textViewAlready.setOnClickListener {
            val intent = Intent(this, SignIn::class.java)
            startActivity(intent)
        }
        val signUp = findViewById(R.id.buttonRegister) as Button
        signUp.setOnClickListener {
            register();
        }
    }
    private fun register()
    {
        //prgsUP.visibility = View.VISIBLE
        val name = findViewById(R.id.fullName) as EditText
        val email = findViewById(R.id.email) as EditText
        val pass = findViewById(R.id.Password) as EditText
        val Repass = findViewById(R.id.rePassword) as EditText



        val user = email.text.toString().trim()
        val Pass = pass.text.toString().trim()
        val rePass = Repass.text.toString().trim()

        if(Pass != rePass)
            Toast.makeText(this,"Passwords are not matching", Toast.LENGTH_SHORT).show()
        else
            if(user.isEmpty() && Pass.isEmpty())
                Toast.makeText(this,"Enter Username or password", Toast.LENGTH_SHORT).show()
            else
                Auth.createUserWithEmailAndPassword(user,Pass).addOnCompleteListener(this,
                    OnCompleteListener { task ->
                        if(task.isSuccessful)
                        {
                            val user = Auth.getCurrentUser()
                            user?.sendEmailVerification()?.addOnCompleteListener { task ->
                                if(task.isSuccessful)
                                    Toast.makeText(this,"Email sent successfully", Toast.LENGTH_SHORT).show()
                                else
                                    Toast.makeText(this,"Email not sent", Toast.LENGTH_SHORT).show()

                            }
                            Toast.makeText(this,"User Registered Successfully", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this,MainActivity :: class.java))
                            finish()
                        }
                        else
                        {
                            Toast.makeText(this,"User Registration Failed", Toast.LENGTH_SHORT).show()
                        }
                    })
        //prgsUP.visibility = View.VISIBLE
    }
}
