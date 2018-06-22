package br.com.stv.appbolsa.ui.activity.sell

import android.app.DatePickerDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.view.View
import android.widget.*
import br.com.stv.appbolsa.R
import br.com.stv.appbolsa.extension.formatForBrazilianCurrency
import br.com.stv.appbolsa.extension.toSimpleString
import br.com.stv.appbolsa.model.SummaryStock
import br.com.stv.appbolsa.ui.activity.buy.BuyData
import kotlinx.android.synthetic.main.activity_sell.*
import android.widget.ArrayAdapter
import br.com.stv.appbolsa.utils.DatePickerFragmentUtils
import java.util.*


class SellActivity : AppCompatActivity(), SellContract.View, DatePickerDialog.OnDateSetListener {

    private lateinit var stockItemSelect: String

    private val sellPresenter: SellContract.Presenter by lazy {
        SellPresenter(this, this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sell)

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
        getSupportActionBar()?.subtitle = getString(R.string.sell_subtitle)
    }

    override fun onSupportNavigateUp(): Boolean {
        this.finish()
        return true
    }

    //region Config Components

    private fun configButtonConfirm() {
        btn_confirm.setOnClickListener {
            sellPresenter.subtract(
                    et_nota.text.toString(),
                    stockItemSelect,
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
        sellPresenter.loadStocksForSales()
        sp_stocks.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
                stockItemSelect = parent.getItemAtPosition(pos).toString()
            }

            override fun onNothingSelected(parent: AdapterView<out Adapter>?) {}

        }
    }

    private fun configDateBuy() {
        et_date_buy.keyListener = null
        et_date_buy.setOnClickListener {
            DatePickerFragmentUtils().show(supportFragmentManager, "et_date_buy")
        }

        et_date_buy.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                DatePickerFragmentUtils().show(supportFragmentManager, "et_date_buy")
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
        input_layout_nota.error = getString(R.string.error_field_required)
    }

    override fun stockRequired() {}

    override fun dateBuyRequired() {
        input_layout_date_buy.error = getString(R.string.error_field_required)

    }

    override fun amountRequired() {
        input_layout_amount.error = getString(R.string.error_field_required)

    }

    override fun ratesRequired() {
        input_layout_rates.error = getString(R.string.error_field_required)

    }

    override fun custPerStockRequired() {
        input_layout_price_stock.error = getString(R.string.error_field_required)
    }

    override fun stockAverage(buyData: BuyData) {
        tv_averagePerStock.text = buyData.averagePerStock.formatForBrazilianCurrency()
        tv_custOperationStock.text = buyData.custOperation.formatForBrazilianCurrency()
    }

    override fun printError(message: String) {
        this.showErrorAlert(this, message)
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }


    private lateinit var listSummary: List<SummaryStock>

    override fun loadStocksForSales(stocksForSales: List<SummaryStock>?) {
        stocksForSales?.let {
            listSummary = it
            // Creating adapter for spinner
            val dataAdapter = ArrayAdapter<SummaryStock>(this, android.R.layout.simple_spinner_item, it)

            // Drop down layout style - list view with radio button
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            // attaching data adapter to spinner
            sp_stocks.adapter = dataAdapter
        }
    }
    //endregion

    private fun calculateStockAverage() {
        sellPresenter.calculateStockAverage(edt_amount.text.toString(), edt_cust.text.toString(), edt_rates.text.toString())
    }

    //region DatePickerDialog.OnDateSetListener

    override fun onDateSet(datePicker: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val cal = GregorianCalendar(year, month, dayOfMonth)
        et_date_buy.setText(cal.time.toSimpleString())
    }

    //endregion

}
