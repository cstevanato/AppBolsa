package br.com.stv.appbolsa.ui.activity.main


import br.com.stv.appbolsa.dao.ISummaryStock
import br.com.stv.appbolsa.ui.activity.BasePresenter
import br.com.stv.appbolsa.ui.activity.BaseView

interface SummaryContract {
        interface Presenter : BasePresenter {
            fun loadSummaries()
        }

        interface View: BaseView<Presenter> {
            fun showSummaries(summaryList: List<ISummaryStock>)
        }

    }