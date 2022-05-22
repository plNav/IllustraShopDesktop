package view.main.composables

import ScreenNav
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.rememberWindowState
import data.model.order.order_request
import data.model.product_shopping.product_shopping_response
import showToast
import theme.Spacing
import utils.currentShoppingProducts
import utils.rememberScreen
import utils.shoppingCartSelected
import utils.userSelected
import view.main.MainViewModel
import java.awt.Desktop
import java.net.URI
import java.util.*

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ShoppingCart(
    screen: MutableState<String>
) {
    val scaffoldState = rememberScaffoldState()
    val isSaved = remember { mutableStateOf(true) }
    val currentLine = remember { mutableStateOf<product_shopping_response?>(null) }
    val openPopUpEdition = remember { mutableStateOf(false) }
    val uriPopUpOpen = remember {mutableStateOf(false)}

    val total = remember { mutableStateOf(0f) }
    val openPopUpComment = remember { mutableStateOf(false) }
    val comment = remember { mutableStateOf("") }


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
    if (openPopUpComment.value) {
        PopUpComment(
            verticalGradient = verticalGradient,
            openPopUpComment = openPopUpComment,
            customSpacing = customSpacing,
            comment = comment,
            isOrders = false
        )
    }

    if (uriPopUpOpen.value){
        PopUpUrl(
            screen = screen,
            customSpacing = customSpacing,
            isPopUpUrlOpen = uriPopUpOpen,
            verticalGradient = verticalGradient
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

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                ExtendedFloatingActionButtonExample(comment = comment, openPopUpComment = openPopUpComment)
            }

            Spacer(modifier = Modifier.height(customSpacing.small))

            /*** BUY NOW ***/
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = {

                        val order = order_request(
                            user = userSelected!!,
                            products = currentShoppingProducts.filter { !it.bought },
                            total = total.value,
                            status = "PENDING",
                            comments = comment.value
                        )

                        mainViewModel.createOrder(order) {
                            mainViewModel.markBoughtProducts(currentShoppingProducts) {
                               // uriPopUpOpen.value = true
                                openInBrowser(URI(mainViewModel.currentPayPalresponse))
                                //openWebpage("http://www.google.com".toHttpUrl())
                                mainViewModel.getAllProductShopping(shoppingCartSelected!!._id) {
                                    currentShoppingProducts = mainViewModel.currentProductsShopping.toMutableList()
                                    var hasToBuy = false
                                    currentShoppingProducts.forEach { product -> if (!product.bought) hasToBuy = true }
                                    if (currentShoppingProducts.isEmpty() || !hasToBuy) {
                                        showToast("See your Product Status \n  in Options > Orders")
                                    } else screen.value = ScreenNav.ShoppingCartScreen.route
                                }
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

@Composable
fun PopUpUrl(
    screen: MutableState<String>,
    customSpacing: Spacing,
    isPopUpUrlOpen: MutableState<Boolean>,
    verticalGradient: Brush
){
    Window(
        onCloseRequest = { isPopUpUrlOpen.value = false },
        state = rememberWindowState(
            width = 500.dp,
            height = 1000.dp,
            position = WindowPosition(alignment = Alignment.Center)
        ),
        title = "Adaptive",
        resizable = false,
        undecorated = true,
        transparent = true,
        alwaysOnTop = true,
        focusable = true
    ) {
        Surface(
            shape = RoundedCornerShape(10.dp),
            color = Color.Transparent,
        ) {
            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    .background(brush = verticalGradient)
                    .clip(RoundedCornerShape(10.dp))
            ) {
                Row(
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(brush = verticalGradient)
                ) {

                    /*********** TITLE ***********/
                    Text(
                        text = "CLICK IN THE LINK TO PAY WITH PAYPAL",
                        textAlign = TextAlign.Start,
                        style = MaterialTheme.typography.body1.copy(color = Color.White),
                        modifier = Modifier
                            .fillMaxWidth(0.85f)
                            .clip(RoundedCornerShape(4.dp))
                            .background(Color.Transparent)
                            .padding(12.dp)
                            .clickable(onClick = { })
                    )



                    /*********** CLOSE ***********/
                    IconButton(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(Color.Transparent),
                        onClick = {
                            isPopUpUrlOpen.value = false

                        },
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            tint = MaterialTheme.colors.onSecondary,
                            contentDescription = null
                        )
                    }
                }

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text("http://www.google.com")

                    Spacer(modifier = Modifier.height(customSpacing.small))


                }

                Button(onClick = {  }) {
                    Text(
                        text = "Open a link",
                        color = Color.Black
                    )
                }



                /*********** OK ***********/
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(onClick = {

                                rememberScreen = ScreenNav.ShoppingCartScreen.route
                                screen.value = ScreenNav.ReloadScreen.route
                            }

                        )
                ) {

                    Text(
                        text = "OK",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.body1.copy(color = Color.White),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(4.dp))
                            .background(brush = verticalGradient)
                            .padding(12.dp)
                    )
                }
            }
        }
    }
}

fun openInBrowser(uri: URI) {
    val osName by lazy(LazyThreadSafetyMode.NONE) { System.getProperty("os.name").lowercase(Locale.getDefault()) }
    val desktop = Desktop.getDesktop()
    when {
        Desktop.isDesktopSupported() && desktop.isSupported(Desktop.Action.BROWSE) -> desktop.browse(uri)
        "mac" in osName -> Runtime.getRuntime().exec("open $uri")
        "nix" in osName || "nux" in osName -> Runtime.getRuntime().exec("xdg-open $uri")
        else -> throw RuntimeException("cannot open $uri")
    }
}

@Composable
fun ExtendedFloatingActionButtonExample(comment: MutableState<String>, openPopUpComment: MutableState<Boolean>) {
    ExtendedFloatingActionButton(

        text = {
            Text(
                text = if (comment.value.isEmpty()) "Add comment" else "Comment Added",
                color = Color.White
            )
        },
        onClick = { openPopUpComment.value = true },
        icon = {
            Icon(
                imageVector =  Icons.Filled.Check,
                tint = Color.White,
                contentDescription = ""
            )
        })
}










