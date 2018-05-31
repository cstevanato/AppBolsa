package br.com.stv.appbolsa.dao

import br.com.stv.appbolsa.model.Stock
import java.util.*

interface IBuySellStock {
    var id: Long
    var note: String
    var stock: String
    var amount: Int
    var cust: Double
    var rates: Double
    var averagePerStock: Double
    var custOperation: Double
    var updateDate: Date
}