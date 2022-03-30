package rutledge.james.currencyconverter.UI.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import rutledge.james.currencyconverter.UI.Model.DisplayCurrency
import rutledge.james.currencyconverter.UI.view.adapter.viewHolder.CurrencyViewHolder
import rutledge.james.currencyconverter.databinding.CurrencyListItemBinding
import java.util.Currency

class CurrencyListAdapter(
    private val onEditCallback: (currency: Currency, value: Double) -> Unit,
) : ListAdapter<DisplayCurrency, CurrencyViewHolder>(CurrencyDiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val bnd = CurrencyListItemBinding.inflate(inflater, parent, false)
        return CurrencyViewHolder(bnd)
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        holder.onBind(getItem(position), onEditCallback)
    }

    object CurrencyDiffCallback : DiffUtil.ItemCallback<DisplayCurrency>() {
        override fun areItemsTheSame(oldItem: DisplayCurrency, newItem: DisplayCurrency): Boolean {
            // There can only be 1 currency represented by the same currency code
            return oldItem.currency.currencyCode == newItem.currency.currencyCode
        }

        override fun areContentsTheSame(
            oldItem: DisplayCurrency,
            newItem: DisplayCurrency
        ): Boolean {
            // If the items value has changed, or has become/is no longer a base currency the view should be refreshed.
            // If it was and is still a base currency, it should not be modified (base currencies do not need to have their values updated)
            return (((oldItem.value == newItem.value)
                    and (oldItem.isBaseCurrency == newItem.isBaseCurrency))
                    or (oldItem.isBaseCurrency and newItem.isBaseCurrency))
        }
    }
}