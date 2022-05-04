package com.helmets.presentation.presenter

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.helmets.presentation.BasePresenter
import com.helmets.presentation.model.BrandType.Companion.FUSE
import com.helmets.presentation.model.BrandType.Companion.ID
import com.helmets.presentation.model.BrandType.Companion.INDEX
import com.helmets.presentation.model.BrandType.Companion.RD
import com.helmets.presentation.model.BrandType.Companion.SPACE_CROWN
import com.helmets.presentation.model.FilterModel
import com.helmets.presentation.model.HelmetModel
import com.helmets.presentation.model.LogoModel
import com.helmets.presentation.model.ProductType.Companion.FLIP_UP
import com.helmets.presentation.model.ProductType.Companion.FULL_FACE
import com.helmets.presentation.model.ProductType.Companion.HALF_FACE
import com.helmets.presentation.model.ProductType.Companion.OPEN_FACE
import com.helmets.presentation.model.ViewType
import com.helmets.presentation.view.MainContractor
import javax.inject.Inject

class MainPresenter @Inject constructor() : BasePresenter<MainContractor.View>(),
    MainContractor.Presenter {
    private lateinit var database: DatabaseReference
    private val helmetsModel = arrayListOf<HelmetModel>()
    private val logoModel = arrayListOf<LogoModel>()

    companion object {
        private const val LOGO_STRING = "logo"
    }

    override fun onStart() {
        database = Firebase.database.reference
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                getDataFromServer(dataSnapshot)
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

    private fun getDataFromServer(dataSnapshot: DataSnapshot) {
        helmetsModel.clear()
        logoModel.clear()
        for (helmets in dataSnapshot.children) {
            for (brand in helmets.children) {
                setLogo(brand)
                helmetsModel.add(
                    HelmetModel(
                        viewType = ViewType.BRAND,
                        brand = brand.key.toString(),
                        imgUrl = getImageLogo(brand.key.toString()),
                    )
                )
                for (model in brand.children) {
                    if (model.key.toString() != LOGO_STRING) {
                        helmetsModel.add(
                            HelmetModel(
                                viewType = ViewType.MODEL,
                                brand = brand.key.toString(),
                                model = model.key.toString()
                            )
                        )
                    }
                    for (helmet in model.children) {
                        helmet.getValue(HelmetModel::class.java)?.let {
                            helmetsModel.add(it)
                        }
                    }
                }
            }
        }
    }

    override fun resetFilter() {
        doInView { view ->
            view.updateHelmetList(helmetsModel)
        }
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

        // filter shield level
        filterHelmetList = filterShieldLevel(filterModel, filterHelmetList)

        // filter price
        filterHelmetList = filterPrice(filterModel, filterHelmetList)

        // add header
        filterHelmetList = addHeader(filterHelmetList)

        doInView { view -> view.displayHelmetFilter(filterHelmetList) }
    }

    private fun createListBrandForFilter(filterModel: FilterModel): ArrayList<String> {
        val listBrand = arrayListOf<String>()
        if (filterModel.isBrandFuse) {
            listBrand.add(FUSE)
        }
        if (filterModel.isBrandIndex) {
            listBrand.add(INDEX)
        }
        if (filterModel.isBrandId) {
            listBrand.add(ID)
        }
        if (filterModel.isBrandRd) {
            listBrand.add(RD)
        }
        if (filterModel.isBrandSpaceCrown) {
            listBrand.add(SPACE_CROWN)
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
            filterModel.isBrandRd ||
            filterModel.isBrandSpaceCrown
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
                val type = item.productType.lowercase().split("/")
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
            listProductType.add(HALF_FACE)
        }
        if (filterModel.isOpenFace) {
            listProductType.add(OPEN_FACE)
        }
        if (filterModel.isFullFace) {
            listProductType.add(FULL_FACE)
        }
        if (filterModel.isFlipUp) {
            listProductType.add(FLIP_UP)
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
                        brand = item.brand.uppercase(),
                        imgUrl = getImageLogo(item.brand)
                    )
                )
            }

            if (!modelList.contains(item.model)) {
                modelList.add(item.model)
                helmetList.add(
                    HelmetModel(
                        viewType = ViewType.MODEL,
                        brand = item.brand.uppercase(),
                        model = item.model.uppercase()
                    )
                )
            }

            helmetList.add(item)

        }
        return helmetList
    }

    private fun filterPrice(
        filterModel: FilterModel,
        filterHelmetList: ArrayList<HelmetModel>
    ): ArrayList<HelmetModel> {
        filterModel.apply {
            minPrice?.let { min ->
                maxPrice?.let { max ->
                    val filterList = ArrayList(filterHelmetList.filter { item ->
                        item.sellPrice.toInt() in min..max
                    })
                    return filterList
                }
            }
        }
        return filterHelmetList
    }

    private fun filterShieldLevel(
        filterModel: FilterModel,
        filterHelmetList: ArrayList<HelmetModel>
    ): ArrayList<HelmetModel> {
        if (filterModel.isShield1 ||
            filterModel.isShield2
        ) {
            val filterShieldList = arrayListOf<HelmetModel>()
            var filterShieldTempList: ArrayList<HelmetModel>

            if (filterModel.isShield1) {
                filterShieldTempList =
                    ArrayList(filterHelmetList.filter { item -> item.shieldLevel.toInt() == 1 })
                filterShieldList.addAll(filterShieldTempList)
            }

            if (filterModel.isShield2) {
                filterShieldTempList =
                    ArrayList(filterHelmetList.filter { item -> item.shieldLevel.toInt() == 2 })
                filterShieldList.addAll(filterShieldTempList)
            }

            filterHelmetList.clear()
            filterHelmetList.addAll(filterShieldList)
            return filterHelmetList
        } else {
            return filterHelmetList
        }
    }

    private fun setLogo(brand: DataSnapshot) {
        val logoName = brand.key.toString()
        val logoUrl = brand.child(LOGO_STRING).value.toString()
        logoModel.add(LogoModel(logoName, logoUrl))
    }

    private fun getImageLogo(brand: String): String {
        val logo = logoModel.first { item -> item.logoName.lowercase() == brand.lowercase() }
        return logo.logoUrl
    }

}