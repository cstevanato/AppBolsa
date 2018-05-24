package br.com.stv.appbolsa.ui.activity.buy

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import br.com.stv.appbolsa.R
import br.com.stv.appbolsa.dao.ISummaryStock
import kotlinx.android.synthetic.main.activity_buy.*

class BuyActivity : AppCompatActivity(), BuyContract.View {
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

        })
    }



}
