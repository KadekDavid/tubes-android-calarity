package org.d3if0126.myapplication.ui.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*
import org.d3if0126.myapplication.R
import org.d3if0126.myapplication.adapter.KeranjangAdapter
import org.d3if0126.myapplication.databinding.FragmentHomeBinding
import org.d3if0126.myapplication.databinding.ListItemKeranjangBinding
import org.d3if0126.myapplication.model.Home
import org.d3if0126.myapplication.ui.detail.DetailFragment
import org.d3if0126.myapplication.ui.keranjang.KeranjangManager
import org.d3if0126.myapplication.ui.keranjang.KeranjangViewModel


class HomeFragment : Fragment(R.layout.fragment_home){

    private lateinit var binding: FragmentHomeBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var listImages: ArrayList<Home>
    private lateinit var databaseReference: DatabaseReference
    private lateinit var databaseKeranjang : DatabaseReference
    private lateinit var keranjangViewModel: KeranjangViewModel
    private lateinit var keranjangAdapter: KeranjangAdapter
    private val keranjangItemList: MutableList<Home> = mutableListOf()

    private var currentKategori: String = "all" // Kategori default

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        keranjangViewModel = ViewModelProvider(this).get(KeranjangViewModel::class.java)
        keranjangAdapter = KeranjangAdapter(requireContext(), keranjangItemList)

        recyclerView = binding.recyclerViewHome

        listImages = arrayListOf()
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        databaseReference = FirebaseDatabase.getInstance().getReference("mitraGambar")
        databaseKeranjang = FirebaseDatabase.getInstance().getReference("keranjang")


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
        binding.extendedFab.setOnClickListener{
            val navController = findNavController()
            navController.navigate(R.id.action_homeFragment_to_transaksiFragment)
        }

        binding.ibKeranjang.setOnClickListener {
            val navController = findNavController()
            navController.navigate(R.id.action_homeFragment_to_transaksiFragment)
        }

        binding.recyclerViewHome.setOnClickListener {
            val position = it.tag as Int
            val item = listImages[position]
            val judul = item.judul
            val harga = item.harga
            val imageUrl = item.url


            val detailFragment = DetailFragment()

            val bundle = Bundle()
            bundle.putString("judul", judul)
            bundle.putString("harga", harga)
            bundle.putString("url", imageUrl)


            detailFragment.arguments = bundle

            // Navigasi ke DetailFragment menggunakan action yang telah ditentukan di nav_graph.xml
            val navController = findNavController()
            navController.navigate(R.id.action_homeFragment_to_detailFragment, bundle)
        }
        loadImages()

        // Set onClickListener untuk tombol-tombol kategori
        binding.btnLogo.setOnClickListener {
            currentKategori = "logo"
            loadImages()
        }

        binding.btnFeed.setOnClickListener {
            currentKategori = "feed"
            loadImages()
        }

        binding.btnPoster.setOnClickListener {
            currentKategori = "poster"
            loadImages()
        }

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
        val imageView = binding.imageView4
        imageView.setOnClickListener {
            binding.sheet.visibility = View.INVISIBLE
        }

        binding.floating.setOnClickListener {
            // Navigasi ke fragmen lain
            binding.sheet.visibility = View.VISIBLE
            val bottomSheetBehavior = BottomSheetBehavior.from(binding.sheet)
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN

            if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED) {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            } else {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            }
            getRetrieveKeranjangItems()
            getDataKeranjang()
        }
    }

    private fun getDataKeranjang(){
        databaseKeranjang.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    keranjangItemList.clear()
                for (dataSnapshot in snapshot.children) {
                    val imageUrl = dataSnapshot.child("url").getValue(String::class.java)
                    val judul = dataSnapshot.child("judul").getValue(String::class.java)
                    val harga = dataSnapshot.child("harga").getValue(String::class.java)

                    val homeData = Home(imageUrl, judul, harga)
                    keranjangItemList.add(homeData)
                }
                    binding.recyclerViewBottomSheet.adapter = KeranjangAdapter(requireContext(), keranjangItemList)
                    binding.recyclerViewBottomSheet.adapter?.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), error.toString(), Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun loadImages() {
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    listImages.clear() // Membersihkan list sebelum menambahkan item baru
                    for (dataSnapshot in snapshot.children) {
                        val kategori = dataSnapshot.child("kategori").getValue(String::class.java)

                        // Filter berdasarkan kategori yang dipilih
                        if (kategori == currentKategori || currentKategori == "all") {
                            val imageUrl = dataSnapshot.child("url").getValue(String::class.java)
                            val judul = dataSnapshot.child("judul").getValue(String::class.java)
                            val harga = dataSnapshot.child("harga").getValue(String::class.java)

                            val homeData = Home(imageUrl, judul, harga)
                            listImages.add(homeData)
                        }
                    }

                    recyclerView.adapter?.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), error.toString(), Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun getRetrieveKeranjangItems() {
        keranjangViewModel.keranjangItemList.observe(viewLifecycleOwner, { itemList ->
            keranjangItemList.clear()
            keranjangAdapter.notifyDataSetChanged()
        })
    }
}