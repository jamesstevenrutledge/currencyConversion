package rutledge.james.currencyconverter

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import rutledge.james.currencyconverter.UI.viewModel.CurrencyViewModel
import rutledge.james.currencyconverter.data.ExchangeRateRepositoryImp
import rutledge.james.currencyconverter.data.network.ExchangeRateApi

val currencyConverterKoinModule = module {
    single { RetrofitHelper.getInstance() }
    single { provideExchangeRateApi(get()) }
    single { ExchangeRateRepositoryImp(get(), 1000) }
    viewModel { CurrencyViewModel(get<ExchangeRateRepositoryImp>()) }
}

/**
 * Helps create a retrofit instance for accessing the exchange rates api
 */
object RetrofitHelper {
    private const val baseUrl = "http://api.exchangeratesapi.io/"

    private class AccessKeyInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
            val original = chain.request()

            // Access key should not be kept in source code.
            val url = original.url().newBuilder()
                .addQueryParameter("access_key", "ebd9837cb2a457b7305b47f64da2027c")
                .build()

            val request = original.newBuilder()
                .url(url)
                .build()
            return chain.proceed(request)
        }
    }

    fun getInstance(): Retrofit {
        val okHttpClient = OkHttpClient()
            .newBuilder()
            .addInterceptor(AccessKeyInterceptor())
            .build()

        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}

/**
 * Provides the retrofit API used for gathering exchange rate info
 */
fun provideExchangeRateApi(retrofit: Retrofit): ExchangeRateApi =
    retrofit.create(ExchangeRateApi::class.java)
