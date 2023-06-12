package org.d3if0126.myapplication.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import org.d3if0126.myapplication.R
import org.d3if0126.myapplication.databinding.FragmentHomeBinding
import org.d3if0126.myapplication.databinding.FragmentProfileBinding
import org.d3if0126.myapplication.model.Home


class HomeFragment : Fragment(R.layout.fragment_home){

    private lateinit var binding: FragmentHomeBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var listImages: ArrayList<Home>
    private lateinit var databaseReference: DatabaseReference

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentHomeBinding.bind(view)
        recyclerView = binding.recyclerViewHome
        listImages = arrayListOf()
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        databaseReference = FirebaseDatabase.getInstance().getReference("mitraGambar")
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    listImages.clear() // Membersihkan list sebelum menambahkan item baru
                    for (dataSnapshot in snapshot.children) {
                        val imageUrl = dataSnapshot.child("url").getValue(String::class.java)
                        val judul = dataSnapshot.child("judul").getValue(String::class.java)
                        val harga = dataSnapshot.child("harga").getValue(String::class.java)

                        val homeData = Home(imageUrl, judul, harga)
                        listImages.add(homeData)
                    }
                    recyclerView.adapter = HomeAdapter(requireContext(), listImages)
                    recyclerView.adapter?.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), error.toString(), Toast.LENGTH_SHORT).show()
            }
        })

        // bottom menu on click
        binding.floatingActionButton.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home -> true

                R.id.account -> {
                    Navigation.findNavController(requireActivity(), R.id.fragmentContainerView)
                        .navigate(R.id.action_homeFragment_to_profileFragment)
                    true
                }
                else -> false
            }
        }
    }
}
