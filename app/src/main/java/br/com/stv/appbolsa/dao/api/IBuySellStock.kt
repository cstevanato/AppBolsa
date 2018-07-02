package br.com.stv.appbolsa.dao.api

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
    var avarageOperation: BigDecimal
    var custOperation: BigDecimal
    var averageTotal: BigDecimal
    var amountTotal: Int
    var updateDate: Date
}