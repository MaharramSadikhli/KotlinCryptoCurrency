package com.imsoft.kotlincryptocurrency.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.imsoft.kotlincryptocurrency.R
import com.imsoft.kotlincryptocurrency.adapter.CryptoAdapter
import com.imsoft.kotlincryptocurrency.databinding.ActivityMainBinding
import com.imsoft.kotlincryptocurrency.model.CryptoCurrencyModel
import com.imsoft.kotlincryptocurrency.service.CryptoCurrencyAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), CryptoAdapter.Listener {

    private val baseUrlApi = "https://raw.githubusercontent.com/"
    private lateinit var binding: ActivityMainBinding
    private var cryptoCurrencyModels: ArrayList<CryptoCurrencyModel>? = null
    private lateinit var cryptoAdapter: CryptoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)

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

                        cryptoCurrencyModels?.let { crypto ->
                            cryptoAdapter = CryptoAdapter(crypto, this@MainActivity)
                            binding.recyclerView.adapter = cryptoAdapter
                        }
                    }
                }
            }

            override fun onFailure(call: Call<List<CryptoCurrencyModel>>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }

    override fun onRowClick(cryptoCurrencyModel: CryptoCurrencyModel) {
        Toast.makeText(this@MainActivity, "${cryptoCurrencyModel.price} $", Toast.LENGTH_LONG).show()
    }
}