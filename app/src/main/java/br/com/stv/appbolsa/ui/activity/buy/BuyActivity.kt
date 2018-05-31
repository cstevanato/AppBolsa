package br.com.stv.appbolsa.ui.activity.buy

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import br.com.stv.appbolsa.R
import br.com.stv.appbolsa.dao.ISummaryStock
import kotlinx.android.synthetic.main.activity_buy.*
import java.text.SimpleDateFormat
import java.util.*

class BuyActivity : AppCompatActivity(), BuyContract.View {

    private val buyPresenter: BuyContract.Presenter by lazy {
        BuyPresenter(this, this)
    }

    override fun showTasks(summaryList: List<ISummaryStock>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun notifyReceivedTasks() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buy)

        btn_confirm.setOnClickListener({
            Toast.makeText(this, "You clicked me.", Toast.LENGTH_SHORT).show()

            val amount: Int = edt_amount.text.toString().toInt()
            val cust: Double = edt_cust.text.toString().toDouble()
            val rate: Double = edt_rate_costs.text.toString().toDouble()
            val custOperation: Double = (amount * cust) + rate
            val averagePerStock: Double = custOperation / amount
            val date: Date = SimpleDateFormat("dd/MM/yyyy").parse(et_date_buy.text.toString())

            val buyData = BuyData(0,
                    et_nota_corretagem.text.toString(),
                    et_code_action.text.toString(),
                    amount, cust, rate, averagePerStock,
                    custOperation, date)

            buyPresenter.add(buyData)
        })
    }



}
