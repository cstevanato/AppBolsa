package br.com.stv.appbolsa.ui.activity.buySellOperations


import br.com.stv.appbolsa.model.BuySell
import br.com.stv.appbolsa.model.SummaryStock
import br.com.stv.appbolsa.ui.activity.BasePresenter
import br.com.stv.appbolsa.ui.activity.BaseView

interface BuySellOperationsContract {

    interface Presenter : BasePresenter {
        fun getBuySellList(stock: String)
        fun getSummaryStock(stock: String)
        fun deleteOperationsQuestion(id: Long, stock: String?)
    }

    interface View: BaseView<Presenter> {
        fun showStocks(stocksByStock: List<BuySell>?)
        fun showSummaryStock(summaryStock: SummaryStock?)
        fun questionDeleteStockGreaterOne(amountDelete: Int, idStock: Long, stock: String?)
        fun questionDeleteStockEqualOne(idStock: Long, stock: String?)
    }

}