package com.imsoft.kotlincryptocurrency.service

import com.imsoft.kotlincryptocurrency.model.CryptoCurrencyModel
import retrofit2.Call
import retrofit2.http.GET

interface CryptoCurrencyAPI {

    //https://github.com/
    // MaharramSadikhli/APIs/blob/main/cryptocurrencyapi.json

    @GET("MaharramSadikhli/APIs/blob/main/cryptocurrencyapi.json")
    fun getData(): Call<List<CryptoCurrencyModel>>

}