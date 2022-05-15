package view.main.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import pab.lop.illustrashopandroid.utils.currentShoppingProducts
import pab.lop.illustrashopandroid.utils.shoppingCartSelected
import pab.lop.illustrashopandroid.utils.userDefaultNoAuth
import pab.lop.illustrashopandroid.utils.userSelected
import showToast

import view.main.MainViewModel


@Composable
fun TopAppBar(
    verticalGradient: Brush,
    scope: CoroutineScope,
    scaffoldState: ScaffoldState,
    addShoppingCart: MutableState<Boolean>,
    screen : MutableState<String>

) {


    val mainViewModel = MainViewModel()
    TopAppBar(
        elevation = 0.dp,
        backgroundColor = Color.Transparent,
        modifier = Modifier.background(verticalGradient),
        title = {
            Text(
                text = "ILUSTRASHOP",
                modifier = Modifier
                    .padding(30.dp, 0.dp, 0.dp, 0.dp),
                color = Color.White
            )
        },
        navigationIcon = {
            IconButton(
                onClick = {
                    scope.launch { scaffoldState.drawerState.open() }
                }) {
                Icon(Icons.Filled.Menu, contentDescription = null, tint = Color.White)
            }
        },
        actions = {
            IconButton(
                onClick = {
                    if (userSelected == userDefaultNoAuth) {showToast("Not Logged") }
                    else mainViewModel.getAllProductShopping(shoppingCartSelected!!._id) {
                        currentShoppingProducts = mainViewModel.currentProductsShopping.toMutableList()
                        var hasToBuy = false
                        currentShoppingProducts.forEach{product -> if(!product.bought)hasToBuy = true }
                        if (currentShoppingProducts.isEmpty() || !hasToBuy) {showToast("Shopping Cart Empty")}
                        else screen.value = ScreenNav.LoginScreen.route
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.ShoppingCart,
                    contentDescription = "ShoppingCart",
                    tint = Color.White,
                )
            }
        }
    )
}