package com.example.instagramspf.Post

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.instagramspf.Models.Reel
import com.example.instagramspf.Models.User
import com.example.instagramspf.R
import com.example.instagramspf.databinding.ActivityReelsBinding
import com.example.instagramspf.utils.REEL
import com.example.instagramspf.utils.REEL_FOLDER
import com.example.instagramspf.utils.USER_NODE
import com.example.instagramspf.utils.uploadVideo
import com.example.instagramspoof.HomeActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase

class ReelsActivity : AppCompatActivity() {

    private lateinit var videoUrl: String
    lateinit var progressDialog: ProgressDialog

    val binding by lazy{
        ActivityReelsBinding.inflate(layoutInflater)
    }

    private val launcher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            uploadVideo(uri, REEL_FOLDER, progressDialog) { url ->
                if (url != null) {
                    videoUrl = url
                }
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        progressDialog = ProgressDialog(this)

        binding.selectReel.setOnClickListener {
            launcher.launch("video/*")
        }

        binding.cancelButton.setOnClickListener {
            startActivity(Intent(this@ReelsActivity, HomeActivity::class.java))
            finish()
        }

        binding.postButton.setOnClickListener {
            Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid).get()
                .addOnSuccessListener {
                    var user: User = it.toObject<User>()!!

                    val reel: Reel = Reel(videoUrl!!, binding.caption.editText?.text.toString(),user.image!!)

                    Firebase.firestore.collection(REEL).document().set(reel).addOnSuccessListener {
                        Firebase.firestore.collection(Firebase.auth.currentUser!!.uid + REEL)
                            .document().set(reel)
                            .addOnSuccessListener {
                                startActivity(Intent(this@ReelsActivity, HomeActivity::class.java))
                                finish()
                            }
                    }
                }


        }

    }
}