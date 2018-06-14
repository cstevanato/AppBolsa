package br.com.stv.appbolsa.ui.activity.buy

import br.com.stv.appbolsa.dao.IBuySellStock
import java.math.BigDecimal
import java.util.*

data class BuyData(override var id: Long = 0,
                   override var note: String = "",
                   override var stock: String = "",
                   override var amount: Int = 0,
                   override var cust: BigDecimal = BigDecimal.ZERO,
                   override var rates: BigDecimal = BigDecimal.ZERO,
                   override var averagePerStock: BigDecimal = BigDecimal.ZERO,
                   override var custOperation: BigDecimal = BigDecimal.ZERO,
                   override var updateDate: Date = Date(),
                   override var totalAveragePerStock: BigDecimal = BigDecimal.ZERO,
                   override var totalAmountStock: Int = 0) : IBuySellStock