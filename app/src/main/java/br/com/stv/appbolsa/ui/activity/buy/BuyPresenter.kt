package br.com.stv.appbolsa.ui.activity.buy

import android.content.Context
import br.com.stv.appbolsa.dao.BuyStockDao
import br.com.stv.appbolsa.dao.IBuySellStock

class BuyPresenter (private val context: Context,
                        private val summaryView: BuyContract.View) : BuyContract.Presenter {
    override fun add(buy: IBuySellStock) {
        try {
            BuyStockDao().insert(buy)
        }
        catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun loadSummaries() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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