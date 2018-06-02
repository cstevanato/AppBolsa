package br.com.stv.appbolsa.ui.activity.buy

import br.com.stv.appbolsa.dao.IBuySellStock
import java.util.*

data class BuyData(override var id: Long,
                   override var note: String,
                   override var stock: String,
                   override var amount: Int,
                   override var cust: Double,
                   override var rates: Double,
                   override var averagePerStock: Double,
                   override var custOperation: Double,
                   override var updateDate: Date,
                   override var totalAveragePerStock: Double,
                   override var totalAmountStock: Int) : IBuySellStock {
}