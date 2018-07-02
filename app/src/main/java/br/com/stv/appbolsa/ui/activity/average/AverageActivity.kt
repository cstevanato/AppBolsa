package br.com.stv.appbolsa.ui.activity.average

import android.app.DatePickerDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.InputFilter
import android.widget.DatePicker
import android.widget.Toast
import br.com.stv.appbolsa.R
import br.com.stv.appbolsa.extension.toSimpleString
import br.com.stv.appbolsa.utils.DatePickerFragmentUtils
import kotlinx.android.synthetic.main.activity_avarege.*
import java.util.*

class AverageActivity  : AppCompatActivity(), AverageContract.View, DatePickerDialog.OnDateSetListener {

    private val averagePresenter: AverageContract.Presenter by lazy {
        AveragePresenter(this, this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_avarege)

        configActionBar()

        configButtonConfirm()

        configStock()

        configDateBuy()

        configAmount()

        configCustPerStock()

    }

    private fun configActionBar() {
        title = getString(R.string.title)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()?.subtitle = getString(R.string.avg_title)
    }

    override fun onSupportNavigateUp(): Boolean {
        this.finish()
        return true
    }


    //region Config Components

    private fun configButtonConfirm() {
        btn_confirm.setOnClickListener {
            averagePresenter.add(
                    et_stock.text.toString(),
                    edt_amount.text.toString(),
                    edt_cust.text.toString(),
                    et_date_buy.text.toString())
            
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

        edt_amount.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                input_layout_amount.error = null
                input_layout_amount.isErrorEnabled = false
            }
        }
    }

    private fun configCustPerStock() {

        edt_cust.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                input_layout_price_stock.error = null
                input_layout_price_stock.isErrorEnabled = false
            }
        }
    }

    //endregion

    //region BuyContract.View

    override fun stockRequired() {
        input_layout_stock.error = getString(R.string.error_field_required)

    }

    override fun dateBuyRequired() {
        input_layout_date_buy.error = getString(R.string.error_field_required)

    }

    override fun amountRequired() {
        input_layout_amount.error = getString(R.string.error_field_required)

    }

    override fun custPerStockRequired() {
        input_layout_price_stock.error = getString(R.string.error_field_required)
    }

    override fun stockAlreadyExist() {
        this.showErrorAlert(this, "Ops, não é possível incluir uma média para Ações que já existam.\nFavor incluir uma compra ou venda.")
    }

    override fun printError(message: String) {
        this.showErrorAlert(this, message)
    }

    override fun AvaregeInserted(stock: String) {
        this.showSucessAlert(this, "Ação: ${stock}.\nIncluída com sucesso") {
            this.finish()
        }
    }
    //endregion

    //region DatePickerDialog.OnDateSetListener

    override fun onDateSet(datePicker: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val cal = GregorianCalendar(year, month, dayOfMonth)
        et_date_buy.setText(cal.time.toSimpleString())
    }

    //endregion

}
