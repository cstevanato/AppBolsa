package br.com.stv.appbolsa.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.stv.appbolsa.R
import br.com.stv.appbolsa.dao.ISummaryStock
import br.com.stv.appbolsa.extension.formatForBrazilianCurrency
import kotlinx.android.synthetic.main.summary_item_detail.view.*

class SummaryAdapter(private val summaryStock: List<ISummaryStock>, private val delegate: (longClickListener : Int) -> Unit) :
        RecyclerView.Adapter<SummaryAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.summary_item_detail, parent, false)

        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return summaryStock.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val summary = summaryStock[position]
        with(holder) {
            codeStock.text = summary.stock?.stock
            title.text = summary.stock?.description

            amount.text = summary.amount.toString()
            average.text  = summary.average.toBigDecimal().formatForBrazilianCurrency()
            itemView.setOnLongClickListener {
                delegate(layoutPosition)
                false
            }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val codeStock = itemView.tv_code_stock
        val title = itemView.tv_title
        val amount = itemView.tv_amount
        val average = itemView.tv_average
    }
}
