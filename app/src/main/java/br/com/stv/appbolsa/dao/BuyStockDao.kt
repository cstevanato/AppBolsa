package br.com.stv.appbolsa.dao

import br.com.stv.appbolsa.model.BuySell
import br.com.stv.appbolsa.model.Stock
import br.com.stv.appbolsa.model.SummaryStock
import br.com.stv.appbolsa.utils.CalculationUtils
import io.realm.Realm


class BuyStockDao {

    fun insert(averageStock: IBuySellStock) {

        val realm = Realm.getDefaultInstance()
        var stock = getStock(realm, averageStock.id)

        try {


            realm.beginTransaction()

            if (stock == null) {
                var max = realm.where(BuySell::class.java).max("id")
                if (max == null) {
                    max = 1
                }
                stock = realm.createObject(Stock::class.java, max!!.toLong() + 1)
                stock.stock = averageStock.stock
            }

            var max = realm.where(BuySell::class.java).max("id")
            if (max == null) {
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


            calcAverageBuyStock(realm, stock, averageStock)


            realm.commitTransaction()
        } catch (ex: Exception) {
            ex.printStackTrace()
        } finally {
            realm.close()
        }
    }

    private fun calcAverageBuyStock(realm: Realm, stock: Stock?, averageStock: IBuySellStock) {
        val summaryStock = getSummaryStock(realm, stock!!)

        summaryStock.average = CalculationUtils().stockAverageBuyTotal(
                summaryStock.average.toBigDecimal(),
                summaryStock.amount,
                averageStock.averagePerStock,
                averageStock.amount).toDouble()

        summaryStock.amount += averageStock.amount
        summaryStock.updateDate = averageStock.updateDate
    }

    fun getStock(realm: Realm, id: Long): Stock? {
        return realm.where(Stock::class.java).equalTo("id", id).findFirst()
    }

    fun getSummaryStock(realm: Realm, stock: Stock): SummaryStock {
        var summaryStock = realm.where(SummaryStock::class.java)
                .equalTo("stock.stock", stock.stock).findFirst()
        if (summaryStock == null) {
            var max = realm.where(SummaryStock::class.java).max("id")
            if (max == null) {
                max = 1
            }
            summaryStock = realm.createObject(SummaryStock::class.java, max!!.toLong() + 1)
            summaryStock!!.stock = stock
        }
        return summaryStock

    }

    fun getStocksForSales(): List<SummaryStock>? {
        val realm = Realm.getDefaultInstance()

        val results = realm.where(SummaryStock::class.java)
                .greaterThan("amount", 0).findAll()
        return realm.copyFromRealm(results)

    }

    fun subtract(averageStock: IBuySellStock) {
        val realm = Realm.getDefaultInstance()
        var stock = getStock(realm, averageStock.id)

        try {


            realm.beginTransaction()

            if (stock == null) {
                var max = realm.where(BuySell::class.java).max("id")
                if (max == null) {
                    max = 1
                }
                stock = realm.createObject(Stock::class.java, max!!.toLong() + 1)
                stock.stock = averageStock.stock
            }

            var max = realm.where(BuySell::class.java).max("id")
            if (max == null) {
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

            calcAverageSellStock(realm, stock, averageStock)

            realm.commitTransaction()
        } catch (ex: Exception) {
            ex.printStackTrace()
        } finally {
            realm.close()
        }
    }


    private fun calcAverageSellStock(realm: Realm, stock: Stock?, averageStock: IBuySellStock) {
        val summaryStock = getSummaryStock(realm, stock!!)

        summaryStock.average = CalculationUtils().stockAverageSellTotal(
                summaryStock.average.toBigDecimal(),
                summaryStock.amount,
                averageStock.amount).toDouble()

        summaryStock.amount -= averageStock.amount
        summaryStock.updateDate = averageStock.updateDate
    }

}



