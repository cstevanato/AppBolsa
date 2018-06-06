package br.com.stv.appbolsa.dao

import br.com.stv.appbolsa.model.SummaryStock
import io.realm.Realm
import io.realm.RealmResults

class SummaryStockDao {

    fun getSummaryStocks(): RealmResults<SummaryStock>? {
        val realm = Realm.getDefaultInstance()
        return realm.where(SummaryStock::class.java).findAll()
    }
}