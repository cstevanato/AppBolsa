package br.com.stv.appbolsa.ui.activity.main

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import br.com.stv.appbolsa.R
import br.com.stv.appbolsa.dao.ISummaryStock
import br.com.stv.appbolsa.model.SummaryStock
import br.com.stv.appbolsa.ui.SeparatorDecoration
import br.com.stv.appbolsa.ui.activity.buy.BuyActivity
import br.com.stv.appbolsa.ui.adapter.SummaryAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
    SummaryContract.View {

    private val summaryPresenter : SummaryContract.Presenter by lazy {
        SummaryPresenter(this, this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

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
        menuInflater.inflate(R.menu.main, menu)
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
            R.id.nav_buy -> {
                val intent = Intent(this, BuyActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_sell-> {

            }

        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }


    //region SummaryContract.View
    private var longClickSelect = -1
    override fun showTasks(summaryList: List<ISummaryStock>) {
        rv_summary.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        val decoration = SeparatorDecoration(this)
        rv_summary.addItemDecoration(decoration)

        rv_summary.adapter = SummaryAdapter(summaryList) { longClickSelect ->
            this.longClickSelect = longClickSelect
        }

        rv_summary.setOnCreateContextMenuListener { menu: ContextMenu, v : View?, menuInfo: ContextMenu.ContextMenuInfo? ->
            menu.add(Menu.NONE, 1, Menu.NONE, "Executar")
        }
    }

    override fun notifyReceivedTasks() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    //endregion

    override fun printError(message: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
