package br.com.stv.appbolsa.dao

import br.com.stv.appbolsa.model.SummaryStock
import br.com.stv.appbolsa.model.Stock
import io.realm.Realm

// https://academy.realm.io/posts/android-architecture-components-and-realm/
// https://github.com/googlecodelabs/android-persistence
class AverageStockDao {

    private val realm: Realm by lazy {
        Realm.getDefaultInstance()
    }

    fun insert(averageStock: ISummaryStock) {

        val stock = realm.where(Stock::class.java).equalTo("id", averageStock.id).findFirst()
        val averageStock =  SummaryStock(averageStock)

        realm.insert(averageStock)
//        realm.beginTransaction()
//        val stockObject = realm.createObject(SummaryStock::class.java, averageStock.id)
//        stockObject.updateDate = averageStock.updateDate
//        stockObject.stock = averageStock.stock
//        stockObject.average = averageStock.average
//        stockObject.amount = averageStock.amount
//        realm.commitTransaction()
    }



}