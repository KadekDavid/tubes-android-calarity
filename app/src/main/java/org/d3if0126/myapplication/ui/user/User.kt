package org.d3if0126.myapplication.ui.user

import androidx.annotation.Keep


@Keep
data class User (
    var noHp: String? = "",
    var alamat: String? = "",
    var name: String? = "",
    var email: String? = "",
    var password: String? = ""
) {
    constructor():this ("", "","","","")
}


