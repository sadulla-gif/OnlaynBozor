package com.example.onlayndars.screen.cart

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.onlayndars.api.db.AppDatabase
import com.example.onlayndars.api.repository.ShopRepository
import com.example.onlayndars.model.CategoryModel
import com.example.onlayndars.model.OfferModel
import com.example.onlayndars.model.ProductModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel: ViewModel() {


    val progress = MutableLiveData<Boolean>()
    val repository = ShopRepository()
    val offerData = MutableLiveData<List<OfferModel>>()
    val error = MutableLiveData<String>()
    val categoriesData = MutableLiveData<List<CategoryModel>>()
    val productsData = MutableLiveData<List<ProductModel>>()

    fun getOffers() {
         repository.getOffers(error,progress,offerData)
    }
    fun getCattegories() {
        repository.getCattegories(error,categoriesData)
    }
    fun getTopProducts() {
        repository.getTopProducts(error,productsData)
    }
    fun getProductsByCategory(id: Int){
        repository.getProductsByCategory(id, error, productsData)
    }
    fun getProductsByIds(ids: List<Int>) {
        repository.getProductsById(ids, error,progress,productsData)
    }

    fun insertAllProducts2DB(items:List<ProductModel>){
        CoroutineScope(Dispatchers.IO).launch {
            AppDatabase.getDataBase().getProductDao().insertAll(items)
            AppDatabase.getDataBase().getProductDao().deleteAll()
        }
    }
    fun insertAllCategories2DB(items:List<CategoryModel>){
        CoroutineScope(Dispatchers.IO).launch {
            AppDatabase.getDataBase().getCategoryDao().insatAll(items)
            AppDatabase.getDataBase().getCategoryDao().deleteAll()
        }
    }

    fun getAllDBProducts() {
        CoroutineScope(Dispatchers.Main).launch {
            productsData.value = withContext(Dispatchers.IO){AppDatabase.getDataBase().getProductDao() .getAllProducts()}
        }
    }
    fun getAllDBCategories() {
        CoroutineScope(Dispatchers.Main).launch {
            categoriesData.value = withContext(Dispatchers.IO){AppDatabase.getDataBase().getCategoryDao().getAllCategories()}
        }
    }

}