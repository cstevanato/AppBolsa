package br.com.stv.appbolsa.dao

import br.com.stv.appbolsa.model.SummaryStock
import io.realm.Realm
import io.realm.RealmResults

class SummaryStockDao {

    private val realm: Realm by lazy {
        Realm.getDefaultInstance()
    }

    fun next(): Long {
        var max = realm.where(SummaryStock::class.java).max("id")
        if (max == null) max = 1
        return max.toLong() + 1
    }

    fun getSummaryStocks(): List<SummaryStock>? {
        return realm.copyFromRealm(realm.where(SummaryStock::class.java).findAll())
    }

    fun getSummaryStockByStock(stock: String): SummaryStock? {
        return realm.where(SummaryStock::class.java)
                .equalTo("stock.stock", stock).findFirst()
    }
}