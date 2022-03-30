package rutledge.james.currencyconverter.UI.view.adapter.viewHolder

import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.RecyclerView
import rutledge.james.currencyconverter.UI.Model.DisplayCurrency
import rutledge.james.currencyconverter.UI.util.Flag
import rutledge.james.currencyconverter.databinding.CurrencyListItemBinding
import java.text.NumberFormat
import java.util.Currency

class CurrencyViewHolder(private val currencyListItemBinding: CurrencyListItemBinding) :
    RecyclerView.ViewHolder(currencyListItemBinding.root) {
    fun onBind(
        currency: DisplayCurrency,
        onEditCallback: (currency: Currency, value: Double) -> Unit
    ) {
        currencyListItemBinding.apply {
            // Set the flag icon
            Flag.getFlagFromID(currency.currency.currencyCode, root.context)?.let {
                val flag = AppCompatResources.getDrawable(currencyListItemBinding.root.context, it)
                flagIcon.setImageDrawable(flag)
                flagIcon.visibility = View.VISIBLE
            } ?: run {
                flagIcon.visibility = View.GONE
            }

            // Set the text
            txtCurrencyCode.text = currency.currency.currencyCode
            txtCurrencyName.text = currency.currency.displayName

            when (currency.isBaseCurrency) {
                true -> {
                    // Set up input box if it is the base currency
                    editTxtInput.visibility = View.VISIBLE
                    txtResult.visibility = View.GONE

                    // If text box is edited (and not empty) update other values
                    editTxtInput.doAfterTextChanged { text ->
                        text.toString().toDoubleOrNull()?.let {
                            onEditCallback(currency.currency, it)
                        }
                    }
                }
                false -> {
                    // Set up calculated currency items
                    txtResult.visibility = View.VISIBLE
                    editTxtInput.visibility = View.GONE

                    // Format amount with the correct formatting for that currency
                    val currencyFormat = NumberFormat.getCurrencyInstance()
                    currencyFormat.currency = currency.currency
                    currencyFormat.maximumFractionDigits = currency.currency.defaultFractionDigits

                    // Set the value text
                    txtResult.text = currencyFormat.format(currency.value)
                }
            }
        }
    }
}