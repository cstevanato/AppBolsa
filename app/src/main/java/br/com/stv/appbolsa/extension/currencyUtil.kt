package br.com.stv.appbolsa.extension

import java.math.BigDecimal
import java.text.DecimalFormat
import java.util.Locale


fun BigDecimal.formatForBrazilianCurrency(): String {
    val brazilianFormat = DecimalFormat
            .getCurrencyInstance(Locale("pt", "br"))
    return brazilianFormat.format(this)
}

fun String.toBigDecimalBrazilianCurrency() : BigDecimal {
    return this.replace(".", "").replace(",", ".").toBigDecimal()
}