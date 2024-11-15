package com.example.jetpackcomposelogin

class Validate {
    fun validateEmail(email: String):Boolean {
        //Primera opción Android
        //return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        val emailPattern = "^[a-zA-Z0-9_]+([.][a-zA-Z0-9_]+)*@[a-zA-Z0-9_]+([.][a-zA-Z0-9_]+)*[.][a-zA-Z]{2,5}$"
        val regex = Regex (emailPattern)
        return regex.matches(email)
    }
}