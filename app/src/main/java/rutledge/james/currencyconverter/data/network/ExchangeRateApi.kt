package rutledge.james.currencyconverter.data.network

import retrofit2.Response
import retrofit2.http.GET
import rutledge.james.currencyconverter.data.ExchangeRateList

/**
 * Fetch information about the current exchange rates
 */
interface ExchangeRateApi {
    @GET("/v1/latest")
    suspend fun fetchLatestExchangeRate(): Response<ExchangeRateList>
}
