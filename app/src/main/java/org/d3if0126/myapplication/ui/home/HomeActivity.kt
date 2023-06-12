package org.d3if0126.myapplication.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import org.d3if0126.myapplication.R
import org.d3if0126.myapplication.databinding.ActivityHomeBinding
import org.d3if0126.myapplication.ui.user.ProfileActivity

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var listImages: ArrayList<HomeDataClass>
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        recyclerView = findViewById(R.id.recyclerViewHome)
        listImages = arrayListOf()
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        databaseReference = FirebaseDatabase.getInstance().getReference("mitraGambar")
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    listImages.clear() // Membersihkan list sebelum menambahkan item baru
                    for (dataSnapshot in snapshot.children) {
                        val imageUrl = dataSnapshot.child("url").getValue(String::class.java)
                        val judul = dataSnapshot.child("judul").getValue(String::class.java)
                        val harga = dataSnapshot.child("harga").getValue(String::class.java)

                        val homeData = HomeDataClass(imageUrl, judul, harga)
                        listImages.add(homeData)
                    }
                    recyclerView.adapter = HomeAdapter(this@HomeActivity, listImages)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@HomeActivity, error.toString(), Toast.LENGTH_SHORT).show()
            }
        })

        // bottom menu on click
        binding.floatingActionButton.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home -> {
                    true
                }

                R.id.account -> {
                    startActivity(Intent(this@HomeActivity, ProfileActivity::class.java))
                    true
                }

                else -> false
            }
        }
    }
}
