package com.helmets.application.view.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.helmets.application.R
import com.helmets.application.databinding.ActivityMainBinding
import com.helmets.application.view.adapter.HelmetListAdapter
import com.helmets.application.view.listener.OnItemClickListenerSingleData
import com.helmets.presentation.model.FilterModel
import com.helmets.presentation.model.HelmetModel
import com.helmets.presentation.presenter.MainPresenter
import com.helmets.presentation.view.MainContractor
import javax.inject.Inject


class MainActivity : BaseActivity(), MainContractor.View,
    OnItemClickListenerSingleData<HelmetModel> {

    private lateinit var binding: ActivityMainBinding
    private lateinit var helmetListAdapter: HelmetListAdapter

    @Inject
    lateinit var presenter: MainPresenter

    private var resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // There are no request codes
            val data: Intent? = result.data
            data?.getParcelableExtra<FilterModel>("filter")?.let {
                presenter.setFilter(it)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        presenter.apply {
            setView(this@MainActivity)
            onStart()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.filter_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.toString()) {
            "filter" -> {
                val intent = Intent(this, FilterActivity::class.java)
                resultLauncher.launch(intent)
            }
            else -> {
                // do nothing
            }
        }
        return true
    }


    override fun updateHelmetList(helmetsList: ArrayList<HelmetModel>) {
        helmetListAdapter.updateList(helmetsList)
    }

    override fun displayHelmetFilter(helmetsList: ArrayList<HelmetModel>) {
        helmetListAdapter.updateList(helmetsList)
    }

    private fun initView() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // set up adapter
        helmetListAdapter = HelmetListAdapter(this)
        binding.rvHelmetList.apply {
            adapter = helmetListAdapter
            this.layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    override fun onCallbackItemData(display: HelmetModel) {
        // do nothing
    }
}