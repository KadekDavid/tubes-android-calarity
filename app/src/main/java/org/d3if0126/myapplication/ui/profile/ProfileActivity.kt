package org.d3if0126.myapplication.ui.profile

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import org.d3if0126.myapplication.databinding.ActivityProfileBinding
import org.d3if0126.myapplication.ui.login.LoginActivity

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        firebaseAuth = FirebaseAuth.getInstance()
        binding.buttonLogout.setOnClickListener {
            // Implement logout logic here
            firebaseAuth.signOut()
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}