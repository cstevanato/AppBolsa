package br.com.stv.appbolsa.dao

import br.com.stv.appbolsa.model.Stock
import java.math.BigDecimal
import java.util.*

interface IBuySellStock {
    var id: Long
    var note: String
    var stock: String
    var amount: Int
    var cust: BigDecimal
    var rates: BigDecimal
    var averagePerStock: BigDecimal
    var custOperation: BigDecimal
    var totalAveragePerStock: BigDecimal
    var totalAmountStock: Int
    var updateDate: Date
}