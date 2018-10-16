package com.eduardodev.currencies.presentation.conversions

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.eduardodev.currencies.R
import com.eduardodev.currencies.presentation.model.Conversion
import org.jetbrains.anko.find
import java.text.DecimalFormat


class ConversionsAdapter(private val onConversionSelected: (Conversion) -> Unit)
    : RecyclerView.Adapter<ConversionsAdapter.ConversionViewHolder>() {

    private val conversions = emptyList<Conversion>().toMutableList()

    fun updateConversions(newConversions: List<Conversion>) {
        conversions.clear()
        conversions.addAll(newConversions)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConversionViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_conversion, parent, false)
        return ConversionViewHolder(itemView, onConversionSelected)
    }

    override fun getItemCount() = conversions.size

    override fun onBindViewHolder(holder: ConversionViewHolder, position: Int) {
        holder.bind(conversions[position])
    }


    class ConversionViewHolder(
            itemView: View,
            onItemSelected: (Conversion) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {

        private val rootView = itemView.find<View>(R.id.itemConversionRoot)
        private val currencyCode = itemView.find<TextView>(R.id.itemConversionCurrencyCode)
        private val currencyName = itemView.find<TextView>(R.id.itemConversionCurrencyName)
        private val rate = itemView.find<TextView>(R.id.itemConversionRate)

        private lateinit var conversion: Conversion

        init {
            rootView.setOnClickListener { onItemSelected(conversion) }
        }

        fun bind(conversion: Conversion) {
            this.conversion = conversion
            currencyCode.text = conversion.rate.currency.currencyCode
            currencyName.text = conversion.rate.currency.displayName
            rate.text = DecimalFormat().apply {
                minimumFractionDigits = 2
                maximumFractionDigits = 2
            }.format(conversion.rate.value)
        }
    }
}