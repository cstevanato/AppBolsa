package br.com.stv.appbolsa.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import java.util.*

@RealmClass
open class BuySell : RealmObject() {
    @PrimaryKey
    var id: Long = 0
    var note: String = ""
    var stock: Stock? = null
    var amount: Int = 0
    var cust: Double = 0.0
    var rates: Double = 0.0
    var averagePerStock: Double = 0.0
    var custOperation: Double = 0.0
    var totalAveragePerStock : Double = 0.0
    var totalAmountStock: Int = 0
    var buy: Boolean = true
    var updateDate: Date = Date()
}