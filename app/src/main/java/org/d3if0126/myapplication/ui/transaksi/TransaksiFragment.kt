package org.d3if0126.myapplication.ui.transaksi

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.database.*
import org.d3if0126.myapplication.R
import org.d3if0126.myapplication.adapter.TransaksiAdapter
import org.d3if0126.myapplication.databinding.FragmentTransaksiBinding
import org.d3if0126.myapplication.model.Detail
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class TransaksiFragment : Fragment(R.layout.fragment_transaksi) {
    private lateinit var binding: FragmentTransaksiBinding
    private lateinit var databaseReference: DatabaseReference
    private lateinit var databaseKategori: DatabaseReference
    private val keranjangItemList: MutableList<Detail> = mutableListOf()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTransaksiBinding.bind(view)
        databaseReference = FirebaseDatabase.getInstance().reference


        databaseReference = FirebaseDatabase.getInstance().getReference("keranjang")

//


        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    keranjangItemList.clear()
                    var totalHarga = 0

                    for (dataSnapshot in snapshot.children) {
                        val judul = dataSnapshot.child("judul").getValue(String::class.java)
                        val hargaString = dataSnapshot.child("harga").getValue(String::class.java)

                        val hargaCleaned = hargaString?.replace("Rp.", "")?.replace(".", "")?.trim()
                        val harga = hargaCleaned?.toIntOrNull()

                        if (harga != null) {
                            val homeData = Detail(judul, harga)
                            keranjangItemList.add(homeData)

                            totalHarga += harga
                        }
                    }

                    // Menampilkan total harga dengan format mata uang
                    val formattedTotalHarga = NumberFormat.getCurrencyInstance(Locale("id", "ID")).format(totalHarga)
                    binding.textTotal.text = formattedTotalHarga

                    binding.recyclerViewTransaksi.adapter = TransaksiAdapter(requireContext(), keranjangItemList as ArrayList<Detail>)
                    binding.recyclerViewTransaksi.adapter?.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), error.toString(), Toast.LENGTH_SHORT).show()
            }
        })

    }
}
