package rutledge.james.currencyconverter.UI.Model

import java.util.Currency

// Currency to be shown in the list items
data class DisplayCurrency(
    val currency: Currency,
    val value: Double,
    val isBaseCurrency: Boolean = false,
)