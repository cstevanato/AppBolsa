package br.com.stv.appbolsa.ui.activity.main

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import br.com.stv.appbolsa.R
import br.com.stv.appbolsa.dao.api.ISummaryStock
import br.com.stv.appbolsa.extension.formatForBrazilianCurrency
import kotlinx.android.synthetic.main.summary_item_detail_alt.view.*
import android.view.*


class SummaryAdapter(private val context: Context,
                     private val summaryStock: List<ISummaryStock>,
                     private val delegate: (summaryStock: ISummaryStock, position : Int, viewHolder: View) -> Unit) :
        RecyclerView.Adapter<SummaryAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return summaryStock.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.summary_item_detail_alt, parent, false)

        return ViewHolder(v)
    }

    @SuppressLint("RestrictedApi")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val summary = summaryStock[position]
        with(holder) {
            codeStock.text = summary.stock?.stock
            title.text = summary.stock?.description

            amount.text = "Qtd.: ${summary.amount}"
            average.text = summary.average.toBigDecimal().formatForBrazilianCurrency()

            itemView.setOnLongClickListener {
                delegate(
                        summaryStock[position] ,
                        position,
                        it
                )
                false
            }

//            menu.setOnClickListener {
//                val menuBuilder = MenuBuilder(context)
//                val inflater = MenuInflater(context)
//                inflater.inflate(R.menu.menu_item_summary, menuBuilder)
//                val optionsMenu = MenuPopupHelper(context, menuBuilder, holder.menu)
//                optionsMenu.setForceShowIcon(true)
//
//                // Set Item Click Listener
//                menuBuilder.setCallback(object : MenuBuilder.Callback {
//                    override fun onMenuItemSelected(menu: MenuBuilder, item: MenuItem): Boolean {
//                        when (item.getItemId()) {
//                            R.id.action_lists -> {
//                                delegate(summaryStock[position])
//
//                            }
//                        }
//                        return true
//                    }
//
//                    override fun onMenuModeChange(menu: MenuBuilder) {}
//                })
//
//                optionsMenu.show()
//            }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val codeStock = itemView.tv_code_stock
        val title = itemView.tv_title
        val amount = itemView.tv_amount
        val average = itemView.tv_average
        //val menu = itemView.tv_menu
    }
}
