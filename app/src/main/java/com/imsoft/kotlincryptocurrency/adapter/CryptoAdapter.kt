package com.imsoft.kotlincryptocurrency.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.imsoft.kotlincryptocurrency.databinding.RecyclerRowBinding
import com.imsoft.kotlincryptocurrency.model.CryptoCurrencyModel

class CryptoAdapter(val cryptoList: ArrayList<CryptoCurrencyModel>, val listener: Listener) :
    RecyclerView.Adapter<CryptoAdapter.CryptoViewHolder>() {

    interface Listener {
        fun onRowClick(cryptoCurrencyModel: CryptoCurrencyModel)
    }

    private val colors: Array<String> = arrayOf(
        "#bf0000",
        "#800000",
        "#400000",
        "#000000",
        "#03002e",
        "#010048",
        "#010057",
        "#02006c"
    )



    class CryptoViewHolder(val binding: RecyclerRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindViewHolder(cryptoCurrencyModel: CryptoCurrencyModel, colors: Array<String>, position: Int, listener: Listener) {

            binding.root.setOnClickListener {
                listener.onRowClick(cryptoCurrencyModel)
            }

            binding.root.setBackgroundColor(Color.parseColor(colors[position % 8]))

            binding.cryptoNameText.text = cryptoCurrencyModel.currency
            binding.cryptoValueText.text = cryptoCurrencyModel.price

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RecyclerRowBinding.inflate(layoutInflater, parent, false)

        return CryptoViewHolder(binding)
    }


    override fun onBindViewHolder(holder: CryptoViewHolder, position: Int) {
        holder.bindViewHolder(cryptoList[position], colors, position, listener)
    }


    override fun getItemCount(): Int {
        return cryptoList.size
    }

}