package com.helmets.presentation.presenter

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.helmets.presentation.BasePresenter
import com.helmets.presentation.model.FilterModel
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
                        helmetsModel.add(
                            HelmetModel(
                                viewType = ViewType.BRAND,
                                brand = brand.key.toString(),
                            )
                        )
                        for (model in brand.children) {
                            helmetsModel.add(
                                HelmetModel(
                                    viewType = ViewType.MODEL,
                                    brand = brand.key.toString(),
                                    model = model.key.toString()
                                )
                            )
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

    override fun setFilter(filterModel: FilterModel) {
        var filterHelmetList = arrayListOf<HelmetModel>()
        filterHelmetList.addAll(helmetsModel)

        // filter only item type
        filterHelmetList = ArrayList(filterHelmetList.filter { item ->
            item.viewType == ViewType.ITEM
        })

        // filter brand
        filterHelmetList = filterBrand(filterModel, filterHelmetList)

        // filter size
        filterHelmetList = filterSize(filterModel, filterHelmetList)

        // filter product type
        filterHelmetList = filterProductType(filterModel, filterHelmetList)


        filterHelmetList = addHeader(filterHelmetList)


        doInView { view -> view.displayHelmetFilter(filterHelmetList) }
    }

    private fun createListBrandForFilter(filterModel: FilterModel): ArrayList<String> {
        val listBrand = arrayListOf<String>()
        if (filterModel.isBrandFuse) {
            listBrand.add("fuse")
        }
        if (filterModel.isBrandIndex) {
            listBrand.add("index")
        }
        if (filterModel.isBrandId) {
            listBrand.add("id")
        }
        if (filterModel.isBrandRd) {
            listBrand.add("rd")
        }
        return listBrand
    }

    private fun filterBrand(
        filterModel: FilterModel,
        filterHelmetList: ArrayList<HelmetModel>
    ): ArrayList<HelmetModel> {
        return if (filterModel.isBrandFuse ||
            filterModel.isBrandIndex ||
            filterModel.isBrandId ||
            filterModel.isBrandRd
        ) {
            val brandList = createListBrandForFilter(filterModel)
            val filterList = ArrayList(filterHelmetList.filter { item ->
                brandList.contains(item.brand.lowercase().trim())
            })
            filterList
        } else {
            filterHelmetList
        }
    }

    private fun filterSize(
        filterModel: FilterModel,
        filterHelmetList: ArrayList<HelmetModel>
    ): ArrayList<HelmetModel> {
        if (filterModel.isSizeS ||
            filterModel.isSizeM ||
            filterModel.isSizeL ||
            filterModel.isSizeXL ||
            filterModel.isSizeXXL
        ) {
            val filterSizeList = arrayListOf<HelmetModel>()
            var filterSizeTempList: ArrayList<HelmetModel>

            if (filterModel.isSizeS) {
                filterSizeTempList =
                    ArrayList(filterHelmetList.filter { item -> item.s.toInt() > 0 })
                filterSizeList.addAll(filterSizeTempList)
            }

            if (filterModel.isSizeM) {
                filterSizeTempList =
                    ArrayList(filterHelmetList.filter { item -> item.m.toInt() > 0 })
                filterSizeList.addAll(filterSizeTempList)
            }

            if (filterModel.isSizeL) {
                filterSizeTempList =
                    ArrayList(filterHelmetList.filter { item -> item.l.toInt() > 0 })
                filterSizeList.addAll(filterSizeTempList)
            }

            if (filterModel.isSizeXL) {
                filterSizeTempList =
                    ArrayList(filterHelmetList.filter { item -> item.xl.toInt() > 0 })
                filterSizeList.addAll(filterSizeTempList)
            }

            if (filterModel.isSizeXXL) {
                filterSizeTempList =
                    ArrayList(filterHelmetList.filter { item -> item.xxl.toInt() > 0 })
                filterSizeList.addAll(filterSizeTempList)
            }
            filterHelmetList.clear()
            filterHelmetList.addAll(filterSizeList)
            return filterHelmetList
        } else {
            return filterHelmetList
        }
    }

    private fun filterProductType(
        filterModel: FilterModel,
        filterHelmetList: ArrayList<HelmetModel>
    ): ArrayList<HelmetModel> {
        return if (filterModel.isHalfFace ||
            filterModel.isOpenFace ||
            filterModel.isFullFace ||
            filterModel.isFlipUp
        ) {
            val productTypeList = createListProductTypeForFilter(filterModel)
            val filterList = ArrayList(filterHelmetList.filter { item ->
                val type = item.product_type.lowercase().split("/")
                when (type.size) {
                    1 -> {
                        productTypeList.contains(type[0].lowercase().trim())
                    }
                    2 -> {
                        productTypeList.contains(type[0].lowercase().trim()) ||
                                productTypeList.contains(type[1].lowercase().trim())
                    }
                    else -> {
                        productTypeList.contains(type[0].lowercase().trim()) ||
                                productTypeList.contains(type[1].lowercase().trim()) ||
                                productTypeList.contains(type[2].lowercase().trim())
                    }
                }
            })
            filterList
        } else {
            filterHelmetList
        }
    }

    private fun createListProductTypeForFilter(filterModel: FilterModel): ArrayList<String> {
        val listProductType = arrayListOf<String>()
        if (filterModel.isHalfFace) {
            listProductType.add("half face")
        }
        if (filterModel.isOpenFace) {
            listProductType.add("open face")
        }
        if (filterModel.isFullFace) {
            listProductType.add("full face")
        }
        if (filterModel.isFlipUp) {
            listProductType.add("flip up")
        }
        return listProductType
    }

    private fun addHeader(filterHelmetList: ArrayList<HelmetModel>): ArrayList<HelmetModel> {
        val helmetList = arrayListOf<HelmetModel>()
        val brandList = arrayListOf<String>()
        val modelList = arrayListOf<String>()

        for (item in filterHelmetList) {
            if (!brandList.contains(item.brand)) {
                brandList.add(item.brand)
                helmetList.add(
                    HelmetModel(
                        viewType = ViewType.BRAND,
                        brand = item.brand
                    )
                )
            }

            if (!modelList.contains(item.model)) {
                modelList.add(item.model)
                helmetList.add(
                    HelmetModel(
                        viewType = ViewType.MODEL,
                        brand = item.brand,
                        model = item.model
                    )
                )
            }

            helmetList.add(item)

        }
        return helmetList
    }

}