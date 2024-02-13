package com.imsoft.kotlincryptocurrency.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.imsoft.kotlincryptocurrency.databinding.RecyclerRowBinding
import com.imsoft.kotlincryptocurrency.model.CryptoCurrencyModel

class CryptoAdapter(val cryptoList: ArrayList<CryptoCurrencyModel>): RecyclerView.Adapter<CryptoAdapter.CryptoViewHolder>() {

    class CryptoViewHolder(val binding: RecyclerRowBinding): RecyclerView.ViewHolder(binding.root) {

        fun bindViewHolder() {

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RecyclerRowBinding.inflate(layoutInflater, parent, false)

        return  CryptoViewHolder(binding)
    }


    override fun onBindViewHolder(holder: CryptoViewHolder, position: Int) {
        holder.bindViewHolder()
    }


    override fun getItemCount(): Int {
        return cryptoList.size
    }

}