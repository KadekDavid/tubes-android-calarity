package org.d3if0126.myapplication.ui.keranjang

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.d3if0126.myapplication.R
import org.d3if0126.myapplication.adapter.KeranjangAdapter
import org.d3if0126.myapplication.databinding.FragmentKeranjangBinding
import org.d3if0126.myapplication.model.Home

class KeranjangFragment : Fragment(R.layout.fragment_keranjang) {
    private lateinit var binding: FragmentKeranjangBinding
    private val keranjangItemList: MutableList<Home> = mutableListOf()
    private lateinit var keranjangAdapter: KeranjangAdapter
    private lateinit var keranjangViewModel: KeranjangViewModel
    private lateinit var imageView: ImageView
    private lateinit var keranjangManager : KeranjangManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentKeranjangBinding.bind(view)
        imageView = binding.imageView4

        keranjangViewModel = ViewModelProvider(this).get(KeranjangViewModel::class.java)




        keranjangAdapter = KeranjangAdapter(requireContext(), keranjangItemList)
        binding.recyclerViewBottomSheet.adapter = keranjangAdapter

        binding.recyclerViewBottomSheet.layoutManager = LinearLayoutManager(requireContext())

        retrieveKeranjangItems()
        getRetrieveKeranjangItems()

        imageView.setOnClickListener {
            Navigation.findNavController(requireActivity(), R.id.fragmentContainerView)
                .navigate(R.id.action_keranjangFragment_to_homeFragment)
        }
    }
    override fun onResume() {
        super.onResume()
        keranjangViewModel.retrieveKeranjangItems()
    }


    private fun retrieveKeranjangItems() {

        // Ambil data keranjang dari arguments
        val judul = arguments?.getString("judul")
        val harga = arguments?.getString("harga")
        val url = arguments?.getString("url")

        // Buat objek Home dari data yang diambil
        val keranjangItem = Home(url, judul, harga)

        // Cek apakah item keranjang sudah ada dalam daftar
        val isItemExist = keranjangItemList.any { it.judul == keranjangItem.judul }

        if (!isItemExist) {
            keranjangViewModel.addItemToKeranjang(keranjangItem)
            keranjangItemList.add(keranjangItem)
        }

        keranjangViewModel.keranjangItemList.observe(viewLifecycleOwner, { itemList ->
            keranjangAdapter.submitList(itemList)
        })

        // Perbarui tampilan RecyclerView
        keranjangAdapter.notifyDataSetChanged()
    }
    private fun getRetrieveKeranjangItems() {
        keranjangViewModel.keranjangItemList.observe(viewLifecycleOwner, { itemList ->
            keranjangItemList.clear()
            keranjangItemList.addAll(itemList)
            keranjangAdapter.notifyDataSetChanged()
        })
    }
}