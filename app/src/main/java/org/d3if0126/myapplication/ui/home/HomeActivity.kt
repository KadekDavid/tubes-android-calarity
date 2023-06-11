package org.d3if0126.myapplication.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.d3if0126.myapplication.R
import org.d3if0126.myapplication.databinding.ActivityHomeBinding
import org.d3if0126.myapplication.ui.keranjang.KeranjangActivity
import org.d3if0126.myapplication.ui.profile.ProfileActivity


class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var imageAdapter: AdapterHome
    private val imageList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inisialisasi RecyclerView
        recyclerView = findViewById(R.id.imageView)

        // Inisialisasi LayoutManager
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        // Inisialisasi adapter dan set adapter ke RecyclerView
        imageAdapter = AdapterHome(imageList)
        recyclerView.adapter = imageAdapter

        // Panggil fungsi untuk mengambil data gambar dari Firebase Storage
        retrieveImagesFromFirebase()

        val floatingActionButton = findViewById<FloatingActionButton>(R.id.floatingActionButton)
        floatingActionButton.setOnClickListener {
            // Kode untuk perpindahan ke halaman lain
            val intent = Intent(this@HomeActivity, KeranjangActivity::class.java)
            startActivity(intent)

        }



        binding.bottomNavigation.setOnNavigationItemSelectedListener { menuItem ->
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
    private fun retrieveImagesFromFirebase() {
        // Mengambil gambar dari Firebase Storage dan menambahkannya ke imageList
        // ...

        // Setelah selesai mengambil gambar, beritahu adapter untuk memperbarui tampilan
        imageAdapter.notifyDataSetChanged()
    }
}