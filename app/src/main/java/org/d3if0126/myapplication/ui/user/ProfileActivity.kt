package org.d3if0126.myapplication.ui.user


import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import android.widget.ImageButton

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import org.d3if0126.myapplication.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        firebaseAuth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().reference

        var currentUser = firebaseAuth.currentUser
        if (currentUser != null) {
            var uid = currentUser.uid

            var userRef = databaseReference.child("users").child(uid)
            userRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val userID = dataSnapshot.key
                    var user = dataSnapshot.getValue(User::class.java)
                    if (user != null) {
                        binding.tvNamaa.text = user.name
                        binding.tvEmaill.text = user.email

                        binding.textHasilUserId.text = userID
                        binding.textHasilNama.text = user.name
                        binding.textHasilEmail.text = user.email
                        binding.textHasilPassword.text = user.password

                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Toast.makeText(this@ProfileActivity, "Failed to fetch user data.", Toast.LENGTH_SHORT).show()
                }
            })
        }

        binding.buttonLogout.setOnClickListener {
            firebaseAuth.signOut()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
//        binding.buttonSave.setOnClickListener {
//            var noHp = binding.edtNoHP.text.toString()
//            var alamat = binding.edtAlamat.text.toString()
//
//            if (noHp.isNotEmpty() && alamat.isNotEmpty()) {
//
//
//
//                if (noHp != alamat) {
//                    firebaseAuth.createUserWithEmailAndPassword(noHp, alamat).addOnCompleteListener { task ->
//                        if (task.isSuccessful) {
//                            var currentUser = firebaseAuth.currentUser
//                            if (currentUser != null) {
//                                var uid = currentUser.uid
//
//                                var user = User(noHp,alamat)
//
//                                var userRef = databaseReference.child("users").child(uid)
//                                userRef.setValue(user).addOnCompleteListener {
//                                        userTask ->
//                                    if (userTask.isSuccessful) {
//                                        var intent = Intent(this, ProfileActivity::class.java)
//                                        startActivity(intent)
//                                        finish()
//                                    } else {
//                                        Toast.makeText(this, userTask.exception.toString(), Toast.LENGTH_SHORT).show()
//                                    }
//                                }
//                            }
//                        } else {
//                            Toast.makeText(this, task.exception.toString(), Toast.LENGTH_SHORT).show()
//                        }
//                    }
//                } else {
//                    Toast.makeText(this, "Alamat is not matching", Toast.LENGTH_SHORT).show()
//                }
//            } else {
//                Toast.makeText(this, "Empty Fields Are not Allowed !!", Toast.LENGTH_SHORT).show()
//            }
//        }
    }

}

