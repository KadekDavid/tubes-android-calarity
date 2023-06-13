package org.d3if0126.myapplication.model

data class Detail(
    val id : String? = "" ,
    val pid : String? = "" ,
    var url: String? = "",
    var judul: String? = "",
    var kategori: String = "",
    var harga: String? = "",
    var deskripsi: String? = ""
)
