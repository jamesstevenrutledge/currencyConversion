package rutledge.james.currencyconverter.UI.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import rutledge.james.currencyconverter.UI.Model.DisplayCurrency
import rutledge.james.currencyconverter.data.ExchangeRateRepository
import java.util.Currency

class CurrencyViewModel(
    private val exchangeRateRepository: ExchangeRateRepository
) : ViewModel() {
    // Subscribe to see display currencies
    val currencies: LiveData<List<DisplayCurrency>>
        get() = mutableCurrencies
    private val mutableCurrencies = MutableLiveData<List<DisplayCurrency>>(emptyList())

    private val baseRateFlow = MutableStateFlow<Double>(0.0)

    // Change what value the conversions should be calculated off
    fun changeBaseRate(baseRate: Double) {
        viewModelScope.launch(Dispatchers.IO) {
            baseRateFlow.emit(baseRate)
        }
    }

    fun changeBaseCurrency(baseCurrency: Currency) {
        // TODO Calculate off new currency if the input currency has changed
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            // If either the exchange rates change, or the base value is changed, we should recalculate the conversions
            exchangeRateRepository.fetchLatestExchangeRates.combine(baseRateFlow) { exchangeRates, toConvert ->
                val baseCurrency = DisplayCurrency(
                    Currency.getInstance(exchangeRates.base),
                    toConvert,
                    true
                )

                val currencies = listOf(baseCurrency) + exchangeRates.rates.map {
                    DisplayCurrency(Currency.getInstance(it.key), it.value * toConvert)
                }
                currencies
            }.collect {
                mutableCurrencies.postValue(it)
            }
        }
    }
}