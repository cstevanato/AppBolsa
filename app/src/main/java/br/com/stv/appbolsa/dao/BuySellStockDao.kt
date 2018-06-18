package br.com.stv.appbolsa.dao

import br.com.stv.appbolsa.TypeActionStock
import br.com.stv.appbolsa.model.BuySell
import br.com.stv.appbolsa.model.Stock
import br.com.stv.appbolsa.model.SummaryStock
import br.com.stv.appbolsa.utils.CalculationUtils
import io.realm.Realm


class BuySellStockDao {

    fun next(realm: Realm): Long {
        var max = realm.where(BuySell::class.java).max("id")
        if (max == null) max = 1
        return max.toLong() + 1
    }


    fun insert(averageStock: IBuySellStock) {

        val realm = Realm.getDefaultInstance()
        var stock = StockDao().getStock(realm, averageStock.stock)

        try {

            realm.beginTransaction()

            if (stock == null) {
                stock = createStock(realm, averageStock)
            }

            val buySellStock = realm.createObject(BuySell::class.java, next(realm))

            buySellStock.stock = stock
            buySellStock.amount = averageStock.amount
            buySellStock.cust = averageStock.cust.toDouble()
            buySellStock.rates = averageStock.rates.toDouble()
            buySellStock.averagePerStock = averageStock.averagePerStock.toDouble()
            buySellStock.custOperation = averageStock.custOperation.toDouble()
            buySellStock.typeAction = TypeActionStock.BUY.value
            buySellStock.updateDate = averageStock.updateDate

            val summaryStock = calcAverageBuyStock(realm, stock, averageStock)

            buySellStock.totalAveragePerStock = summaryStock.average
            buySellStock.totalAmountStock = summaryStock.amount

            realm.commitTransaction()
        } catch (ex: Exception) {
            ex.printStackTrace()
        } finally {
            realm.close()
        }
    }

    private fun createStock(realm: Realm, averageStock: IBuySellStock): Stock {
        var stock = StockDao().create(realm)
        stock.stock = averageStock.stock
        return stock
    }

    /**
     * Calcula a média de compra
     */
    private fun calcAverageBuyStock(realm: Realm, stock: Stock, averageStock: IBuySellStock): SummaryStock {
        val summaryStock = getSummaryStock(realm, stock)

        summaryStock.average = CalculationUtils().stockAverageBuyTotal(
                summaryStock.average.toBigDecimal(),
                summaryStock.amount,
                averageStock.averagePerStock,
                averageStock.amount).toDouble()

        summaryStock.amount += averageStock.amount
        summaryStock.updateDate = averageStock.updateDate

        return summaryStock
    }


    fun getSummaryStock(realm: Realm, stock: Stock): SummaryStock {

        var summaryStock = SummaryStockDao()
                .getSummaryStockByStock(realm, stock.stock!!)


        if (summaryStock == null) {
            val summaryStockId = SummaryStockDao().next(realm)
            summaryStock = realm.createObject(SummaryStock::class.java, summaryStockId)
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
        var stock = StockDao().getStock(realm, averageStock.stock)

        try {
            realm.beginTransaction()

            val buySellStock = realm.createObject(BuySell::class.java, next(realm))

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

    /**
     * Calculo média de venda.
     */
    private fun calcAverageSellStock(realm: Realm, stock: Stock?, averageStock: IBuySellStock): SummaryStock {
        val summaryStock = getSummaryStock(realm, stock!!)

        summaryStock.amount -= averageStock.amount
        summaryStock.updateDate = averageStock.updateDate

        return summaryStock

    }

}



