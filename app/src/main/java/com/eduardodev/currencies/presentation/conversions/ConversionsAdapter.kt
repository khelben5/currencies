package com.eduardodev.currencies.presentation.conversions

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.eduardodev.currencies.R
import com.eduardodev.currencies.presentation.model.Conversion


class ConversionsAdapter : RecyclerView.Adapter<ConversionsAdapter.ConversionViewHolder>() {

    private val conversions = emptyList<Conversion>().toMutableList()

    fun updateConversions(newConversions: List<Conversion>) {
        conversions.clear()
        conversions.addAll(newConversions)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ConversionViewHolder(
            LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_conversion, parent, false)
    )

    override fun getItemCount() = conversions.size

    override fun onBindViewHolder(holder: ConversionViewHolder, position: Int) {
        holder.bind(conversions[position])
    }


    class ConversionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val currencyCode = itemView.findViewById<TextView>(R.id.itemConversionCurrencyCode)

        fun bind(conversion: Conversion) {
            currencyCode.text = conversion.currencyCode
        }
    }
}