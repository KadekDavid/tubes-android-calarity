package org.d3if0126.myapplication.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.d3if0126.myapplication.R
import org.d3if0126.myapplication.model.Keranjang

class KeranjangAdapter(
    private val context: Context,
    private val listKeranjang: List<Keranjang>
) : RecyclerView.Adapter<KeranjangAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageProduk: ImageView = itemView.findViewById(R.id.ivProductImg)
        val textNamaProduk: TextView = itemView.findViewById(R.id.judulGambarTv)
        val textHargaProduk: TextView = itemView.findViewById(R.id.hargakeranjang)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_item_keranjang, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val keranjang = listKeranjang[position]
        holder.textNamaProduk.text = keranjang.judul
        holder.textHargaProduk.text = keranjang.harga

        // Tampilkan gambar produk menggunakan Glide atau library lainnya
//        Glide.with(context)
//            .load(keranjang.url)
//            .placeholder(R.drawable.placeholder)
//            .into(holder.imageProduk)
    }

    override fun getItemCount(): Int {
        return listKeranjang.size
    }
}
