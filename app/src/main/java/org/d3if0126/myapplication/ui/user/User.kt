package org.d3if0126.myapplication.ui.user

data class User(
    var name: String? = "",
    var email: String? = "",
    var password: String? = ""
) {
    constructor() : this("", "", "")
}

