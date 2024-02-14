package com.imsoft.kotlincryptocurrency.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.imsoft.kotlincryptocurrency.adapter.CryptoAdapter
import com.imsoft.kotlincryptocurrency.databinding.ActivityMainBinding
import com.imsoft.kotlincryptocurrency.model.CryptoCurrencyModel
import com.imsoft.kotlincryptocurrency.service.CryptoCurrencyAPI
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), CryptoAdapter.Listener {

    private val baseUrlApi = "https://raw.githubusercontent.com/"
    private lateinit var binding: ActivityMainBinding
    private var cryptoCurrencyModels: ArrayList<CryptoCurrencyModel>? = null
    private lateinit var cryptoAdapter: CryptoAdapter
    private lateinit var compositeDisposable: CompositeDisposable
    private lateinit var job: Job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)

        compositeDisposable = CompositeDisposable()

        job = Job()

        loadData()
    }

    private fun loadData() {

        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrlApi)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        val service = retrofit.create(CryptoCurrencyAPI::class.java)

        job = CoroutineScope(Dispatchers.IO)
            .launch {
                val response = service.getData()

                withContext(Dispatchers.Main) {

                    if (response.isSuccessful) {

                        response.body()?.let {

                            cryptoCurrencyModels = ArrayList(it)

                            cryptoCurrencyModels?.let {
                                cryptoAdapter = CryptoAdapter(it, this@MainActivity)
                                binding.recyclerView.adapter = cryptoAdapter
                            }

                        }

                    }

                }
            }


    }

    override fun onRowClick(cryptoCurrencyModel: CryptoCurrencyModel) {
        Toast.makeText(this@MainActivity, "${cryptoCurrencyModel.price} $", Toast.LENGTH_LONG)
            .show()
    }


    override fun onDestroy() {
        super.onDestroy()

        job.cancel()
    }
}