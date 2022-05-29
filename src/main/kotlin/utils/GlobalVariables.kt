package utils

import data.model.family.family_response
import data.model.order.order_response
import data.model.product_shopping.product_shopping_response
import data.model.product_stock.product_stock_response
import data.model.shopping_cart.shopping_cart_response
import data.model.user.user_response
import pab.lop.illustrashopandroid.data.model.analytics.analytics_response
import java.util.*
import kotlin.collections.HashMap

const val URL_HEAD_API : String = "http://illustrashop.herokuapp.com/api/"
const val URL_HEAD_IMAGES : String = "http://illustrashop.herokuapp.com/api/images/"

const val PENDING = "PENDING"
const val SENT = "SENT"
const val ENDED = "ENDED"
const val UNPAID = "UNPAID"

var rememberScreen = ""
var isEditionMode = false
var familyProducts: HashMap<String, List<product_stock_response>> = hashMapOf()
var excludedFamilies : MutableList<String> = mutableListOf("SecondFamily")
var familyNameList : MutableList<String> = mutableListOf()

val regexSpecialChars = Regex("[^A-Za-z0-9 ]")
val regexEmail = Regex("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}\\@[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}(\\.[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25})+")

var productSelected : product_stock_response? = null
var familySelected : family_response? = null
var userSelected : user_response? = null
var shoppingCartSelected : shopping_cart_response? = null
var currentShoppingProducts : MutableList<product_shopping_response> = mutableListOf()
var allOrders : MutableList<order_response> = mutableListOf()
var userOrders : MutableList<order_response> = mutableListOf()
var wishlistProducts : MutableList<product_stock_response> = mutableListOf()
var analytics : analytics_response = analytics_response("", 0, 0, 0.0f)

val userDefaultNoAuth = user_response(
    _id = "",
    name = "",
    last_name = "",
    username =  "",
    email = "",
    password = "",
    rol = "",
    address = "",
    country = "",
    postal_code = "",
    phone = "",
    pay_method = "",
    pay_number = "",
    total_spent = 0.0f,
    register_count = 0.0f,
    verified_buys = listOf(),
    wishlist = mutableListOf(),
    first_register = Date(),
    last_register = Date()
)

val shoppingCartDefaultNoAuth = shopping_cart_response(
    _id = "",
    id_user = "",
    discount = "",
    pay_method = "",
    comment = ""
)

