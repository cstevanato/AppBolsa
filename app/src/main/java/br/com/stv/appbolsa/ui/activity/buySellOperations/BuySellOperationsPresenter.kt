package br.com.stv.appbolsa.ui.activity.buySellOperations

import android.content.Context
import br.com.stv.appbolsa.dao.BuySellStockDao
import br.com.stv.appbolsa.dao.SummaryStockDao


class BuySellOperationsPresenter(private val context: Context,
                                 private val bsoView: BuySellOperationsContract.View) :
        BuySellOperationsContract.Presenter {

    val buySellStockDao = BuySellStockDao()
    val summaryStockDao = SummaryStockDao()

    override fun deleteOperationsQuestion(idStock: Long, stock: String?) {
        // 1 - Obter quantidade de regtistros deletados
        // 2 - se > 1 pergunta para o usuário com problema que pode ocorrer
        // 3 - se = 1 pergunta de confirmação

        val amountDelete: Int = buySellStockDao.amountDeleted(idStock, stock)

        if (amountDelete == 1) {
            // erase records greater than 1
            bsoView.questionDeleteStockGreaterOne(amountDelete, idStock, stock)
        } else {
            bsoView.questionDeleteStockEqualOne(idStock, stock)
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