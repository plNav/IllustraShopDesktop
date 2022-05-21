package view.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

import data.api.ApiServices
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import data.model.order.order_request
import data.model.product_shopping.product_shopping_request
import data.model.product_shopping.product_shopping_response
import data.model.user.user_response
import data.model.product_stock.product_stock_response
import data.model.shopping_cart.shopping_cart_response
import retrofit2.Response

@OptIn(DelicateCoroutinesApi::class)
@Suppress("UNCHECKED_CAST")
class MainViewModel {

    var allUsersClientResponse: List<user_response> by mutableStateOf(listOf())
    var familyProductsResponse: HashMap<String, List<product_stock_response>> by mutableStateOf(hashMapOf())
    var currentShoppingCartResponse: shopping_cart_response? by mutableStateOf(null)
    var currentProductsShopping : MutableList<product_shopping_response> by mutableStateOf(mutableListOf())
    var updateOkResponse : Boolean by mutableStateOf(false)
    var currentProductShoppingResponse : product_shopping_response? by mutableStateOf(null)
    var productStockResponse : product_stock_response? by mutableStateOf(null)
    var productListResponse : MutableList<product_stock_response> by mutableStateOf(mutableListOf())
    var currentPayPalresponse : String by mutableStateOf("")



    private var errorMessage: String by mutableStateOf("")


    fun getAllUsers(onSuccessCallback: () -> Unit) {
        GlobalScope.launch(Dispatchers.IO) {
            val apiServices = ApiServices.getInstance()

            try {
                allUsersClientResponse = apiServices.getAllUsers()
                onSuccessCallback()

            } catch (e: Exception) {
            }
        }
    }

    private fun getProductStock(id: String, onSuccessCallback: () -> Unit){
        val apiServices = ApiServices.getInstance()
        GlobalScope.launch(Dispatchers.IO) {
            val response: Response<product_stock_response> = apiServices.getProductStock(id)
            if (response.isSuccessful) {
                productStockResponse = response.body()
                onSuccessCallback()
            }
        }
    }

    fun getAllProductStock(productList : MutableList<String>, onSuccessCallback: () -> Unit){
           val apiServices = ApiServices.getInstance()
        GlobalScope.launch(Dispatchers.IO) {
            try {
              //  val products = productList.joinToString("-")
              //  Logger.i(products)
                val response: Response<List<product_stock_response>> =
                    apiServices.getProductsWish(products = productList as ArrayList<String>)
                if (response.isSuccessful) {
                    productListResponse = response.body() as MutableList<product_stock_response>
                    onSuccessCallback()
                }
            }catch (e : Exception){
                e.printStackTrace()
            }
        }
    }

    fun getProductsFamily(onSuccessCallback: () -> Unit) {
        GlobalScope.launch(Dispatchers.IO) {
            val apiServices = ApiServices.getInstance()
            val hasMapFamilyProduct: HashMap<String, List<product_stock_response>> = hashMapOf()
            val productsFamily: MutableList<product_stock_response> = mutableListOf()
            try {
                var familyName: String

                val list = apiServices.getProductsFamily()

                for (item in list) {

                    if (item == "") continue

                    val parts = item.toString().split(", separator=null,")
                    val replaced = parts[1].replace("products=[[{", "").replace("]]}", "")
                    val split = replaced.split("__v=0.0}")

                    familyName = parts[0].replace("{family=", "").replace(",", "")

                    for (s in split) {

                        var _id = ""
                        var name = ""
                        var image = ""
                        var price = 0.0f
                        var stock = 0.0f
                        var sales = 0.0f
                        var wishlists = 0.0f
                        val likes: MutableList<String> = mutableListOf()
                        val families: MutableList<String> = mutableListOf()


                        if (s.isEmpty()) continue
                        val sReplaced = s.replace(", {", "")
                        val familiesRaw = sReplaced.split("families=")
                        val fam = familiesRaw[1].replace("[", "").replace("]", "").split(",")
                        for (i in 0..fam.size - 2) {
                            families.add(fam[i].trim())
                        }


                        val likesRaw = familiesRaw[0].split("likes=")
                        val restAtr = likesRaw[0].split(",")
                        val lik = likesRaw[1].replace("[", "").replace("]", "").split(",")
                        for (i in 0..lik.size - 2) {
                            if (lik[i].trim().isNotEmpty()) likes.add(lik[i].trim())
                        }


                        for (atr in restAtr) {

                            if (atr.contains("=") && atr.isNotEmpty()) {
                                val atrValue = atr.split("=")
                                if (atrValue.size < 2) continue
                                when {
                                    atrValue[0].trim() == "_id" -> _id = atrValue[1]
                                    atrValue[0].trim() == "name" -> name = atrValue[1]
                                    atrValue[0].trim() == "image" -> image = atrValue[1]
                                    atrValue[0].trim() == "price" -> price = atrValue[1].toFloat()
                                    atrValue[0].trim() == "stock" -> stock = atrValue[1].toFloat()
                                    atrValue[0].trim() == "sales" -> sales = atrValue[1].toFloat()
                                    atrValue[0].trim() == "wishlists" -> wishlists = atrValue[1].toFloat()
                                    else -> Unit
                                }
                            }
                        }

                        val p = product_stock_response(
                            _id = _id,
                            name = name,
                            image = image,
                            price = price,
                            stock = stock,
                            sales = sales,
                            wishlists = wishlists,
                            likes = likes,
                            families = families
                        )
                        productsFamily.add(p)

                    }
                    hasMapFamilyProduct.put(familyName, productsFamily.toList())
                    productsFamily.clear()
                }
                familyProductsResponse = hasMapFamilyProduct
                onSuccessCallback()

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getShoppingCart(id_user: String, onSuccessCallback: () -> Unit) {

        val apiServices = ApiServices.getInstance()
        try {
            GlobalScope.launch(Dispatchers.IO) {
                val response: Response<shopping_cart_response> = apiServices.getShoppingCart(id_user)
                if (response.isSuccessful) {
                    currentShoppingCartResponse = response.body()
                    onSuccessCallback()
                }
            }
        } catch (e: Exception) {
            errorMessage = e.message.toString()
        }

    }

    fun createProductShopping(newProduct: product_shopping_request, onSuccessCallback: () -> Unit) {

        val apiServices = ApiServices.getInstance()
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response: Response<product_shopping_response> = apiServices.createProductShopping(newProduct)
                if (response.isSuccessful) {
                    currentProductShoppingResponse = response.body()!!
                    onSuccessCallback()
                }
            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }

        }

    }

    fun getAllProductShopping(id_cart: String, onSuccessCallback: () -> Unit) {

        val apiServices = ApiServices.getInstance()
        try {
            GlobalScope.launch(Dispatchers.IO) {
                val response: Response<List<product_shopping_response>> = apiServices.getAllProductsFromShoppingCart(id_cart)
                if (response.isSuccessful) {
                    currentProductsShopping = mutableListOf()
                    currentProductsShopping = (response.body() as MutableList<product_shopping_response>?)!!
                    onSuccessCallback()
                }            }
        } catch (e: Exception) {
            errorMessage = e.message.toString()
        }
    }

    fun updateProductShopping(product: product_shopping_response, onSuccessCallback: () -> Unit) {
        GlobalScope.launch(Dispatchers.IO) {
            val apiServices = ApiServices.getInstance()
            updateOkResponse = false
            try{
                product.total = product.amount * product.price
                val response = apiServices.updateProductShopping(product._id, product)
                if (response.isSuccessful) {
                    updateOkResponse = true
                    onSuccessCallback()
                }
            }catch (e: Exception){
                errorMessage = e.message.toString()
            }
        }
    }

    fun markBoughtProducts(products: List<product_shopping_response>, onSuccessCallback: () -> Unit) {
        GlobalScope.launch(Dispatchers.IO) {
            val apiServices = ApiServices.getInstance()
            updateOkResponse = false
            try{
                for(product in products){
                    if(!product.bought){
                        product.bought = true
                        val response = apiServices.updateProductShopping(product._id, product)
                        if (response.isSuccessful) {
                            updateOkResponse = true
                        }
                    }
                }
                onSuccessCallback()

            }catch (e: Exception){
                errorMessage = e.message.toString()
            }
        }
    }

    fun createOrder(order: order_request, onSuccessCallback: () -> Unit) {

        val apiServices = ApiServices.getInstance()
        GlobalScope.launch(Dispatchers.IO) {
            try {
                order.status = "UNPAID"
                val response: Response<Any> = apiServices.createOrder(
                    pay = order.total,
                    order = order
                )
                if (response.isSuccessful) {
                    currentPayPalresponse = response.body().toString()
                    onSuccessCallback()
                }
            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }

        }
    }

    fun deleteProductsCart(products : List<product_shopping_response>, onSuccessCallback: () -> Unit){
        GlobalScope.launch(Dispatchers.IO) {
            val apiServices = ApiServices.getInstance()
            updateOkResponse = false
            try{
                for(product in products){
                    val response = apiServices.deleteProductShopping(product._id)
                    if(response.isSuccessful) continue
                    else throw Exception("Fail deleting $product")
                }
                onSuccessCallback()

            }catch (e: Exception){
                errorMessage = e.message.toString()
            }
        }
    }

    fun deleteProductSelected(id: String, onSuccessCallback: () -> Unit) {

        val apiServices = ApiServices.getInstance()
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response: Response<Any> = apiServices.deleteProductShopping(id)
                if (response.isSuccessful) {
                    onSuccessCallback()
                }
            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }

        }
    }

    fun updateUserComplete(id: String, user: user_response, onSuccessCallback: () -> Unit) {
        GlobalScope.launch(Dispatchers.IO) {
            val apiServices = ApiServices.getInstance()
            updateOkResponse = false
            try{
                val response = apiServices.updateUserComplete(id = id, user = user)
                if (response.isSuccessful) {
                    updateOkResponse = true
                    onSuccessCallback()
                }
            }catch (e: Exception){
                errorMessage = e.message.toString()
            }
        }
    }


}