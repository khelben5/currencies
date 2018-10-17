package com.eduardodev.currencies.presentation.conversions

import android.support.design.widget.TextInputLayout
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.eduardodev.currencies.R
import com.eduardodev.currencies.presentation.model.Conversion
import org.jetbrains.anko.find
import java.text.DecimalFormat
import java.util.*

private const val DELTA = 0.001

class ConversionsAdapter(
    private val onCurrencySelected: (Currency) -> Unit,
    private val textWatcher: TextWatcher
) : RecyclerView.Adapter<ConversionsAdapter.ConversionViewHolder>() {

    private val conversions = emptyList<Conversion>().toMutableList()

    fun updateConversions(newConversions: List<Conversion>) {
        DiffUtil.calculateDiff(DiffCallback(conversions, newConversions), false).dispatchUpdatesTo(this)
        conversions.clear()
        conversions.addAll(newConversions)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConversionViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_conversion, parent, false)
        return ConversionViewHolder(itemView, onCurrencySelected, textWatcher)
    }

    override fun getItemCount() = conversions.size

    override fun onBindViewHolder(holder: ConversionViewHolder, position: Int) {
        holder.bind(conversions[position], position == 0)
    }


    class DiffCallback(
        private val oldConversions: List<Conversion>,
        private val newConversions: List<Conversion>
    ) : DiffUtil.Callback() {

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldConversions[oldItemPosition].rate.currency == newConversions[newItemPosition].rate.currency

        override fun getOldListSize() = oldConversions.size

        override fun getNewListSize() = newConversions.size

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            Math.abs(oldConversions[oldItemPosition].value - newConversions[newItemPosition].value) <= DELTA &&
                    (oldItemPosition == 0 && newItemPosition == 0 || oldItemPosition > 0 && newItemPosition > 0)
    }


    class ConversionViewHolder(
        itemView: View,
        onCurrencySelected: (Currency) -> Unit,
        private val textWatcher: TextWatcher
    ) : RecyclerView.ViewHolder(itemView) {

        private lateinit var conversion: Conversion

        private val rootView = itemView.find<View>(R.id.itemConversionRoot)
        private val currencyCode = itemView.find<TextView>(R.id.itemConversionCurrencyCode)
        private val currencyName = itemView.find<TextView>(R.id.itemConversionCurrencyName)
        private val valueLayout = itemView.find<TextInputLayout>(R.id.itemConversionValueLayout)
        private val value = itemView.find<TextView>(R.id.itemConversionValue)

        init {
            rootView.setOnClickListener { onCurrencySelected(conversion.rate.currency) }
        }

        fun bind(conversion: Conversion, enabled: Boolean) {
            this.conversion = conversion

            currencyCode.text = conversion.rate.currency.currencyCode
            currencyName.text = conversion.rate.currency.displayName

            value.removeTextChangedListener(textWatcher)
            value.text = DecimalFormat().apply {
                minimumFractionDigits = 0
                maximumFractionDigits = 2
            }.format(conversion.value)

            if (enabled) {
                valueLayout.isEnabled = true
                value.addTextChangedListener(textWatcher)
            } else {
                valueLayout.isEnabled = false
            }
        }
    }
}