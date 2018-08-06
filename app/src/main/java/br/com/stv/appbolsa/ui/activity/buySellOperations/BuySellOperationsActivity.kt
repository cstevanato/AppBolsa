package br.com.stv.appbolsa.ui.activity.buySellOperations

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import br.com.stv.appbolsa.R
import br.com.stv.appbolsa.extension.formatForBrazilianCurrency
import br.com.stv.appbolsa.model.BuySell
import br.com.stv.appbolsa.model.SummaryStock
import br.com.stv.appbolsa.ui.SeparatorDecoration
import kotlinx.android.synthetic.main.activity_buy_sell_operations.*
import kotlinx.android.synthetic.main.content_main.*

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
        this.showErrorAlert(this, message)
    }
    var buySellStock: BuySell? = null

    override fun showStocks(stocksByStock: List<BuySell>?) {
        rv_buySellStocks.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        val decoration = SeparatorDecoration(this)
        rv_buySellStocks.addItemDecoration(decoration)

        stocksByStock?.let {
            rv_buySellStocks.adapter = BuySellOperationsAdapter(this, stocksByStock)
            { buySell ->
                this.buySellStock = buySell
            }

            rv_buySellStocks.setOnCreateContextMenuListener { menu: ContextMenu, v: View?, menuInfo: ContextMenu.ContextMenuInfo? ->
                menu.add(Menu.NONE, 1, Menu.NONE, getString(R.string.menu_title_delete))
            }
        }

//
//        summaryList?.let {
//            if (it.size > 0) tv_message.visibility = View.GONE
//        }
    }
    override fun onContextItemSelected(item: MenuItem?): Boolean {
        val idDoMenu = item?.itemId
        if(idDoMenu == 1) {
            bsoPresenter.deleteOperationsQuestion(buySellStock?.id!!, buySellStock?.stock?.stock )
        }
        return super.onContextItemSelected(item)
    }


    override fun showSummaryStock(summaryStock: SummaryStock?) {
        tv_code.text = summaryStock?.stock?.stock
        tv_description.text = summaryStock?.stock?.description
        tv_amount.text = "Qtd: ${summaryStock?.amount.toString()}"
        tv_averege.text = "Média: ${summaryStock?.average?.toBigDecimal()?.formatForBrazilianCurrency()}"
    }

    override fun questionDeleteStockGreaterOne(amountDelete: Int, idStock: Long, stock: String) {
        this.showQuestion(this, getString(R.string.bso_msn_delete_more_stock, stock),
                {
                    bsoPresenter.deleteOperations(idStock, stock)
                    Toast.makeText(this@BuySellOperationsActivity, "Apagando Registro",
                            Toast.LENGTH_LONG)
                } ,
                {
                    Toast.makeText(this@BuySellOperationsActivity, "Recusando Registro",
                            Toast.LENGTH_LONG)
                })
    }

    override fun questionDeleteStockEqualOne(idStock: Long, stock: String) {
        this.showQuestion(this, "Confirma \"Exclusão\"?",
                {
                    bsoPresenter.deleteOperations(idStock, stock)
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
