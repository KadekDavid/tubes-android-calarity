package org.d3if0126.myapplication.ui.keranjang

import com.google.firebase.database.*
import org.d3if0126.myapplication.model.Home

class KeranjangManager {
    private val keranjangRef: DatabaseReference =
        FirebaseDatabase.getInstance().reference.child("keranjang")

    fun addItemToKeranjang(item: Home) {
        val newItemRef = keranjangRef.push()
        newItemRef.setValue(item)
    }

    fun getKeranjangItems(callback: (List<Home>) -> Unit) {
        keranjangRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val itemList = snapshot.children.mapNotNull { it.getValue(Home::class.java) }
                callback(itemList)
            }
            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

}