package br.com.stv.appbolsa.ui.activity.main

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.view.menu.MenuBuilder
import android.support.v7.view.menu.MenuPopupHelper
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.LinearLayout
import br.com.stv.appbolsa.R
import br.com.stv.appbolsa.dao.api.ISummaryStock
import br.com.stv.appbolsa.ui.SeparatorDecoration
import br.com.stv.appbolsa.ui.activity.average.AverageActivity
import br.com.stv.appbolsa.ui.activity.buy.BuyActivity
import br.com.stv.appbolsa.ui.activity.buySellOperations.BuySellOperationsActivity
import br.com.stv.appbolsa.ui.activity.sell.SellActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.jetbrains.anko.email


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
        SummaryContract.View {

    private val summaryPresenter: SummaryContract.Presenter by lazy {
        SummaryPresenter(this, this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        this.title = getString(R.string.text_portfolio)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        rv_summary.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        val decoration = SeparatorDecoration(this)
        rv_summary.addItemDecoration(decoration)


        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

    }

    override fun onResume() {
        super.onResume()
        summaryPresenter.loadSummaries()
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        //menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_average -> {
                val intent = Intent(this, AverageActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_buy -> {
                val intent = Intent(this, BuyActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_sell -> {
                val intent = Intent(this, SellActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_doubt -> {
                email("cstevanato@gmail.com",
                        "Investidor Total", "Duvida: ")
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return false
    }


    //region SummaryContract.View

    override fun showSummaries(summaryList: List<ISummaryStock>) {
        rv_summary.adapter = SummaryAdapter(this, summaryList)
        { summaryStock, position, viewHolder ->
            summaryDatailsPopupMenu(position, summaryStock, viewHolder)
        }


        summaryList.let {
            if (it.size > 0) tv_message.visibility = View.GONE
        }
    }

    //endregion

    @SuppressLint("RestrictedApi")
    private fun summaryDatailsPopupMenu(position: Int, summaryStock: ISummaryStock, viewHolder: View) {
        val menuBuilder = MenuBuilder(this@MainActivity)
        val inflater = MenuInflater(this@MainActivity)
        inflater.inflate(R.menu.menu_item_summary, menuBuilder)
        val optionsMenu = MenuPopupHelper(this@MainActivity, menuBuilder, viewHolder)


        optionsMenu.setForceShowIcon(true)

        // Set Item Click Listener
        menuBuilder.setCallback(object : MenuBuilder.Callback {
            override fun onMenuItemSelected(menu: MenuBuilder, item: MenuItem): Boolean {
                when (item.getItemId()) {
                    R.id.action_lists -> {
                        buySellOperationsActivityStart(summaryStock)
                    }
                }
                return true
            }

            override fun onMenuModeChange(menu: MenuBuilder) {}
        })

        //optionsMenu.tryShow(viewHolder.pivotX.toInt(), viewHolder.pivotY.toInt())
        optionsMenu.tryShow(viewHolder.pivotX.toInt(), viewHolder.pivotY.toInt() * -1)
    }

    private fun buySellOperationsActivityStart(summaryStock: ISummaryStock) {
        val intent = Intent(this, BuySellOperationsActivity::class.java)
        intent.putExtra(BuySellOperationsActivity.INTENT_STOCK, summaryStock?.stock!!.stock)
        startActivityForResult(intent, 12345)
    }


    override fun printError(message: String) {
        this.showErrorAlert(this, message)
    }

}
