package io.github.techtastic.hexweb

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient

object HexWeb {
    const val MOD_ID: String = "hexweb"

    val client = OkHttpClient()
    val JSON = "application/json; charset=utf-8".toMediaTypeOrNull()

    @JvmStatic
    fun init() {
    }
}