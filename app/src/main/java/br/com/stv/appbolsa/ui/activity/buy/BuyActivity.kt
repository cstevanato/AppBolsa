package br.com.stv.appbolsa.ui.activity.buy

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import br.com.stv.appbolsa.R
import br.com.stv.appbolsa.extension.formatForBrazilianCurrency
import kotlinx.android.synthetic.main.activity_buy.*


// https://www.journaldev.com/14748/android-textinputlayout-example
class BuyActivity : AppCompatActivity(), BuyContract.View {


    private val buyPresenter: BuyContract.Presenter by lazy {
        BuyPresenter(this, this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buy)

        configActionBar()

        configButtonConfirm()

        configNote()

        configStock()

        configDateBuy()

        configAmount()

        configCustPerStock()

        configRates()

    }

    private fun configActionBar() {
        title = getString(R.string.title)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()?.subtitle = getString(R.string.buy_subtitle)
    }

    override fun onSupportNavigateUp(): Boolean {
        this.finish()
        return true
    }


    //region Config Components

    private fun configButtonConfirm() {
        btn_confirm.setOnClickListener {
            Toast.makeText(this, "You clicked me.", Toast.LENGTH_SHORT).show()


            buyPresenter.add(
                    et_nota.text.toString(),
                    et_stock.text.toString(),
                    edt_amount.text.toString(),
                    edt_cust.text.toString(),
                    edt_rates.text.toString(),
                    et_date_buy.text.toString())

            this.showSucessAlert(this, "Inclido com sucesso.") {
                this.finish()
            }
        }
    }

    private fun configNote() {
        et_nota.filters = arrayOf<InputFilter>(InputFilter.AllCaps())
        et_nota.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                input_layout_nota.error = null
                input_layout_nota.isErrorEnabled = false
            }
        }
    }

    private fun configStock() {
        et_stock.filters = arrayOf<InputFilter>(InputFilter.AllCaps())
        et_stock.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                input_layout_price_stock.error = null
                input_layout_price_stock.isErrorEnabled = false
            }
        }
    }

    private fun configDateBuy() {
        et_date_buy.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                input_layout_date_buy.error = null
                input_layout_date_buy.isErrorEnabled = false
            }
        }
    }

    private fun configAmount() {
        edt_amount.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable?) {
                calculateStockAverage()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })

        edt_amount.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                input_layout_amount.error = null
                input_layout_amount.isErrorEnabled = false
            }
        }
    }

    private fun configCustPerStock() {

        edt_cust.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable?) {
                calculateStockAverage()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })

        edt_cust.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                input_layout_price_stock.error = null
                input_layout_price_stock.isErrorEnabled = false
            }
        }
    }

    private fun configRates() {
        edt_rates.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable?) {
                calculateStockAverage()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })

        edt_rates.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                input_layout_rates.error = null
                input_layout_rates.isErrorEnabled = false
            }
        }
    }
    //endregion

    //region BuyContract.View

    override fun noteRequired() {
        input_layout_nota.setError(getString(R.string.error_field_required))
    }

    override fun stockRequired() {
        input_layout_stock.setError(getString(R.string.error_field_required))

    }

    override fun dateBuyRequired() {
        input_layout_date_buy.setError(getString(R.string.error_field_required))

    }

    override fun amountRequired() {
        input_layout_amount.setError(getString(R.string.error_field_required))

    }

    override fun ratesRequired() {
        input_layout_rates.setError(getString(R.string.error_field_required))

    }

    override fun custPerStockRequired() {
        input_layout_price_stock.setError(getString(R.string.error_field_required))
    }

    override fun stockAverage(buyData: BuyData) {
        tv_averagePerStock.text = buyData.averagePerStock.formatForBrazilianCurrency()
        tv_custOperationStock.text = buyData.custOperation.formatForBrazilianCurrency()
    }

    override fun printError(message: String) {
        this.showErrorAlert(this, message)
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
    //endregion

    private fun calculateStockAverage() {
        buyPresenter.calculateStockAverage(edt_amount.text.toString(), edt_cust.text.toString(), edt_rates.text.toString())
    }

}
