package view.main.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.rememberWindowState
import data.model.product_shopping.product_shopping_request
import showToast
import theme.Spacing
import utils.*
import view.main.MainViewModel


@Composable
fun PopUpDetails(
    popUpDetailsOpen: MutableState<Boolean>,
    verticalGradient: Brush,
    addShoppingCart: MutableState<Boolean>,
    verticalGradientDisabled: Brush,
    isWishList: Boolean,
    screen : MutableState<String>
) {


    val mainViewModel = MainViewModel()
    val customSpacing = Spacing.customSpacing
    val context = rememberCompositionContext()

    val verticalGradientIncomplete = Brush.verticalGradient(
        colors = listOf(MaterialTheme.colors.onSecondary, Color.DarkGray),
        startY = 0f,
        endY = 100f
    )

    val isInWishList = remember { mutableStateOf(userSelected!!.wishlist.contains(productSelected!!._id)) }

/*    AlertDialog(onDismissRequest = {},
        title = {
            Text("A title")
        },
        text = {
            Text("A text")
        },
        confirmButton = {
            Button(onClick = {}) {
                Text("A button")
            }
        },
    )*/


    Window(
        onCloseRequest = { popUpDetailsOpen.value = false },
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
                    .background(brush = verticalGradient)
                    .clip(RoundedCornerShape(10.dp))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(brush = verticalGradient),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    /************ TITLE ************/
                    Text(
                        text = "${productSelected!!.name} - ${productSelected!!.price} €",
                        textAlign = TextAlign.Start,
                        style = MaterialTheme.typography.body1.copy(color = Color.White),
                        modifier = Modifier
                            .fillMaxWidth(0.85f)
                            .clip(RoundedCornerShape(4.dp))
                            .background(Color.Transparent)
                            .padding(12.dp)
                            .clickable(onClick = { })
                    )

                    /************ CLOSE ************/
                    IconButton(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(Color.Transparent),
                        onClick = { popUpDetailsOpen.value = false }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            tint = MaterialTheme.colors.onSecondary,
                            contentDescription = "",
                        )
                    }
                }

                ZoomableImage(isRotation = false)


                /************ ADD TO SHOPPING CART ************/
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(onClick = {
                            var isRepeated = false
                            if (userSelected == userDefaultNoAuth) {
                                showToast("Login Needed")
                            } else {
                                if (currentShoppingProducts.isEmpty()) {
                                    createProductShopping(
                                        mainViewModel,
                                        addShoppingCart,
                                        popUpDetailsOpen,
                                    )
                                } else {
                                    for (product in currentShoppingProducts) {
                                        if (!product.bought && product.name == productSelected!!.name) {
                                            product.amount++
                                            product.total = product.amount * product.price
                                            isRepeated = true
                                            mainViewModel.updateProductShopping(product) {
                                            }
                                        }
                                    }
                                    if (!isRepeated) {
                                        createProductShopping(
                                            mainViewModel,
                                            addShoppingCart,
                                            popUpDetailsOpen,
                                        )
                                    } else {
                                        addShoppingCart.value = true
                                        popUpDetailsOpen.value = false
                                        showToast("Product added to shopping cart")
                                    }
                                }
                            }
                        })
                ) {

                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(4.dp))
                            .background(brush = if (userSelected!! == userDefaultNoAuth) verticalGradientDisabled else verticalGradient)
                            .padding(12.dp),
                        text = "Add product to shopping cart",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.body1.copy(color = Color.White),
                    )
                }


                /************ ADD TO WISHLIST ************/
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(onClick = {
                            if (isWishList) {
                                userSelected!!.wishlist.remove(productSelected!!._id)
                                mainViewModel.updateUserComplete(userSelected!!._id, userSelected!!) {
                                    mainViewModel.getAllProductStock(userSelected!!.wishlist) {
                                        wishlistProducts = mainViewModel.productListResponse
                                        showToast("Deleted")
                                        popUpDetailsOpen.value = false

                                    }
                                }
                            } else {
                                if (userSelected!! == userDefaultNoAuth) {
                                   showToast("Not Logged")
                                } else {
                                    if (!isInWishList.value) {
                                        userSelected!!.wishlist.add(productSelected!!._id)
                                        mainViewModel.updateUserComplete(userSelected!!._id, userSelected!!) {
                                            showToast("Added to WishList!")
                                            popUpDetailsOpen.value = false
                                        }
                                    } else {

                                      showToast("Already in WishList")
                                    }
                                }
                            }
                        })
                ) {

                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(4.dp))
                            .background(
                                brush =
                                if (isWishList) verticalGradientIncomplete
                                else if (userSelected!! == userDefaultNoAuth || isInWishList.value) verticalGradientDisabled
                                else verticalGradient
                            )
                            .padding(12.dp),
                        text =  if (isWishList)"Delete product from wishlist" else "Add to wishlist",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.body1.copy(color = Color.White)
                    )
                }

            }
        }
    }
}

private fun createProductShopping(
    mainViewModel: MainViewModel,
    addShoppingCart: MutableState<Boolean>,
    popUpDetailsOpen: MutableState<Boolean>,
) {
    val product = product_shopping_request(
        id_shopping_cart = shoppingCartSelected!!._id,
        id_product = productSelected!!._id,
        name = productSelected!!.name,
        image = productSelected!!.image,
        price = productSelected!!.price,
        total = productSelected!!.price
    )

    mainViewModel.createProductShopping(newProduct = product) {
        currentShoppingProducts.add(mainViewModel.currentProductShoppingResponse!!)
        addShoppingCart.value = true
        popUpDetailsOpen.value = false
        showToast("Product Added to Shopping Cart")
    }
}

@Composable
fun ZoomableImage(
    maxScale: Float = .30f,
    minScale: Float = 3f,
    contentScale: ContentScale = ContentScale.Fit,
    isRotation: Boolean = false,
    isZoomable: Boolean = true
) {
    val scale = remember { mutableStateOf(1f) }
    val rotationState = remember { mutableStateOf(1f) }
    val offsetX = remember { mutableStateOf(1f) }
    val offsetY = remember { mutableStateOf(1f) }



    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .height(800.dp)
            .clip(RectangleShape)
            .background(Color.Transparent)
            .pointerInput(Unit) {
                if (isZoomable) {
                    forEachGesture {
                        awaitPointerEventScope {
                            awaitFirstDown()
                            do {
                                val event = awaitPointerEvent()
                                scale.value *= event.calculateZoom()
                                if (scale.value > 1) {
                                    val offset = event.calculatePan()
                                    offsetX.value += offset.x
                                    offsetY.value += offset.y
                                    rotationState.value += event.calculateRotation()
                                } else {
                                    scale.value = 1f
                                    offsetX.value = 1f
                                    offsetY.value = 1f
                                }
                            } while (event.changes.any { it.pressed })
                        }
                    }
                }
            }
    ) {
        AsyncImage(
            load = { loadImageBitmap("$URL_HEAD_IMAGES${productSelected!!.image}") },
            painterFor = { remember { BitmapPainter(it) } },
            contentDescription = "Sample",
            modifier = Modifier
                .align(Alignment.Center)
                .graphicsLayer {
                    if (isZoomable) {
                        scaleX = maxOf(maxScale, minOf(minScale, scale.value))
                        scaleY = maxOf(maxScale, minOf(minScale, scale.value))
                        if (isRotation) {
                            rotationZ = rotationState.value
                        }
                        translationX = offsetX.value
                        translationY = offsetY.value
                    }
                },
            contentScale = contentScale
        )
    }
}