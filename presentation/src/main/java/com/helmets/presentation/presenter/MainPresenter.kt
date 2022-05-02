package com.helmets.presentation.presenter

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.helmets.presentation.BasePresenter
import com.helmets.presentation.model.HelmetModel
import com.helmets.presentation.model.ViewType
import com.helmets.presentation.view.MainContractor
import javax.inject.Inject

class MainPresenter @Inject constructor() : BasePresenter<MainContractor.View>(),
    MainContractor.Presenter {
    private lateinit var database: DatabaseReference
    private val helmetsModel = arrayListOf<HelmetModel>()
    override fun onStart() {
        database = Firebase.database.reference
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                helmetsModel.clear()
                for (helmets in dataSnapshot.children) {
                    for (brand in helmets.children) {
                        helmetsModel.add(HelmetModel(viewType = ViewType.BRAND,brand = brand.key.toString()))
                        for (model in brand.children) {
                            helmetsModel.add(HelmetModel(viewType = ViewType.MODEL,model = model.key.toString()))
                            for (helmet in model.children) {
                                helmet.getValue(HelmetModel::class.java)?.let {
                                    helmetsModel.add(it)
                                }
                            }
                        }
                    }
                }
                doInView { view ->
                    view.updateHelmetList(helmetsModel)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // do nothing
            }
        }
        database.addValueEventListener(postListener)
    }

}