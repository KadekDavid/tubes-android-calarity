package org.d3if0126.myapplication.ui.user

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import org.d3if0126.myapplication.R
import org.d3if0126.myapplication.databinding.FragmentLoginBinding
import org.d3if0126.myapplication.ui.home.HomeFragment


class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentLoginBinding.bind(view)
        firebaseAuth = FirebaseAuth.getInstance()


        if (firebaseAuth.currentUser != null) {
            Navigation.findNavController(requireActivity(), R.id.fragmentContainerView)
                .navigate(R.id.action_loginFragment_to_homeFragment)
        }


        binding.button.setOnClickListener {
            val email = binding.edtEmail.text.toString()
            val pass = binding.edtPassword.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty()) {
                firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
                    if (it.isSuccessful) {
                        Navigation.findNavController(requireActivity(), R.id.fragmentContainerView)
                            .navigate(R.id.action_loginFragment_to_homeFragment)
                    } else {
                        Toast.makeText(
                            requireContext(),
                            it.exception.toString(),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } else {
                Toast.makeText(
                    requireContext(),
                    "Email dan Password tidak boleh kosong !!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }


        binding.daftarSekarang.setOnClickListener {
            Navigation.findNavController(requireActivity(), R.id.fragmentContainerView)
                .navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }
}

