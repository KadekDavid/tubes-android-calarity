package org.d3if0126.myapplication.ui.user

import androidx.annotation.Keep


@Keep
data class User (
    var name: String? = "",
    var email: String? = "",
    var password: String? = ""
) {
    constructor():this ("", "","")
}


