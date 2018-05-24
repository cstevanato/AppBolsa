package br.com.stv.appbolsa.ui.activity.buy

import br.com.stv.appbolsa.dao.ISummaryStock
import br.com.stv.appbolsa.ui.activity.BasePresenter
import br.com.stv.appbolsa.ui.activity.BaseView

interface BuyContract {

    interface Presenter : BasePresenter {
        fun loadSummaries()
        fun syncTask()
    }

    interface View: BaseView<Presenter> {
        fun showTasks(summaryList: List<ISummaryStock>)
        fun notifyReceivedTasks()
    }

}