package rutledge.james.currencyconverter

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class CurrencyConverterApplication : Application() {
    override fun onCreate() {
        startKoin {
            androidContext(this@CurrencyConverterApplication)
            modules(
                currencyConverterKoinModule
            )
        }
        super.onCreate()
    }
}