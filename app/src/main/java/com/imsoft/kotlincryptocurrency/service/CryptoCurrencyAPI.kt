package com.imsoft.kotlincryptocurrency.service

import com.imsoft.kotlincryptocurrency.model.CryptoCurrencyModel
import retrofit2.Call
import retrofit2.http.GET

interface CryptoCurrencyAPI {

    // https://raw.githubusercontent.com/
    // MaharramSadikhli/APIs/main/cryptocurrencyapi.json

    @GET("MaharramSadikhli/APIs/main/cryptocurrencyapi.json")
    fun getData(): Call<List<CryptoCurrencyModel>>

}