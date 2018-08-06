package br.com.stv.appbolsa.dao.api

import br.com.stv.appbolsa.model.Stock
import java.util.*

interface ISummaryStock {
    var id: Long
    var stock: Stock?
    var amount: Int
    var average: Double
    var updateDate: Date
}