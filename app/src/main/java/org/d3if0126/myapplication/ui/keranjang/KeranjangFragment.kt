package org.d3if0126.myapplication.ui.keranjang

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import org.d3if0126.myapplication.R
import org.d3if0126.myapplication.adapter.KeranjangAdapter
import org.d3if0126.myapplication.databinding.FragmentKeranjangBinding
import org.d3if0126.myapplication.model.Home

class KeranjangFragment : Fragment(R.layout.fragment_keranjang){

    private lateinit var binding: FragmentKeranjangBinding
    private val keranjangItemList: MutableList<Home> = mutableListOf()
    private lateinit var keranjangAdapter: KeranjangAdapter
    private lateinit var keranjangViewModel: KeranjangViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentKeranjangBinding.bind(view)
        keranjangViewModel = ViewModelProvider(requireActivity()).get(KeranjangViewModel::class.java)

        keranjangAdapter = KeranjangAdapter(requireContext(), keranjangItemList)
        binding.recyclerViewBottomSheet.adapter = keranjangAdapter

        binding.recyclerViewBottomSheet.layoutManager = LinearLayoutManager(requireContext())

        retrieveKeranjangItems()
    }

    private fun retrieveKeranjangItems() {
        // Ambil data keranjang dari arguments
        val judul = arguments?.getString("judul")
        val harga = arguments?.getString("harga")
        val url = arguments?.getString("url")

        // Buat objek Home dari data yang diambil
        val keranjangItem = Home(url, judul, harga)

        keranjangViewModel.addItemToKeranjang(keranjangItem)

        keranjangViewModel.keranjangItemList.observe(viewLifecycleOwner, { itemList ->
            keranjangAdapter.submitList(itemList)
        })

        // Tambahkan objek Home ke list keranjangItemList
        keranjangItemList.add(keranjangItem)

        // Perbarui tampilan RecyclerView
        keranjangAdapter.notifyDataSetChanged()
    }
}
