package org.d3if0126.myapplication.adapter



import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.d3if0126.myapplication.R
import org.d3if0126.myapplication.model.Home
import org.d3if0126.myapplication.ui.home.HomeFragment

//import org.d3if0126.myapplication.ui.keranjang.KeranjangFragment

class KeranjangAdapter(private val context: Context, private var itemList: List<Home>) :
    RecyclerView.Adapter<KeranjangAdapter.ViewHolder>() {

    private var keranjangItemList: List<Home> = listOf()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.ivProductImg)
        val judulTextView: TextView = itemView.findViewById(R.id.judulGambarTv)
        val hargaTextView: TextView = itemView.findViewById(R.id.hargakeranjang)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_keranjang, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentItem = itemList[position]
        Glide.with(context).load(currentItem.url).into(holder.imageView)
        holder.judulTextView.text = currentItem.judul
        holder.hargaTextView.text = currentItem.harga

        holder.itemView.setOnClickListener {

            val url = currentItem.url
            val judul = currentItem.judul
            val harga = currentItem.harga


            val homeFragment = HomeFragment()

            val bundle = Bundle()
            bundle.putString("url", url)
            bundle.putString("judul", judul)
            bundle.putString("harga", harga)


            homeFragment.arguments = bundle

            // Navigasi ke KeranjangFragment
//            it.findNavController().navigate(R.id.keranjangFragment, bundle)
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
    fun submitList(newList: List<Home>) {
        keranjangItemList = newList
        notifyDataSetChanged()
    }

}