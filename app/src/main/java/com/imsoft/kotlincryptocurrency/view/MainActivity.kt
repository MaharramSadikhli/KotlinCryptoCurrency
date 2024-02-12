package com.imsoft.kotlincryptocurrency.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.imsoft.kotlincryptocurrency.R
import com.imsoft.kotlincryptocurrency.model.CryptoCurrencyModel
import com.imsoft.kotlincryptocurrency.service.CryptoCurrencyAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private val baseUrlApi = "https://raw.githubusercontent.com/"
    private var cryptoCurrencyModels: ArrayList<CryptoCurrencyModel>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        loadData()
    }

    private fun loadData() {

        val retrofit = Retrofit
            .Builder()
            .baseUrl(baseUrlApi)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(CryptoCurrencyAPI::class.java)
        val call = service.getData()

        call.enqueue(object: Callback<List<CryptoCurrencyModel>> {
            override fun onResponse(
                call: Call<List<CryptoCurrencyModel>>,
                response: Response<List<CryptoCurrencyModel>>
            ) {

                if (response.isSuccessful) {
                    response.body()?.let {
                        cryptoCurrencyModels = ArrayList(it)

                        for (cryptoCurrency: CryptoCurrencyModel in cryptoCurrencyModels!!) {
                            println("Currency -> ${cryptoCurrency.currency}")
                            println("Price    -> ${cryptoCurrency.price}")
                            println("--------------------------------")
                        }
                    }
                }
            }

            override fun onFailure(call: Call<List<CryptoCurrencyModel>>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }
}