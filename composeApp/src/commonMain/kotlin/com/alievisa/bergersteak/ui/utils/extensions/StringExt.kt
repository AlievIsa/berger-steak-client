package com.alievisa.bergersteak.ui.utils.extensions

fun String.toPhoneNumberFormat(): String {
    if (this.length != 11) return this

    val cleanNumber = if (this.first() == '8') {
        "7" + this.substring(1)
    } else {
        this
    }

    return buildString {
        append("+7 (")
        append(cleanNumber.substring(1..3))
        append(") ")
        append(cleanNumber.substring(4..6))
        append("-")
        append(cleanNumber.substring(7..8))
        append("-")
        append(cleanNumber.substring(9..10))
    }
}

fun checkPhoneNumberMatches(phoneNumber: String): Boolean {
    val phoneRegex = Regex("^[78]\\d{10}\$")
    return phoneRegex.matches(phoneNumber)
}

fun checkFormattedPhoneNumberMatches(phoneNumber: String): Boolean {
    val phoneRegex = Regex("^(\\+7|8)[\\s-]?\\(?\\d{3}\\)?[\\s-]?\\d{3}[\\s-]?\\d{2}[\\s-]?\\d{2}\$")
    return phoneRegex.matches(phoneNumber)
}

fun String.filterNumberInput(): String {
    val stripped = Regex("[^0-9]").replace(this, "")
    return if (stripped.length >= 11) {
        stripped.substring(0..10)
    } else {
        stripped
    }
}

fun checkEmailMatches(email: String): Boolean {
    val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")
    return emailRegex.matches(email)
}