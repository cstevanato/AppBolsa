package br.com.stv.appbolsa.ui.activity.buySellOperations

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.view.menu.MenuBuilder
import android.support.v7.view.menu.MenuPopupHelper
import android.support.v7.widget.RecyclerView
import android.view.*
import br.com.stv.appbolsa.R
import br.com.stv.appbolsa.extension.formatForBrazilianCurrency
import br.com.stv.appbolsa.extension.toSimpleString
import br.com.stv.appbolsa.model.BuySell
import kotlinx.android.synthetic.main.buy_sell_item_detail.view.*

class BuySellOperationsAdapter(private val context: Context,
                               private val buySellStocks: List<BuySell>,
                               private val delegate: (buySell : BuySell) -> Unit)  :
        RecyclerView.Adapter<BuySellOperationsAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return buySellStocks.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.buy_sell_item_detail, parent, false)

        return ViewHolder(v)
    }


    @SuppressLint("RestrictedApi")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val buySell = buySellStocks[position]
        with(holder) {
            dateOperation.text = buySell.updateDate.toSimpleString()
             note.text = buySell.note.toString()
             amount.text = buySell.amount.toString()
             cust.text = buySell.cust.toBigDecimal().formatForBrazilianCurrency()
             custOperation.text = buySell.custOperation.toBigDecimal().formatForBrazilianCurrency()

            menu.setOnClickListener {
                val menuBuilder = MenuBuilder(context)
                val inflater = MenuInflater(context)
                inflater.inflate(R.menu.menu_item_buy_sell_operations, menuBuilder)
                val optionsMenu = MenuPopupHelper(context, menuBuilder, holder.menu)

                optionsMenu.setForceShowIcon(true)

                // Set Item Click Listener
                menuBuilder.setCallback(object : MenuBuilder.Callback {
                    override fun onMenuItemSelected(menu: MenuBuilder, item: MenuItem): Boolean {
                        when (item.getItemId()) {
                            R.id.action_delete -> {
                                delegate(buySellStocks[position])
                            }
                        }
                        return true
                    }

                    override fun onMenuModeChange(menu: MenuBuilder) {}
                })

                optionsMenu.show()
            }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dateOperation = itemView.tv_date
        val note = itemView.tv_note
        val amount = itemView.tv_amount
        val cust = itemView.tv_cust
        val custOperation = itemView.tv_custOperation
        val menu = itemView.tv_menu

    }
}
