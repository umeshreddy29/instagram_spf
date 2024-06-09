package com.example.instagramspf

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.instagramspf.Models.User
import com.example.instagramspf.databinding.ActivitySignUpBinding
import com.example.instagramspf.utils.USER_PROFILE_FOLDER
import com.example.instagramspf.utils.uploadImage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SignUpActivity : AppCompatActivity() {

    //Binding
    val binding by lazy {
        ActivitySignUpBinding.inflate(layoutInflater)
    }

    lateinit var user: User

    // Launcher
    private val launcher = registerForActivityResult(ActivityResultContracts.GetContent()) {
            uri->
        uri?.let{
            uploadImage(uri, USER_PROFILE_FOLDER){
                if(it!=null)
                {
                    user.image = it
                    binding.profileImage.setImageURI(uri)
                }
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)
        user = User()

//        if(intent.hasExtra("MODE")) {
//            if(intent.getIntExtra("MODE",-1)==1)
//            {
//                binding.signUpBtn.text = "UPDATE PROFILE"
//
//                Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid).get()
//                    .addOnSuccessListener {
//
//                        val user: User = it.toObject<User>()!!
//                        if(!user.image.isNullOrEmpty())
//                        {
//                            Picasso.get().load(user.image).into(binding.profileImage)
//                        }
//                        binding.enterName.editText?.setText(user.name)
//                        binding.enterEmail.editText?.setText(user.email)
//                        binding.enterPassword.editText?.setText(user.password)
//
//                    }
//            }
//        }

        binding.loginHereText.setOnClickListener{
            startActivity(Intent(this@SignUpActivity,LoginActivity::class.java))
            finish()
        }

        binding.signUpBtn.setOnClickListener {

//            if(intent.hasExtra("MODE")) {
//                if (intent.getIntExtra("MODE", -1) == 1)
//                {
//                    Firebase.firestore.collection(USER_NODE)
//                        .document(Firebase.auth.currentUser!!.uid).set(user)
//                        .addOnSuccessListener {
//                            startActivity(Intent(this@SignUpActivity,HomeActivity::class.java))
//                            finish()
//                        }
//                }
//            }

            if (binding.enterName.editText?.text.toString().equals("") or
                binding.enterEmail.editText?.text.toString().equals("") or
                binding.enterPassword.editText?.text.toString().equals("") )
            {
                Toast.makeText(this@SignUpActivity,"Please fill all the details", Toast.LENGTH_SHORT).show()
            }
            else
            {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                    binding.enterEmail.editText?.text.toString(),
                    binding.enterPassword.editText?.text.toString() )
                    .addOnCompleteListener {
                            result ->

                        if (result.isSuccessful)
                        {
                            user.name = binding.enterName.editText?.text.toString()
                            user.email = binding.enterEmail.editText?.text.toString()
                            user.password = binding.enterPassword.editText?.text.toString()
                            Firebase.firestore.collection("User")
                                .document(Firebase.auth.currentUser!!.uid).set(user)
                                .addOnSuccessListener {
                                    Toast.makeText(this@SignUpActivity, "Login", Toast.LENGTH_SHORT).show()
//                                startActivity(Intent(this@SignUpActivity,HomeActivity::class.java))
//                                finish()
                                }

                        }
                        else
                        {
                            Toast.makeText(this@SignUpActivity, result.exception?.localizedMessage, Toast.LENGTH_SHORT).show()
                        }
                    }

            }
        }

        binding.plus.setOnClickListener{
            launcher.launch("image/*")
        }





    }
}