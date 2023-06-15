package org.d3if0126.myapplication.ui.transaksi

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import org.d3if0126.myapplication.R
import org.d3if0126.myapplication.databinding.FragmentProfileBinding
import org.d3if0126.myapplication.databinding.FragmentTransaksiBinding

class TransaksiFragment : Fragment(R.layout.fragment_transaksi) {
//    private lateinit var binding: FragmentTransaksiBinding
//    private lateinit var transaksiAdapter: TransaksiAdapter
//    private lateinit var transaksiViewModel: TransaksiViewModel
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        binding = FragmentTransaksiBinding.bind(view)
//
//        // Initialize the RecyclerView and its adapter
//        transaksiAdapter = TransaksiAdapter()
//        binding.recyclerView.adapter = transaksiAdapter
//        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
//
//        // Initialize the TransaksiViewModel
//        transaksiViewModel = ViewModelProvider(this).get(TransaksiViewModel::class.java)
//
//        // Observe the list of transaksi items from the view model
//        transaksiViewModel.transaksiItemList.observe(viewLifecycleOwner) { itemList ->
//            // Update the RecyclerView adapter with the new list of items
//            transaksiAdapter.submitList(itemList)
//        }
//    }
}
