package br.com.stv.appbolsa.ui.activity.buySellOperations

import android.content.Context
import br.com.stv.appbolsa.dao.BuySellStockDao
import br.com.stv.appbolsa.dao.SummaryStockDao
import com.crashlytics.android.Crashlytics


class BuySellOperationsPresenter(private val context: Context,
                                 private val bsoView: BuySellOperationsContract.View) :
        BuySellOperationsContract.Presenter {


    val buySellStockDao = BuySellStockDao()
    val summaryStockDao = SummaryStockDao()

    override fun deleteOperationsQuestion(idStock: Long, stock: String?) {
        stock?.let {
            val amountDelete: Int = buySellStockDao.amountDeleted(idStock, stock)

            when (amountDelete) {
                0 -> return
                1 -> bsoView.questionDeleteStockEqualOne(idStock, stock)
                else -> bsoView.questionDeleteStockGreaterOne(amountDelete, idStock, stock)
            }
        }
    }

    override fun deleteOperations(idStock: Long, stock: String) {
        try {
            buySellStockDao.delete(idStock, stock)
            bsoView.showStocks(buySellStockDao.getStocksByStockOrderDesc(stock))
            bsoView.showSummaryStock(summaryStockDao.getSummaryStockByStock(stock))
        } catch (e: Exception) {
            Crashlytics.logException(e)
            bsoView.printError("Não foi possível excluir o(a) registro(s).")
        }

    }

    override fun getSummaryStock(stock: String) {
        bsoView.showSummaryStock(summaryStockDao.getSummaryStockByStock(stock))
    }

    override fun getBuySellList(stock: String) {
        bsoView.showStocks(buySellStockDao.getStocksByStockOrderDesc(stock))
    }

    override fun start() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun dispose() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}