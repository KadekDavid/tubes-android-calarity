package org.d3if0126.myapplication.ui.keranjang

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.d3if0126.myapplication.model.Home

class KeranjangViewModel : ViewModel() {
    val keranjangItemList: MutableLiveData<MutableList<Home>> by lazy {
        MutableLiveData<MutableList<Home>>()
    }

    fun addItemToKeranjang(item: Home) {
        val currentList = keranjangItemList.value ?: mutableListOf()
        currentList.add(item)
        keranjangItemList.value = currentList
    }
}
