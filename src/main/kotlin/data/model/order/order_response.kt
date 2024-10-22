package data.model.order

import data.model.product_shopping.product_shopping_response
import data.model.user.user_response
import java.util.*

data class order_response(
    val _id: String,
    var user: user_response,
    var products: List<product_shopping_response>,
    var total: Float,
    var date_order : Date,
    var date_arrive: Date,
    var status: String,
    var comments: String
)