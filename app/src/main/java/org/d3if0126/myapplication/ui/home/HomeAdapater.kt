package org.d3if0126.myapplication.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.d3if0126.myapplication.R

class HomeAdapter(
    private val context: Context,
    private val listImages: ArrayList<HomeDataClass>
) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.ImageView2)
        val judulTextView: TextView = itemView.findViewById(R.id.judul)
        val hargaTextView: TextView = itemView.findViewById(R.id.harga)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_home, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = listImages[position]
        Glide.with(context).load(currentItem.url).into(holder.imageView)
        holder.judulTextView.text = currentItem.judul
        holder.hargaTextView.text = currentItem.harga
    }

    override fun getItemCount(): Int {
        return listImages.size
    }
}

