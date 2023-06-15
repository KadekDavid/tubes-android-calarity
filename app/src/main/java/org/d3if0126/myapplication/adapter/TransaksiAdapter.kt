package org.d3if0126.myapplication.adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.d3if0126.myapplication.R
import org.d3if0126.myapplication.model.Detail
import org.d3if0126.myapplication.model.Home
import org.d3if0126.myapplication.ui.detail.DetailFragment
import org.d3if0126.myapplication.ui.home.HomeFragment
import org.d3if0126.myapplication.ui.transaksi.TransaksiFragment
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class TransaksiAdapter(
    private val context: Context,
    private val listImages: ArrayList<Detail>
) : RecyclerView.Adapter<TransaksiAdapter.ViewHolder>() {

    private var trasaksiItemList: List<Detail> = listOf()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val judulTextView: TextView = itemView.findViewById(R.id.textJudul)
        val hargaTextView: TextView = itemView.findViewById(R.id.tvharga)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item_history, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = listImages[position]

        holder.judulTextView.text = currentItem.judul

        val formattedHarga = NumberFormat.getCurrencyInstance(Locale("id", "ID")).format(currentItem.harga)
        holder.hargaTextView.text = formattedHarga


        holder.itemView.setOnClickListener {
            val intent = Intent(context, TransaksiFragment::class.java)

            intent.putExtra("judul", currentItem.judul)
            intent.putExtra("harga", currentItem.harga)

            holder.itemView.setOnClickListener {
                val bundle = bundleOf(

                    "judul" to currentItem.judul,
                    "harga" to currentItem.harga,

                    )
                it.findNavController().navigate(R.id.transaksiFragment, bundle)
            }


            val judul = currentItem.judul
            val harga = currentItem.harga


            val homeFragment = HomeFragment()

            val bundle = Bundle()

            bundle.putString("judul", judul)
            bundle.putString("harga", harga.toString())


            homeFragment.arguments = bundle

            // Navigasi ke KeranjangFragment
            it.findNavController().navigate(R.id.transaksiFragment, bundle)
        }
    }
    override fun getItemCount(): Int {
        return listImages.size
    }

}

