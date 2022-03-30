package rutledge.james.currencyconverter.data

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import rutledge.james.currencyconverter.data.network.ExchangeRateApi

class ExchangeRateRepositoryImp(
    exchangeRateApi: ExchangeRateApi,
    refreshIntervalMs: Long
) : ExchangeRateRepository {
    override val fetchLatestExchangeRates: Flow<ExchangeRateList> = flow {
        while (true) {
            val result = exchangeRateApi.fetchLatestExchangeRate()

            result.body()?.let {
                emit(it)
            }
            // Only refresh from API call every refreshIntervalMs
            delay(refreshIntervalMs)
        }
    }
}