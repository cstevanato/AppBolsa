package br.com.stv.appbolsa.ui.activity.average

import android.content.Context
import br.com.stv.appbolsa.dao.BuySellStockDao
import br.com.stv.appbolsa.dao.SummaryStockDao
import br.com.stv.appbolsa.extension.toBigDecimalBrazilianCurrency
import br.com.stv.appbolsa.ui.activity.buy.BuyData
import br.com.stv.appbolsa.utils.CalculationUtils
import com.crashlytics.android.Crashlytics
import java.text.SimpleDateFormat


class AveragePresenter(private val context: Context,
                       private val avaregeView: AverageContract.View) : AverageContract.Presenter {


    override fun add(stock: String, amount: String, cust: String, updateDate: String) {
        val buyData = BuyData()
        var error = false


        error = validadeStock(buyData, stock, error)
        error = validadeHasStock(stock, error)
        error = validadeDateBuy(buyData, updateDate, error)
        error = validadeAmount(buyData, amount, error)
        error = validadeCustPerStock(buyData, cust, error)

        if (error) return

        try {

            buyData.custOperation = CalculationUtils().custOperation(buyData.cust, buyData.amount,  buyData.rates)
            buyData.avarageOperation = buyData.cust

            BuySellStockDao().insert(buyData)
            avaregeView.AvaregeInserted(buyData.stock)
        } catch (e: Exception) {
            Crashlytics.logException(e)
            e.printStackTrace()
            avaregeView.printError(e.message!!)
        }
    }

    override fun start() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun dispose() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    private fun validadeAmount(buyData: BuyData, amount: String, error: Boolean): Boolean {
        var rError = error
        if (amount.isEmpty()) {
            avaregeView.amountRequired()
            rError = true
        }
        try {
            buyData.amount = amount.toInt()
        } catch (ex: Exception) {
            avaregeView.amountRequired()
            rError = true
        }
        return rError
    }

    private fun validadeDateBuy(buyData: BuyData, dateBuy: String, error: Boolean): Boolean {
        var rError = error
        if (dateBuy.isEmpty()) {
            avaregeView.dateBuyRequired()
            rError = true
        }

        try {
            buyData.updateDate = SimpleDateFormat("dd/MM/yyyy").parse(dateBuy)
        } catch (ex: Exception) {
            avaregeView.dateBuyRequired()
            rError = true
        }

        return rError
    }

    private fun validadeStock(buyData: BuyData, stock: String, error: Boolean): Boolean {
        var rError = error
        if (stock.isEmpty()) {
            avaregeView.stockRequired()
            rError = true
        }

        buyData.stock = stock

        return rError
    }

    private fun validadeCustPerStock(buyData: BuyData, custPerStock: String, error: Boolean): Boolean {
        var rError = error
        if (custPerStock.isEmpty()) {
            avaregeView.custPerStockRequired()
            rError = true
        }
        try {
            buyData.cust = custPerStock.toBigDecimalBrazilianCurrency()
        } catch (ex: Exception) {
            avaregeView.custPerStockRequired()
            rError = true
            ex.printStackTrace()
        }
        return rError
    }

    private fun validadeHasStock(stock: String, error: Boolean): Boolean {
        var rError = error

        val summaryStockByStock = SummaryStockDao().getSummaryStockByStock(stock)

        if (summaryStockByStock != null) {
            avaregeView.stockAlreadyExist()
            rError = true
        }
        return rError
    }
}
