package view.main.composables

import ScreenNav
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.rememberWindowState
import data.model.product_shopping.product_shopping_response
import showToast
import theme.Spacing
import utils.*
import view.main.MainViewModel

@Composable
fun PopUpEdition(
    mainViewModel : MainViewModel,
    verticalGradient: Brush,
    openPopUpEdition: MutableState<Boolean>,
    customSpacing: Spacing,
    currentLine: MutableState<product_shopping_response?>,
    isSaved: MutableState<Boolean>,
    screen : MutableState<String>
) {
    val amount = remember { mutableStateOf(currentLine.value!!.amount) }
    val deleteConfirmation = remember { mutableStateOf(false) }

    Window(
        onCloseRequest = { openPopUpEdition.value = false },
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
                        text = "${currentLine.value!!.name} - ${amount.value}",
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
                            openPopUpEdition.value = false

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
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        IconButton(
                            onClick = {
                                amount.value++
                                deleteConfirmation.value = false
                            }
                        ) {
                            Icon(
                                Icons.Filled.AddCircle,
                                tint = MaterialTheme.colors.onSecondary,
                                contentDescription = null
                            )
                        }

                        Card(
                            modifier = Modifier
                                .height(150.dp)
                                .width(150.dp)
                        ) {
                            AsyncImage(
                                load = { loadImageBitmap("$URL_HEAD_IMAGES${currentLine.value!!.image}") },
                                painterFor = { remember { BitmapPainter(it) } },
                                contentDescription = "Sample",
                                contentScale = ContentScale.Crop
                            )
                        }

                        IconButton(
                            onClick = {
                                if (amount.value == 1f) {
                                    if (deleteConfirmation.value) {
                                            mainViewModel.deleteProductSelected(id = currentLine.value!!._id){
                                                mainViewModel.getAllProductShopping(shoppingCartSelected!!._id) {
                                                    currentShoppingProducts =
                                                        mainViewModel.currentProductsShopping.toMutableList()
                                                    openPopUpEdition.value = false
                                                    rememberScreen = ScreenNav.ShoppingCartScreen.route
                                                    screen.value = ScreenNav.ReloadScreen.route
                                                }
                                            }
                                    } else {
                                        deleteConfirmation.value = true
                                        showToast("Press Again to Delete")
                                    }
                                } else {
                                    amount.value--
                                }
                            },
                        ) {
                            Icon(
                                Icons.Filled.Delete,
                                tint = MaterialTheme.colors.onSecondary,
                                contentDescription = null
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(customSpacing.small))


                }


                /*********** SAVE ***********/
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(onClick = {
                            currentLine.value!!.amount = amount.value
                            mainViewModel.updateProductShopping(currentLine.value!!) {
                                openPopUpEdition.value = false
                                isSaved.value = true
                                rememberScreen = ScreenNav.ShoppingCartScreen.route
                                screen.value = ScreenNav.ReloadScreen.route
                            }

                        })
                ) {

                    Text(
                        text = "SAVE",
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