package br.com.stv.appbolsa.ui.activity.buy

import br.com.stv.appbolsa.ui.activity.BasePresenter
import br.com.stv.appbolsa.ui.activity.BaseView

interface BuyContract {

    interface Presenter : BasePresenter {
        fun loadSummaries()
        fun syncTask()
        fun add( note: String,
                 stock: String,
                 amount: String,
                 cust: String,
                 rates: String,
                 updateDate: String)
        /**
         * Calcula valores média por ação e custo total da operação por ação
         */
        fun calculateStockAverage(toString: String, toString1: String, toString2: String)
    }

    interface View: BaseView<Presenter> {
        fun noteRequired()
        fun stockRequired()
        fun dateBuyRequired()
        fun amountRequired()
        fun ratesRequired()
        fun custPerStockRequired()
        fun stockAverage(buyData: BuyData)
    }

}