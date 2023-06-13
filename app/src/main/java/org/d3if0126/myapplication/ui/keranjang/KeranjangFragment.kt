package org.d3if0126.myapplication.ui.keranjang

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import org.d3if0126.myapplication.R
import org.d3if0126.myapplication.databinding.FragmentKeranjangBinding
import org.d3if0126.myapplication.databinding.FragmentProfileBinding

class KeranjangFragment : Fragment(R.layout.fragment_keranjang) {

    private lateinit var binding: FragmentKeranjangBinding
    private val keranjangItems: MutableList<String> = mutableListOf()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentKeranjangBinding.bind(view)


    }

    fun tambahDataKeKeranjang (namaProduk: String, hargaProduk: String) {
        val item = "$namaProduk - $hargaProduk"
        keranjangItems.add(item)
        Toast.makeText(requireContext(), "Produk ditambahkan ke keranjang", Toast.LENGTH_SHORT).show()
    }
}