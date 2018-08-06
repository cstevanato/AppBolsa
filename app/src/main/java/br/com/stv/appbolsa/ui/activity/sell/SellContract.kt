package br.com.stv.appbolsa.ui.activity.sell

import br.com.stv.appbolsa.model.SummaryStock
import br.com.stv.appbolsa.ui.activity.BasePresenter
import br.com.stv.appbolsa.ui.activity.BaseView
import br.com.stv.appbolsa.ui.activity.buy.BuyData

interface SellContract {

    interface Presenter : BasePresenter {

        fun subtract(note: String,
                     stock: String,
                     amount: String,
                     cust: String,
                     rates: String,
                     updateDate: String)
        /**
         * Calcula valores média por ação e custo total da operação por ação
         */
        fun calculateStockAverage(amount: String,
                                  cust: String,
                                  rates: String)

        fun loadStocksForSales()
    }

    interface View: BaseView<Presenter> {
        fun noteRequired()
        fun stockRequired()
        fun dateBuyRequired()
        fun amountRequired()
        fun ratesRequired()
        fun custPerStockRequired()
        fun stockAverage(buyData: BuyData)
        fun loadStocksForSales(stocksForSales: List<SummaryStock>?)
        fun sellInserted(stock: String)
    }

}