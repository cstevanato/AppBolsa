package br.com.stv.appbolsa.dao

import br.com.stv.appbolsa.model.BuySell
import br.com.stv.appbolsa.model.Stock
import br.com.stv.appbolsa.model.SummaryStock
import br.com.stv.appbolsa.utils.CalculationUtils
import io.realm.Realm
import kotlin.coroutines.experimental.EmptyCoroutineContext


class BuyStockDao {

    fun insert(averageStock: IBuySellStock) {

        val realm = Realm.getDefaultInstance()
        var stock = getStock(realm, averageStock.id)

        try {


            realm.beginTransaction()

            if (stock == null) {
                var max = realm.where(BuySell::class.java).max("id")
                if(max == null) {
                    max = 1
                }
                stock = realm.createObject(Stock::class.java, max!!.toLong() + 1)
                stock.stock = averageStock.stock
            }

            var max = realm.where(BuySell::class.java).max("id")
            if(max == null) {
                max = 1
            }
            val buySellStock = realm.createObject(BuySell::class.java, max!!.toLong() + 1)

            buySellStock.stock = stock
            buySellStock.amount = averageStock.amount
            buySellStock.cust = averageStock.cust.toDouble()
            buySellStock.rates = averageStock.rates.toDouble()
            buySellStock.averagePerStock = averageStock.averagePerStock.toDouble()
            buySellStock.custOperation = averageStock.custOperation.toDouble()
            buySellStock.buy = true
            buySellStock.updateDate = averageStock.updateDate


            val summaryStock = getSummaryStock(realm, stock!!)

            summaryStock.average = CalculationUtils().stockAveragePerTotal(
                    summaryStock.average.toBigDecimal(),
                    summaryStock.amount,
                    averageStock.averagePerStock,
                    averageStock.amount).toDouble()

            summaryStock.amount += averageStock.amount
            summaryStock.updateDate = averageStock.updateDate


            realm.commitTransaction()
        } catch (ex: Exception) {
            ex.printStackTrace()
        } finally {
            realm.close()
        }
    }

    fun getStock(realm: Realm, id: Long): Stock? {
        return realm.where(Stock::class.java).equalTo("id", id).findFirst()
    }

    fun getSummaryStock(realm: Realm, stock: Stock): SummaryStock {
        var summaryStock = realm.where(SummaryStock::class.java)
                .equalTo("stock.stock", stock.stock).findFirst()
        if (summaryStock == null) {
            var max= realm.where(SummaryStock::class.java).max("id")
            if(max == null) {
                max = 1
            }
            summaryStock = realm.createObject(SummaryStock::class.java, max!!.toLong() + 1)
            summaryStock!!.stock = stock
        }
        return summaryStock

    }
}



