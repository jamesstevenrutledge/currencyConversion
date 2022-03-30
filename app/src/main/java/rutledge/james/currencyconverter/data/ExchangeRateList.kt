package rutledge.james.currencyconverter.data

/**
 * This information comes from the API call in ExchangeRateApi
 */
data class ExchangeRateList(
    val base: String,
    val rates: Map<String, Double>
)
