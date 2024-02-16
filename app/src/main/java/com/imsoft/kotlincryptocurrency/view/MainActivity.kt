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
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), CryptoAdapter.Listener {

    private val baseUrlApi = "https://raw.githubusercontent.com/"
    private lateinit var binding: ActivityMainBinding
    private var cryptoCurrencyModels: ArrayList<CryptoCurrencyModel>? = null
    private lateinit var cryptoAdapter: CryptoAdapter
    private lateinit var compositeDisposable: CompositeDisposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)

        compositeDisposable = CompositeDisposable()

        loadData()
    }

    private fun loadData() {

        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrlApi)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        val service = retrofit.create(CryptoCurrencyAPI::class.java)

        compositeDisposable.add(
            service
                .getData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handlerResponse)
        )


    }

    override fun onRowClick(cryptoCurrencyModel: CryptoCurrencyModel) {
        Toast.makeText(this@MainActivity, "${cryptoCurrencyModel.price} $", Toast.LENGTH_LONG)
            .show()
    }

    private fun handlerResponse(cryptoList: List<CryptoCurrencyModel>) {
        cryptoCurrencyModels = ArrayList(cryptoList)

        cryptoCurrencyModels?.let {
            cryptoAdapter = CryptoAdapter(it, this@MainActivity)
            binding.recyclerView.adapter = cryptoAdapter
        }

    }

    override fun onDestroy() {
        super.onDestroy()

        compositeDisposable.clear()
    }
}
