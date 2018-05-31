package br.com.stv.appbolsa.dao

import br.com.stv.appbolsa.model.BuySell
import br.com.stv.appbolsa.model.Stock
import io.realm.Realm


class BuyStockDao {

    fun insert(averageStock: IBuySellStock) {

        val realm = Realm.getDefaultInstance()
        val stock = getStock(realm, averageStock.id)

        realm.beginTransaction()

        stock?.let {
            val max : Long = realm.where(Stock::class.java).max("id") as Long
            val stock = realm.createObject(Stock::class.java, max + 1)
            stock.stock = averageStock.stock
        }

        val max : Long = realm.where(BuySell::class.java).max("id") as Long
        val buySellStock = realm.createObject(BuySell::class.java, max + 1)

        buySellStock.stock  = stock
        buySellStock.amount = averageStock.amount
        buySellStock.cust  = averageStock.cust
        buySellStock.rates = averageStock.rates
        buySellStock.averagePerStock = averageStock.averagePerStock
        buySellStock.custOperation = averageStock.custOperation
        buySellStock.buy = true
        buySellStock.updateDate = averageStock.updateDate

        realm.commitTransaction()
    }

    fun getStock(realm: Realm, id: Long) : Stock? {
       return  realm.where(Stock::class.java).equalTo("id", id).findFirst()
    }


}



