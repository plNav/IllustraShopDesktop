package view.admin


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import data.api.ApiServices
import data.model.family.family_request
import data.model.family.family_response
import data.model.order.order_response
import data.model.product_stock.product_stock_request
import data.model.product_stock.product_stock_response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import pab.lop.illustrashopandroid.data.model.analytics.analytics_response
import retrofit2.Response


class AdminViewModel {

    private var errorMessage: String by mutableStateOf("")
    var familyProductsResponse: HashMap<String, List<product_stock_response>> by mutableStateOf(hashMapOf())
    private var byteArray: ByteArray? by mutableStateOf(null)
    private var currentFamilyResponse : family_response by mutableStateOf(family_response("",""))
    var familyNameListResponse : List<String> by mutableStateOf(listOf())
    var productListResponse : List<product_stock_response> by mutableStateOf(listOf())
    var familyListResponse : List<family_response> by mutableStateOf(listOf())
    var updateOkResponse : Boolean by mutableStateOf(false)
    var allOrdersResponse : List<order_response> by mutableStateOf(listOf())
    var analyticsResponse : analytics_response? by mutableStateOf(null)


    fun createFamily(family: family_request, onSuccessCallback: () -> Unit){
        val apiServices = ApiServices.getInstance()
        GlobalScope.launch(Dispatchers.IO) {
            try{
                val response : Response<family_response> = apiServices.createFamily(family)
                if(response.isSuccessful){
                    currentFamilyResponse = response.body()!!
                    onSuccessCallback()
                }
            }catch (e: Exception){
                errorMessage = e.message.toString()
            }
        }
    }

    fun getFamilyNames(onSuccessCallback: () -> Unit){
        val apiServices = ApiServices.getInstance()
        try{
            GlobalScope.launch(Dispatchers.IO) {
                val response : Response<List<String>> = apiServices.getFamilyNames()
                if(response.isSuccessful){
                    familyNameListResponse = response.body() as List<String>
                    onSuccessCallback()
                }
            }
        }catch (e: Exception){
            errorMessage = e.message.toString()
        }
    }

    fun getFamilies(onSuccessCallback: () -> Unit) {
        val apiServices = ApiServices.getInstance()
        try{
            GlobalScope.launch(Dispatchers.IO) {
                val response : Response<List<family_response>> = apiServices.getFamilies()
                if(response.isSuccessful){
                    familyListResponse = response.body() as List<family_response>
                    onSuccessCallback()
                }
            }
        }catch (e: Exception){
            errorMessage = e.message.toString()
        }
    }

    fun getProducts(onSuccessCallback: () -> Unit){
        val apiServices = ApiServices.getInstance()
        try{
            GlobalScope.launch(Dispatchers.IO) {
                val response : Response<List<product_stock_response>> = apiServices.getProducts()
                if(response.isSuccessful){
                    productListResponse = response.body() as List<product_stock_response>
                    onSuccessCallback()
                }
            }
        }catch (e: Exception){
            errorMessage = e.message.toString()
        }
    }



    fun createProductStock(newProduct: product_stock_request, onSuccessCallback: () -> Unit) {
        val apiServices = ApiServices.getInstance()

        GlobalScope.launch(Dispatchers.IO) {
            try{
                val response : Response<product_stock_response> = apiServices.createProductStock(newProduct)
                if(response.isSuccessful){
                    onSuccessCallback()
                }
            }catch (e: Exception){
                errorMessage = e.message.toString()
            }
        }
    }

    /*fun multipartImageUpload(
        byteArray: MutableState<ByteArray?>,
        bitmap: MutableState<Bitmap?>,
        customName: MutableState<String>,
        onSuccess: () -> Unit
    ) {
        val apiService = ApiServices.getInstance()
        try {
            GlobalScope.launch(Dispatchers.IO) {
                if (bitmap.value != null) {

                    val bos = ByteArrayOutputStream()
                    bitmap.value?.compress(Bitmap.CompressFormat.JPEG, 100, bos)
                    byteArray.value = bos.toByteArray()

                    val filesDir: File = context.filesDir
                    val file = File(filesDir, customName.value + ".jpg")
                    val fos = FileOutputStream(file)

                    fos.write(byteArray.value)
                    fos.flush()
                    fos.close()

                    val fileBody = ProgressRequestBody(file)
                    val body: MultipartBody.Part = createFormData("upload", file.name, fileBody)
                    val name = RequestBody.create("text/plain".toMediaTypeOrNull(), "upload")

                    val mainHandler = Handler(getMainLooper())

                    val runnable = Runnable {
                        viewModelScope.async {
                            apiService.postImage(image = body, name = name).await()
                        }
                    }
                    mainHandler.postDelayed(runnable, 200)  //delay)
                    onSuccess()
                }
            }

        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()

        }
    }*/

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
                    val splitted = replaced.split("__v=0.0}")

                    familyName = parts[0].replace("{family=", "").replace(",", "")

                    for (s in splitted) {

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
            }
        }
    }

    fun updateFamily(
        newName: String,
        oldName: family_response?,
        onSuccessCallback: () -> Unit
    ) {
        GlobalScope.launch(Dispatchers.IO) {
            val apiServices = ApiServices.getInstance()
            updateOkResponse = false
            try{
                if(oldName != null){
                    val response = apiServices.updateFamily(oldName._id, family_response(oldName._id, newName))
                    if (response.isSuccessful){
                        updateOkResponse = true
                        onSuccessCallback()
                    }
                }

            }catch (e: Exception){
                errorMessage = e.message.toString()

            }
        }

    }

    fun deleteFamily(familySelected: family_response?, onSuccessCallback: () -> Unit) {
        GlobalScope.launch(Dispatchers.IO) {
            val apiServices = ApiServices.getInstance()
            updateOkResponse = false
            try{
               if(familySelected != null){
                   val response = apiServices.deleteFamily(familySelected._id)
                   if (response.isSuccessful){
                       updateOkResponse = true
                       onSuccessCallback()
                   }
               }
            }catch (e: Exception){
                errorMessage = e.message.toString()
            }
        }
    }

    fun updateProductStock(
        newProduct: product_stock_response,
        oldProductId: String,
        onSuccessCallback: () -> Unit
    ) {
        GlobalScope.launch(Dispatchers.IO) {
            val apiServices = ApiServices.getInstance()
            updateOkResponse = false
            try{
                    val response = apiServices.updateProductStock(oldProductId = oldProductId, newProduct = newProduct)
                    if (response.isSuccessful){
                        updateOkResponse = true
                        onSuccessCallback()
                    }

            }catch (e: Exception){
                errorMessage = e.message.toString()
            }
        }
    }

    fun deleteProductStock(oldProductId: String, onSuccessCallback: () -> Unit) {
        GlobalScope.launch(Dispatchers.IO) {
            val apiServices = ApiServices.getInstance()
            updateOkResponse = false
            try{
                    val response = apiServices.deleteProductStock(oldProductId)
                    if (response.isSuccessful){
                        updateOkResponse = true
                        onSuccessCallback()
                    }
            }catch (e: Exception){
                errorMessage = e.message.toString()
            }
        }
    }

    fun getOrders(onSuccessCallback: () -> Unit) {
        GlobalScope.launch(Dispatchers.IO) {
            val apiServices = ApiServices.getInstance()
            try {
                val response : Response<List<order_response>> = apiServices.getOrders()
                if (response.isSuccessful) {
                    updateOkResponse = true
                    allOrdersResponse = response.body()!!
                    onSuccessCallback()
                }
            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }

        }

    }

    fun updateOrder(order: order_response, onSuccessCallback: () -> Unit) {
        GlobalScope.launch(Dispatchers.IO) {
            val apiServices = ApiServices.getInstance()
            updateOkResponse = false
            try{
                val response = apiServices.updateOrder(oldOrderId = order._id, newOrder = order)
                if (response.isSuccessful){
                    updateOkResponse = true
                    onSuccessCallback()
                }
            }catch (e: Exception){
                errorMessage = e.message.toString()
            }
        }
    }

    fun getUserOrders(userId: String, onSuccessCallback: () -> Unit) {
        GlobalScope.launch(Dispatchers.IO) {
            val apiServices = ApiServices.getInstance()
            try {
                allOrdersResponse = listOf()
                val response : Response<List<order_response>> = apiServices.getUserOrders(id = userId)
                if (response.isSuccessful) {
                    updateOkResponse = true
                    allOrdersResponse = response.body()!!
                    onSuccessCallback()
                }
            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }

        }
    }

    fun getAnalytics(onSuccessCallback: () -> Unit) {
        GlobalScope.launch {
            val apiServices = ApiServices.getInstance()
            try {
                val response : Response<analytics_response> = apiServices.getAnalytics()
                if (response.isSuccessful) {
                    analyticsResponse = response.body()!!
                    onSuccessCallback()
                }

            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }

        }
    }


}