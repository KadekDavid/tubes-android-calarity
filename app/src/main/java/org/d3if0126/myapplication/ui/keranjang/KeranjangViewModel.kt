package org.d3if0126.myapplication.ui.keranjang

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.d3if0126.myapplication.model.Home
import androidx.lifecycle.LiveData


class KeranjangViewModel : ViewModel() {
    private val keranjangManager = KeranjangManager()

    private val _keranjangItemList: MutableLiveData<List<Home>> = MutableLiveData()
    val keranjangItemList: LiveData<List<Home>> get() = _keranjangItemList

    fun addItemToKeranjang(item: Home) {
        keranjangManager.addItemToKeranjang(item)
    }

    fun retrieveKeranjangItems() {
        keranjangManager.getKeranjangItems { itemList ->
            _keranjangItemList.value = itemList
        }
    }
}