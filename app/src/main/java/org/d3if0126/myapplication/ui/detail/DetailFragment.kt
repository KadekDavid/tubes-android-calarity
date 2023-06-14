package org.d3if0126.myapplication.ui.detail


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.firebase.database.*
import org.d3if0126.myapplication.R
import org.d3if0126.myapplication.adapter.KeranjangAdapter
import org.d3if0126.myapplication.databinding.FragmentDetailBinding
import org.d3if0126.myapplication.model.Home
import org.d3if0126.myapplication.ui.home.HomeAdapter
import org.d3if0126.myapplication.ui.keranjang.KeranjangFragment

class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding

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

        val judul = arguments?.getString("judul")
        val harga = arguments?.getString("harga")
        val url = arguments?.getString("url")


        binding.textJudul.text = judul
        binding.textHarga.text = harga


        Glide.with(requireContext())
            .load(url)
            .into(binding.viewImage)

        binding.btnKeranjang.setOnClickListener {
            val namaProduk = binding.textJudul.text.toString()
            val hargaProduk = binding.textHarga.text.toString()
            val imageUrl = arguments?.getString("url")

            val keranjangFragment = KeranjangFragment()

            val bundle = Bundle()
            bundle.putString("judul", namaProduk)
            bundle.putString("harga", hargaProduk)
            bundle.putString("url", imageUrl)

            keranjangFragment.arguments = bundle

            // Navigasi ke KeranjangFragment menggunakan action yang telah ditentukan di nav_graph.xml
            val navController = findNavController()
            navController.navigate(R.id.action_detailFragment_to_keranjangFragment, bundle)
        }

        binding.floatingActionButton.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home -> false

                R.id.account -> {
                    Navigation.findNavController(requireActivity(), R.id.fragmentContainerView)
                        .navigate(R.id.action_detailFragment_to_profileFragment)
                    true
                }
                else -> false
            }
        }
//
    }
}