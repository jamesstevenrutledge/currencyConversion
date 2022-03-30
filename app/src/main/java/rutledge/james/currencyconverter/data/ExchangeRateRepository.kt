package rutledge.james.currencyconverter.data

import kotlinx.coroutines.flow.Flow

interface ExchangeRateRepository {
    val fetchLatestExchangeRates: Flow<ExchangeRateList>
}