package com.example.instagramspf

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.instagramspf.Models.User
import com.example.instagramspf.databinding.ActivityLoginBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    val binding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.signUpHereText.setOnClickListener{
            startActivity(Intent(this@LoginActivity,SignUpActivity::class.java))
            finish()
        }

        binding.loginBtn.setOnClickListener{
            if (binding.enterEmail.editText?.text.toString().equals("") or
                binding.enterPassword.editText?.text.toString().equals("") )
            {
                Toast.makeText(this@LoginActivity,"Please fill all the details", Toast.LENGTH_SHORT).show()
            }
            else
            {
                var user = User(binding.enterEmail.editText?.text.toString(),
                    binding.enterPassword.editText?.text.toString())

                Firebase.auth.signInWithEmailAndPassword(user.email!!,user.password!!)
                    .addOnCompleteListener {
                        if(it.isSuccessful)
                        {
                            Toast.makeText(this@LoginActivity,"Login Successful", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                            finish()
                        }
                        else
                        {
                            Toast.makeText(this@LoginActivity, it.exception?.localizedMessage, Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }

    }
}