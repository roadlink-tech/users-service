package com.peya.usersservice.domain.user

class Normalizer {

    companion object {
        fun removeWhiteSpaces(data: String): String {
            return data.replace("\\s".toRegex(), "")
        }
    }
}