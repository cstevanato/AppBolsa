package br.com.stv.appbolsa.dao

import br.com.stv.appbolsa.TypeActionStock
import br.com.stv.appbolsa.model.BuySell
import br.com.stv.appbolsa.model.Stock
import br.com.stv.appbolsa.model.SummaryStock
import br.com.stv.appbolsa.utils.CalculationUtils
import io.realm.Realm
import io.realm.Sort


class BuySellStockDao {

    private val realm: Realm by lazy {
        Realm.getDefaultInstance()
    }

    fun next(): Long {
        var max = realm.where(BuySell::class.java).max("id")
        if (max == null) max = 1
        return max.toLong() + 1
    }

    fun insert(averageStock: IBuySellStock) {

        var stock = StockDao().getStock( averageStock.stock)

        try {

            realm.beginTransaction()

            if (stock == null) {
                stock = createStock(realm, averageStock)
            }

            val buySellStock = realm.createObject(BuySell::class.java, next())

            buySellStock.stock = stock
            buySellStock.amount = averageStock.amount
            buySellStock.cust = averageStock.cust.toDouble()
            buySellStock.rates = averageStock.rates.toDouble()
            buySellStock.averagePerStock = averageStock.averagePerStock.toDouble()
            buySellStock.custOperation = averageStock.custOperation.toDouble()
            buySellStock.typeAction = TypeActionStock.BUY.value
            buySellStock.updateDate = averageStock.updateDate

            val summaryStock = calcAverageBuyStock(stock, averageStock)

            buySellStock.totalAveragePerStock = summaryStock.average
            buySellStock.totalAmountStock = summaryStock.amount

            realm.commitTransaction()
        } catch (ex: Exception) {
            ex.printStackTrace()
        } finally {
            realm.close()
        }
    }

    fun subtract(averageStock: IBuySellStock) {
        var stock = StockDao().getStock(averageStock.stock)

        try {
            realm.beginTransaction()

            val buySellStock = realm.createObject(BuySell::class.java, next())

            buySellStock.stock = stock
            buySellStock.amount = averageStock.amount
            buySellStock.cust = averageStock.cust.toDouble()
            buySellStock.rates = averageStock.rates.toDouble()
            buySellStock.averagePerStock = averageStock.averagePerStock.toDouble()
            buySellStock.custOperation = averageStock.custOperation.toDouble()
            buySellStock.typeAction = TypeActionStock.SELL.value
            buySellStock.updateDate = averageStock.updateDate

            val summaryStock = calcAverageSellStock(realm, stock, averageStock)

            buySellStock.totalAveragePerStock = summaryStock.average
            buySellStock.totalAmountStock = summaryStock.amount
            realm.commitTransaction()
        } catch (ex: Exception) {
            ex.printStackTrace()
        } finally {
            realm.close()
        }
    }

    fun getSummaryStock(stock: Stock): SummaryStock {

        var summaryStock = SummaryStockDao()
                .getSummaryStockByStock(stock.stock!!)

        if (summaryStock == null) {
            val summaryStockId = SummaryStockDao().next()
            summaryStock = realm.createObject(SummaryStock::class.java, summaryStockId)
            summaryStock!!.stock = stock
        }
        return summaryStock

    }

    fun getStocksForSales(): List<SummaryStock>? {
        val results = realm.where(SummaryStock::class.java)
                .greaterThan("amount", 0).findAll()
        return realm.copyFromRealm(results)
    }

    private fun createStock(realm: Realm, averageStock: IBuySellStock): Stock {
        var stock = StockDao().create()
        stock.stock = averageStock.stock
        return stock
    }

    /**
     * Calcula a média de compra
     */
    private fun calcAverageBuyStock(stock: Stock, averageStock: IBuySellStock): SummaryStock {
        val summaryStock = getSummaryStock(stock)

        summaryStock.average = CalculationUtils().stockAverageBuyTotal(
                summaryStock.average.toBigDecimal(),
                summaryStock.amount,
                averageStock.averagePerStock,
                averageStock.amount).toDouble()

        summaryStock.amount += averageStock.amount
        summaryStock.updateDate = averageStock.updateDate

        return summaryStock
    }

    /**
     * Calculo média de venda.
     */
    private fun calcAverageSellStock(realm: Realm, stock: Stock?, averageStock: IBuySellStock): SummaryStock {
        val summaryStock = getSummaryStock(stock!!)

        summaryStock.amount -= averageStock.amount
        summaryStock.updateDate = averageStock.updateDate

        return summaryStock

    }

    fun getStocksByStockOrderDesc(stock: String): List<BuySell>? {
        val results = realm.where(BuySell::class.java)
                .endsWith("stock.stock", stock)
                .sort("id", Sort.DESCENDING)
                .findAll()
        return realm.copyFromRealm(results)
    }

    fun amountDeleted(idStock: Long, stock: String?): Int {
        return 1
    }

}



