package view.main.composables

import ScreenNav
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import pab.lop.illustrashopandroid.data.model.order.order_request
import pab.lop.illustrashopandroid.data.model.product_shopping.product_shopping_response
import utils.currentShoppingProducts
import utils.userSelected
import theme.Spacing
import view.main.MainViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ShoppingCart(
    screen: MutableState<String>
) {
    val scaffoldState = rememberScaffoldState()
    val isSaved = remember { mutableStateOf(true) }
    val currentLine = remember { mutableStateOf<product_shopping_response?>(null) }
    val openPopUpEdition = remember { mutableStateOf(false) }

    val total = remember { mutableStateOf(0f) }


    val mainViewModel = MainViewModel()
    val customSpacing = Spacing.customSpacing

    if (isSaved.value) {
        total.value = 0f
        for (item in currentShoppingProducts) {
            if (!item.bought) total.value += item.total
        }
        isSaved.value = false
    }


    val verticalGradient = Brush.verticalGradient(
        colors = listOf(MaterialTheme.colors.primary, MaterialTheme.colors.primaryVariant),
        startY = 0f,
        endY = 100f
    )

    if (openPopUpEdition.value) {
        PopUpEdition(
            mainViewModel = mainViewModel,
            verticalGradient = verticalGradient,
            openPopUpEdition = openPopUpEdition,
            customSpacing = customSpacing,
            currentLine = currentLine,
            isSaved = isSaved,
            screen = screen
        )
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                elevation = 0.dp,
                modifier = Modifier.background(verticalGradient),
                backgroundColor = Color.Transparent,
                title = {
                    Text(
                        text = "SHOPPING CART",
                        modifier = Modifier
                            .padding(30.dp, 0.dp, 0.dp, 0.dp),
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = { screen.value = ScreenNav.MainScreen.route }
                    ) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = null, tint = Color.White)
                    }
                },
            )
        }
    ) {

        Column() {
            CartHeader(customSpacing, total)

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(5.dp)
            ) {
                CartBody(
                    currentLine = currentLine,
                    openPopUpEdition = openPopUpEdition,
                )
            }
            Spacer(modifier = Modifier.height(customSpacing.small))

            /*** BUY NOW ***/
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = {
                        //TODO VALIDACION DE PAGO - COMPRA

                        val order = order_request(
                            user = userSelected!!,
                            products = currentShoppingProducts,
                            total = total.value,
                            status = "PENDING"
                        )

                        mainViewModel.createOrder(order) {
                            mainViewModel.markBoughtProducts(currentShoppingProducts) {
                                //linkToWebpage(currentContext)
                                //TODO OPEN PAYPAL.COM
                                screen.value = ScreenNav.MainScreen.route

                            }
                        }
                    })
            ) {

                Text(
                    text = "BUY NOW",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.body1.copy(color = Color.White),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(4.dp))
                        .background(brush = verticalGradient)
                        .padding(12.dp)

                )
            }
            Spacer(modifier = Modifier.height(customSpacing.small))

        }
    }
}

/*fun linkToWebpage(context: Context) {
    val openURL = Intent(Intent.ACTION_VIEW)
    openURL.data = Uri.parse("https://www.paypal.com/")
    startActivity(context, openURL, null)
}*/










