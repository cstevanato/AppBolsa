package br.com.stv.appbolsa.utils

import java.math.BigDecimal

class CalculationUtils {

    fun custOperation(cust: BigDecimal, amount: Int, rates: BigDecimal): BigDecimal =
            cust.multiply(amount.toBigDecimal()) + rates

    /**
     * Calculo média das ação (compra/venda)
     */
    fun stockAverage(custOperation: BigDecimal, amount: Int): BigDecimal =
            if (amount != 0) custOperation.divide(amount.toBigDecimal(), 3)
            else BigDecimal.ZERO

    /**
     * Calculo média total de compra
     */
    fun stockAverageBuyTotal(summaryStockAverage: BigDecimal, summaryAmount: Int,
                             buyStockAvarege: BigDecimal, buyStockAmount: Int): BigDecimal {
        val totalAmount = summaryAmount + buyStockAmount
        return if (totalAmount == 0) {
            BigDecimal.ZERO
        } else {
            summaryStockAverage
                    .multiply(summaryAmount.toBigDecimal())
                    .plus(buyStockAvarege.multiply(buyStockAmount.toBigDecimal()))
                    .divide(totalAmount.toBigDecimal(), 3)
        }
    }

    /**
     * Calculo média total de venda
     */
    fun stockAverageSellTotal(summaryStockAverage: BigDecimal, summaryAmount: Int, sellStockAmount: Int): BigDecimal {
        val totalAmount = summaryAmount - sellStockAmount
       return if (totalAmount == 0) {
            BigDecimal.ZERO
        } else {
            summaryStockAverage.multiply(totalAmount.toBigDecimal())
        }
    }
}