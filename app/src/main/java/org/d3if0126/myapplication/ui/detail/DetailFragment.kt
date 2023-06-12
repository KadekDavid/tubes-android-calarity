package org.d3if0126.myapplication.ui.detail


import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.database.*
import org.d3if0126.myapplication.R
import org.d3if0126.myapplication.databinding.FragmentDetailBinding


class DetailFragment : Fragment(R.layout.fragment_detail) {

    private lateinit var binding: FragmentDetailBinding
    private lateinit var databaseReference: DatabaseReference

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        databaseReference = FirebaseDatabase.getInstance().getReference("mitraGambar")

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (dataSnapshot in dataSnapshot.children) {

                        val judul = dataSnapshot.child("judul").getValue(String::class.java)
                        val harga = dataSnapshot.child("harga").getValue(String::class.java)
                        val deskripsi = dataSnapshot.child("deskripsi").getValue(String::class.java)

                        binding.textHarga.text = harga
                        binding.txtDeskripsi.text = deskripsi
                        binding.textJudul.text = judul
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), error.toString(), Toast.LENGTH_SHORT).show()
            }
        })
    }
}