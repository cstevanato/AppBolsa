package br.com.stv.appbolsa.ui.activity.average

import br.com.stv.appbolsa.ui.activity.BasePresenter
import br.com.stv.appbolsa.ui.activity.BaseView
import br.com.stv.appbolsa.ui.activity.buy.BuyData

interface AverageContract {

    interface Presenter : BasePresenter {

        fun add(stock: String,
                amount: String,
                cust: String,
                updateDate: String)

    }

    interface View : BaseView<Presenter> {

        fun stockRequired()
        fun dateBuyRequired()
        fun amountRequired()
        fun custPerStockRequired()
        fun stockAlreadyExist()
        fun AvaregeInserted(stock: String)
    }
}