package br.com.stv.appbolsa.ui.activity.buy

import android.content.Context
import br.com.stv.appbolsa.dao.BuySellStockDao
import br.com.stv.appbolsa.extension.toBigDecimalBrazilianCurrency
import br.com.stv.appbolsa.utils.CalculationUtils
import com.crashlytics.android.Crashlytics
import java.text.SimpleDateFormat

class BuyPresenter(private val context: Context,
                   private val summaryView: BuyContract.View) : BuyContract.Presenter {

    override fun calculateStockAverage(amount: String,
                                       cust: String,
                                       rates: String) {
        synchronized(this) {
            val buyData = BuyData()

            if (amount.isNotEmpty()) buyData.amount = amount.toInt()

            if (cust.isNotEmpty()) buyData.cust = cust.toBigDecimalBrazilianCurrency()

            if (rates.isNotEmpty()) buyData.rates = rates.toBigDecimalBrazilianCurrency()

            calculateStockAverage(buyData)

            summaryView.stockAverage(buyData)
        }
    }

    private fun calculateStockAverage(buyData: BuyData) {
        buyData.custOperation = CalculationUtils().custOperation(buyData.cust, buyData.amount,  buyData.rates)
        buyData.avarageOperation = CalculationUtils().stockAverage(buyData.custOperation, buyData.amount)
    }

    override fun add(
            note: String,
            stock: String,
            amount: String,
            cust: String,
            rates: String,
            updateDate: String) {

        val buyData = BuyData()
        var error = false

        error = validadeNote(buyData, note, error)
        error = validadeStock(buyData, stock, error)
        error = validadeDateBuy(buyData, updateDate, error)
        error = validadeAmount(buyData, amount, error)
        error = validadeCustPerStock(buyData, cust, error)
        error = validadeRates(buyData, rates, error)

        if (error) return

        try {
            calculateStockAverage(buyData)

            BuySellStockDao().insert(buyData)

            summaryView.buyInserted(buyData.stock)
        } catch (e: Exception) {
            Crashlytics.logException(e)
            e.printStackTrace()
            summaryView.printError(e.message!!)
        }
    }


    private fun validadeRates(buyData: BuyData, rates: String, error: Boolean): Boolean {
        var rError = error
        if (rates.isEmpty()) {
            summaryView.ratesRequired()
            rError = true
        }
        try {
            buyData.rates = rates.toBigDecimalBrazilianCurrency()
        } catch (ex: Exception) {
            summaryView.ratesRequired()
            rError = true
            ex.printStackTrace()
        }

        return rError
    }

    private fun validadeCustPerStock(buyData: BuyData, custPerStock: String, error: Boolean): Boolean {
        var rError = error
        if (custPerStock.isEmpty()) {
            summaryView.custPerStockRequired()
            rError = true
        }
        try {
            buyData.cust = custPerStock.toBigDecimalBrazilianCurrency()
        } catch (ex: Exception) {
            summaryView.custPerStockRequired()
            rError = true
            ex.printStackTrace()
        }
        return rError
    }

    private fun validadeAmount(buyData: BuyData, amount: String, error: Boolean): Boolean {
        var rError = error
        if (amount.isEmpty()) {
            summaryView.amountRequired()
            rError = true
        }
        try {
            buyData.amount = amount.toInt()
        } catch (ex: Exception) {
            summaryView.amountRequired()
            rError = true
        }
        return rError
    }

    private fun validadeDateBuy(buyData: BuyData, dateBuy: String, error: Boolean): Boolean {
        var rError = error
        if (dateBuy.isEmpty()) {
            summaryView.dateBuyRequired()
            rError = true
        }

        try {
            buyData.updateDate = SimpleDateFormat("dd/MM/yyyy").parse(dateBuy)
        } catch (ex: Exception) {
            summaryView.dateBuyRequired()
            rError = true
        }

        return rError
    }

    private fun validadeStock(buyData: BuyData, stock: String, error: Boolean): Boolean {
        var rError = error
        if (stock.isEmpty()) {
            summaryView.stockRequired()
            rError = true
        }

        buyData.stock = stock

        return rError
    }

    private fun validadeNote(buyData: BuyData, note: String, error: Boolean): Boolean {
        var rError = error
        if (note.isEmpty()) {
            summaryView.noteRequired()
            rError = true
        }

        buyData.note = note

        return rError
    }

    override fun loadSummaries() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun syncTask() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun start() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun dispose() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}