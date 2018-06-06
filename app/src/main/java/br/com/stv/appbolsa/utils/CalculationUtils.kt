package br.com.stv.appbolsa.utils

import java.math.BigDecimal

class CalculationUtils {

    fun custOperation(cust: BigDecimal, amount: Int, rates: BigDecimal): BigDecimal =
            cust.multiply(amount.toBigDecimal()) + rates

    /**
     * Média das ações por compra
     */
    fun stockAveragePerBuy(custOperation: BigDecimal, amount: Int): BigDecimal =
            if (amount != 0) custOperation.divide(amount.toBigDecimal(), 3)
            else BigDecimal.ZERO

    fun stockAveragePerTotal(summaryStockAverage: BigDecimal, summaryAmount: Int,
                             buyStockAvarege: BigDecimal, buyStockAmount: Int) : BigDecimal {
        val totalAmount = summaryAmount + buyStockAmount
        if (totalAmount == 0) {
            return BigDecimal.ZERO
        } else {
           return  summaryStockAverage
                   .multiply(summaryAmount.toBigDecimal())
                   .plus(buyStockAvarege.multiply(buyStockAmount.toBigDecimal()))
                   .divide(totalAmount.toBigDecimal(), 3)
        }
    }

}