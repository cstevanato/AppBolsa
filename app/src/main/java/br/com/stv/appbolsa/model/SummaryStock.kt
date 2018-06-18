package br.com.stv.appbolsa.model

import br.com.stv.appbolsa.dao.ISummaryStock
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import java.util.*

/**
 * Média das Ações
 */

@RealmClass
open class SummaryStock() : RealmObject(), ISummaryStock {
    @PrimaryKey
    override var id: Long = 0
    override var stock: Stock? = null
    override var amount: Int = 0
    override var average: Double = 0.0
    override var updateDate: Date = Date()


    constructor(summaryStock: ISummaryStock) : this() {
        this.amount = summaryStock.amount
        this.average = summaryStock.average
        this.id = summaryStock.id
        this.stock = summaryStock.stock
        this.updateDate = summaryStock.updateDate
    }

    override fun toString(): String {
        return this.stock?.stock!!
    }
}