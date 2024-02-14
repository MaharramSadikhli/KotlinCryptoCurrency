package com.imsoft.kotlincryptocurrency.service

import com.imsoft.kotlincryptocurrency.model.CryptoCurrencyModel
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface CryptoCurrencyAPI {

    // https://raw.githubusercontent.com/
    // MaharramSadikhli/APIs/main/cryptocurrencyapi.json

    @GET("MaharramSadikhli/APIs/main/cryptocurrencyapi.json")
    suspend fun getData(): Response<List<CryptoCurrencyModel>>

    //fun getData(): Observable<List<CryptoCurrencyModel>>
    //fun getData(): Call<List<CryptoCurrencyModel>>

}