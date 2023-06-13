package org.d3if0126.myapplication.ui.keranjang

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.database.*
import org.d3if0126.myapplication.R
import org.d3if0126.myapplication.adapter.KeranjangAdapter
import org.d3if0126.myapplication.databinding.FragmentKeranjangBinding
import org.d3if0126.myapplication.databinding.FragmentProfileBinding
import org.d3if0126.myapplication.model.Keranjang

class KeranjangFragment : Fragment() {

    private lateinit var binding: FragmentKeranjangBinding
    private lateinit var keranjangAdapter: KeranjangAdapter
    private lateinit var databaseReference: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentKeranjangBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        databaseReference = FirebaseDatabase.getInstance().getReference("keranjang")

        // Inisialisasi adapter
        keranjangAdapter = KeranjangAdapter(requireContext(), emptyList())

        // Mengatur layout manager dan adapter untuk RecyclerView
        binding.recyclerViewBottomSheet.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewBottomSheet.adapter = keranjangAdapter

        // Mendapatkan data keranjang dari database Firebase
        fetchDataKeranjang()
    }

    private fun fetchDataKeranjang() {
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val keranjangList = mutableListOf<Keranjang>()
                for (dataSnapshot in dataSnapshot.children) {
                    val judul = dataSnapshot.child("judul").getValue(String::class.java)
                    val harga = dataSnapshot.child("harga").getValue(String::class.java)
                    val url = dataSnapshot.child("url").getValue(String::class.java)
                    val keranjang = Keranjang(judul, harga, url)
                    keranjangList.add(keranjang)
                }

                // Update data dalam adapter
                keranjangAdapter.updateData(keranjangList)
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
                Toast.makeText(requireContext(), error.toString(), Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun tambahDataKeKeranjang(namaProduk: String, hargaProduk: String) {

        // Generate ID untuk data baru
        val newKeranjangId = databaseReference.push().key

        // Buat objek Keranjang baru
        val keranjangBaru = Keranjang(newKeranjangId, namaProduk, hargaProduk)

        // Simpan data ke database
        if (newKeranjangId != null) {
            databaseReference.child(newKeranjangId).setValue(keranjangBaru)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(requireContext(), "Produk ditambahkan ke keranjang", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(requireContext(), "Gagal menambahkan produk ke keranjang", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}
