package pab.lop.illustrashopandroid.ui.view.login_register

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

import data.api.ApiServices
import kotlinx.coroutines.*
import pab.lop.illustrashopandroid.data.model.shoppin.shopping_cart_request
import pab.lop.illustrashopandroid.data.model.shopping_cart.shopping_cart_response
import pab.lop.illustrashopandroid.data.model.user.user_request
import pab.lop.illustrashopandroid.data.model.user.user_response
import utils.getSHA256
import retrofit2.Response

@OptIn(DelicateCoroutinesApi::class)
class LoginRegisterViewModel {

    var currentUserResponse: MutableState<user_response?> = mutableStateOf(null)
    var currentShoppingCartResponse: MutableState<shopping_cart_response?> = mutableStateOf(null)


    var usernameListResponse: List<String> by mutableStateOf(listOf())
    var emailListResponse: List<String> by mutableStateOf(listOf())

    var updateOkResponse: Boolean by mutableStateOf(false)


    private var errorMessage: String by mutableStateOf("")


    fun validateUser(email: String, passw: String, onSuccessCallback: () -> Unit, onFailureCallback: () -> Unit) {
        GlobalScope.launch(Dispatchers.IO) {
            val apiServices = ApiServices.getInstance()
            try {
                val passwEncrypt = getSHA256(passw)
                val list: Response<List<user_response>> = apiServices.validateClient(email, passwEncrypt)

                if (list.isSuccessful && list.body()!!.isNotEmpty()) {
                    if (list.body()!!.size == 1) {
                        currentUserResponse.value = list.body()!![0]
                        onSuccessCallback()
                    } else {
                        onFailureCallback()
                    }
                }
            } catch (e: Exception) {
                errorMessage = e.message.toString()
                onFailureCallback()
            }
        }
    }


    fun getAllEmails(onSuccessCallback: () -> Unit) {
        val apiServices = ApiServices.getInstance()
        try {
            GlobalScope.launch(Dispatchers.IO) {
                val response: Response<List<String>> = apiServices.getAllEmail()
                if (response.isSuccessful) {
                    emailListResponse = response.body() as List<String>
                    onSuccessCallback()
                }
            }
        } catch (e: Exception) {
            errorMessage = e.message.toString()
        }
    }

    fun getAllUsernames(onSuccessCallback: () -> Unit) {
        val apiServices = ApiServices.getInstance()
        try {
            GlobalScope.launch(Dispatchers.IO) {
                val response: Response<List<String>> = apiServices.getAllUsernames()
                if (response.isSuccessful) {
                    usernameListResponse = response.body() as List<String>
                    onSuccessCallback()
                }
            }
        } catch (e: Exception) {
            errorMessage = e.message.toString()
        }
    }

    fun createUser(newUser: user_request, onSuccessCallback: () -> Unit) {
        val apiServices = ApiServices.getInstance()
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response: Response<user_response> = apiServices.createUser(newUser)
                if (response.isSuccessful) {
                    currentUserResponse.value = response.body()
                    onSuccessCallback()
                }
            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    fun createShoppingCart(newShoppingCart: shopping_cart_request, onSuccessCallback: () -> Unit) {
        val apiServices = ApiServices.getInstance()
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response: Response<shopping_cart_response> = apiServices.createShoppingCart(newShoppingCart)
                if (response.isSuccessful) {
                    currentShoppingCartResponse.value = response.body()
                    onSuccessCallback()
                }
            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    fun getShoppingCartFromUser(id_user: String, onSuccessCallback: () -> Unit) {
        val apiServices = ApiServices.getInstance()
        try {
            GlobalScope.launch(Dispatchers.IO) {
                val response: Response<shopping_cart_response> = apiServices.getShoppingCart(id_user)
                if (response.isSuccessful) {
                    currentShoppingCartResponse.value = response.body()
                    onSuccessCallback()
                }
            }
        } catch (e: Exception) {
            errorMessage = e.message.toString()
        }
    }

    fun updateUserPartial(id: String, user: user_response, onSuccessCallback: () -> Unit) {
        GlobalScope.launch(Dispatchers.IO) {
            val apiServices = ApiServices.getInstance()
            updateOkResponse = false
            try {
                val response = apiServices.updateUserPartial(id = id, user = user)
                if (response.isSuccessful) {
                    updateOkResponse = true
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
            try {
                val response = apiServices.updateUserComplete(id = id, user = user)
                if (response.isSuccessful) {
                    updateOkResponse = true
                    onSuccessCallback()
                }
            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }
}
