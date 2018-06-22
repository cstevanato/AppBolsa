package br.com.stv.appbolsa.ui.activity.buySellOperations

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import android.widget.Toast
import br.com.stv.appbolsa.R
import br.com.stv.appbolsa.extension.formatForBrazilianCurrency
import br.com.stv.appbolsa.model.BuySell
import br.com.stv.appbolsa.model.SummaryStock
import br.com.stv.appbolsa.ui.SeparatorDecoration
import kotlinx.android.synthetic.main.activity_buy_sell_operations.*

class BuySellOperationsActivity : AppCompatActivity(), BuySellOperationsContract.View {

    companion object {
        val INTENT_STOCK = "stock"
    }

    private val bsoPresenter: BuySellOperationsContract.Presenter by lazy {
        BuySellOperationsPresenter(this, this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buy_sell_operations)

        configActionBar()

        val stotck = intent.getStringExtra(INTENT_STOCK)
                ?: throw IllegalStateException("field $INTENT_STOCK missing in Intent")

        bsoPresenter.getSummaryStock(stotck)
        bsoPresenter.getBuySellList(stotck)
    }

    private fun configActionBar() {
        title = getString(R.string.menu_title_detalhar)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()?.subtitle = getString(R.string.bso_subtitle)
    }

    override fun onSupportNavigateUp(): Boolean {
        this.finish()
        return true
    }

    //region BuySellOperationsContract.View

    override fun printError(message: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showStocks(stocksByStock: List<BuySell>?) {
        rv_buySellStocks.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        val decoration = SeparatorDecoration(this)
        rv_buySellStocks.addItemDecoration(decoration)

        stocksByStock?.let {
            rv_buySellStocks.adapter = BuySellOperationsAdapter(this, stocksByStock)
            { buySell ->
                Toast.makeText(this@BuySellOperationsActivity, buySell.stock?.stock, Toast.LENGTH_SHORT).show()
                bsoPresenter.deleteOperationsQuestion(buySell.id, buySell.stock?.stock )
            }
        }

//
//        summaryList?.let {
//            if (it.size > 0) tv_message.visibility = View.GONE
//        }
    }

    override fun showSummaryStock(summaryStock: SummaryStock?) {
        tv_code.text = summaryStock?.stock?.stock
        tv_description.text = summaryStock?.stock?.description
        tv_amount.text = "Qtd: ${summaryStock?.amount.toString()}"
        tv_averege.text = "Média: ${summaryStock?.average?.toBigDecimal()?.formatForBrazilianCurrency()}"
    }

    override fun questionDeleteStockGreaterOne(amountDelete: Int, idStock: Long, stock: String?) {
        this.showQuestion(this, getString(R.string.bso_msn_delete_more_stock, "xxxxx"),
                {
                    Toast.makeText(this@BuySellOperationsActivity, "Apagando Registro",
                            Toast.LENGTH_LONG)
                } ,
                {
                    Toast.makeText(this@BuySellOperationsActivity, "Recusando Registro",
                            Toast.LENGTH_LONG)
                })
    }

    override fun questionDeleteStockEqualOne(idStock: Long, stock: String?) {
        this.showQuestion(this, "Confirma \"Exclusão\"?",
                {
                    Toast.makeText(this@BuySellOperationsActivity, "Apagando Registro",
                            Toast.LENGTH_LONG)
                } ,
                {
                    Toast.makeText(this@BuySellOperationsActivity, "Recusando Registro",
                            Toast.LENGTH_LONG)
                })
    }

    //endregion
}
