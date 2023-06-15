package org.d3if0126.myapplication.ui.profile


import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import org.d3if0126.myapplication.R
import org.d3if0126.myapplication.databinding.FragmentProfileBinding

import org.d3if0126.myapplication.model.User

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentProfileBinding.bind(view)
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
                    val username = dataSnapshot.child("namaPengguna").getValue(String::class.java)
                    if (user != null) {
                        binding.tvNamaa.text = user.name
                        binding.tvEmaill.text = user.email
                        binding.textHasilUserId.text = userID
                        binding.textHasilNama.text = user.name
                        binding.textHasilEmail.text = user.email
                        binding.textHasilUsername.text = username

                    }
                }
                override fun onCancelled(databaseError: DatabaseError) {
                    Toast.makeText(requireContext(), "Failed to fetch user data.", Toast.LENGTH_SHORT).show()
                }
            })
        }

        binding.buttonLogout.setOnClickListener {
            firebaseAuth.signOut()
            Navigation.findNavController(requireActivity(), R.id.fragmentContainerView)
                .navigate(R.id.action_profileFragment_to_loginFragment)
        }
        binding.tvTentangAplikasi.setOnClickListener {
            Navigation.findNavController(requireActivity(), R.id.fragmentContainerView)
                .navigate(R.id.action_profileFragment_to_tentangAplikasiFragment)
        }
        binding.tvTentangKita.setOnClickListener {
            Navigation.findNavController(requireActivity(), R.id.fragmentContainerView)
                .navigate(R.id.action_profileFragment_to_tentangKitaFragment)
        }

    }
}
