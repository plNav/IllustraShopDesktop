package view.admin.composables

import ScreenNav
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.rememberWindowState
import data.model.order.order_response
import view.admin.AdminViewModel
import theme.Spacing
import theme.SurfaceAlmostBlack
import utils.*
import view.main.composables.PopUpComment
import java.util.*

@Composable
fun PopUp_EditOrder(
    isEditOpen: MutableState<Boolean>,
    orderSelected: MutableState<order_response?>,
    adminViewModel: AdminViewModel,
    verticalGradient: Brush,
    screen : MutableState<String>,
    customSpacing: Spacing,
    isAdmin: Boolean
) {

    val openPopUpComment = remember { mutableStateOf(false) }
    val comment = remember { mutableStateOf(orderSelected.value!!.comments) }
    val filter = remember { mutableStateOf(orderSelected.value!!.status) }

    Window(
        onCloseRequest = { isEditOpen.value = false },
        state = rememberWindowState(
            width = 500.dp,
            height = 1200.dp,
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
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(brush = verticalGradient)
                ) {

                    /************ TITLE ************/
                    Text(
                        text = "${orderSelected.value!!.user.username} - ${orderSelected.value!!.total} €",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.body1.copy(color = Color.White),
                        modifier = Modifier
                            .fillMaxWidth(0.85f)
                            .clip(RoundedCornerShape(4.dp))
                            .background(Color.Transparent)
                            .padding(12.dp)
                    )

                    /************ CLOSE ************/
                    IconButton(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(Color.Transparent),
                        onClick = { isEditOpen.value = false },
                    ) {
                        Icon(
                            Icons.Filled.Close,
                            tint = MaterialTheme.colors.onSecondary,
                            contentDescription = null
                        )
                    }
                }

                Spacer(
                    modifier = Modifier.height(
                        customSpacing.mediumSmall
                    )
                )

                /************ USER INFO ************/

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(customSpacing.small)
                ) {
                    /************ COMMENT ************/
                    if (orderSelected.value!!.comments.isNotEmpty()) {
                        TextField(
                            enabled = false,
                            value = "Comments: \n${orderSelected.value!!.comments}",
                            onValueChange = { },
                            maxLines = 5,
                            textStyle = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold),
                            modifier = Modifier
                                .padding(20.dp)
                                .height(200.dp)
                                .fillMaxWidth()
                        )
                    }
                    Spacer(modifier = Modifier.height(customSpacing.small))

                    Text(
                        style = MaterialTheme.typography.body2,
                        text = """
                            Name         : ${orderSelected.value!!.user.name}
                            Last Name    : ${orderSelected.value!!.user.last_name}
                            Email        : ${orderSelected.value!!.user.email}
                            Address      : ${orderSelected.value!!.user.address}
                            Country      : ${orderSelected.value!!.user.country} 
                            PostalCode   : ${orderSelected.value!!.user.postal_code} 
                            Phone        : ${orderSelected.value!!.user.phone}
                    """.trimIndent()
                    )

                    Spacer(modifier = Modifier.height(customSpacing.small))

                    Text(
                        style = MaterialTheme.typography.body1,
                        text = "Products (${orderSelected.value!!.products.count()}) :"
                    )

                    Spacer(modifier = Modifier.height(customSpacing.small))

                }

                /************ PRODUCTS INFO ************/

                LazyColumn(Modifier.fillMaxHeight(if (isAdmin) 0.3f else 0.6f)) {
                    itemsIndexed(orderSelected.value!!.products) { _, product ->
                        Card(Modifier.padding(customSpacing.small)) {
                            Row(
                                horizontalArrangement = Arrangement.Start,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(customSpacing.small)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .height(customSpacing.extraLarge)
                                        .width(customSpacing.extraLarge)
                                ) {
                                    AsyncImage(
                                        load = { loadImageBitmap("$URL_HEAD_IMAGES${product.image}") },
                                        painterFor = { remember { BitmapPainter(it) } },
                                        contentDescription = "Sample",
                                        contentScale = ContentScale.Crop
                                    )
                                }

                                Text(
                                    modifier = Modifier.padding(customSpacing.small),
                                    text = """
                                        Product : ${product.name}
                                        Cant.   : ${product.amount}
                            """.trimIndent()
                                )
                            }
                        }
                    }
                }


                /************ OPTIONS ************/


                /************ OPTIONS ************/

                Card(
                    modifier = Modifier.padding(customSpacing.small),
                    border = BorderStroke(2.dp, MaterialTheme.colors.primaryVariant)
                ) {
                    Column(
                        verticalArrangement = Arrangement.SpaceEvenly,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(customSpacing.small)
                    ) {


                        if (isAdmin) {
                            Button(
                                modifier = Modifier
                                    .height(customSpacing.superLarge)
                                    .fillMaxWidth(),
                                colors =
                                if (filter.value == PENDING) ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
                                else ButtonDefaults.buttonColors(backgroundColor = SurfaceAlmostBlack),


                                onClick = {
                                    filter.value = PENDING
                                },
                            ) {
                                Text(text = PENDING, color = Color.White)
                            }

                            Spacer(modifier = Modifier.height(customSpacing.small))

                            Button(
                                modifier = Modifier
                                    .height(customSpacing.superLarge)
                                    .fillMaxWidth(),
                                colors =
                                if (filter.value == SENT) ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
                                else ButtonDefaults.buttonColors(backgroundColor = SurfaceAlmostBlack),
                                onClick = {
                                    filter.value = SENT
                                },
                            ) {
                                Text(text = SENT, color = Color.White)
                            }

                            Spacer(modifier = Modifier.height(customSpacing.small))

                            Button(
                                enabled = isAdmin,
                                modifier = Modifier
                                    .height(customSpacing.superLarge)
                                    .fillMaxWidth(),
                                colors =
                                if (filter.value == ENDED) ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
                                else ButtonDefaults.buttonColors(backgroundColor = SurfaceAlmostBlack),
                                onClick = {
                                    filter.value = ENDED
                                },
                            ) {
                                Icon(Icons.Filled.Done, contentDescription = null, tint = Color.White)
                            }
                        } else {
                            Text(
                                text = when (orderSelected.value!!.status) {
                                    PENDING -> PENDING
                                    SENT -> SENT
                                    ENDED -> ENDED
                                    else -> ""
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(customSpacing.small))

                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        //.fillMaxSize()
                        .padding(horizontal = customSpacing.mediumSmall)

                ) {

                    /************ OK ************/
                    Text(
                        text = "OK",
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(4.dp))
                            .background(brush = verticalGradient)
                            .padding(12.dp)
                            .clickable(onClick = {
                                if (isAdmin) {
                                    orderSelected.value!!.status = filter.value
                                    orderSelected.value!!.date_arrive = Date();
                                    adminViewModel.updateOrder(order = orderSelected.value!!) {
                                        adminViewModel.getOrders {
                                            isEditOpen.value = false
                                            allOrders = adminViewModel.allOrdersResponse as MutableList<order_response>
                                            screen.value = ScreenNav.OrderScreen.route
                                        }
                                    }
                                } else {
                                    adminViewModel.getUserOrders(userId = userSelected!!._id) {
                                        screen.value = ScreenNav.OrderScreen.route
                                    }
                                }
                            }),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.body1.copy(color = Color.White)
                    )

                    Spacer(
                        modifier = Modifier.height(
                            customSpacing.mediumMedium
                        )
                    )
                }
            }
        }
    }
}