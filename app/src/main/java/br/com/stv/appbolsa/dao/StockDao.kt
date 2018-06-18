package br.com.stv.appbolsa.dao

import br.com.stv.appbolsa.model.Stock
import io.realm.Realm

class StockDao {

    fun next(realm: Realm): Long {
        var max = realm.where(Stock::class.java).max("id")
        if (max == null) max = 1
        return max.toLong() + 1
    }

    fun getStock(realm: Realm, stock: String): Stock? {
        return realm.where(Stock::class.java).equalTo("stock", stock).findFirst()
    }

    fun create(realm: Realm): Stock {
        return realm.createObject(Stock::class.java, next(realm))
    }

}