package br.com.stv.appbolsa.ui.activity.main

import android.content.Context
import br.com.stv.appbolsa.dao.SummaryStockDao
import br.com.stv.appbolsa.model.Stock
import br.com.stv.appbolsa.model.SummaryStock


class SummaryPresenter (private val context: Context,
                        private val summaryView: SummaryContract.View) : SummaryContract.Presenter {

    private val summaryStocks : MutableList<SummaryStock> = mutableListOf()
    private val summaryStockList: List<SummaryStock> = summaryStocks

    override fun loadSummaries() {

        summaryStocks.addAll(SummaryStockDao().getSummaryStocks()!!)
        summaryView.showTasks(summaryStockList)

//        var summaryStock = SummaryStock()
//        summaryStock.amount = 100
//        summaryStock.stock = Stock()
//        summaryStock.stock?.stock = "KNCI11"
//        summaryStock.stock?.description = "Kineea MultMercado"
//        summaryStock.id = 1
//        summaryStock.average = 104.5
//        summaryStocks.add(summaryStock)
//
//        summaryStock = SummaryStock()
//        summaryStock.amount = 13
//        summaryStock.stock = Stock()
//        summaryStock.stock?.stock = "XXXXI11"
//        summaryStock.stock?.description = "XXXX teste 0001 "
//        summaryStock.id = 2
//        summaryStock.average = 52.5
//        summaryStocks.add(summaryStock)

        //summaryView.showTasks(summaryStockList)
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