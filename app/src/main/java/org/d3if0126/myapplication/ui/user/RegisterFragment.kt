package org.d3if0126.myapplication.ui.user


import android.content.Intent

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.d3if0126.myapplication.R

import org.d3if0126.myapplication.databinding.FragmentLoginBinding
import org.d3if0126.myapplication.databinding.FragmentRegisterBinding
import org.d3if0126.myapplication.model.User


class RegisterFragment : Fragment(R.layout.fragment_register) {

    private lateinit var binding: FragmentRegisterBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentRegisterBinding.bind(view)
        firebaseAuth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().reference



        binding.belumPunya.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_registerFragment_to_loginFragment)
        }




        binding.button.setOnClickListener {


            var email = binding.edtEmail.text.toString()
            var name = binding.edtNama.text.toString()
            var namaPengguna = binding.etUsername.text.toString()
            var pass = binding.edtPassword.text.toString()
            var confirmPass = binding.edtConfirmPass.text.toString()



            if (name.isNotEmpty() && email.isNotEmpty() && namaPengguna.isNotEmpty()&& pass.isNotEmpty() && confirmPass.isNotEmpty()) {
                if (pass == confirmPass) {
                    firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            var currentUser = firebaseAuth.currentUser
                            if (currentUser != null) {
                                var uid = currentUser.uid

                                var user = User(email, name, namaPengguna, pass)

                                var userRef = databaseReference.child("users").child(uid)
                                userRef.setValue(user).addOnCompleteListener { userTask ->
                                    if (userTask.isSuccessful) {
                                        Navigation.findNavController(view)
                                            .navigate(R.id.action_registerFragment_to_homeFragment)
                                    } else {
                                        Toast.makeText(requireContext(), userTask.exception.toString(), Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }
                        } else {
                            Toast.makeText(requireContext(), task.exception.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(requireContext(), "Password is not matching", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "Empty Fields Are not Allowed !!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
