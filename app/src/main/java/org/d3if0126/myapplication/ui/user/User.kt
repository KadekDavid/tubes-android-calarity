package org.d3if0126.myapplication.ui.user

import androidx.annotation.Keep


@Keep
data class User (

    var email: String? = "",
    var namaPengguna: String? = "",
    var name: String? = "",
    var password: String? = ""

) {
    constructor():this ("", "","","")
}


