package org.d3if0126.myapplication.ui.home

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
import org.d3if0126.myapplication.model.Home
import org.d3if0126.myapplication.ui.detail.DetailFragment
import org.d3if0126.myapplication.ui.keranjang.KeranjangFragment

class HomeAdapter(
    private val context: Context,
    private val listImages: ArrayList<Home>
) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    private var keranjangItemList: List<Home> = listOf()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.ivProductImg)
        val judulTextView: TextView = itemView.findViewById(R.id.tvProductName)
        val hargaTextView: TextView = itemView.findViewById(R.id.tvProductPrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item_gambar, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = listImages[position]
        Glide.with(context).load(currentItem.url).into(holder.imageView)
        holder.judulTextView.text = currentItem.judul
        holder.hargaTextView.text = currentItem.harga


        holder.itemView.setOnClickListener {
            val intent = Intent(context, DetailFragment::class.java)
            intent.putExtra("url", currentItem.url)
            intent.putExtra("judul", currentItem.judul)
            intent.putExtra("harga", currentItem.harga)

            holder.itemView.setOnClickListener {
                val bundle = bundleOf(
                    "url" to currentItem.url,
                    "judul" to currentItem.judul,
                    "harga" to currentItem.harga,
                    "deskripsi" to currentItem.deskripsi
                )
                it.findNavController().navigate(R.id.detailFragment, bundle)
            }

            val url = currentItem.url
            val judul = currentItem.judul
            val harga = currentItem.harga


            val keranjangFragment = KeranjangFragment()

            val bundle = Bundle()
            bundle.putString("url", url)
            bundle.putString("judul", judul)
            bundle.putString("harga", harga)


            keranjangFragment.arguments = bundle

            // Navigasi ke KeranjangFragment
            it.findNavController().navigate(R.id.detailFragment, bundle)
        }
    }
    override fun getItemCount(): Int {
        return listImages.size
    }

}




