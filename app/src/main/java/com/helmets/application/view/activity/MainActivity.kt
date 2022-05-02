package com.helmets.application.view.activity

import android.os.Bundle
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.helmets.application.R
import com.helmets.application.databinding.ActivityMainBinding
import com.helmets.application.view.adapter.HelmetListAdapter
import com.helmets.application.view.listener.OnItemClickListenerSingleData
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        presenter.apply {
            setView(this@MainActivity)
            onStart()
        }
    }

    override fun updateHelmetList(helmetsList: ArrayList<HelmetModel>) {
        helmetListAdapter.submitList(helmetsList)
    }

    private fun initView() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)

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