package rutledge.james.currencyconverter.UI.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.koin.androidx.viewmodel.ext.android.viewModel
import rutledge.james.currencyconverter.UI.view.adapter.CurrencyListAdapter
import rutledge.james.currencyconverter.UI.viewModel.CurrencyViewModel
import rutledge.james.currencyconverter.databinding.CurrencyListViewBinding

class CurrencyListActivity : AppCompatActivity() {
    private val currencyViewModel: CurrencyViewModel by viewModel()

    lateinit var bnd: CurrencyListViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bnd = CurrencyListViewBinding.inflate(layoutInflater)

        // Set up list view
        val currencyAdapter = CurrencyListAdapter { newCurrency, newBaseRate ->
            currencyViewModel.changeBaseRate(newBaseRate)
            currencyViewModel.changeBaseCurrency(newCurrency)
        }

        val linearLayoutManager = LinearLayoutManager(
            this,
            RecyclerView.VERTICAL,
            false
        )

        val dividerItemDecoration = DividerItemDecoration(
            this,
            linearLayoutManager.orientation
        )

        bnd.lstCurrencies.apply {
            adapter = currencyAdapter
            layoutManager = linearLayoutManager
            addItemDecoration(dividerItemDecoration)
        }

        val view = bnd.root
        setContentView(view)

        attachObservers()
    }

    private fun attachObservers() {
        // Update the list view if the list changes
        currencyViewModel.currencies.observe(this) {
            (bnd.lstCurrencies.adapter as CurrencyListAdapter).submitList(it)
        }
    }
}