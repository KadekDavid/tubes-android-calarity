package org.d3if0126.myapplication.ui.detail


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*
import org.d3if0126.myapplication.R
import org.d3if0126.myapplication.adapter.KeranjangAdapter
import org.d3if0126.myapplication.databinding.FragmentDetailBinding
import org.d3if0126.myapplication.model.Home
import org.d3if0126.myapplication.ui.keranjang.KeranjangViewModel


class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private lateinit var databaseReference: DatabaseReference
    private lateinit var keranjangViewModel: KeranjangViewModel
    private lateinit var keranjangAdapter: KeranjangAdapter
    private lateinit var databaseKeranjang : DatabaseReference
    private val keranjangItemList: MutableList<Home> = mutableListOf()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailBinding.bind(view)

        keranjangViewModel = ViewModelProvider(requireActivity()).get(KeranjangViewModel::class.java)
        keranjangAdapter = KeranjangAdapter(requireContext(), keranjangItemList)
        databaseReference = FirebaseDatabase.getInstance().getReference("mitraGambar")
        databaseKeranjang = FirebaseDatabase.getInstance().getReference("keranjang")

        val fabNavigate: FloatingActionButton = view.findViewById(R.id.floatingdetail)
        fabNavigate.setOnClickListener {
            findNavController().navigate(R.id.action_detailFragment_to_keranjangFragment)
        }


        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (dataSnapshot in dataSnapshot.children) {

                        val deskripsi = dataSnapshot.child("deskripsi").getValue(String::class.java)
                        binding.textPenjelasan.text = deskripsi

                        val judul = arguments?.getString("judul")
                        val harga = arguments?.getString("harga")
                        val url = arguments?.getString("url")


                        binding.textJudul.text = judul
                        binding.textHarga.text = harga
                        binding.textPenjelasan.text = deskripsi

                        Glide.with(requireContext())
                            .load(url)
                            .into(binding.viewImage)
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), error.toString(), Toast.LENGTH_SHORT).show()
            }
        })

        binding.btnKeranjang.setOnClickListener {
            val namaProduk = binding.textJudul.text.toString()
            val hargaProduk = binding.textHarga.text.toString()
            val imageUrl = arguments?.getString("url")

            val keranjangItem = Home(imageUrl, namaProduk, hargaProduk)
            keranjangViewModel.addItemToKeranjang(keranjangItem)

            Toast.makeText(requireContext(), "Produk ditambahkan ke keranjang", Toast.LENGTH_SHORT).show()
        }


        binding.floatingActionButton.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home -> {
                    Navigation.findNavController(requireActivity(), R.id.fragmentContainerView)
                        .navigate(R.id.action_detailFragment_to_homeFragment)
                    true
                }

                R.id.account -> {
                    Navigation.findNavController(requireActivity(), R.id.fragmentContainerView)
                        .navigate(R.id.action_detailFragment_to_profileFragment)
                    true
                }
                else -> false
            }
        }
        val imageView = binding.imageView4
        imageView.setOnClickListener {
            binding.sheet.visibility = View.INVISIBLE
        }
        getRetrieveKeranjangItems()
        binding.floatingdetail.setOnClickListener {
            // Navigasi ke fragmen lain
            binding.sheet.visibility = View.VISIBLE
            val bottomSheetBehavior = BottomSheetBehavior.from(binding.sheet)
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN

            if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED) {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            } else {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            }


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

    private fun getRetrieveKeranjangItems() {
        keranjangViewModel.keranjangItemList.observe(viewLifecycleOwner, { itemList ->
            keranjangItemList.clear()
//            keranjangItemList.addAll(itemList)
            keranjangAdapter.notifyDataSetChanged()
        })
    }
}